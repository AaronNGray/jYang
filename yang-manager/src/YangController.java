import org.w3c.dom.*;
import org.w3c.dom.Node;

import datatree.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import jyang.parser.*;

import datatree.*;

public class YangController {

	Pattern empty = Pattern.compile("\\s*");

	private DataNode model = null;

	public YangView createView(Document doc,
			Hashtable<String, YANG_Specification> specs) {
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
					model = walk("", docelt, b);
				}
			}
		}
		if (!found)
			System.out.println("No spec found " + root);
		
		YangView view = new YangView(model);

		return view;
	}

	private DataNode walk(String p, Node node, YANG_Body b) {

		if (b instanceof YANG_Leaf) {
			LeafNode ln = null;
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (!(n instanceof Element)) {
					Matcher m = empty.matcher(n.getTextContent());
					if (!m.matches()) {
						ln = new LeafNode((YANG_Leaf) b, n.getTextContent());
						return ln;
					}
				} else {
					System.err.println(p + "leaf " + b.getBody()
							+ " without value");
					return null;
				}

			}
			if (nl.getLength() == 0)
				return new LeafNode((YANG_Leaf) b, "");

		} else if (b instanceof YANG_Container) {

			YANG_Container ycont = (YANG_Container) b;
			ContainerNode cn = new ContainerNode(ycont);

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
									cn.addContent(walk(p + " ", n, ddefused));
								}
							}
						} else if (n.getNodeName().equals(ddef.getBody())) {
							cn.addContent(walk(p + " ", n, ddef));
						}
					}

				}
			}
			return cn;
		} else if (b instanceof YANG_List) {
			YANG_List ylist = (YANG_List) b;
			int index;
			String keyname = "";
			String keyvalue = "";
			int nbcol = ylist.getDataDefs().size();

			if (ylist.getKey() == null)
				index = 0;
			else {
				YANG_Key k = ylist.getKey();
				keyname = k.getKey();
			}

			ListNode ln = new ListNode(ylist);

			Vector<DataNode> lentry = new Vector<DataNode>();
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (n instanceof Element) {
					boolean foundElt = false;
					for (Enumeration<YANG_DataDef> eb = ylist.getDataDefs()
							.elements(); eb.hasMoreElements() && !foundElt;) {

						YANG_DataDef ddef = eb.nextElement();
						if (ddef instanceof YANG_Uses) {
							YANG_Grouping g = ((YANG_Uses) ddef).getGrouping();
							for (Enumeration<YANG_DataDef> eddef = g
									.getDataDefs().elements(); eddef
									.hasMoreElements();) {
								YANG_DataDef ddefused = eddef.nextElement();
								lentry.add(walk(p + " ", n,
										ddefused));
							}
						}
						if (ddef.getBody().equals(n.getNodeName())) {
							nbcol--;
							foundElt = true;
							if (ddef instanceof YANG_Container
									|| ddef instanceof YANG_List) {
								DataNode dn = walk(p + " ", n, ddef);
								lentry.add(dn);

							} else if (ddef instanceof YANG_Leaf) {
								lentry.add( walk(p + " ", n,
										ddef));
							}

							if (ddef.getBody().equals(keyname)) {
								NodeList nlk = n.getChildNodes();
								boolean found = false;
								for (int k = 0; k < nlk.getLength() && !found; k++) {
									Node nk = nlk.item(k);
									if (!(nk instanceof Element)) {
										Matcher m = empty.matcher(nk
												.getTextContent());
										if (!m.matches()) {
											keyvalue = nk.getTextContent();
											ln.setKey(keyvalue);
											found = true;
										}
									} else {
										System.err.println(p + " key leaf "
												+ nk.getTextContent()
												+ " without value");
									}
								}
								if (nlk.getLength() == 0)
									System.err.println(p + " key leaf "
											+ b.getBody() + " without value");
							}
						}
					}
				}
			}
			if (nbcol == 0) {
				ln.setEntry(lentry);
				nbcol = ylist.getDataDefs().size();
			}
			return ln;
		}
		return null;
	}

	
}
