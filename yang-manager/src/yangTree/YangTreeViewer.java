package yangTree;


import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import yangTree.nodes.DataNode;





public class YangTreeViewer extends JTree{
	
	public YangTreeViewer(DataNode root){
		super(new YangTreeModel(root));
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(true);
	}

}
