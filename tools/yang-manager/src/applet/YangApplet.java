package applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import yangtree.ChoiceStillPresentException;
import yangtree.TreeFiller;
import yangtree.nodes.ListNode;
import yangtree.nodes.RootNode;
import yangtree.nodes.YangNode;

/**
 * Provides the display of the "Yang browsing" applet.<br>
 * Contains methods to interact with the Netconf manager.
 */
@SuppressWarnings("serial")
public class YangApplet extends JApplet implements TreeSelectionListener {

	private static DocumentBuilderFactory documentBuilderFactory = new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();

	private String agentIP;

	private JPanel mainPanel;

	private JPanel bottomPanel;
	private boolean editionInProgress = false;

	private RootNode specTree;
	private YangTreeViewer leftTreeViewer = null;

	private RootNode dataTree;
	private TreePath currentlyDisplayedPath = null;
	private boolean getConfig ;
	private YangTreeViewer rightTreeViewer = null;

	private JScrollPane infoView;
	private InfoPanel infoPanel;

	private JSplitPane verticalSplitPane = null;
	private JSplitPane horizontalSplitPane = null;

	public int height;
	public int width;

	public void init() {
		height = getSize().height;
		width = getSize().width;
		agentIP = getParameter("agentIP");

		infoPanel = new InfoPanel(this);

		displaySpecTree();
	}

	public InfoPanel getInfoPanel() {
		return infoPanel;
	}

	private void buildDisplay() {

		LinkedList<TreePath> display = null;
		if (leftTreeViewer != null)
			display = leftTreeViewer.getCurrentDisplay();

		int verticalDividerLocation = (int) Math.floor(height * 0.7);
		if (verticalSplitPane != null)
			verticalDividerLocation = verticalSplitPane.getDividerLocation();

		int horizontalDividerLocation = (int) Math.floor(width * 0.5);
		if (horizontalSplitPane != null)
			horizontalDividerLocation = horizontalSplitPane.getDividerLocation();

		mainPanel = new JPanel();
		setContentPane(mainPanel);

		mainPanel.setLayout(new BorderLayout());

		JScrollPane specTreeView = new JScrollPane(leftTreeViewer);
		JScrollPane dataTreeView = new JScrollPane(rightTreeViewer);

		infoView = new JScrollPane(infoPanel);

		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, specTreeView, dataTreeView);
		horizontalSplitPane.setDividerLocation(horizontalDividerLocation);

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, infoView);
		verticalSplitPane.setDividerLocation(verticalDividerLocation);
		mainPanel.add(verticalSplitPane, BorderLayout.CENTER);

		specTreeView.setMinimumSize(new Dimension(100, 150));
		dataTreeView.setMinimumSize(new Dimension(100, 150));
		infoView.setMinimumSize(new Dimension(width, 50));

		if (display != null)
			leftTreeViewer.setDisplay(display);

		bottomPanel = new JPanel();
		JButton buttonCancel = new JButton("< Cancel all modifications");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editionInProgress = false;
				displayDataTree(currentlyDisplayedPath,getConfig);
			}
		});
		bottomPanel.add(buttonCancel);
		
		JButton buttonEdit = new JButton("Apply all modifications >");
		buttonEdit.addActionListener(new ApplyModificationsActionListener());
		bottomPanel.add(buttonEdit);
		
		bottomPanel.setVisible(editionInProgress);

		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		validate();

	}

	/**
	 * Send a specific request to the Netconf manager.
	 * 
	 * @param request
	 *            : the request to send.
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendRequestToServer(String request) {
		try {

			URL url = getCodeBase();

			HttpsURLConnection connexion = (HttpsURLConnection) url.openConnection();

			connexion.addRequestProperty("Content-type", "multipart/form-data; boundary=A");

			connexion.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connexion.getOutputStream());
			wr.write(request);
			wr.flush();
			wr.close();

			return connexion.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Send a "get" request to the Netconf manager.
	 * 
	 * @param filter
	 *            : the filter to use in the "get" request.
	 * @param getConfig
	 *            : <code>true</code> if the operation that will be performed is
	 *            "get-config" ; <code>false</code> if the operation that will
	 *            be performed is "get"
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendGetRequest(String filter, boolean getConfig) {
		String operation = getConfig ? "get-config" : "get";
		String requeteAvecPOST = "" + "--A\r\n" + "Content-Disposition: form-data; name=\"source\"\r\n\r\nrunning\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"operation\"\r\n\r\n"+operation+"\r\n" + "--A\r\n" + "Content-Disposition: form-data; name=\"filter\"\r\n\r\n"
				+ filter + "\r\n" + "--A\r\n" + "Content-Disposition: form-data; name=\"type\"\r\n\r\nsubtree\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"noHTML\"\r\n\r\nyes\r\n" + "--A--\r\n";
		return sendRequestToServer(requeteAvecPOST);
	}

	/**
	 * Send a "edit" request to the Netconf manager.
	 * 
	 * @param filter
	 *            : the filter to use in the "edit" request.
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendEditRequest(String filter) {
		String requeteAvecPOST = "" + "--A\r\n" + "Content-Disposition: form-data; name=\"target\"\r\n\r\nrunning\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"default-operation\"\r\n\r\nmerge\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"subtree\"\r\n\r\n" + filter + "\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"error-option\"\r\n\r\nstop-on-error\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"test-option\"\r\n\r\ntest-then-set\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"operation\"\r\n\r\nedit-config\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"noHTML\"\r\n\r\nyes\r\n" + "--A--\r\n";
		return sendRequestToServer(requeteAvecPOST);
	}

	/**
	 * Refreshes the applet so it will display the specifications tree.
	 */
	public void displaySpecTree() {
		try {

			URL url = new URL(getCodeBase() + agentIP + ".yang.byte");
			HttpsURLConnection connexion = (HttpsURLConnection) url.openConnection();
			ObjectInputStream ois = new ObjectInputStream(connexion.getInputStream());
			Object inputObject = ois.readObject();
			ois.close();

			specTree = (RootNode) inputObject;
			leftTreeViewer = new YangSpecTreeViewer(this, specTree);
			leftTreeViewer.addTreeSelectionListener(this);
			buildDisplay();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the applet so it will display the data tree.
	 * 
	 * @param path
	 *            : the path of the node that will be filled and displayed.
	 * @param getConfig
	 *            : <code>true</code> if the operation that will be performed is
	 *            "get-config" ; <code>false</code> if the operation that will
	 *            be performed is "get"
	 */
	public void displayDataTree(TreePath path, boolean getConfig) {

		YangNode[] ppath = new YangNode[path.getPath().length - 1];
		for (int i = 0; i < ppath.length; i++) {
			ppath[i] = (YangNode) path.getPath()[i + 1];
		}
		String filter = buildNetconfRequestFilter(ppath);

		try {

			Document xmlDoc = documentBuilderFactory.newDocumentBuilder().parse(sendGetRequest(filter,getConfig));

			dataTree = TreeFiller.createDataTree(specTree, path, xmlDoc);
			rightTreeViewer = new YangDataTreeViewer(this, dataTree);
			rightTreeViewer.addTreeSelectionListener(this);
			currentlyDisplayedPath = path;
			this.getConfig = getConfig;

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		buildDisplay();
	}

	/**
	 * Refreshes the applet so it will display the entire data tree.
	 * 
	 * @param getConfig
	 *            : <code>true</code> if the operation that will be performed is
	 *            "get-config" ; <code>false</code> if the operation that will
	 *            be performed is "get"
	 */
	public void displayDataTree(boolean getConfig) {
		TreePath path = new TreePath(specTree);
		path = path.pathByAddingChild(specTree.getDescendantNodes().getFirst());
		displayDataTree(path, getConfig);
	}
	
	/**
	 * Refreshes the applet so it will display an empty data tree
	 * @param path
	 *            : the path of the node that will be displayed.
	 */
	public void displayEmptyTree(TreePath path){
		YangNode node = (YangNode) path.getLastPathComponent();
		RootNode root = new RootNode();
		root.setPath(path.getParentPath());
		root.addChild(node.cloneTree());
		dataTree = root;
		dataTree.recheckAll();
		rightTreeViewer = new YangDataTreeViewer(this, dataTree);
		rightTreeViewer.addTreeSelectionListener(this);
		
		currentlyDisplayedPath = path;
		getConfig = true;
		
		buildDisplay();
	}
	
	/**
	 * Refreshes the applet so it will display the entire empty data tree
	 * 
	 */
	public void displayEmptyTree(){
		TreePath path = new TreePath(specTree);
		path = path.pathByAddingChild(specTree.getDescendantNodes().getFirst());
		displayEmptyTree(path);
	}

	/**
	 * Updates the display after an edition have been performed in the data
	 * tree.
	 */
	public void editionPerformed() {
		int selectedRow = rightTreeViewer.getSelectionRows()[0];
		dataTree.recheckAll();
		rightTreeViewer = new YangDataTreeViewer(this, dataTree);
		rightTreeViewer.addTreeSelectionListener(this);
		buildDisplay();
		rightTreeViewer.setSelectionRow(selectedRow);
		rightTreeViewer.scrollRowToVisible(selectedRow);

		editionInProgress = true;
		bottomPanel.setVisible(true);
	}
	
	/**
	 * Updates the display after an edition have been performed in the data
	 * tree, and selects a specific node in the data tree.
	 * @param selectedPath : the path of the node that will be selected.
	 */
	public void editionPerformed(TreePath selectedPath){
		dataTree.recheckAll();
		rightTreeViewer = new YangDataTreeViewer(this, dataTree);
		rightTreeViewer.addTreeSelectionListener(this);
		buildDisplay();
		rightTreeViewer.setSelectionPath(selectedPath);
		rightTreeViewer.scrollPathToVisible(selectedPath);

		editionInProgress = true;
		bottomPanel.setVisible(true);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		YangNode selectedNode = null;

		if (e.getSource() == leftTreeViewer) {
			selectedNode = (YangNode) leftTreeViewer.getLastSelectedPathComponent();
			infoPanel.setTreeFilled(false);
			if (rightTreeViewer != null)
				rightTreeViewer.removeSelection();
		} else if (e.getSource() == rightTreeViewer) {
			selectedNode = (YangNode) rightTreeViewer.getLastSelectedPathComponent();
			infoPanel.setTreeFilled(true);
			leftTreeViewer.removeSelection();
		}

		if (selectedNode == null)
			return;

		selectedNode.buildInfoPanel(infoPanel);
		infoPanel.repaint();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JScrollBar verticalScrollBar = infoView.getVerticalScrollBar();
				verticalScrollBar.setValue(verticalScrollBar.getMinimum());
				JScrollBar horizontalScrollBar = infoView.getHorizontalScrollBar();
				horizontalScrollBar.setValue(verticalScrollBar.getMinimum());
			}
		});
	}

	private static String buildNetconfRequestFilter(YangNode[] path) {

		if (path.length == 0) {
			return "";
		}

		YangNode[] newPath = new YangNode[path.length - 1];
		for (int i = 0; i < newPath.length; i++) {
			newPath[i] = path[i + 1];
		}

		String[] firstNodePath;
		if (path[0] instanceof ListNode) {
			firstNodePath = ((ListNode) path[0]).xmlFilter(path.length > 1);
		} else {
			firstNodePath = path[0].xmlFilter();
		}

		return firstNodePath[0] + buildNetconfRequestFilter(newPath) + firstNodePath[1];

	}
	
	private class ApplyModificationsActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			InputStream reply = null;
			try {
				
				reply = sendEditRequest(dataTree.getXMLRepresentation());
				
			} catch (ChoiceStillPresentException e2) {
				
				infoPanel.displayPlainText("Modifications cannot be applied :\nsome choices remain unsolved.");
				repaint();
				return;
				
			}
			
			try {
				Document replyDocument = documentBuilderFactory.newDocumentBuilder().parse(reply);
				infoPanel.setEditionReplyInfo(replyDocument);
			} catch (SAXException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			}
			editionInProgress = false;
			displayDataTree(currentlyDisplayedPath,getConfig);
			
		}
		
	}

}
