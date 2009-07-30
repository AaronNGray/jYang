package yangTree.attributes.restrictions;

import yangTree.attributes.UnitValueCheck;

public abstract class Restriction {
	
	protected String errorMessage = "";

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public abstract UnitValueCheck check(String value);
	
	public abstract String getDescription();

}
