package applet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import yangTree.attributes.LeafType;
import yangTree.nodes.DataNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;
import yangTree.nodes.RootNode;

public class InfoPanel extends JPanel {

	private JLabel titre;
	private boolean isTreeFilled = false;
	
	private static final Insets insets = new Insets(3,0,3,0);

	public InfoPanel(boolean isTreeFilled) {
		super();
		this.isTreeFilled = isTreeFilled;
		setLayout(new GridBagLayout());
		setHelpInfo();
	}

	private void clean() {
		removeAll();
		titre = new JLabel();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = insets;
		add(titre, c);
	}

	private void setHelpInfo() {
		clean();
		titre.setText("Select a node to display more information about it ;");

		String helpText;
		if (isTreeFilled) {
			helpText = "Click on \"Return to specifications\" button to empty the tree of its values.";
		} else {
			helpText = "Click on \"Retrieve Values\" button to retrieve leaves values.";
		}
		JLabel label = new JLabel(helpText);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = insets;
		add(label, c);
		repaint();
	}

	private void setTreeNodeInfo(DataNode node) {
		clean();
		titre.setText(node.getNodeType() + " : " + node.getName());

		if (node.getNameSpace() != null) {
			buildTextField(1, "Namespace", node.getNameSpace().getNameSpace(),
					false);
		}

		if (node instanceof ListNode) {
			ListNode list = (ListNode) node;
			if (isTreeFilled) {
				buildTextField(2, list.getKeyName(), list.getKeyValue(), false);
			} else {
				buildTextField(2, "Key", list.getKeyName(), false);
			}
		}
		repaint();
	}

	private void setLeafInfo(DataNode node) {
		clean();
		titre.setText(node.getNodeType() + " : " + node.getName());

		if (node instanceof LeafNode) {
			
			LeafNode leaf = (LeafNode) node;
			buildTypePanel(1,leaf.getTypeDef());
			
			int row = 2;
			if (isTreeFilled){
				buildTextField(row, "Value", leaf.getValue(), false);
				row++;
			}
			
			if (leaf.isMandatory()) {
				buildTextField(row, "Mandatory", "Yes", false);
				row++;
			}
			if (leaf.getDefaultValue() != null) {
				buildTextField(row, "Default value",
						leaf.getDefaultValue(), false);
				row++;
			}
			if (leaf.getTypeDef().getDefaultValue() != null) {
				buildTextField(row, "Default value", leaf.getTypeDef()
						.getDefaultValue(), false);
				row++;
			}
			if (leaf.getDescription() != null) {
				String description = leaf.getDescription();
				Pattern pattern = Pattern.compile("[\n\t]");
				Matcher matcher = pattern.matcher(description);
				description = matcher.replaceAll("");
				buildTextArea(row, "Description", description, false);
			}

		} else {
			
			LeafListNode leafList = (LeafListNode) node;
			String type = leafList.getType();
			String value = leafList.getValue();

			buildTextField(1, "Type", type, false);

			if (value == null) {
				value = "(No defined value)";
			}
			if (isTreeFilled)
				buildTextField(2, "Value", value, false);

		}
		repaint();
	}

	private void buildTextField(int row, String name, String value,
			boolean isEditable) {
		JLabel label = new JLabel(name + " : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = row;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		JTextField field = new JTextField(value);
		field.setEditable(isEditable);
		field.setMargin(new Insets(0,3,0,3));
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(field, c);
	}

	private void buildTextArea(int row, String name, String value,
			boolean isEditable) {
		JLabel label = new JLabel(name + " : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = row;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		JTextArea area = new JTextArea(value);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(isEditable);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(area, c);

	}
	
	private void buildTypePanel(int row, LeafType type){
		JLabel label = new JLabel("Type : ");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = row;
		c.insets = insets;
		c.anchor = GridBagConstraints.NORTHEAST;
		add(label, c);

		TypePanel typePanel = new TypePanel(type);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(typePanel, c);
		
	}

	public void setInfo(DataNode node) {
		if (node instanceof RootNode) {
			setHelpInfo();
		} else if (node instanceof LeafNode || node instanceof LeafListNode) {
			setLeafInfo(node);
		} else {
			setTreeNodeInfo(node);
		}
	}

}
