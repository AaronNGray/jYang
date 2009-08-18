package yangTree.nodes;

import java.util.LinkedList;

import javax.swing.tree.TreePath;

import applet.InfoPanel;

import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;

/**
 * Represents a leaf in a YangTree.
 * 
 * @see YangInnerNode
 * 
 */
@SuppressWarnings("serial")
public abstract class YangLeaf extends YangNode implements CheckedYangNode {

	protected String value = null;
	protected LeafType type;
	protected ValueCheck check = null;

	/**
	 * Returns the value of this leaf if such exists, otherwise returns
	 * <code>null</code>.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the type of this leaf.
	 */
	public LeafType getType() {
		return type;
	}

	public ValueCheck getCheck() {
		return check;
	}
	
	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		
		panel.addTypePanel(type);
		
		String value = getValue();
		if (panel.isTreeFilled()) {
			if (value == null) {
				value = "(No value)";
			}
			panel.addTextField("Value", value);
		}
	}

	/**
	 * Sets the value of this leaf. After this method is invoked, the value will
	 * be <u>immediately</u> checked.
	 */
	public abstract void setValue(String value);

}
