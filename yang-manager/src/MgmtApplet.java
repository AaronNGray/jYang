import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.sun.net.ssl.HttpsURLConnection;

import datatree.*;


public class MgmtApplet extends Applet implements ActionListener, TreeSelectionListener, TreeExpansionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3165057795551915564L;
	private JTree tree;
	private YangJavaTreeModel model;
	private String response = "";
	
	public void init() {
		JPanel jp = createManagerInterface();
		response = getParameter("reply");
		System.out.println(response);
		add(jp);
		setVisible(true);
	}
	
	
	
	public JPanel createManagerInterface() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		
		JTextField jtf = new JTextField();
		jp.add("North",jtf);
		
		jtf.setText(getCodeBase().toString());
		
		jp.add("Center", createSceneGraph());
		Container c = new Container();
		c.setLayout(new GridLayout());
		JButton get = new JButton("Get Config");
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
		/*
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
*/
		tree.setEditable(true);
		JScrollPane jsp = new JScrollPane(tree);
		return jsp;
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

	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals("get-config")){
			System.out.println("hello");
		}
		
		
	}

	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void treeCollapsed(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void treeExpanded(TreeExpansionEvent event) {
		// TODO Auto-generated method stub
		
	}

	

}
