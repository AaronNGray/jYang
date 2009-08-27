package applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import yangTree.nodes.YangLeaf;

/**
 * The panel in which the value of a leaf is displayed and can be edited.
 * 
 */
@SuppressWarnings("serial")
public class ValuePanel extends JPanel {
	
	private JTextField valueField;
	private JButton buttonOK;
	private JButton buttonCancel;
	
	private YangLeaf leaf;
	private YangApplet applet;
	
	public ValuePanel(YangLeaf leaf, YangApplet applet){
		
		this.leaf = leaf;
		this.applet = applet;
		
		setLayout(new GridBagLayout());
		
		valueField = new JTextField();
		setValue();
		valueField.setEditable(false);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(valueField,c);
		
		buttonOK = new JButton("OK");
		buttonOK.addActionListener(new OKActionListener());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(buttonOK,c);
		buttonOK.setVisible(false);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new CancelActionListener());
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		add(buttonCancel,c);
		buttonCancel.setVisible(false);
			
	}
	
	private void setValue(){
		if (leaf.getValue()!=null) {
			valueField = new JTextField(leaf.getValue());
		} else {
			valueField = new JTextField("(No value)");
		}
	}
	
	/**
	 * Updates this panel so it will allow the edition of the value.
	 */
	public void allowEdition(){
		
		valueField.setColumns(valueField.getText().length()+6);
		valueField.setEditable(true);
		valueField.selectAll();
		valueField.addActionListener(new OKActionListener());
		
		buttonCancel.setVisible(true);
		buttonOK.setVisible(true);
		
		validate();
		
	}
	
	/**
	 * Updates this panel so it will forbid the edition of the value.
	 */
	public void forbidEdition(){
		
		valueField.setColumns(valueField.getText().length());
		setValue();
		valueField.setEditable(false);
		
		buttonCancel.setVisible(false);
		buttonOK.setVisible(false);
		
		validate();
		
	}

	private class OKActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			YangLeaf leafNode = (YangLeaf) leaf;
			leafNode.setValue(valueField.getText());
			forbidEdition();
			applet.editionPerformed();
		}
		
	}
	
	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			forbidEdition();
		}
		
	}
	

}
