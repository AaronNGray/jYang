package applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;

/**
 * The bottom panel of the applet, used to display all the informations about a
 * node.<br>
 * <br>
 * This panel is made of a <code>JLabel</code> title and a undefined number of lines. Each
 * line is a <code>JLabel</code> followed by a <code>JComponent</code>. This class provides methods to add
 * different types of lines.
 * 
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	private JLabel title;
	private boolean isTreeFilled = false;
	private int currentRow = 1;

	private static final Insets insets = new Insets(3, 0, 3, 0);

	/**
	 * Creates a new InfoPanel with default display.
	 * 
	 * @param isTreeFilled
	 *            : <code>true</code> if the displayed tree is filled,
	 *            <code>false</code> otherwise
	 */
	public InfoPanel() {
		super();
		setLayout(new GridBagLayout());
		setHelpInfo();
	}

	public boolean isTreeFilled() {
		return isTreeFilled;
	}
	
	public void setTreeFilled(boolean isTreeFilled){
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
	 * Sets this InfoPanel to its default display.
	 */
	public void setHelpInfo() {
		clean();
		title.setText("Select a node to display more information about it ;");

		String helpText = "Right-click to perform operations";
		
		JLabel label = new JLabel(helpText);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = insets;
		add(label, c);
	}

	/**
	 * Adds a non-editable <code>JTextField</code> line.
	 * @param name : the name of the argument of the line.
	 * @param value : the text that will be displayed in the JTextField.
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
	 * @param name : the name of the argument of the line.
	 * @param value : the text that will be displayed in the JTextArea.
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
	 * @param type : The type to be displayed.
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
	 * Adds a line displaying errors or warnings if such exists.
	 * @param check : The ValueCheck to be displayed.
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

}
