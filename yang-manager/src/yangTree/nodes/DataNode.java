package yangTree.nodes;


import java.io.Serializable;

import jyang.parser.YANG_DataDef;

public abstract class DataNode implements Serializable{
	
	public YANG_DataDef definition;
	private NameSpace nameSpace;
	
	public String getName(){
		return definition.getBody();
	}
	
	public void setNameSpace(NameSpace nameSpace){
		this.nameSpace = nameSpace;
	}
	
	public NameSpace getNameSpace(){
		return nameSpace;
	}
	
	public String xmlFilter(){
		String result = "<"+getName()+" ";
		if (nameSpace.getNameSpace()!=null){
			result = result + nameSpace.getXMLArg();
		}
		result = result + ">\r\n\r\n</"+getName()+">";
		return result;
	}
	
	
	abstract public String getNodeType();
	
	
}
