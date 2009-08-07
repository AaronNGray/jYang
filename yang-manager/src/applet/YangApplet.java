package applet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
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
import yangTree.nodes.DataNode;
import yangTree.nodes.RootNode;

public class YangApplet extends JApplet implements TreeSelectionListener {

	private boolean isTreeFilled;
	private String agentIP;

	private RootNode tree;
	private YangTreeViewer treeViewer;

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

		treeViewer = new YangTreeViewer(tree);
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

	private InputStream sendRequestToServer(String requete) {
		try {

			URL url = new URL("https://localhost:8888/");

			HttpsURLConnection connexion = (HttpsURLConnection) url
					.openConnection();

			connexion.addRequestProperty("Content-type",
					"multipart/form-data; boundary=A");

			connexion.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connexion
					.getOutputStream());
			wr.write(requete);
			wr.flush();
			wr.close();

			return connexion.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

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

	public void displayTreeValues() {
		RootNode treeFilled = new RootNode("Yang Data");

		String xmlCode = "<rpc-reply><data><netconf><security><rbac><roles><role><id>0</id><junior-roles><junior-role id=\"0\"/></junior-roles></role></roles></rbac></security></netconf></data></rpc-reply>";

		for (DataNode node : tree.getNodes()) {
			try {
				DocumentBuilderFactory docBF = new com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl();
				// Document xmlDoc =
				// docBF.newDocumentBuilder().parse(sendGetRequest(node.xmlFilter()));
				Document xmlDoc = docBF.newDocumentBuilder().parse(
						new InputSource(new StringReader(xmlCode)));
				treeFilled.addContent(TreeFiller.fillTree(node, xmlDoc));
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
		tree = treeFilled;
		isTreeFilled = true;
		buildDisplay();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		DataNode selectedNode = (DataNode) treeViewer
				.getLastSelectedPathComponent();

		if (selectedNode == null)
			return;

		infoPanel.setInfo(selectedNode);

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
