package applet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import yangTree.YangTreeModel;
import yangTree.nodes.CheckedYangNode;
import yangTree.nodes.YangNode;

/**
 * Provides a display of a YangTree.
 * 
 */
@SuppressWarnings("serial")
public class YangTree extends JTree {

	public YangTree(YangNode root) {
		super(new YangTreeModel(root));
		
		ToolTipManager.sharedInstance().registerComponent(this);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(true);
		setCellRenderer(new CustomCellRenderer());
		addMouseListener(new CustomMouseListener());
	}
	
	public void collapseAll(){
		for (int i=getRowCount()-1;i>0;i--){
			collapseRow(i);
		}
	}
	
	public void expandAll(){
		for (int i=0;i<getRowCount();i++){
			expandRow(i);
		}
	}

	private class CustomCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			YangNode node = (YangNode) value;
			
			setToolTipText(null);
			if (node instanceof CheckedYangNode){
				CheckedYangNode checkedNode = (CheckedYangNode) node;
				if (checkedNode.getCheck()!=null && !checkedNode.getCheck().isOk())
					setToolTipText(checkedNode.getCheck().toString());
			} 
			setIcon(node.getIcon());
			return this;
		}
		
		@Override
		public Dimension getPreferredSize(){
			Dimension result = super.getPreferredSize();
			result.setSize(result.width, result.height+4);
			return result;
		}

	}
	
	private class CustomMouseListener extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e){
			int selRow = getRowForLocation(e.getX(), e.getY());
			
			if (e.getButton()==MouseEvent.BUTTON3){
				if (selRow==-1){
					new DefaultPopupMenu(YangTree.this, e.getX(), e.getY());
				}
			}
		}
		
	}

}
