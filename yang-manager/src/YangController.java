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
				if ((b instanceof YANG_Container || b instanceof YANG_List || b instanceof YANG_LeafList)
						&& b.getBody().equals(root)) {
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
		
		/**
		 * Leaf node
		 */
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
			/**
			 * Container node
			 */
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

			/**
			 * First check if each node in the response has a yang definition
			 */
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				boolean found = false;
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
									found = true;
									cn.addContent(walk(p + " ", n, ddefused));
								}
							}
						} else if (n.getNodeName().equals(ddef.getBody())) {
							found = true;
							cn.addContent(walk(p + " ", n, ddef));
						}
					}

				}
			}
			for(Enumeration<DataNode> edn = lookForDefaultLeaf(nl, ycont.getDataDefs()
							.elements()); edn.hasMoreElements();)
				cn.addContent(edn.nextElement());
	
			return cn;
			/**
			 * List node
			 */
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
								lentry.add(walk(p + " ", n, ddefused));
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
								lentry.add(walk(p + " ", n, ddef));
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
			for(Enumeration<DataNode> edn = lookForDefaultLeaf(nl, ylist.getDataDefs()
					.elements()); edn.hasMoreElements();)
				lentry.add(edn.nextElement());

			ln.setEntry(lentry);
			nbcol = ylist.getDataDefs().size();
			return ln;
		}
		return null;
	}
		
		/**
		 * Check if a leaf is not present and put its
		 * default value (or the default value of the type of the leaf)
		 * if there is such value, else the leaf is omitted
		 */
	private Enumeration<DataNode> lookForDefaultLeaf(NodeList nl, Enumeration<YANG_DataDef> eddef){
		
		Vector<DataNode> result = new Vector<DataNode>();
		boolean leafmandatory = false;
		while (eddef.hasMoreElements()) {

			YANG_DataDef ddef = eddef.nextElement();

			if (ddef instanceof YANG_Leaf) {
				
				YANG_Leaf leaf = (YANG_Leaf) ddef;
				
				if (leaf.getMandatory() == null)
					leafmandatory = false;
				else if (leaf.getMandatory().getMandatory()
						.equals("false"))
					leafmandatory = false;
				else
					leafmandatory = true;
				
				String leafdefault = null;					
				if (leaf.getDefault() != null)
					leafdefault = leaf.getDefault().getDefault();
				
				String typeleafdefault = null;
				if (leaf.getType().getTypedef() != null)
					if (leaf.getType().getTypedef().getDefault() != null)
						typeleafdefault = leaf.getType().getTypedef().getDefault().getDefault();
				
				boolean found = false;
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n instanceof Element) {
						if (n.getNodeName().equals(ddef.getBody())) {
							found = true;
						}
					}
				}
				if (!found)  // we don't find a node with the name of the leaf
					if (leafmandatory) {
						System.err.println("Error : a mandatory leaf is not present");
					} else {
						if (leafdefault != null){
							LeafNode ln = new LeafNode(leaf, leafdefault);
							result.add(ln);
						} else if (typeleafdefault != null){
							LeafNode ln = new LeafNode(leaf, typeleafdefault);
							result.add(ln);
						}
					}
			}
		}
		return result.elements();
	}

}
