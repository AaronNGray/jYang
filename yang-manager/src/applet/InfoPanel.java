package applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import yangTree.NetconfReplyMalformedException;
import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;
import yangTree.nodes.YangLeaf;

/**
 * The bottom panel of the applet, used to display all the informations about a
 * node.<br>
 * <br>
 * This panel is made of a <code>JLabel</code> title and a undefined number of
 * lines. Each line is a <code>JLabel</code> followed by a
 * <code>JComponent</code>. This class provides methods to add different types
 * of lines.
 * 
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	private YangApplet applet;

	private JLabel title;
	private boolean isTreeFilled = false;
	private int currentRow = 1;

	private static final Insets insets = new Insets(3, 0, 3, 0);

	/**
	 * Creates a new InfoPanel with default display.
	 * 
	 * @param applet : The <code>YangApplet</code> that contains this <code>InfoPanel</code>.
	 */
	public InfoPanel(YangApplet applet) {
		super();
		this.applet = applet;
		setLayout(new GridBagLayout());
		setHelpInfo();
	}

	/**
	 * Display the information contained in the reply of a netconf agent to a
	 * "edit-config" operation.
	 * 
	 * @param xmlDocument
	 *            : the xml-formatted netconf reply. This reply <u>must</u> be a
	 *            reply to a "edit-config" operation.
	 */
	public void setEditionReplyInfo(Document xmlDocument) {

		clean();
		Node root = xmlDocument.getFirstChild();
		if (!root.getNodeName().equals("rpc-reply")) {
			throw new NetconfReplyMalformedException("Expected \"rpc-reply\" node not present");
		}
		Node rpcErrorNode = null;
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("ok")) {
				setTitleText("Modifications applied sucessfully.");
				return;
			}
			if (list.item(i).getNodeName().equals("rpc-error"))
				rpcErrorNode = list.item(i);
		}

		if (rpcErrorNode == null)
			throw new NetconfReplyMalformedException("Expected \"ok\" or \"rpc-error\" node not present.");

		setTitleText("Error when trying to apply modifications :");
		list = rpcErrorNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			NodeList children = node.getChildNodes();
			for (int j = 0; j < children.getLength(); j++) {
				addTextArea(node.getNodeName(), children.item(j).getNodeValue());
			}
		}

	}

	public boolean isTreeFilled() {
		return isTreeFilled;
	}

	public void setTreeFilled(boolean isTreeFilled) {
		this.isTreeFilled = isTreeFilled;
	}

	/**
	 * Sets the text of the title.
	 */
	public void setTitleText(String text) {
		title.setText(text);
	}

	/**
	 * Removes all the lines of this InfoPanel and remove any title text.
	 */
	public void clean() {
		currentRow = 1;
		removeAll();
		title = new JLabel();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = insets;
		add(title, c);
	}
	
	/**
	 * Display a given text.
	 * @param text : the text to display.
	 */
	public void displayPlainText(String text){
		clean();
		String[] lines = text.split("\n");
		title.setText(lines[0]);
		
		for (int i=1;i<lines.length;i++){
			JLabel label = new JLabel(lines[i]);
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = i;
			c.insets = insets;
			add(label, c);
		}
		
	}

	/**
	 * Sets this InfoPanel to its default display.
	 */
	public void setHelpInfo() {
		displayPlainText("Select a node to display more information about it ;\nRight-click to perform operations");
	}

	/**
	 * Adds a non-editable <code>JTextField</code> line.
	 * 
	 * @param name
	 *            : the name of the argument of the line.
	 * @param value
	 *            : the text that will be displayed in the JTextField.
	 */
	public void addTextField(String name, String value) {
		JLabel label = new JLabel(name + " : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = currentRow;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		JTextField field = new JTextField(value);
		field.setEditable(false);
		field.setMargin(new Insets(0, 3, 0, 3));
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(field, c);

		currentRow++;
	}

	/**
	 * Adds a non-editable <code>JTextArea</code> line.
	 * 
	 * @param name
	 *            : the name of the argument of the line.
	 * @param value
	 *            : the text that will be displayed in the JTextArea.
	 */
	public void addTextArea(String name, String value) {
		JLabel label = new JLabel(name + " : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = currentRow;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		JTextArea area = new JTextArea(value);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(area, c);

		currentRow++;
	}

	/**
	 * Adds a line displaying a type.
	 * 
	 * @param type
	 *            : The type to be displayed.
	 * @see TypePanel
	 */
	public void addTypePanel(LeafType type) {
		JLabel label = new JLabel("Type : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = currentRow;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		TypePanel typePanel = new TypePanel(type);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(typePanel, c);

		currentRow++;
	}

	/**
	 * Adds a line displaying a value of a leaf.
	 * 
	 * @param leaf
	 *            : The leaf which value will be displayed.
	 * @see ValuePanel
	 */
	public void addValuePanel(YangLeaf leaf) {
		JLabel label = new JLabel("Value : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = currentRow;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		ValuePanel valuePanel = new ValuePanel(leaf, applet);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(valuePanel, c);

		currentRow++;
	}

	/**
	 * Adds a line displaying errors or warnings if such exists.
	 * 
	 * @param check
	 *            : The ValueCheck to be displayed.
	 */
	public void addValueCheckPanel(ValueCheck check) {
		if (check != null && !check.isOk()) {
			if (check.isCritical()) {
				addTextArea("Errors", check.toString());
			} else {
				addTextArea("Warnings", check.toString());
			}
		}
	}

	/**
	 * Updates the display so it will allow the edition of the value of the
	 * current leaf.
	 */
	public void allowEdition() {
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i) instanceof ValuePanel) {
				((ValuePanel) getComponent(i)).allowEdition();
			}
		}
	}

}
