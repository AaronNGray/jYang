package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollBar;

public class BottomButton extends JButton implements ActionListener {
	
	private YangApplet applet;
	
	public BottomButton(YangApplet applet){
		this.applet = applet;
		setText();
		addActionListener(this);
	}
	
	private void setText(){
		if (applet.isTreeFilled()){
			setText("Return to specifications");
		} else {
			setText("Retrieve Values");
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (applet.isTreeFilled()){
			applet.displaySpecTree();
		} else {
			applet.displayTreeValues();
		}
	}

}
