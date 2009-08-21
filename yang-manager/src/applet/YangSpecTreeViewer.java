package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

@SuppressWarnings("serial")
public class YangSpecTreeViewer extends YangTreeViewer {
	
	public YangSpecTreeViewer(YangApplet applet, YangNode root){
		super(applet,root);
		
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
			JMenuItem updateItem = add("Get all values");
			updateItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree();
				}
			});

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

			JMenuItem refreshItem = add("Get value");
			refreshItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					applet.displayDataTree(getPathForLocation(x, y));
				}
			});

			if (node instanceof YangInnerNode) {

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

}
