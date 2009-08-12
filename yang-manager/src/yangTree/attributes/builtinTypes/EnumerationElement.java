package yangTree.attributes.builtinTypes;

import java.io.Serializable;

import applet.Util;

import jyang.parser.YANG_Enum;

public class EnumerationElement implements Serializable{
	
	private String name;
	private int value;
	private String description = null;
	
	public EnumerationElement(String name, int value, String description){
		this.name = Util.cleanValueString(name);
		this.value = value;
		this.description = description;
	}
	
	public EnumerationElement(String name, int value){
		this.name = Util.cleanValueString(name);
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getDescription(){
		return description;
	}
	

}
