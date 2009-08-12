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
import yangTree.nodes.DataLeaf;
import yangTree.nodes.DataNode;
import yangTree.nodes.LeafListNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;
import yangTree.nodes.RootNode;

public class InfoPanel extends JPanel {

	private JLabel titre;
	private boolean isTreeFilled = false;

	private static final Insets insets = new Insets(3, 0, 3, 0);

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

		int row = 2;
		if (node instanceof ListNode) {
			ListNode list = (ListNode) node;
			buildTextField(row, "Keys", list.getKeysRepresentation(), false);
			row++;

			if (list.getCheck() != null && !list.getCheck().isOk()) {
				if (list.getCheck().isCritical()) {
					buildTextArea(row, "Errors", list.getCheck().toString(),
							false);
				} else {
					buildTextArea(row, "Warnings", list.getCheck().toString(),
							false);
				}
			}

		}
		repaint();
	}

	private void setLeafInfo(DataLeaf node) {
		clean();
		titre.setText(node.getNodeType() + " : " + node.getName());

		buildTypePanel(1, node.getType());

		int row = 2;
		String value = node.getValue();
		if (isTreeFilled) {
			if (value == null) {
				value = "(No value)";
			}
			buildTextField(row, "Value", value, false);
		}
		row++;
		if (node.getCheck() != null && !node.getCheck().isOk()) {
			if (node.getCheck().isCritical()) {
				buildTextArea(row, "Errors", node.getCheck().toString(), false);
			} else {
				buildTextArea(row, "Warnings", node.getCheck().toString(),
						false);
			}
			row++;
		}

		if (node instanceof LeafNode) {

			LeafNode leaf = (LeafNode) node;

			if (leaf.isMandatory()) {
				buildTextField(row, "Mandatory", "Yes", false);
				row++;
			}
			if (leaf.getDefaultValue() != null) {
				buildTextField(row, "Default value", leaf.getDefaultValue(),
						false);
				row++;
			}
			if (leaf.getType().getDefaultValue() != null) {
				buildTextField(row, "Default value", leaf.getType()
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
		field.setMargin(new Insets(0, 3, 0, 3));
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

	private void buildTypePanel(int row, LeafType type) {
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
		} else if (node instanceof DataLeaf) {
			setLeafInfo((DataLeaf) node);
		} else {
			setTreeNodeInfo(node);
		}
	}

}
