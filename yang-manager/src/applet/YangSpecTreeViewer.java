package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import yangTree.nodes.CaseNode;
import yangTree.nodes.ChoiceNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.ListNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

/**
 * Provides a display of a Yang specification tree.
 * 
 */
@SuppressWarnings("serial")
public class YangSpecTreeViewer extends YangTreeViewer {

	public YangSpecTreeViewer(YangApplet applet, YangNode root) {
		super(applet, root);

		setRootVisible(true);
		addMouseListener(new CustomMouseListener());
	}

	private class CustomMouseListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			int selRow = getRowForLocation(e.getX(), e.getY());

			if (e.getButton() == MouseEvent.BUTTON3) {
				if (selRow == -1 || selRow == 0) {
					new DefaultPopupMenu(e.getX(), e.getY());
				} else {
					new NodePopupMenu(getPathForLocation(e.getX(), e.getY()), e.getX(), e.getY());
				}
			}
		}

	}

	private class DefaultPopupMenu extends JPopupMenu {

		public DefaultPopupMenu(int x, int y) {
			super();
			buildMenu();
			show(YangSpecTreeViewer.this, x, y);
		}

		private void buildMenu() {
			JMenuItem getItem = add("Get all");
			getItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree(false);
				}
			});

			JMenuItem getConfigItem = add("Get-config all");
			getConfigItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree(true);
				}
			});

			JMenuItem editItem = add("Edit-config all");
			editItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayEmptyTree();
				}
			});

			addSeparator();

			JMenuItem expandItem = add("Expand all");
			expandItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					expandAll();
				}
			});

			JMenuItem collapseItem = add("Collapse all");
			collapseItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					collapseAll();
				}
			});
		}

	}

	private class NodePopupMenu extends JPopupMenu {

		private TreePath path;
		private YangNode node;
		private int x;
		private int y;

		public NodePopupMenu(TreePath path, int x, int y) {
			super();
			this.path = path;
			this.node = (YangNode) path.getLastPathComponent();
			this.x = x;
			this.y = y;
			buildMenu();
			show(YangSpecTreeViewer.this, x, y);
		}

		private void buildMenu() {

			JMenuItem getItem = add("Get");
			getItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree(getPathForLocation(x, y), false);
				}
			});

			JMenuItem getConfigItem = add("Get-config");
			getConfigItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree(getPathForLocation(x, y), true);
				}
			});

			if (isEligileEditConfig(path)) {
				if (node instanceof ListNode) {
					JMenuItem editItem = add("Add list occurrence");
					editItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							applet.displayEmptyTree(path);
						}
					});
				} else if (node instanceof LeafListNode) {
					JMenuItem editItem = add("Add leaf-list occurrence");
					editItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							applet.displayEmptyTree(path);
						}
					});
				} else {
					JMenuItem editItem = add("Edit-config");
					editItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							applet.displayEmptyTree(path);
						}
					});
				}
			}

			if (node instanceof YangInnerNode) {

				addSeparator();

				JMenuItem expandItem = add("Expand subtree");
				expandItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						expandNode(path);
					}
				});

				JMenuItem collapseItem = add("Collapse subtree");
				collapseItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						collapseNode(path);
					}
				});
			}
		}

	}

	/**
	 * Returns whatever a node at a given path can be edit-config or not.
	 * 
	 * @param path
	 *            : the path of the node to test.
	 */
	private static boolean isEligileEditConfig(TreePath path) {
		Object[] array = path.getPath();
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] instanceof ListNode || array[i] instanceof ChoiceNode || array[i] instanceof CaseNode || array[i] instanceof LeafListNode)
				return false;
		}
		Object lastNode = array[array.length - 1];
		if (lastNode instanceof ChoiceNode || lastNode instanceof CaseNode)
			return false;
		return true;
	}

}
