package yangTree.nodes;

import javax.swing.ImageIcon;

import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;

/**
 * Represents a leaf in a YangTree.
 * 
 * @see YangInnerNode
 * 
 */
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

	/**
	 * Sets the value of this leaf. After this method is invoked, the value will
	 * be <u>immediately</u> checked.
	 */
	public abstract void setValue(String value);

}
