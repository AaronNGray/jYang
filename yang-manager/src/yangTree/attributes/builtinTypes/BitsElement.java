package yangTree.attributes.builtinTypes;

public class BitsElement {
	
	private String name;
	private int position;
	private String description;
	
	public BitsElement(String name, int value, String description){
		this.name = name;
		this.position = value;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public int getValue(){
		return position;
	}
	
	public String getDescription(){
		return description;
	}

}
