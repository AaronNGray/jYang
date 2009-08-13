package yangTree.nodes;

import yangTree.attributes.ValueCheck;

/**
 * Representation of a node that can be checked, i.e. that can present errors or warnings
 */
public interface CheckedYangNode {

	/**
	 * Returns the errors and warnings detected on this leaf, or
	 * <code>null</code> if no check have been performed yet.
	 */
	public ValueCheck getCheck();

}
