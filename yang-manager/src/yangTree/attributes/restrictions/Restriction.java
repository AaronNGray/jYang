package yangTree.attributes.restrictions;

import java.io.Serializable;

import yangTree.attributes.UnitValueCheck;

/**
 * Represents a restriction that can be applied on a built-in type.
 */
@SuppressWarnings("serial")
public abstract class Restriction implements Serializable{
	
	/**
	 * Checks if a value is correct given this restriction.
	 */
	public abstract UnitValueCheck check(String value);
	
	/**
	 * Returns a HTML-formatted description of the restriction.
	 */
	public abstract String getDescription();

}
