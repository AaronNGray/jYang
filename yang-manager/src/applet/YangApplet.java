package applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import yangTree.TreeFiller;
import yangTree.attributes.YangTreePath;
import yangTree.nodes.YangNode;
import yangTree.nodes.RootNode;

/**
 * YangApplet main class.
 * <br>
 * Contains methods to interact with the Netconf manager.
 */
@SuppressWarnings("serial")
public class YangApplet extends JApplet implements TreeSelectionListener {

	private boolean isTreeFilled;
	private String agentIP;

	private RootNode tree;
	private YangTree treeViewer;

	private JPanel mainPanel;
	private JScrollPane infoView;
	private JScrollPane treeView;
	private InfoPanel infoPanel;
	public int height;
	public int width;

	public void init() {
		height = getSize().height;
		width = getSize().width;
		agentIP = getParameter("agentIP");
		displaySpecTree();
	}

	public boolean isTreeFilled() {
		return isTreeFilled;
	}

	private void buildDisplay() {

		mainPanel = new JPanel();
		setContentPane(mainPanel);

		mainPanel.setLayout(new BorderLayout());

		JButton button = new BottomButton(this);
		mainPanel.add(button, BorderLayout.PAGE_END);

		treeViewer = new YangTree(tree);
		treeViewer.addTreeSelectionListener(this);
		treeView = new JScrollPane(treeViewer);

		infoPanel = new InfoPanel(isTreeFilled);
		infoView = new JScrollPane(infoPanel);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(infoView);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainPanel.add(splitPane, BorderLayout.CENTER);

		splitPane.setDividerLocation((int) Math.floor(height * 0.7));

		treeView.setMinimumSize(new Dimension(width, 150));
		infoView.setMinimumSize(new Dimension(width, 50));

		validate();

	}

	/**
	 * Send a specific request to the Netconf manager.
	 * @param request : the request to send.
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendRequestToServer(String request) {
		try {

			URL url = new URL("https://localhost:8888/");

			HttpsURLConnection connexion = (HttpsURLConnection) url
					.openConnection();

			connexion.addRequestProperty("Content-type",
					"multipart/form-data; boundary=A");

			connexion.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connexion
					.getOutputStream());
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
	 * @param filter : the filter to use in the "get" request.
	 * @return an InputStream with the response of the manager.
	 */
	private InputStream sendGetRequest(String filter) {
		String requeteAvecPOST = ""
				+ "--A\r\n"
				+ "Content-Disposition: form-data; name=\"source\"\r\n\r\nrunning\r\n"
				+ "--A\r\n"
				+ "Content-Disposition: form-data; name=\"operation\"\r\n\r\nget\r\n"
				+ "--A\r\n"
				+ "Content-Disposition: form-data; name=\"filter\"\r\n\r\n"
				+ filter
				+ "\r\n"
				+ "--A\r\n"
				+ "Content-Disposition: form-data; name=\"type\"\r\n\r\nsubtree\r\n"
				+ "--A\r\n"
				+ "Content-Disposition: form-data; name=\"noHTML\"\r\n\r\nyes\r\n"
				+ "--A--\r\n";
		return sendRequestToServer(requeteAvecPOST);
	}

	/**
	 * Refreshes the applet so it will display the specifications tree.
	 */
	public void displaySpecTree() {
		try {
			URL url = new URL("https://localhost:8888/" + agentIP
					+ ".yang.byte");
			HttpsURLConnection connexion = (HttpsURLConnection) url
					.openConnection();
			ObjectInputStream ois = new ObjectInputStream(connexion
					.getInputStream());
			Object inputObject = ois.readObject();
			ois.close();
			tree = (RootNode) inputObject;
			isTreeFilled = false;
			buildDisplay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the applet so it will display the tree filled with values.
	 */
	public void displayTreeValues() {
		RootNode treeFilled = new RootNode("Yang Data");

		/*
		 * ! Méthode modifiée pour les tests !
		 */
		String xmlCode = "<rpc-reply><data><netconf><system>" +
				"<listT> <leafT>B</leafT> </listT>" +
				"<listT> <leafT>a22</leafT> </listT>"+
				"<listT> <leafT>e</leafT> </listT>"+
				"</system></netconf></data></rpc-reply>";

		for (YangNode node : tree.getNodes()) {
			try {
				DocumentBuilderFactory docBF = new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();
				//Document xmlDoc = docBF.newDocumentBuilder().parse(sendGetRequest(node.xmlFilter()));
				Document xmlDoc = docBF.newDocumentBuilder().parse(new InputSource(new StringReader(xmlCode)));
				treeFilled.addContent(TreeFiller.fillTree(node, xmlDoc,new YangTreePath(treeFilled)));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		TreeFiller.setPendingValues();
		tree = treeFilled;
		isTreeFilled = true;
		buildDisplay();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		YangNode selectedNode = (YangNode) treeViewer
				.getLastSelectedPathComponent();

		if (selectedNode == null)
			return;

		selectedNode.buildInfoPanel(infoPanel);
		infoPanel.repaint();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JScrollBar verticalScrollBar = infoView.getVerticalScrollBar();
				verticalScrollBar.setValue(verticalScrollBar.getMinimum());
				JScrollBar horizontalScrollBar = infoView
						.getHorizontalScrollBar();
				horizontalScrollBar.setValue(verticalScrollBar.getMinimum());
			}
		});

	}

}
