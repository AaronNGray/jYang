package yangTree.attributes.restrictions;

import yangTree.attributes.UnitValueCheck;

public abstract class Restriction {
	
	public abstract UnitValueCheck check(String value);
	
	public abstract String getDescription();

}
