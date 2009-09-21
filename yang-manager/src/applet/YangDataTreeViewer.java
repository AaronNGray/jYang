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

import yangtree.nodes.CaseNode;
import yangtree.nodes.ChoiceNode;
import yangtree.nodes.ContainerNode;
import yangtree.nodes.EmptyNode;
import yangtree.nodes.LeafListNode;
import yangtree.nodes.LeafNode;
import yangtree.nodes.ListNode;
import yangtree.nodes.RootNode;
import yangtree.nodes.YangInnerNode;
import yangtree.nodes.YangNode;

/**
 * Provides a display of a Yang data tree.
 *
 */
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

				if (node instanceof ContainerNode || node instanceof ListNode) {
					YangInnerNode innerNode = (YangInnerNode) node;
					LinkedList<YangNode> specDescendants = innerNode.getSpecificationNode().getDescendantNodes();
					LinkedList<LeafListNode> leafListChildren = new LinkedList<LeafListNode>();
					LinkedList<ListNode> listChildren = new LinkedList<ListNode>();
					for (YangNode node : specDescendants) {
						if (node instanceof LeafListNode)
							leafListChildren.add((LeafListNode) node);
						if (node instanceof ListNode)
							listChildren.add((ListNode) node);
					}

					if (leafListChildren.size() > 0) {
						JMenu addLeafListItem = new JMenu("Add leaf-list occurrence");
						for (LeafListNode leafList : leafListChildren) {
							JMenuItem item = addLeafListItem.add(leafList.getName());
							item.addActionListener(new CreationActionListener(innerNode, leafList));
						}
						add(addLeafListItem);
					}

					if (listChildren.size() > 0) {
						JMenu addListItem = new JMenu("Add list occurrence");
						for (ListNode list : listChildren) {
							JMenuItem item = addListItem.add(list.getName());
							item.addActionListener(new CreationActionListener(innerNode, list));
						}
						add(addListItem);
					}
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

			if (node instanceof CaseNode) {
				JMenuItem item = add("Choose this case");
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChoiceNode choice = (ChoiceNode) path.getParentPath().getLastPathComponent();
						YangInnerNode parentNode = (YangInnerNode) path.getParentPath().getParentPath().getLastPathComponent();
						parentNode.getDescendantNodes().remove(choice);
						parentNode.getDescendantNodes().addAll(((CaseNode) node).getDescendantNodes());
						applet.editionPerformed(path.getParentPath().getParentPath());
					}
				});
			}

			if (node instanceof ListNode || node instanceof LeafListNode) {
				if (!(path.getParentPath().getLastPathComponent() instanceof RootNode)) {
					JMenuItem item = add("Delete this node");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							YangInnerNode parent = (YangInnerNode) path.getParentPath().getLastPathComponent();
							parent.getDescendantNodes().remove(node);
							parent.markChildToBeDeleted(node);
							applet.editionPerformed(path.getParentPath());
						}
					});
				}
			}
			
			if (node instanceof YangInnerNode){
				
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
			
			if (node instanceof EmptyNode){
				JMenuItem item = add("Create this leaf");
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						LeafNode leaf = ((EmptyNode) node).createLeaf();
						YangInnerNode parent = (YangInnerNode) path.getParentPath().getLastPathComponent();
						parent.addChild(leaf);
						parent.getDescendantNodes().remove(node);
						applet.editionPerformed(path.getParentPath().pathByAddingChild(leaf));
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
				if (nodeToCreate instanceof LeafListNode) {
					newChild = ((LeafListNode) nodeToCreate).cloneBody();
				}
				if (nodeToCreate instanceof ListNode) {
					newChild = ((ListNode) nodeToCreate).cloneTree();
				}
				parentNode.addChild(newChild);
				applet.editionPerformed(NodePopupMenu.this.path.pathByAddingChild(newChild));
				applet.getInfoPanel().allowEdition();
			}

		}

	}

}
