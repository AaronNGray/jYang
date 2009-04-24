package datatree;

import jyang.YANG_DataDef;

public abstract class DataNode {
	
	protected YANG_DataDef definition;
	
	public String getName(){
		return definition.getBody();
	}
	
}
