package yangTree.nodes;

import java.util.LinkedList;

import applet.InfoPanel;

/**
 * Represents a inner node (i.e. that is not a leaf) in a YangTree.
 * 
 * @see YangLeaf
 * 
 */
@SuppressWarnings("serial")
public abstract class YangInnerNode extends YangNode {

	protected LinkedList<YangNode> descendantNodes = new LinkedList<YangNode>();

	/**
	 * Returns the children of this node.
	 */
	public LinkedList<YangNode> getDescendantNodes() {
		return descendantNodes;
	}

	public void setDescendantsNodes(LinkedList<YangNode> nodes) {
		this.descendantNodes = nodes;
	}

	/**
	 * Adds a child to this node.
	 */
	public void addChild(YangNode node) {
		descendantNodes.add(node);
	}

	/**
	 * Returns an empty clone (i.e. without children) of this node.<br>
	 * <b>Note :</b> All the other parameters of this node <u>(including the
	 * list of children in specification)</u> <u>WILL</u> be kept.
	 */
	public abstract YangInnerNode cloneBody();

	@Override
	public void buildInfoPanel(InfoPanel panel) {
		super.buildInfoPanel(panel);
		if (getNameSpace() != null)
			panel.addTextField("Namespace", getNameSpace().getNameSpace());
	}

}
