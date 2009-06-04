package datatree;

import jyang.parser.YANG_DataDef;

public abstract class DataNode {
	
	protected YANG_DataDef definition;
	
	public String getName(){
		return definition.getBody();
	}
	
	abstract public String toString(String p);
	
}
