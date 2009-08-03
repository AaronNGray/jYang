package yangTree;


import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import yangTree.nodes.*;


public class YangTreeModel implements TreeModel {

	private DataNode root;
	
	public YangTreeModel(DataNode root){
		this.root = root;
	}
	
	public Object getChild(Object parent,int index){
		
		DataTree tree = (DataTree) parent;
		return tree.getNodes().get(index);

	}
	
	public int getChildCount(Object parent){
		DataTree node = (DataTree) parent;
		return node.getNodes().size();
	}
	
	public int getIndexOfChild(Object parent, Object child){
		
		DataTree tree = (DataTree) parent;
		int i = 0;
		for (DataNode node : tree.getNodes()){
			if (node.equals(child)){
				return i;
			} else {
				i++;
			}
		}
		return -1;
			
	}
	
	public boolean isLeaf(Object node){
		DataNode dataNode = (DataNode) node;
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