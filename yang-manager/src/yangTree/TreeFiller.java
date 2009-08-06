package yangTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import yangTree.attributes.BuiltinType;
import yangTree.attributes.NameSpace;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.builtinTypes.EmptyType;
import yangTree.nodes.CaseNode;
import yangTree.nodes.ChoiceNode;
import yangTree.nodes.DataNode;
import yangTree.nodes.DataTree;
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

	public static DataNode fillTree(DataNode dataNode, Document xmlDocument) {
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
						return fillTreeEngine(dataNode, nodeList.item(j));
					}
				}
			}
		}
		throw new NetconfReplyMalformedException(
				"Expected \"data\" node not present");
	}

	private static DataNode fillTreeEngine(DataNode dataNode, Node xmlNode) {

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

		DataNode result = null;

		// Handle the leaves cases
		if (dataNode instanceof LeafNode) {

			LeafNode filledNode = ((LeafNode) dataNode).cloneBody();

			String value = null;
			if (!(filledNode.getTypeDef().getBuiltinType() instanceof EmptyType)) {
				if (xmlNode.hasChildNodes()) {
					value = xmlNode.getFirstChild().getNodeValue();
				} else if (xmlNode.hasAttributes()) {
					value = xmlNode.getAttributes().item(0).getNodeValue();
				} else {
					value = filledNode.getDefaultValue();
				}
				filledNode.setValue(value);
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
		} else if (dataNode instanceof DataTree) {

			DataTree tree = (DataTree) dataNode;
			DataTree treeResult = tree.cloneBody();

			// build the list of nodes that can match a child of xmlNode, with
			// their associated choice & case (if exists).
			Map<DataNode, NodeDescriptor> eligibleNodes = new HashMap<DataNode, NodeDescriptor>();
			int choiceIndex = 0;
			for (DataNode node : tree.getNodes()) {
				if (node instanceof ChoiceNode) {
					choiceIndex++;
					int caseIndex = 0;
					for (DataNode caseNode : ((ChoiceNode) node).getNodes()) {
						caseIndex++;
						for (DataNode caseNodeChild : ((CaseNode) caseNode)
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

			// Case Chooser : see "caseChooser" function for more details
			for (int choice = 1; choice <= choiceIndex; choice++) {
				eligibleNodes = caseChooser(choice, eligibleNodes, xmlChildren);
			}

			// At last, search for matching between xmlChildren and
			// dataNodeChildren
			for (Node xmlChild : xmlChildren) {
				for (Map.Entry<DataNode, NodeDescriptor> entry : eligibleNodes
						.entrySet()) {
					DataNode nodeChild = entry.getKey();
					String[] xmlChildName = xmlChild.getNodeName().split(":");
					if (xmlChildName[xmlChildName.length - 1].equals(nodeChild
							.getName())) {
						entry.setValue(MATCHED);
						DataNode newChild = fillTreeEngine(nodeChild, xmlChild);
						if (newChild != null) {
							treeResult.addContent(newChild);
						}
					}
				}
			}

			// Unmatched parts of the tree will be filled with default values.
			for (DataNode node : eligibleNodes.keySet()) {
				if (eligibleNodes.get(node).equals(UNMATCHED)) {
					DataNode newChild = fillTreeWithDefaultValues(node);
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
	 * built matching the specifications, and filled with default values.
	 */
	private static DataNode fillTreeWithDefaultValues(DataNode dataNode) {

		// Handle the leaves cases
		if (dataNode instanceof LeafNode) {

			LeafNode filledNode = ((LeafNode) dataNode).cloneBody();
			String value = null;
			if (filledNode.isMandatory()) {
				System.out.println("WARNING : The mandatory leaf \""
						+ filledNode.getName() + "\" is not present.");
			} else {
				value = filledNode.getDefaultValue();
			}
			filledNode.setValue(value);
			return filledNode;

		} else if (dataNode instanceof LeafListNode) {

			return null;

			// Handle the tree cases
		} else if (dataNode instanceof DataTree) {

			if (dataNode instanceof ChoiceNode || dataNode instanceof ListNode) {
				return null;
			}

			DataTree filledNode = ((DataTree) dataNode).cloneBody();
			for (DataNode node : ((DataTree) dataNode).getNodes()) {
				DataNode child = fillTreeWithDefaultValues(node);
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
	 * Walk through the list xmlChildren, searching for a match with an element
	 * of a caseNode
	 */
	private static Map<DataNode, NodeDescriptor> caseChooser(int choice,
			Map<DataNode, NodeDescriptor> eligibleNodes,
			LinkedList<Node> xmlChildren) {
		Map<DataNode, NodeDescriptor> newEligibleNodes = new HashMap<DataNode, NodeDescriptor>();
		for (Node xmlChild : xmlChildren) {

			for (DataNode nodeChild : eligibleNodes.keySet()) {
				if (xmlChild.getNodeName().equals(nodeChild.getName())) {

					/*
					 * If the matched node is an element of a caseNode, this
					 * case is kept, and all other cases are removed from the
					 * eligible nodes
					 */
					if (eligibleNodes.get(nodeChild).choiceIndex == choice) {

						Integer caseKept = eligibleNodes.get(nodeChild).caseIndex;
						for (DataNode node : eligibleNodes.keySet()) {
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
		for (DataNode key : eligibleNodes.keySet()) {
			if (eligibleNodes.get(key).equals(UNMATCHED)) {
				newEligibleNodes.put(key, UNMATCHED);
			}
		}
		return newEligibleNodes;
	}

	/*
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

}
