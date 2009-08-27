package applet;

import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import yangTree.YangTreeModel;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

/**
 * Provides a display of a YangTree.
 * 
 */
@SuppressWarnings("serial")
public abstract class YangTreeViewer extends JTree {

	protected YangApplet applet;

	public YangTreeViewer(YangApplet applet, YangNode root) {

		super(new YangTreeModel(root));
		this.applet = applet;

		ToolTipManager.sharedInstance().registerComponent(this);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setCellRenderer(new CustomCellRenderer());

	}

	/**
	 * Removes the current selection.
	 */
	public void removeSelection() {
		if (getSelectionPath() != null)
			removeSelectionPath(getSelectionPath());
	}

	/**
	 * Collapses all the lines of the displayed tree.
	 */
	public void collapseAll() {
		for (int i = getRowCount() - 1; i > 0; i--) {
			collapseRow(i);
		}
	}

	/**
	 * Expands all the lines of the displayed tree.
	 */
	public void expandAll() {
		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}

	/**
	 * Collapses a given node and all his descendants.
	 * 
	 * @param path
	 *            : the path of the node.
	 */
	public void collapseNode(TreePath path) {
		YangInnerNode node = (YangInnerNode) path.getLastPathComponent();
		for (YangNode child : node.getDescendantNodes()) {
			if (child instanceof YangInnerNode) {
				collapseNode(path.pathByAddingChild(child));
			}
		}
		collapsePath(path);
	}

	/**
	 * Expands a given node and all his descendants.
	 * 
	 * @param path
	 *            : the path of the node.
	 */
	public void expandNode(TreePath path) {
		expandPath(path);
		YangInnerNode node = (YangInnerNode) path.getLastPathComponent();
		for (YangNode child : node.getDescendantNodes()) {
			if (child instanceof YangInnerNode) {
				expandNode(path.pathByAddingChild(child));
			}
		}
	}

	/**
	 * Returns a description of the current display state of the tree.
	 * 
	 * @return the list of <code>TreePath</code>s that are currently expanded
	 * @see #setDisplay(LinkedList)
	 */
	public LinkedList<TreePath> getCurrentDisplay() {
		LinkedList<TreePath> result = new LinkedList<TreePath>();
		for (int i = 0; i < getRowCount(); i++) {
			if (isExpanded(i))
				result.add(getPathForRow(i));
		}
		return result;
	}

	/**
	 * Makes the tree to expand all the given lines and collapse all other.
	 * 
	 * @param display
	 *            : the list of <code>TreePath</code>s that will be expanded.
	 * @see #getCurrentDisplay()
	 */
	public void setDisplay(LinkedList<TreePath> display) {
		collapseAll();
		for (TreePath path : display) {
			expandPath(path);
		}
	}

	private class CustomCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			YangNode node = (YangNode) value;

			setToolTipText(null);
			if (node.getCheck() != null && !node.getCheck().isOk())
				setToolTipText(Util.cutString(node.getCheck().toString(),100));
			setIcon(node.getIcon());
			return this;
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension result = super.getPreferredSize();
			result.setSize(result.width, result.height + 4);
			return result;
		}

	}

}
