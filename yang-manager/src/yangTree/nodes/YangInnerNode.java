package yangTree.nodes;

import java.util.LinkedList;

import yangTree.attributes.UnitValueCheck;
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
	protected YangInnerNode specificationNode = null;
	
	
	public void check(){
		for (YangNode child : descendantNodes){
			for (YangNode cchild : descendantNodes){
				if (child!=cchild && child.getName().equals(cchild.getName()) && child instanceof ListedYangNode && cchild instanceof ListedYangNode) {
					ListedYangNode list = (ListedYangNode) child;
					ListedYangNode llist = (ListedYangNode) cchild;
					if (list.equalsOccurrence(llist))
						((YangNode) list).getCheck().addUnitCheck(new UnitValueCheck("Duplicated occurrence"));
				}
			}
		}
	}
	
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
	 * Returns the specification node associated with this data node, or <code>null</code> if this node is already a specification node.
	 */
	public YangInnerNode getSpecificationNode(){
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
	 * @param childName : the name of the child to get.
	 * @return The list of children that is matching the given name.
	 */
	public LinkedList<YangNode> getChildByName(String childName){
		LinkedList<YangNode> result = new LinkedList<YangNode>();
		for (YangNode child : descendantNodes){
			if (child.getName().equals(childName))
				result.add(child);
		}
		return result;
	}
	
	public void checkSubtree(){
		super.checkSubtree();
		for (YangNode child : descendantNodes){
			child.checkSubtree();
		}
	}
	

	/**
	 * Returns an empty clone (i.e. without children) of this node.
	 */
	public abstract YangInnerNode cloneBody();
	
	/**
	 * Creates a new occurrence of this node, filled with empty nodes.
	 * @return a new occurrence of this node.
	 */
	public YangInnerNode createNewOccurrence(){
		YangInnerNode result = cloneBody();
		for (YangNode child : descendantNodes){
			if (child instanceof YangInnerNode){
				result.addChild(((YangInnerNode) child).createNewOccurrence());
			} else if (child instanceof LeafNode){
				result.addChild(child.cloneBody());
			}
		}
		return result;
	}
	

	@Override
	public void buildInfoPanel(InfoPanel panel) {
		super.buildInfoPanel(panel);
		if (getNameSpace() != null)
			panel.addTextField("Namespace", getNameSpace().getNameSpace());
	}
	
	public String getXMLRepresentation(){
		String result = "<"+getName();
		if (nameSpace!=null && nameSpace.getNameSpace() != null) {
			result += " "+nameSpace.getXMLArg();
		}
		result += ">";
		for (YangNode child : getDescendantNodes()){
			result+= child.getXMLRepresentation();
		}
		return result+"</"+getName()+">";
	}

}
