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
	
	protected LinkedList<YangNode> nodes = new LinkedList<YangNode>();
	
	/**
	 * Returns the children of this node.
	 */
	public LinkedList<YangNode> getNodes(){
		return nodes;
	}
	
	/**
	 * Returns an empty (i.e. without child) clone of this node.
	 */
	public abstract YangInnerNode cloneBody();
	
	/**
	 * Add a child to this node.
	 */
	public void addContent(YangNode node){
		nodes.add(node);
	}
	
	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		if (getNameSpace()!=null)
			panel.addTextField("Namespace", getNameSpace().getNameSpace());
	}

}
