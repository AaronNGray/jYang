package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollBar;

public class ButtonGetValues extends JButton implements ActionListener {
	
	private YangApplet applet;
	
	public ButtonGetValues(YangApplet applet){
		this.applet = applet;
		setText("Get Values");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		applet.getTreeValues();
	}

}
