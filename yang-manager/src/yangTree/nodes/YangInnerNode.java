package yangTree.nodes;

import java.util.LinkedList;

import applet.InfoPanel;

/**
 * Represents a inner node (i.e. that is not a leaf) in a YangTree.
 * @see YangLeaf
 *
 */
@SuppressWarnings("serial")
public abstract class YangInnerNode extends YangNode {
	
	protected boolean isExpanded = false;
	protected LinkedList<YangNode> nodes = new LinkedList<YangNode>();
	
	/**
	 * Returns the children of this node.
	 */
	public LinkedList<YangNode> getDescendantNodes(){
		return nodes;
	}
	
	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	/**
	 * Adds a child to this node.
	 */
	public void addContent(YangNode node){
		nodes.add(node);
	}
	
	/**
	 * Returns an empty clone (i.e. without children) of this node.
	 */
	public abstract YangInnerNode cloneBody();
	
	public void setNodes(LinkedList<YangNode> nodes){
		this.nodes = nodes;
	}
	
	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		if (getNameSpace()!=null)
			panel.addTextField("Namespace", getNameSpace().getNameSpace());
	}

}
