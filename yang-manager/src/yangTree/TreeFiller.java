package yangTree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.tree.TreePath;

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
import yangTree.nodes.ChoiceNode;
import yangTree.nodes.EmptyNode;
import yangTree.nodes.ListedYangNode;
import yangTree.nodes.RootNode;
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
	private static String nodeToFillName ;

	/**
	 * Creates the filled data Tree.
	 * 
	 * @param specRoot
	 *            : the root of the specification tree.
	 * @param path
	 *            : the path of the specification node that will be filled.
	 * @param xmlDocument
	 *            : the Netconf reply XML document.
	 * @return the root of the filled data tree.
	 */
	public static RootNode createDataTree(RootNode specRoot, TreePath treePath, Document xmlDocument) {

		YangNode nodeToFill = (YangNode) treePath.getLastPathComponent() ;
		nodeToFillName = nodeToFill.getName();
		Node currentXmlNode = null;

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
				currentXmlNode = list.item(i);
			}
		}

		if (currentXmlNode == null)
			throw new NetconfReplyMalformedException("Expected \"data\" node not present");

		int emptyLevels = treePath.getPathCount() - 1;
		RootNode result = new RootNode("Yang Data");

		YangNode completeDataTree = dataTreeEngine(specRoot, currentXmlNode, emptyLevels, new YangTreePath(nodeToFillName));

		YangInnerNode currentNode = (YangInnerNode) completeDataTree;
		TreePath rootPath = new TreePath(currentNode);

		while (emptyLevels > 1 && currentNode.getDescendantNodes().size() == 1) {
			currentNode = (YangInnerNode) currentNode.getDescendantNodes().getFirst();
			rootPath = rootPath.pathByAddingChild(currentNode);
			emptyLevels--;
		}

		result.setPath(rootPath);
		for (YangNode child : currentNode.getDescendantNodes()) {
			result.addChild(child);
		}
		
		if (result.getDescendantNodes().size()==0)
			result.addChild(new EmptyNode((YangLeaf) nodeToFill, nodeToFillName+" : No such leaf retrieved."));

		result.checkSubtree();
		return result;
	}

	/**
	 * Creates a filled data tree.
	 * 
	 * @param specNode
	 *            : the node of a specification tree.
	 * @param xmlNode
	 *            : the xmlNode matching this specification tree.
	 * @return the filled tree.
	 */
	private static YangNode dataTreeEngine(YangNode specNode, Node xmlNode, int emptyLevelsLeft, YangTreePath path) {
		
		
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
		if (specNode instanceof LeafNode) {

			LeafNode filledNode = ((LeafNode) specNode).cloneBody();
			YangTreePath newPath = path.pathByAppendingChild(filledNode);

			String value = null;
			if (!(filledNode.getType().getBuiltinType() instanceof EmptyType)) {
				if (xmlNode.hasChildNodes()) {
					value = xmlNode.getFirstChild().getNodeValue();
				} else if (xmlNode.hasAttributes()) {
					value = xmlNode.getAttributes().item(0).getNodeValue();
				} else {
					value = null;
				}
				//PathSensitiveTypes have to know the path of their leaf.
				if (filledNode.getType().getBuiltinType() instanceof PathSensitiveType){
					((PathSensitiveType) filledNode.getType().getBuiltinType()).setPath(newPath);
				}
				filledNode.setValue(value);
			}
			result = filledNode;

		} else if (specNode instanceof LeafListNode) {

			LeafListNode filledNode = ((LeafListNode) specNode).cloneBody();
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
		} else if (specNode instanceof YangInnerNode) {

			YangInnerNode tree = (YangInnerNode) specNode;
			YangInnerNode treeResult = tree.cloneBody();
			YangTreePath newPath = path.pathByAppendingChild(treeResult);

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
			for (int i=0;i<attrChildren.getLength();i++){
				if (!attrChildren.item(i).getNodeName().contains("xmlns")) {
					xmlChildren.add(attrChildren.item(i));
				}
			}

			// solve all the choices.
			LinkedList<YangNode> yangChildren = new LinkedList<YangNode>();
			LinkedList<Integer> alreadySolvedNodes = new LinkedList<Integer>();
			for (YangNode node : eligibleNodes.keySet()) {
				if (eligibleNodes.get(node).equals(NO_CASE)) {
					yangChildren.add(node);
				} else {
					int choice = eligibleNodes.get(node).choiceIndex;
					if (!alreadySolvedNodes.contains(choice)) {
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
						YangNode newChild = dataTreeEngine(nodeChild, xmlChild, emptyLevelsLeft - 1, newPath);
						matchFound = true;
						if (newChild != null) {
							// Special handling if the child is a ListedYangNode
							// :
							if (newChild instanceof ListedYangNode) {
								listBuilders.get(newChild.getName()).addOccurrence((ListedYangNode) newChild);
							} else {

								// At empty levels, list children must be
								// "silently" added.
								if (treeResult instanceof ListNode && !newChild.getName().equals(nodeToFillName) && emptyLevelsLeft > 0) {
									((ListNode) treeResult).addChildSilently(newChild);
								} else {
									treeResult.addChild(newChild);
								}

							}
						}
					}
				}
				// If the current node must be filled, unmatched parts of the
				// tree will be built.
				if (!matchFound && emptyLevelsLeft <= 0) {
					YangNode newChild = buildEmptyTree(nodeChild);
					if (newChild != null) {
						treeResult.addChild(newChild);
					}
				}
			}

			// Now, the ListedYangNodes can be added :
			for (String key : listBuilders.keySet()) {
				treeResult.getCheck().addUnitCheck(listBuilders.get(key).check());
				for (ListedYangNode n : listBuilders.get(key).occurrences) {
					//Only non-empty list will be added :
					if (n instanceof ListNode){
						if (((ListNode) n).getDescendantNodes().size()>0)
							treeResult.addChild((YangNode) n);
					} else {
						treeResult.addChild((YangNode) n);
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
			
			if (!filledNode.isMandatory())
				return null;
			
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
					filledNode.addChild(child);
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
					 * case is kept, and all other cases are removed from the
					 * eligible nodes
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
	 * Special class used to sort a set of occurrences of a
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
