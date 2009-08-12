package yangTree.nodes;

import java.util.LinkedList;

/**
 * Represents a node that is not a leaf (i.e. that can have children) in a YangTree.
 * @see DataLeaf
 *
 */
public abstract class DataTree extends DataNode {
	
	protected LinkedList<DataNode> nodes = new LinkedList<DataNode>();
	
	/**
	 * Returns the children of this node.
	 */
	public LinkedList<DataNode> getNodes(){
		return nodes;
	}
	
	/**
	 * Returns an empty (i.e. without child) clone of this node.
	 */
	public abstract DataTree cloneBody();
	
	/**
	 * Add a child to this node.
	 */
	public void addContent(DataNode node){
		nodes.add(node);
	}

}
