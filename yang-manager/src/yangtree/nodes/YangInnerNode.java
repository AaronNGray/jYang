package yangtree.nodes;

import java.util.LinkedList;

import yangtree.ChoiceStillPresentException;
import yangtree.attributes.DeleteOperations;
import yangtree.attributes.UnitValueCheck;
import applet.InfoPanel;

/**
 * Represents a inner node (i.e. that is not a leaf) in a YangTree.
 * 
 * @see YangNode
 * 
 */
@SuppressWarnings("serial")
public abstract class YangInnerNode extends YangNode {

	protected LinkedList<YangNode> descendantNodes = new LinkedList<YangNode>();
	protected YangInnerNode specificationNode = null;
	protected DeleteOperations deleteOperations = null;

	/**
	 * Returns the children of this node.
	 */
	public LinkedList<YangNode> getDescendantNodes() {
		return descendantNodes;
	}

	/**
	 * Sets the children of this node
	 * 
	 * @param nodes
	 *            : the new list of children of this node.
	 */
	public void setDescendantsNodes(LinkedList<YangNode> nodes) {
		this.descendantNodes = nodes;
	}

	/**
	 * Marks a specific child to be deleted. This operation will be performed at
	 * next "apply modifications" action.
	 * 
	 * @param childToDelete
	 *            : the node to delete. It <u>must</u> be a child of this
	 *            <code>YangInnerNode</code> .
	 */
	public void markChildToBeDeleted(YangNode childToDelete) {
		if (deleteOperations == null)
			deleteOperations = new DeleteOperations();
		deleteOperations.addDeleteOperation(childToDelete);
	}

	/**
	 * Returns the specification node associated with this data node, or
	 * <code>null</code> if this node is already a specification node.
	 */
	public YangInnerNode getSpecificationNode() {
		return specificationNode;
	}

	/**
	 * Adds a child to this node.
	 */
	public void addChild(YangNode node) {
		descendantNodes.add(node);
	}

	/**
	 * Gets all the children of this node that is matching a given name.
	 * 
	 * @param childName
	 *            : the name of the child to get.
	 * @return The list of children that is matching the given name.
	 */
	public LinkedList<YangNode> getChildByName(String childName) {
		LinkedList<YangNode> result = new LinkedList<YangNode>();
		for (YangNode child : descendantNodes) {
			if (child.getName().equals(childName))
				result.add(child);
		}
		return result;
	}

	public void check() {
		for (YangNode child : descendantNodes) {
			for (YangNode cchild : descendantNodes) {
				if (child != cchild && child.getName().equals(cchild.getName()) && child instanceof ListedYangNode && cchild instanceof ListedYangNode) {
					ListedYangNode list = (ListedYangNode) child;
					ListedYangNode llist = (ListedYangNode) cchild;
					if (list.equalsOccurrence(llist))
						((YangNode) list).getCheck().addUnitCheck(new UnitValueCheck("Duplicated occurrence"));
				}
			}
		}
	}

	protected void checkSubtree() {
		super.checkSubtree();
		for (YangNode child : descendantNodes) {
			child.checkSubtree();
		}
	}

	protected void uncheckSubtree() {
		super.uncheckSubtree();
		for (YangNode child : descendantNodes) {
			child.uncheckSubtree();
		}
	}

	/**
	 * Returns an empty clone (i.e. without children) of this node.
	 */
	public abstract YangInnerNode cloneBody();

	public YangInnerNode cloneTree() {
		YangInnerNode result = cloneBody();
		for (YangNode child : descendantNodes) {
			if (!(child instanceof ListNode) && !(child instanceof LeafListNode))
				result.addChild(child.cloneTree());
		}
		return result;
	}

	@Override
	public void buildInfoPanel(InfoPanel panel) {
		super.buildInfoPanel(panel);
		if (getNameSpace() != null)
			panel.addTextField("Namespace", getNameSpace().getNameSpace());
	}

	public String getXMLRepresentation() throws ChoiceStillPresentException {
		String result = "<" + getName();
		if (nameSpace != null && nameSpace.getNameSpace() != null) {
			result += " " + nameSpace.getXMLArg();
		}
		result += ">";
		for (YangNode child : getDescendantNodes()) {
			result += child.getXMLRepresentation();
		}
		if (deleteOperations != null)
			result += deleteOperations.getOperations();
		return result + "</" + getName() + ">";
	}

	/**
	 * Returns the XML representation of this node that performs the operation
	 * "replace"
	 * @throws ChoiceStillPresentException
	 *             : if this node is a <code>ChoiceNode</code> of if there is
	 *             such node in its descendants.
	 */
	public String getXMLReplaceRepresentation() throws ChoiceStillPresentException {
		String result = "<" + getName();
		if (nameSpace != null && nameSpace.getNameSpace() != null) {
			result += " " + nameSpace.getXMLArg();
		}
		result += " xmlns:xc=\"urn:ietf:params:xml:ns:netconf:base:1.0\" xc:operation=\"replace\"";
		result += ">";
		for (YangNode child : getDescendantNodes()) {
			result += child.getXMLRepresentation();
		}
		if (deleteOperations != null)
			result += deleteOperations.getOperations();
		return result + "</" + getName() + ">";
	}
}
