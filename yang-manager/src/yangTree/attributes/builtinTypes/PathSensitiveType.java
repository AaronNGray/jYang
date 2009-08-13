package yangTree.attributes.builtinTypes;

import yangTree.attributes.YangTreePath;

/**
 * Represents a Built-in Type that needs to know the path of the leaf in which
 * it is applied.<br>
 * <br>
 * <b>Warning :</b> PathSensitiveTypes must be checked only when the tree is
 * <u>totally</u> filled.
 * 
 * @see YangTreePath
 */
public interface PathSensitiveType {

	/**
	 * Sets the path of the leaf in which this type is applied.
	 */
	public void setPath(YangTreePath path);

}
