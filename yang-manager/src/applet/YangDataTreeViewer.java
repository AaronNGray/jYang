package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import yangTree.nodes.EmptyNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

@SuppressWarnings("serial")
public class YangDataTreeViewer extends YangTreeViewer {

	public YangDataTreeViewer(YangApplet applet, YangNode root) {
		super(applet, root);

		setRootVisible(false);
		addMouseListener(new CustomMouseListener());

		expandAll();
	}

	private class CustomMouseListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			int selRow = getRowForLocation(e.getX(), e.getY());

			if (e.getButton() == MouseEvent.BUTTON3) {
				if (selRow == -1) {
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
			show(YangDataTreeViewer.this, x, y);
		}

		private void buildMenu() {

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

		public NodePopupMenu(TreePath path, int x, int y) {
			super();
			this.path = path;
			this.node = (YangNode) path.getLastPathComponent();
			buildMenu();
			show(YangDataTreeViewer.this, x, y);
		}

		private void buildMenu() {

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

				YangInnerNode innerNode = (YangInnerNode) node;
				LinkedList<YangNode> specDescendants = innerNode.getSpecificationNode().getDescendantNodes();
				LinkedList<LeafListNode> leafListChildren = new LinkedList<LeafListNode>();
				LinkedList<ListNode> listChildren = new LinkedList<ListNode>();
				for (YangNode node : specDescendants){
					if (node instanceof LeafListNode)
						leafListChildren.add((LeafListNode) node);
					if (node instanceof ListNode)
						listChildren.add((ListNode) node);
				}
				
				if (leafListChildren.size() > 0) {
					JMenu addLeafListItem = new JMenu("add leaf-list occurrence");
					for (LeafListNode leafList : leafListChildren) {
						JMenuItem item = addLeafListItem.add(leafList.getName());
						item.addActionListener(new CreationActionListener(innerNode, leafList));
					}
					add(addLeafListItem);
				}
				
				if (listChildren.size() > 0) {
					JMenu addListItem = new JMenu("add list occurrence");
					for (ListNode list : listChildren) {
						JMenuItem item = addListItem.add(list.getName());
						item.addActionListener(new CreationActionListener(innerNode, list));
					}
					add(addListItem);
				}

			}

			if (node instanceof LeafNode) {
				JMenuItem expandItem = add("Edit value");
				expandItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						applet.getInfoPanel().allowEdition();
					}
				});
			}

			if (node instanceof EmptyNode) {
				JMenuItem expandItem = add("Create this leaf");
				expandItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						((EmptyNode) node).createLeaf((YangInnerNode) path.getParentPath().getLastPathComponent());
						applet.editionPerformed();
						applet.getInfoPanel().allowEdition();
					}
				});
			}
		}

		private class CreationActionListener implements ActionListener {
		
			public YangInnerNode parentNode;
			public YangNode nodeToCreate;
		
			public CreationActionListener(YangInnerNode parentNode, YangNode nodeToCreate) {
				this.parentNode = parentNode;
				this.nodeToCreate = nodeToCreate;
			}
		
			@Override
			public void actionPerformed(ActionEvent e) {
				YangNode newChild = null;
				if (nodeToCreate instanceof LeafListNode){
					newChild = ((LeafListNode) nodeToCreate).cloneBody();
				}
				if (nodeToCreate instanceof ListNode){
					newChild = ((ListNode) nodeToCreate).createNewOccurrence();
				}
				parentNode.addChild(newChild);
				applet.editionPerformed(NodePopupMenu.this.path.pathByAddingChild(newChild));
				applet.getInfoPanel().allowEdition();
			}
		
		}

	}

}
