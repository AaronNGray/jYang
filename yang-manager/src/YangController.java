import org.w3c.dom.*;
import org.w3c.dom.Node;

import datatree.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import jyang.parser.*;

public class YangController {

	Pattern empty = Pattern.compile("\\s*");

	private DataNode model = null;

	public YangView3D createView3D(Document doc,
			Hashtable<String, YANG_Specification> specs) {
		createModel(doc, specs);
		YangView3D view = new YangView3D(model);
		return view;
	}

	public YangView2D createView2D(Document doc,
			Hashtable<String, YANG_Specification> specs) {
		createModel(doc, specs);
		YangView2D view = new YangView2D(model);
		return view;
	}

	private void createModel(Document doc,
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
				if ((b instanceof YANG_Container || b instanceof YANG_List
						|| b instanceof YANG_LeafList || b instanceof YANG_Leaf)
						&& b.getBody().equals(root)) {
					found = true;
					Vector<String> builded = new Vector<String>();
					Hashtable<String, YangTreeNode> importedtreenodes = new Hashtable<String, YangTreeNode>();
					builded.add(b.getBody());

					YangTreeNode tn = s.buildTreeNode(new String[0], builded,
							importedtreenodes);
					model = walk(docelt, tn);

				}
			}
		}
		if (!found)
			System.out.println("No spec found " + root);
	}

	private DataNode walk(Node node, YangTreeNode tn) {
		YANG_Body b = tn.getNode();
		if (b instanceof YANG_Leaf) {
			System.out.println("leaf " + b.getBody());
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
					System.err
							.println("leaf " + b.getBody() + " without value");
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
			System.out.println("container " + cn.getName());

			/**
			 * First check if each node in the response has a yang definition
			 */
			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node n = nl.item(i);
				if (n instanceof Element) {

					for (Enumeration<YangTreeNode> eytn = tn.getChilds()
							.elements(); eytn.hasMoreElements();) {

						YangTreeNode ytn = eytn.nextElement();
						YANG_Body b2 = ytn.getNode();
						if (b2.getBody().equals(n.getNodeName()))
							cn.addContent(walk(n, ytn));
					}
				}
			}
			for (Enumeration<DataNode> edn = lookForDefaultLeaf(nl, ycont
					.getDataDefs().elements()); edn.hasMoreElements();) {
				cn.addContent(edn.nextElement());
			}
			return cn;
			/**
			 * List node
			 */
		} else if (b instanceof YANG_List) {
			YANG_List ylist = (YANG_List) b;
			System.out.println("list " + ylist.getBody());
			int index;
			String keyname = "";
			String keyvalue = "";

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
					for (Enumeration<YangTreeNode> eytn = tn.getChilds()
							.elements(); eytn.hasMoreElements();) {

						YangTreeNode ytn = eytn.nextElement();
						YANG_Body b2 = ytn.getNode();
						if (b2.getBody().equals(n.getNodeName())) {
							lentry.add(walk(n, ytn));
							if (b2.getBody().equals(keyname)) {
								keyvalue = n.getTextContent();
								ln.setKey(keyvalue);
							}
						}
					}

				}
			}
			for (Enumeration<DataNode> edn = lookForDefaultLeaf(nl, ylist
					.getDataDefs().elements()); edn.hasMoreElements();) {
				lentry.add(edn.nextElement());
			}
			for (Enumeration<DataNode> edn = lookForChoiceNode(nl, ylist
					.getDataDefs().elements(), tn); edn.hasMoreElements();) {
				lentry.add(edn.nextElement());
			}
			ln.setEntry(lentry);
			return ln;
		} else if (b instanceof YANG_LeafList) {
			System.out.println("LeafList " + b.getBody());
			System.out.println(node.getTextContent());
			YANG_LeafList yll = (YANG_LeafList) b;
			LeafListNode lln = new LeafListNode(yll);
			lln.setValue(node.getTextContent());

			return lln;

		}

		return null;
	}

	private Enumeration<DataNode> lookForChoiceNode(NodeList nl,
			Enumeration<YANG_DataDef> eddef, YangTreeNode tn) {

		Vector<DataNode> result = new Vector<DataNode>();

		while (eddef.hasMoreElements()) {

			YANG_DataDef ddef = eddef.nextElement();
			if (ddef instanceof YANG_Choice) {
				YANG_Choice choice = (YANG_Choice) ddef;
				boolean foundcase = false;
				for (Enumeration<YangTreeNode> eytn = tn.getChilds().elements(); eytn
						.hasMoreElements();) {
					YangTreeNode ytn = eytn.nextElement();
					if (ytn.getNode().getBody().equals(choice.getBody()))
						for (int i = 0; i < nl.getLength(); i++) {
							Node n = nl.item(i);
							if (n instanceof Element)
								for (Enumeration<YangTreeNode> ceytn = ytn
										.getChilds().elements(); ceytn
										.hasMoreElements();) {
									YangTreeNode cytn = ceytn.nextElement();
									if (n.getNodeName().equals(
											cytn.getNode().getBody())) {
										result.add(walk(n, cytn));
									}
								}

						}

				}
			}
		}

		return result.elements();
	}

	/**
	 * Check if a leaf is not present and put its default value (or the default
	 * value of the type of the leaf) if there is such value, else the leaf is
	 * omitted
	 */
	private Enumeration<DataNode> lookForDefaultLeaf(NodeList nl,
			Enumeration<YANG_DataDef> eddef) {

		Vector<DataNode> result = new Vector<DataNode>();
		boolean leafmandatory = false;
		while (eddef.hasMoreElements()) {

			YANG_DataDef ddef = eddef.nextElement();

			if (ddef instanceof YANG_Leaf) {

				YANG_Leaf leaf = (YANG_Leaf) ddef;

				if (leaf.getMandatory() == null)
					leafmandatory = false;
				else if (leaf.getMandatory().getMandatory().equals("false"))
					leafmandatory = false;
				else
					leafmandatory = true;

				String leafdefault = null;
				if (leaf.getDefault() != null)
					leafdefault = leaf.getDefault().getDefault();

				String typeleafdefault = null;
				if (leaf.getType().getTypedef() != null)
					if (leaf.getType().getTypedef().getDefault() != null)
						typeleafdefault = leaf.getType().getTypedef()
								.getDefault().getDefault();

				boolean found = false;
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n instanceof Element) {
						if (n.getNodeName().equals(ddef.getBody())) {
							found = true;
						}
					}
				}
				if (!found) // we don't find a node with the name of the leaf
					if (leafmandatory) {
						System.err
								.println("Error : a mandatory leaf is not present");
					} else {
						if (leafdefault != null) {
							LeafNode ln = new LeafNode(leaf, leafdefault);
							result.add(ln);
						} else if (typeleafdefault != null) {
							LeafNode ln = new LeafNode(leaf, typeleafdefault);
							result.add(ln);
						}
					}
			}
		}
		return result.elements();
	}

	public void request(Object[] paths) {

		XMLStreamWriter xsw;
		try {
			xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(
					System.out);

			int nbCont = 0;
			int nbList = 0;
			int nbLeafList = 0;
			int nbLeaf = 0;
			
			xsw.writeStartDocument();
			xsw.writeCharacters("\n");
			for (int i = 0; i < paths.length; i++) {
				Object n = paths[i];
				if (n instanceof ContainerNode) {
					nbCont++;
					ContainerNode cn = (ContainerNode) n;
					xsw.writeStartElement(cn.getName());
					xsw.writeCharacters("\n");
				} else if (n instanceof ListNode){
					nbList++;
					ListNode ln = (ListNode)n;
					xsw.writeStartElement(ln.getName());
					xsw.writeCharacters("\n");
				} else if (n instanceof LeafListNode) {
					nbLeafList++;
					LeafListNode lln = (LeafListNode)n;
					xsw.writeStartElement(lln.getName());
					xsw.writeCharacters("\n");
				} else if (n instanceof LeafNode) {
					LeafNode lf = (LeafNode) n;
					nbLeaf++;
					xsw.writeStartElement(lf.getName());
					xsw.writeCharacters("\n");
				}
			}
			for (int f = 0; f < nbCont + nbList + nbLeafList + nbLeaf; f++){
				xsw.writeEndElement();
				xsw.writeCharacters("\n");
			}
			xsw.flush();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void oneLevel(XMLStreamWriter xsw){
		
	}

}
