package applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import yangTree.TreeFiller;
import yangTree.attributes.YangTreePath;
import yangTree.nodes.ListNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;
import yangTree.nodes.RootNode;

/**
 * YangApplet main class. <br>
 * Contains methods to interact with the Netconf manager.
 */
@SuppressWarnings("serial")
public class YangApplet extends JApplet implements TreeSelectionListener {

	private String agentIP;
	
	private JPanel mainPanel;
	
	private RootNode specTree;
	private YangTreeViewer leftTreeViewer = null;
	
	private RootNode dataTree;
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
		
		infoPanel = new InfoPanel();
		
		displaySpecTree();
	}


	private void buildDisplay() {
		
		LinkedList<TreePath> display = null;
		if (leftTreeViewer!=null)
			display = leftTreeViewer.getCurrentDisplay();
		
		int verticalDividerLocation = (int) Math.floor(height * 0.7);
		if (verticalSplitPane!=null)
			verticalDividerLocation = verticalSplitPane.getDividerLocation();
		
		int horizontalDividerLocation = (int) Math.floor(width * 0.5);
		if (horizontalSplitPane!=null)
			horizontalDividerLocation = horizontalSplitPane.getDividerLocation();

		mainPanel = new JPanel();
		setContentPane(mainPanel);

		mainPanel.setLayout(new BorderLayout());

		JScrollPane specTreeView = new JScrollPane(leftTreeViewer);
		JScrollPane dataTreeView = new JScrollPane(rightTreeViewer);
	
		infoView = new JScrollPane(infoPanel);
		
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,specTreeView,dataTreeView);
		horizontalSplitPane.setDividerLocation(horizontalDividerLocation);
		
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, infoView);
		verticalSplitPane.setDividerLocation(verticalDividerLocation);
		mainPanel.add(verticalSplitPane, BorderLayout.CENTER);

		specTreeView.setMinimumSize(new Dimension(100,150));
		dataTreeView.setMinimumSize(new Dimension(100,150));
		infoView.setMinimumSize(new Dimension(width, 50));
		
		if (display!=null)
			leftTreeViewer.setDisplay(display);

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
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendGetRequest(String filter) {
		String requeteAvecPOST = "" + "--A\r\n" + "Content-Disposition: form-data; name=\"source\"\r\n\r\nrunning\r\n" + "--A\r\n"
				+ "Content-Disposition: form-data; name=\"operation\"\r\n\r\nget\r\n" + "--A\r\n" + "Content-Disposition: form-data; name=\"filter\"\r\n\r\n"
				+ filter + "\r\n" + "--A\r\n" + "Content-Disposition: form-data; name=\"type\"\r\n\r\nsubtree\r\n" + "--A\r\n"
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
			leftTreeViewer = new YangSpecTreeViewer(this,specTree);
			leftTreeViewer.addTreeSelectionListener(this);
			buildDisplay();
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the applet so it will display the data tree.
	 * @param path : the path of the node that will be filled and displayed.
	 */
	public void displayDataTree(TreePath path) {
		
		YangNode[] ppath = new YangNode[path.getPath().length-1];
		for (int i=0;i<ppath.length;i++){
			ppath[i] = (YangNode) path.getPath()[i+1];
		}
		String filter = buildNetconfRequestFilter(ppath);
		
		DocumentBuilderFactory docBF = new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();
		try {
			
			Document xmlDoc = docBF.newDocumentBuilder().parse(sendGetRequest(filter));
			
			dataTree = TreeFiller.createDataTree(specTree, path, xmlDoc);
			rightTreeViewer = new YangDataTreeViewer(this,dataTree);
			rightTreeViewer.addTreeSelectionListener(this);
			
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
	 */
	public void displayDataTree(){
		TreePath path = new TreePath(specTree);
		path = path.pathByAddingChild(specTree.getDescendantNodes().getFirst());
		displayDataTree(path);
	}
	

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		YangNode selectedNode = null;
		
		if (e.getSource()==leftTreeViewer){
			selectedNode = (YangNode) leftTreeViewer.getLastSelectedPathComponent();
			infoPanel.setTreeFilled(false);
			if (rightTreeViewer!=null)
				rightTreeViewer.removeSelection();
		} else
		if (e.getSource()==rightTreeViewer){
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

		String[] firstNodePath ;
		if (path[0] instanceof ListNode){
			firstNodePath = ((ListNode) path[0]).xmlFilter(path.length>1);
		} else {
			firstNodePath = path[0].xmlFilter();
		}
		
		return firstNodePath[0] + buildNetconfRequestFilter(newPath) + firstNodePath[1];

	}

}
