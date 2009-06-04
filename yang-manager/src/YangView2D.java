import java.awt.Dimension;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import datatree.*;

public class YangView2D extends JPanel implements TreeSelectionListener,
		TreeExpansionListener {

	private JTree tree;
	private YangJavaTreeModel model;

	public YangView2D(DataNode m) {
		model = new YangJavaTreeModel(m);
	}

	public JScrollPane createSceneGraph() {
		
		tree = new JTree(model);
		tree.setAutoscrolls(true);
		tree.addTreeSelectionListener(this);
		tree.addTreeExpansionListener(this);

		tree.setEditable(true);
		JScrollPane jsp = new JScrollPane(tree);
		return jsp;
	}

	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getPath());

	}

	private class YangJavaTreeModel implements TreeModel {

		private DataNode model;

		public YangJavaTreeModel(DataNode m) {
			model = m;
		}

		public void addTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub

		}

		public Object getChild(Object parent, int index) {
			Enumeration<DataNode> edn = null;
			if (parent instanceof ContainerNode) {
				ContainerNode cn = (ContainerNode) parent;
				edn = cn.getNodes().elements();
			} else if (parent instanceof ListNode) {
				ListNode ln = (ListNode) parent;
				edn = ln.getNodes().elements();
			} else if (parent instanceof LeafListNode) {
				LeafListNode ll = (LeafListNode) parent;
				edn = ll.getNodes().elements();
			} else
				return null;

			DataNode dn = null;
			for (int i = 0; edn.hasMoreElements() && i <= index; i++) {
				dn = edn.nextElement();
			}
			return dn;
		}

		public int getChildCount(Object parent) {
			if (parent instanceof ContainerNode) {
				ContainerNode cn = (ContainerNode) parent;
				return cn.getNodes().size();
			} else if (parent instanceof ListNode) {
				ListNode ln = (ListNode) parent;
				return ln.getNodes().size();
			} else if (parent instanceof LeafListNode) {
				LeafListNode ll = (LeafListNode) parent;
				return ll.getNodes().size();
			} else
				return 0;
		}

		public int getIndexOfChild(Object parent, Object child) {
			// TODO Auto-generated method stub
			return 0;
		}

		public Object getRoot() {
			// TODO Auto-generated method stub
			return model;
		}

		public boolean isLeaf(Object node) {
			return node instanceof LeafNode;
		}

		public void removeTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub

		}

		public void valueForPathChanged(TreePath path, Object newValue) {
			// TODO Auto-generated method stub

		}

	}

	public void treeCollapsed(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("collapsed " + event.getPath());
	}

	public void treeExpanded(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("expanded");

	}

}
