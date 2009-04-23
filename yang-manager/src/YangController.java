import org.w3c.dom.*;
import org.w3c.dom.Node;

import datatree.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import jyang.*;

import datatree.*;

public class YangController {

	Pattern empty = Pattern.compile("\\s*");

	private Hashtable<String, DataTree> models = new Hashtable<String, DataTree>();

	public YangView createView(Document doc,
			Hashtable<String, YANG_Specification> specs) {
		YangView view = new YangView();
		DataNode rootnode = null;
		// YangModel model = new YangModel();

		Element docelt = doc.getDocumentElement();
		String root = docelt.getNodeName();
		boolean found = false;
		for (Enumeration<String> es = specs.keys(); es.hasMoreElements()
				&& !found;) {
			YANG_Specification s = specs.get(es.nextElement());
			for (Enumeration<YANG_Body> eb = s.getBodies().elements(); eb
					.hasMoreElements()
					&& !found;) {
				YANG_Body b = eb.nextElement();
				if (b instanceof YANG_Container && b.getBody().equals(root)) {
					found = true;
					rootnode = walk(docelt, b);
				}
			}
		}
		if (!found)
			System.out.println("No spec found " + root);

		return view;
	}

	private DataNode walk2(Node node, YANG_Body b) {
		System.out.println(node.getNodeName());
		NodeList nl = node.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (!(n instanceof Element)) {
				Matcher m = empty.matcher(n.getTextContent());
				if (!m.matches()) {
					System.out.println(n.getTextContent());
				}
			} else
				walk2(n, b);
		}
		return null;

	}

	private DataNode walk(Node node, YANG_Body b) {

		System.out.println("START WALK " + node.getNodeName() + " "
				+ b.getBody());

		if (b instanceof YANG_Leaf) {
			LeafNode ln = null;
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (!(n instanceof Element)) {
					Matcher m = empty.matcher(n.getTextContent());
					if (!m.matches()) {
						ln = new LeafNode((YANG_Leaf) b, n.getTextContent());
						System.out.println("create leaf " + b.getBody()
								+ " with value : " + n.getTextContent());
						return ln;
					}
				} else
					System.err
							.println("leaf " + b.getBody() + " without value");

			}

		} else if (b instanceof YANG_Container) {

			YANG_Container ycont = (YANG_Container) b;
			ContainerNode cn = new ContainerNode(ycont);
			System.out.println("Create container " + ycont.getBody());

			// retrieve attributes of a node
			// don't know what to do with
			/*
			 * NamedNodeMap nnm = node.getAttributes(); for (int i = 0; i <
			 * nnm.getLength(); i++) { Node n = nnm.item(i);
			 * System.out.println(n.getNodeName() + "=" + n.getTextContent()); }
			 */

			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (n instanceof Element) {
					for (Enumeration<YANG_DataDef> eb = ycont.getDataDefs()
							.elements(); eb.hasMoreElements();) {

						YANG_DataDef ddef = eb.nextElement();

						if (ddef instanceof YANG_Uses) {
							YANG_Grouping g = ((YANG_Uses) ddef).getGrouping();
							for (Enumeration<YANG_DataDef> eddef = g
									.getDataDefs().elements(); eddef
									.hasMoreElements();) {
								YANG_DataDef ddefused = eddef.nextElement();
								if (ddefused.getBody().equals(n.getNodeName())) {
									cn.addContent(walk(n, ddefused));
								}
							}
						}else if (n.getNodeName().equals(ddef.getBody())) {
								cn.addContent(walk(n, ddef));
						}
					}
					
				}	
			}
			System.out.println("END WALK container " + cn.getName());
					return cn;
		} else if (b instanceof YANG_List) {

			YANG_List ylist = (YANG_List) b;
			ListNode ln = new ListNode(ylist);
			System.out.println("Create list " + ylist.getBody());

			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (n instanceof Element) {

					Hashtable<String, DataNode> lentry = new Hashtable<String, DataNode>();
					for (Enumeration<YANG_DataDef> eb = ylist.getDataDefs()
							.elements(); eb.hasMoreElements();) {

						YANG_DataDef ddef = eb.nextElement();
						if (ddef instanceof YANG_Uses) {
							YANG_Grouping g = ((YANG_Uses) ddef).getGrouping();
							for (Enumeration<YANG_DataDef> eddef = g
									.getDataDefs().elements(); eddef
									.hasMoreElements();) {
								YANG_DataDef ddefused = eddef.nextElement();
								lentry.put(ddefused.getBody(),
										walk(n, ddefused));
							}
						}
						if (ddef.getBody().equals(n.getNodeName())) {
							if (ddef instanceof YANG_Container
									|| ddef instanceof YANG_List) {
								DataNode dn = walk(n, ddef);
								lentry.put(ddef.getBody(), dn);

							} else if (ddef instanceof YANG_Leaf) {
								lentry.put(ddef.getBody(), walk(n, ddef));
							}
						}
					}// ln.addEntry(ylist.getBody(), lentry);
				}

			}
			System.out.println("END WALK list " + ln.getName());
			return ln;
		} else if (b instanceof YANG_Grouping) {
			YANG_Grouping g = (YANG_Grouping) b;
			for (Enumeration<YANG_DataDef> eddef = g.getDataDefs().elements(); eddef
					.hasMoreElements();) {
				YANG_DataDef ddefused = eddef.nextElement();

			}
		}
		System.out.println("END WALK");
		return null;

	}

	public YangView createView(XMLStreamReader xsr) {
		YangView view = new YangView();
		try {
			if (xsr.getEventType() == XMLStreamReader.START_DOCUMENT) {
				xsr.nextTag();
				while (xsr.getEventType() != XMLStreamReader.END_DOCUMENT) {
					if (xsr.getEventType() == XMLStreamReader.START_ELEMENT) {
						System.out.println(xsr.getLocalName());
						xsr.next();
						System.out.println(xsr.getLocalName());
						xsr.next();
						System.out.println(xsr.getLocalName());
					}
					if (xsr.getEventType() == XMLStreamReader.END_ELEMENT)
						xsr.nextTag();
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return view;
	}
}
