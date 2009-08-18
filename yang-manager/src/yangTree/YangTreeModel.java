package yangTree;


import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import yangTree.nodes.*;


public class YangTreeModel implements TreeModel {

	private YangNode root;
	
	public YangTreeModel(YangNode root){
		this.root = root;
	}
	
	public Object getChild(Object parent,int index){
		
		YangInnerNode tree = (YangInnerNode) parent;
		return tree.getDescendantNodes().get(index);

	}
	
	public int getChildCount(Object parent){
		YangInnerNode node = (YangInnerNode) parent;
		return node.getDescendantNodes().size();
	}
	
	public int getIndexOfChild(Object parent, Object child){
		
		YangInnerNode tree = (YangInnerNode) parent;
		int i = 0;
		for (YangNode node : tree.getDescendantNodes()){
			if (node.equals(child)){
				return i;
			} else {
				i++;
			}
		}
		return -1;
			
	}
	
	public boolean isLeaf(Object node){
		YangNode dataNode = (YangNode) node;
		return (dataNode instanceof LeafNode) || (dataNode instanceof LeafListNode);
	}
	
	public Object getRoot(){
		return root;
	}
	
	public void valueForPathChanged(TreePath path, Object newValue){
		System.out.println("Warning : a value in the tree has changed");
	}
	
	public void addTreeModelListener(TreeModelListener l){
		
	}
	
	public void removeTreeModelListener(TreeModelListener l){
		
	}
}
