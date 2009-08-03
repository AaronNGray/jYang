package yangTree.attributes.restrictions;

import java.io.Serializable;

import yangTree.attributes.UnitValueCheck;

public abstract class Restriction implements Serializable{
	
	public abstract UnitValueCheck check(String value);
	
	public abstract String getDescription();

}
