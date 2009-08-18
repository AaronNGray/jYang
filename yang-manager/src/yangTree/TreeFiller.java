package yangTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import yangTree.attributes.NameSpace;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.attributes.builtinTypes.EmptyType;
import yangTree.attributes.builtinTypes.PathSensitiveType;
import yangTree.nodes.CaseNode;
import yangTree.nodes.CheckedYangNode;
import yangTree.nodes.ChoiceNode;
import yangTree.nodes.ListedYangNode;
import yangTree.nodes.YangLeaf;
import yangTree.nodes.YangNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;

/**
 * Class used to create a tree filled with data, using a specification tree and
 * a XML Document consistent with a standard netConf reply.
 */
public class TreeFiller {

	private static final NodeDescriptor NO_CASE = new NodeDescriptor(-1, 0);

	private static LinkedList<NameSpace> nameSpacePrefixList = new LinkedList<NameSpace>();
	private static Map<LeafNode, String> pendingValues = new HashMap<LeafNode, String>();

	/**
	 * Updates the values of all descendant leaves of a given YangNode.<br>
	 * <b>Warning :</b> use only this method on a tree which has been already
	 * filled.
	 * 
	 * @param tree
	 *            : the tree which descendant leaves values will be updated.
	 * @param xmlDocument
	 *            : the Netconf reply XML document.
	 * @param path
	 *            : the <code>YangTreePath</code> of <code>YangNode</code>
	 *            <b>tree</b>.
	 */
	public static void UpdateTree(YangNode tree, Document xmlDocument, YangTreePath path) throws NodeNoLongerExistsException {

		Node currentXmlNode = null;

		Node root = xmlDocument.getFirstChild();
		if (!root.getNodeName().equals("rpc-reply")) {
			throw new NetconfReplyMalformedException("Expected \"rpc-reply\" node not present");
		}
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("data")) {
				NodeList nodeList = list.item(i).getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					if (!nodeList.item(j).getNodeName().equals("#text")) {
						currentXmlNode = nodeList.item(j);
					}
				}
			}
		}

		if (currentXmlNode == null)
			throw new NetconfReplyMalformedException("Expected \"data\" node not present");

		Node nodeToUpdate = updateTreeEngine(tree.getName(), currentXmlNode);

		if (nodeToUpdate == null) {
			if (tree instanceof YangLeaf) {
				((YangLeaf) tree).setValue(null);
			} else if (tree instanceof YangInnerNode) {
				throw new NodeNoLongerExistsException();
			}
			return;
		}

		YangNode newtree = fillTreeEngine(tree, nodeToUpdate, path);

		if (tree instanceof YangLeaf) {
			((YangLeaf) tree).setValue(((YangLeaf) newtree).getValue());
		} else if (tree instanceof YangInnerNode) {
			((YangInnerNode) tree).setNodes(((YangInnerNode) newtree).getDescendantNodes());
		}

	}

	public static Node updateTreeEngine(String treeName, Node currentNode) {
		
		if (currentNode.getNodeName().equals(treeName))
			return currentNode;
		
		Node result = null;
		LinkedList<Node> children = new LinkedList<Node>();
		
		NodeList nodeList = currentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			children.add(nodeList.item(i));
		}
		NamedNodeMap attrChildren = currentNode.getAttributes();
		for (int i = 0; i < attrChildren.getLength(); i++) {
			if (!attrChildren.item(i).getNodeName().contains("xmlns")) {
				children.add(attrChildren.item(i));
			}
		}
		
		for (Node child : children){
			Node nextNode = updateTreeEngine(treeName, child);
			if (nextNode != null)
				result = nextNode;
		}
		return result;
	}

	/**
	 * Creates the filled tree.
	 * 
	 * @param dataNode
	 *            : the root of an empty specification tree.
	 * @param xmlDocument
	 *            : the Netconf reply XML Document.
	 * @param path
	 *            : the path of the RootNode of the final filled tree.
	 * @return the filled tree.
	 */
	public static YangNode fillTree(YangNode dataNode, Document xmlDocument, YangTreePath path) {

		Node root = xmlDocument.getFirstChild();
		if (!root.getNodeName().equals("rpc-reply")) {
			throw new NetconfReplyMalformedException("Expected \"rpc-reply\" node not present");
		}
		NamedNodeMap map = root.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node attr = map.item(i);
			String[] attrName = attr.getNodeName().split(":");
			if (attrName.length == 2 && attrName[0].equalsIgnoreCase("xmlns")) {
				nameSpacePrefixList.add(new NameSpace(attrName[1], attr.getNodeValue()));
			}
		}
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("data")) {
				NodeList nodeList = list.item(i).getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					if (!nodeList.item(j).getNodeName().equals("#text")) {
						YangNode result = fillTreeEngine(dataNode, nodeList.item(j), path);

						return result;
					}
				}
			}
		}
		throw new NetconfReplyMalformedException("Expected \"data\" node not present");

	}

	private static YangNode fillTreeEngine(YangNode dataNode, Node xmlNode, YangTreePath currentPath) {

		YangTreePath path = currentPath.clone();
		String[] nodeName = xmlNode.getNodeName().split(":");
		String name = nodeName[0];
		NameSpace namespace = null;

		// Look for namespace prefix
		if (nodeName.length == 2) {
			namespace = lookForNameSpace(name);
			name = nodeName[1];
		}

		// Look for namespace in attributes
		if (xmlNode.hasAttributes()) {
			NamedNodeMap attrMap = xmlNode.getAttributes();
			for (int i = 0; i < attrMap.getLength(); i++) {
				Node attr = attrMap.item(i);
				String[] attrName = attr.getNodeName().split(":");
				if (attrName[0].equalsIgnoreCase("xmlns")) {
					if (attrName.length == 1) {
						namespace = new NameSpace(attr.getNodeValue());
					} else if (attrName.length == 2) {
						namespace = new NameSpace(attrName[1], attr.getNodeValue());
						nameSpacePrefixList.add(namespace);
					}
				}
			}
		}

		YangNode result = null;

		// Handle the leaves cases
		if (dataNode instanceof LeafNode) {

			LeafNode filledNode = ((LeafNode) dataNode).cloneBody();
			path.appendChild(filledNode.getName());

			String value = null;
			if (!(filledNode.getType().getBuiltinType() instanceof EmptyType)) {
				if (xmlNode.hasChildNodes()) {
					value = xmlNode.getFirstChild().getNodeValue();
				} else if (xmlNode.hasAttributes()) {
					value = xmlNode.getAttributes().item(0).getNodeValue();
				} else {
					value = null;
				}
				// PathSensitiveTypes have to be filled in the end.
				if (filledNode.getType().getBuiltinType() instanceof PathSensitiveType) {
					((PathSensitiveType) filledNode.getType().getBuiltinType()).setPath(path);
					pendingValues.put(filledNode, value);
				} else {
					filledNode.setValue(value);
				}
			}
			result = filledNode;

		} else if (dataNode instanceof LeafListNode) {

			LeafListNode filledNode = ((LeafListNode) dataNode).cloneBody();
			String value = null;
			if (xmlNode.hasChildNodes()) {
				value = xmlNode.getFirstChild().getNodeValue();
			} else if (xmlNode.hasAttributes()) {
				value = xmlNode.getAttributes().item(0).getNodeValue();
			} else {
				return null;
			}
			filledNode.setValue(value);
			result = filledNode;

			// Handle the tree cases
		} else if (dataNode instanceof YangInnerNode) {

			YangInnerNode tree = (YangInnerNode) dataNode;
			YangInnerNode treeResult = tree.cloneBody();
			path.appendChild(treeResult.getName());

			// build the list of nodes that can match a child of xmlNode, with
			// their associated choice & case (if exists).
			Map<YangNode, NodeDescriptor> eligibleNodes = new LinkedHashMap<YangNode, NodeDescriptor>();
			int choiceIndex = 0;
			for (YangNode node : tree.getDescendantNodes()) {
				if (node instanceof ChoiceNode) {
					choiceIndex++;
					int caseIndex = 0;
					for (YangNode caseNode : ((ChoiceNode) node).getDescendantNodes()) {
						caseIndex++;
						for (YangNode caseNodeChild : ((CaseNode) caseNode).getDescendantNodes()) {
							eligibleNodes.put(caseNodeChild, new NodeDescriptor(choiceIndex, caseIndex));
						}
					}
				} else {
					eligibleNodes.put(node, NO_CASE);
				}
			}

			// build the list of children of xmlNode
			LinkedList<Node> xmlChildren = new LinkedList<Node>();
			NodeList regularChildren = xmlNode.getChildNodes();
			for (int i = 0; i < regularChildren.getLength(); i++) {
				if (!regularChildren.item(i).getNodeName().equals("#text")) {
					xmlChildren.add(regularChildren.item(i));
				}
			}
			NamedNodeMap attrChildren = xmlNode.getAttributes();
			for (int i = 0; i < attrChildren.getLength(); i++) {
				if (!attrChildren.item(i).getNodeName().contains("xmlns")) {
					xmlChildren.add(attrChildren.item(i));
				}
			}

			// solve all the choices.
			LinkedList<YangNode> yangChildren = new LinkedList<YangNode>();
			LinkedList<Integer> alreadySolvedNodes = new LinkedList<Integer>();
 			for (YangNode node : eligibleNodes.keySet()){
				if (eligibleNodes.get(node).equals(NO_CASE)){
					yangChildren.add(node);
				} else {
					int choice = eligibleNodes.get(node).choiceIndex;
					if (!alreadySolvedNodes.contains(choice)){
						yangChildren.addAll(caseChooser(choice, eligibleNodes, xmlChildren));
						alreadySolvedNodes.add(choice);
					}
				}
			}

			// Create a ListBuilder for each ListedYangNode contained in the
			// current node :
			Map<String, ListBuilder> listBuilders = new HashMap<String, ListBuilder>();
			for (YangNode n : yangChildren) {
				if (n instanceof ListedYangNode)
					listBuilders.put(n.getName(), new ListBuilder((ListedYangNode) n, n.getNodeType() + " \"" + n.getName() + "\""));
			}


			// At last, search for matching between xmlChildren and
			// dataNodeChildren :
			for (YangNode nodeChild : yangChildren) {
				boolean matchFound = false;
				for (Node xmlChild : xmlChildren) {
					String[] xmlChildName = xmlChild.getNodeName().split(":");
					if (xmlChildName[xmlChildName.length - 1].equals(nodeChild.getName())) {
						YangNode newChild = fillTreeEngine(nodeChild, xmlChild, path);
						matchFound = true;
						if (newChild != null) {
							// Special handling if the child is a ListedYangNode
							// :
							if (newChild instanceof ListedYangNode) {
								listBuilders.get(newChild.getName()).addOccurrence((ListedYangNode) newChild);
							} else {
								treeResult.addContent(newChild);
							}
						}
					}
				}
				// Unmatched parts of the tree will be built, but not filled.
				if (!matchFound) {
					YangNode newChild = buildEmptyTree(nodeChild);
					if (newChild != null) {
						treeResult.addContent(newChild);
					}
				}
			}

			// Now, the ListedYangNodes can be added :
			for (String key : listBuilders.keySet()) {
				((CheckedYangNode) treeResult).getCheck().addUnitCheck(listBuilders.get(key).check());
				for (ListedYangNode n : listBuilders.get(key).occurrences) {
					treeResult.addContent((YangNode) n);
				}
			}

			result = treeResult;

		}

		if (namespace != null) {
			result.setNameSpace(namespace);
		}
		return result;

	}

	/*
	 * When the xml no longer define values for a part of the tree, this part is
	 * built matching the specifications, and not filled with values.
	 */
	private static YangNode buildEmptyTree(YangNode dataNode) {

		// Handle the leaves cases
		if (dataNode instanceof LeafNode) {

			LeafNode filledNode = ((LeafNode) dataNode).cloneBody();
			filledNode.setValue(null);
			return filledNode;

		} else if (dataNode instanceof LeafListNode) {

			return null;

			// Handle the tree cases
		} else if (dataNode instanceof YangInnerNode) {

			if (dataNode instanceof ChoiceNode || dataNode instanceof ListNode) {
				return null;
			}

			YangInnerNode filledNode = ((YangInnerNode) dataNode).cloneBody();
			for (YangNode node : ((YangInnerNode) dataNode).getDescendantNodes()) {
				YangNode child = buildEmptyTree(node);
				if (child != null) {
					filledNode.addContent(child);
				}
			}
			return filledNode;
		}
		return null;
	}

	private static NameSpace lookForNameSpace(String prefix) {
		for (NameSpace ns : nameSpacePrefixList) {
			if (ns.getPrefix().equals(prefix)) {
				return ns;
			}
		}
		return null;
	}

	/*
	 * Walks through the list xmlChildren, searching for a match with an element
	 * of a caseNode. Returns the nodes of the matching case.
	 */
	private static LinkedList<YangNode> caseChooser(int choice, Map<YangNode, NodeDescriptor> eligibleNodes, LinkedList<Node> xmlChildren) {
		LinkedList<YangNode> caseNodes = new LinkedList<YangNode>();

			for (YangNode nodeChild : eligibleNodes.keySet()) {
				for (Node xmlChild : xmlChildren) {

					if (xmlChild.getNodeName().equals(nodeChild.getName())) {

						/*
						 * If the matched node is an element of a caseNode, this
						 * case is kept, and all other cases are removed from
						 * the eligible nodes
						 */
						if (eligibleNodes.get(nodeChild).choiceIndex == choice) {

							Integer caseKept = eligibleNodes.get(nodeChild).caseIndex;
							for (YangNode node : eligibleNodes.keySet()) {
								if (eligibleNodes.get(node).equals(new NodeDescriptor(choice, caseKept))) {
									caseNodes.add(node);
								}
							}
							return caseNodes;
						}

					}
				}
			}
		return caseNodes;
	}

	/**
	 * Fills the pending values. This method must be called after the tree have
	 * been <u>totally</u> filled with the other values.
	 */
	public static void setPendingValues() {
		for (LeafNode leaf : TreeFiller.pendingValues.keySet()) {
			leaf.setValue(TreeFiller.pendingValues.get(leaf));
		}
	}

	/**
	 * Special class used only to describe nodes for the case chooser, or to
	 * check if a node has been matched.
	 */
	private static class NodeDescriptor {

		public int choiceIndex;
		public int caseIndex;

		public NodeDescriptor(int choiceIndex, int caseIndex) {
			this.caseIndex = caseIndex;
			this.choiceIndex = choiceIndex;
		}

		public boolean equals(NodeDescriptor other) {
			return other.caseIndex == caseIndex && other.choiceIndex == choiceIndex;
		}

	}

	/**
	 * Special class used to sort and check a set of occurrences of a
	 * ListedYangNode.
	 */
	private static class ListBuilder {

		public LinkedList<ListedYangNode> occurrences;
		public String listName;
		public int MinElements;
		public int MaxElements;

		public ListBuilder(ListedYangNode listedNode, String listName) {
			occurrences = new LinkedList<ListedYangNode>();
			this.listName = listName;
			MinElements = listedNode.getMinElements();
			MaxElements = listedNode.getMaxElements();
		}

		public void addOccurrence(ListedYangNode occurrence) {
			int i = 0;
			while (i < occurrences.size() && occurrences.get(i).compareTo(occurrence) < 0) {
				i++;
			}
			if (i == occurrences.size()) {
				occurrences.addLast(occurrence);
			} else {
				if (occurrences.get(i).compareTo(occurrence) == 0)
					occurrence.getCheck().addUnitCheck(new UnitValueCheck("Duplicated key"));
				occurrences.add(i, occurrence);
			}
		}

		public UnitValueCheck check() {
			if (occurrences.size() < MinElements)
				return new UnitValueCheck("Too less elements of " + listName + " (min = " + MinElements + ").");
			if (occurrences.size() > MaxElements)
				return new UnitValueCheck("Too many elements of " + listName + " (max = " + MaxElements + ").");
			return UnitValueCheck.checkOK();
		}

	}

}
