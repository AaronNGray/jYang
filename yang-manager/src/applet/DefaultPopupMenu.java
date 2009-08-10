package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class DefaultPopupMenu extends JPopupMenu {
	
	private YangTree tree;
	
	public DefaultPopupMenu(YangTree tree, int x, int y){
		super();
		this.tree = tree;
		buildMenu();
		show(tree, x, y);
	}
	
	private void buildMenu(){
		JMenuItem expandItem = add("Expand all");
		expandItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				tree.expandAll();
			}
		});
		
		JMenuItem collapseItem = add("Collapse all");
		collapseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				tree.collapseAll();
			}
		});
	}
	
	

}
