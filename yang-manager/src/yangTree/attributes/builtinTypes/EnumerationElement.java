package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Enum;

public class EnumerationElement {
	
	private String name;
	private int value;
	private String description;
	
	public EnumerationElement(String name, int value, String description){
		this.name = name;
		this.value = value;
		this.description = description;
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
