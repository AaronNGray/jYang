package yangTree;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import yangTree.nodes.DataNode;

public class YangTreeViewer extends JTree {

	public YangTreeViewer(DataNode root) {
		super(new YangTreeModel(root));
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(true);
		setCellRenderer(new CustomCellRenderer());
	}

	private class CustomCellRenderer extends DefaultTreeCellRenderer {


		public CustomCellRenderer() {
			super();
		}


		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			DataNode node = (DataNode) value;
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

}
