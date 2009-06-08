import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import datatree.*;

public class YangView2D extends JPanel implements TreeSelectionListener,
		TreeExpansionListener, ActionListener {

	private JTree tree;
	private YangJavaTreeModel model;
	private YangController controller;

	public YangView2D(DataNode m) {
		model = new YangJavaTreeModel(m);
	}

	public JPanel createManagerInterface() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add("Center", createSceneGraph());
		Container c = new Container();
		c.setLayout(new GridLayout());
		JButton get = new JButton("Get-Config");
		get.setActionCommand("get-config");
		c.add(get);
		JButton edit = new JButton("Edit-Config");
		edit.setActionCommand("edit-config");
		c.add(edit);

		get.addActionListener(this);
		edit.addActionListener(this);

		jp.add("South", c);
		return jp;
	}

	public JScrollPane createSceneGraph() {

		tree = new JTree(model);
		tree.setAutoscrolls(true);
		tree.addTreeSelectionListener(this);
		tree.addTreeExpansionListener(this);
		ImageIcon leafIcon = createImageIcon("images/leaf.png");
		if (leafIcon != null) {
			ImageIcon dirCloseIcon = createImageIcon("images/close.gif");
			if (dirCloseIcon != null) {
				ImageIcon dirOpenIcon = createImageIcon("images/open.gif");
				if (dirOpenIcon != null) {
					DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
					renderer.setLeafIcon(leafIcon);
					renderer.setOpenIcon(dirOpenIcon);
					renderer.setClosedIcon(dirCloseIcon);
					tree.setCellRenderer(renderer);
				}

			}
		}

		tree.setEditable(true);
		JScrollPane jsp = new JScrollPane(tree);
		return jsp;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = YangView2D.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	TreePath tp = null;

	public void valueChanged(TreeSelectionEvent e) {
		tp = e.getPath();
		System.out.println(tp);

	}

	public void treeCollapsed(TreeExpansionEvent event) {
	}

	public void treeExpanded(TreeExpansionEvent event) {
	}

	public void setController(YangController controller) {

		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		if (com.equals("get-config")) {
			if (tp != null) {

				Object[] ps = tp.getPath();

				controller.request(ps);

			} else if (com.equals("edit-config")) {

			}

		}
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
			} else
				return 0;
		}

		public int getIndexOfChild(Object parent, Object child) {
			// TODO Auto-generated method stub
			return 0;
		}

		public Object getRoot() {
			return model;
		}

		public boolean isLeaf(Object node) {
			return node instanceof LeafNode || node instanceof LeafListNode;
		}

		public void removeTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub

		}

		public void valueForPathChanged(TreePath path, Object newValue) {
			// TODO Auto-generated method stub

		}

	}

}
