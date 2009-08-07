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
import yangTree.nodes.DataLeaf;
import yangTree.nodes.DataNode;

public class YangTreeViewer extends JTree {

	public YangTreeViewer(DataNode root) {
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
			DataNode node = (DataNode) value;
			
			setToolTipText(null);
			if (node instanceof DataLeaf){
				DataLeaf dataLeaf = (DataLeaf) node;
				if (dataLeaf.getCheck()!=null && !dataLeaf.getCheck().isOk())
					setToolTipText(dataLeaf.getCheck().toString());
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
					new DefaultPopupMenu(YangTreeViewer.this, e.getX(), e.getY());
				}
			}
		}
		
	}

}
