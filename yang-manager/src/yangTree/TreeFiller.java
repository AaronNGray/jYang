package yangTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import yangTree.attributes.NameSpace;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.attributes.builtinTypes.BuiltinType;
import yangTree.attributes.builtinTypes.EmptyType;
import yangTree.attributes.builtinTypes.LeafrefType;
import yangTree.attributes.builtinTypes.PathSensitiveType;
import yangTree.attributes.builtinTypes.UnionType;
import yangTree.nodes.CaseNode;
import yangTree.nodes.CheckedYangNode;
import yangTree.nodes.ChoiceNode;
import yangTree.nodes.ListedYangNode;
import yangTree.nodes.YangNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;

/*
 * Class used to create a tree filled with data, using a specification tree 
 * and a XML Document consistent with a standard netConf reply.
 */
public class TreeFiller {

	private static final NodeDescriptor MATCHED = new NodeDescriptor(-1, 0);
	private static final NodeDescriptor UNMATCHED = new NodeDescriptor(-2, 0);

	private static LinkedList<NameSpace> nameSpacePrefixList = new LinkedList<NameSpace>();
	private static Map<LeafNode, String> pendingValues = new HashMap<LeafNode, String>();

	public static YangNode fillTree(YangNode dataNode, Document xmlDocument,
			YangTreePath path) {

		Node root = xmlDocument.getFirstChild();
		if (!root.getNodeName().equals("rpc-reply")) {
			throw new NetconfReplyMalformedException(
					"Expected \"rpc-reply\" node not present");
		}
		NamedNodeMap map = root.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node attr = map.item(i);
			String[] attrName = attr.getNodeName().split(":");
			if (attrName.length == 2 && attrName[0].equalsIgnoreCase("xmlns")) {
				nameSpacePrefixList.add(new NameSpace(attrName[1], attr
						.getNodeValue()));
			}
		}
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("data")) {
				NodeList nodeList = list.item(i).getChildNodes();
				for (int j = 0; j < nodeList.getLength(); j++) {
					if (!nodeList.item(j).getNodeName().equals("#text")) {
						YangNode result = fillTreeEngine(dataNode, nodeList
								.item(j), path);

						return result;
					}
				}
			}
		}
		throw new NetconfReplyMalformedException(
				"Expected \"data\" node not present");

	}

	private static YangNode fillTreeEngine(YangNode dataNode, Node xmlNode,
			YangTreePath currentPath) {

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
						namespace = new NameSpace(attrName[1], attr
								.getNodeValue());
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
					((PathSensitiveType) filledNode.getType().getBuiltinType())
							.setPath(path);
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
			Map<YangNode, NodeDescriptor> eligibleNodes = new HashMap<YangNode, NodeDescriptor>();
			int choiceIndex = 0;
			for (YangNode node : tree.getNodes()) {
				if (node instanceof ChoiceNode) {
					choiceIndex++;
					int caseIndex = 0;
					for (YangNode caseNode : ((ChoiceNode) node).getNodes()) {
						caseIndex++;
						for (YangNode caseNodeChild : ((CaseNode) caseNode)
								.getNodes()) {
							eligibleNodes.put(caseNodeChild,
									new NodeDescriptor(choiceIndex, caseIndex));
						}
					}
				} else {
					eligibleNodes.put(node, UNMATCHED);
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

			// Case Chooser : see "caseChooser" function for more details.
			for (int choice = 1; choice <= choiceIndex; choice++) {
				eligibleNodes = caseChooser(choice, eligibleNodes, xmlChildren);
			}

			// Create a ListBuilder for each ListedYangNode contained in the
			// current node :
			Map<String, ListBuilder> listBuilders = new HashMap<String, ListBuilder>();
			for (YangNode n : eligibleNodes.keySet()) {
				if (n instanceof ListedYangNode)
					listBuilders.put(n.getName(), new ListBuilder(
							(ListedYangNode) n, n.getNodeType() + " \""
									+ n.getName() + "\""));
			}

			// At last, search for matching between xmlChildren and
			// dataNodeChildren :
			for (Node xmlChild : xmlChildren) {
				for (Map.Entry<YangNode, NodeDescriptor> entry : eligibleNodes
						.entrySet()) {
					YangNode nodeChild = entry.getKey();
					String[] xmlChildName = xmlChild.getNodeName().split(":");
					if (xmlChildName[xmlChildName.length - 1].equals(nodeChild
							.getName())) {
						entry.setValue(MATCHED);
						YangNode newChild = fillTreeEngine(nodeChild, xmlChild,
								path);
						if (newChild != null) {
							// Special handling if the child is a ListedYangNode
							// :
							if (newChild instanceof ListedYangNode) {
								listBuilders.get(newChild.getName())
										.addOccurrence(
												(ListedYangNode) newChild);
							} else {
								treeResult.addContent(newChild);
							}
						}
					}
				}
			}

			// Now, the ListedYangNodes can be added :
			for (String key : listBuilders.keySet()) {
				((CheckedYangNode) treeResult).getCheck().addUnitCheck(
						listBuilders.get(key).check());
				for (ListedYangNode n : listBuilders.get(key).occurrences) {
					treeResult.addContent((YangNode) n);
				}
			}

			// Unmatched parts of the tree will be built, but not filled.
			for (YangNode node : eligibleNodes.keySet()) {
				if (eligibleNodes.get(node).equals(UNMATCHED)) {
					YangNode newChild = buildEmptyTree(node);
					if (newChild != null) {
						treeResult.addContent(newChild);
					}
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
			for (YangNode node : ((YangInnerNode) dataNode).getNodes()) {
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

	/**
	 * Walk through the list xmlChildren, searching for a match with an element
	 * of a caseNode
	 */
	private static Map<YangNode, NodeDescriptor> caseChooser(int choice,
			Map<YangNode, NodeDescriptor> eligibleNodes,
			LinkedList<Node> xmlChildren) {
		Map<YangNode, NodeDescriptor> newEligibleNodes = new HashMap<YangNode, NodeDescriptor>();
		for (Node xmlChild : xmlChildren) {

			for (YangNode nodeChild : eligibleNodes.keySet()) {
				if (xmlChild.getNodeName().equals(nodeChild.getName())) {

					/*
					 * If the matched node is an element of a caseNode, this
					 * case is kept, and all other cases are removed from the
					 * eligible nodes
					 */
					if (eligibleNodes.get(nodeChild).choiceIndex == choice) {

						Integer caseKept = eligibleNodes.get(nodeChild).caseIndex;
						for (YangNode node : eligibleNodes.keySet()) {
							if (eligibleNodes.get(node).equals(
									new NodeDescriptor(choice, caseKept))
									|| eligibleNodes.get(node)
											.equals(UNMATCHED)) {
								newEligibleNodes.put(node, UNMATCHED);
							}
						}
						return newEligibleNodes;
					}

				}
			}
		}
		for (YangNode key : eligibleNodes.keySet()) {
			if (eligibleNodes.get(key).equals(UNMATCHED)) {
				newEligibleNodes.put(key, UNMATCHED);
			}
		}
		return newEligibleNodes;
	}

	/**
	 * Fills the pending values. This method must be called after ALL other
	 * leaves have been filled and ALL the tree have been built.
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
			return other.caseIndex == caseIndex
					&& other.choiceIndex == choiceIndex;
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
			while (i < occurrences.size()
					&& occurrences.get(i).compareTo(occurrence) < 0) {
				i++;
			}
			if (i == occurrences.size()) {
				occurrences.addLast(occurrence);
			} else {
				if (occurrences.get(i).compareTo(occurrence) == 0)
					occurrence.getCheck().addUnitCheck(
							new UnitValueCheck("Duplicated key"));
				occurrences.add(i, occurrence);
			}
		}

		public UnitValueCheck check() {
			if (occurrences.size() < MinElements)
				return new UnitValueCheck("Too less elements of " + listName
						+ " (min = " + MinElements + ").");
			if (occurrences.size() > MaxElements)
				return new UnitValueCheck("Too many elements of " + listName
						+ " (max = " + MaxElements + ").");
			return UnitValueCheck.checkOK();
		}

	}

}
