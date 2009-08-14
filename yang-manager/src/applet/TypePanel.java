package applet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import yangTree.attributes.LeafType;
import yangTree.attributes.builtinTypes.BuiltinType;

/**
 * The panel that displays informations about a {@link LeafType}.
 *
 */
@SuppressWarnings("serial")
public class TypePanel extends JPanel {

	private JTextField typeName;
	private JButton button;
	private JEditorPane typeDescription;
	

	public TypePanel(LeafType type) {
		
		setLayout(new GridBagLayout());
		
		String name = Util.cutString(type.getName(),100);
		if (type.getBuiltinType().hasRestrictions() && type.isBuiltInType()){
			name += " (with restrictions)";
		}

		typeName = new JTextField(name);
		typeName.setEditable(false);
		typeName.setMargin(new Insets(0,3,0,3));
		
		button = new JButton("+");
		button.setPreferredSize(new Dimension(19,19));
		button.setMargin(new Insets(1,1,1,1));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performButtonAction();
			}
		});
		
		buildDescription(type);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		add(typeName,c);
		
		c.gridx = 1;
		c.gridy = 0;
		add(button,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		add(typeDescription,c);

	}

	private void performButtonAction() {
		boolean isExpanded = typeDescription.isVisible();
		if (isExpanded) {
			typeDescription.setVisible(false);
			button.setText("+");
		} else {
			typeDescription.setVisible(true);
			button.setText("-");
		}
		validate();
	}

	private void buildDescription(LeafType type) {

		BuiltinType builtinType = type.getBuiltinType();
		String htmlCode = "<font size=\"3\">";

		if (!type.isBuiltInType()) {
			htmlCode += "<b>Name : </b>" + type.getName() + "<br>";
			if (type.getDescription() != null) {
				htmlCode += "<b>Description : </b>" + Util.wrapHTMLline(type.getDescription(),120, 25)
						+ "<br>";
			}
			if (type.getUnits() != null) {
				htmlCode += "<b>Units : </b>" + type.getUnits() + "<br>";
			}
			if (type.getDefaultValue() != null) {
				htmlCode += "<b>Default Value : </b>" + type.getDefaultValue()
						+ "<br>";
			}
		}

		htmlCode += "<b>Built-in type : </b>" + builtinType.getContent() + "<br>";
		if (builtinType.hasRestrictions()) {
			htmlCode += "<b>Restrictions : </b>"
					+ builtinType.getRestrictionsDescription();
		}
		
		htmlCode += "</font>";

		
		typeDescription = new JEditorPane();
		typeDescription.setEditable(false);
		typeDescription.setContentType("text/html");
		typeDescription.setText(htmlCode);
		
		
		typeDescription.setVisible(false);
		

	}
}
