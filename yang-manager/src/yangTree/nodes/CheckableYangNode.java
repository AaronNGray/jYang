package yangTree.nodes;

import yangTree.attributes.ValueCheck;

/**
 * Representation of a node that can be checked, i.e. that can present errors or warnings.<br>
 * After a yang data tree has been totally built, the <code>check()</code> method should be called on each <code>CheckableNode</code>.
 */
public interface CheckableYangNode {

	/**
	 * Returns the errors and warnings detected on this leaf, or
	 * <code>null</code> if no check have been performed yet.
	 */
	public ValueCheck getCheck();
	
	/**
	 * Checks the node for possible errors or warnings and consequently modify the ValueCheck.
	 */
	public void check();

}
