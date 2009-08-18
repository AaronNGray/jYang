package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import yangTree.nodes.LeafListNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.YangNode;

/**
 * The pop-up menu displayed when right-clicking on a node of a <code>YangTree</code>.
 * 
 */
@SuppressWarnings("serial")
public class NodePopupMenu extends JPopupMenu {

	private YangTreeViewer tree;
	private YangNode node;
	private int x;
	private int y;

	public NodePopupMenu(YangTreeViewer tree, YangNode node, int x, int y) {
		super();
		this.tree = tree;
		this.node = node;
		this.x = x;
		this.y = y;
		buildMenu();
		show(tree, x, y);
	}

	private void buildMenu() {
		if (!(node instanceof LeafListNode)) {
			JMenuItem expandItem = add("Refresh values");
			expandItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.updateTree(tree.getPathForLocation(x, y));
				}
			});
		}
		if (node instanceof YangInnerNode){
			
		}
	}

}
