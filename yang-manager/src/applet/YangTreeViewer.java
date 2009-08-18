package applet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import yangTree.YangTreeModel;
import yangTree.nodes.CheckedYangNode;
import yangTree.nodes.RootNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

/**
 * Provides a display of a YangTree.
 * 
 */
@SuppressWarnings("serial")
public class YangTreeViewer extends JTree {

	private YangApplet applet;

	public YangTreeViewer(YangApplet applet, YangNode root) {
		super(new YangTreeModel(root));

		this.applet = applet;

		ToolTipManager.sharedInstance().registerComponent(this);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(true);
		setCellRenderer(new CustomCellRenderer());
		addMouseListener(new CustomMouseListener());
	}

	public void updateTree(TreePath path) {
		applet.updateTree(path);
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

	/**
	 * Sets the "<code>isExpanded</code>" value to <code>true</code> for each
	 * <code>YangInnerNode</code> of the displayed tree that is currently
	 * expanded.
	 * 
	 * @see #applyDisplayFromTree(RootNode)
	 */
	public void setDisplayOnTree() {
		for (TreePath path : getCurrentDisplay()) {
			((YangInnerNode) path.getLastPathComponent()).setExpanded(true);
		}
	}

	/**
	 * Changes the display of the current tree according to each
	 * <code>YangInnerNode</code> "<code>isExpanded</code>" value.
	 * 
	 * @param root
	 *            : the root of the currently displayed tree.
	 * @see #setDisplayOnTree()
	 */
	public void applyDisplayFromTree(RootNode root) {
		applyDisplayFromTree_Engine(root, new TreePath(root));
	}

	private void applyDisplayFromTree_Engine(YangInnerNode node, TreePath path) {
		if (node.isExpanded())
			expandPath(path);
		for (YangNode child : node.getDescendantNodes()) {
			if (child instanceof YangInnerNode) {
				YangInnerNode cchild = (YangInnerNode) child;
				applyDisplayFromTree_Engine(cchild, path.pathByAddingChild(cchild));
			}
		}
	}

	private class CustomCellRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			YangNode node = (YangNode) value;

			setToolTipText(null);
			if (node instanceof CheckedYangNode) {
				CheckedYangNode checkedNode = (CheckedYangNode) node;
				if (checkedNode.getCheck() != null && !checkedNode.getCheck().isOk())
					setToolTipText(checkedNode.getCheck().toString());
			}
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

	private class CustomMouseListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			int selRow = getRowForLocation(e.getX(), e.getY());

			if (e.getButton() == MouseEvent.BUTTON3) {
				if (selRow == -1 || selRow == 0) {
					new DefaultPopupMenu(YangTreeViewer.this, e.getX(), e.getY());
				} else {
					new NodePopupMenu(YangTreeViewer.this, (YangNode) getPathForLocation(e.getX(), e.getY()).getLastPathComponent(), e.getX(), e.getY());
				}
			}
		}

	}

}
