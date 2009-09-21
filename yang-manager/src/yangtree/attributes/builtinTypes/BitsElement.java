package yangtree.attributes.builtinTypes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BitsElement implements Comparable<BitsElement> , Serializable{
	
	private String name;
	private int position;
	private String description = null;
	
	public BitsElement(String name, int value, String description){
		this.name = name;
		this.position = value;
		this.description = description;
	}
	
	public BitsElement(String name, int value){
		this.name = name;
		this.position = value;
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

	@Override
	public int compareTo(BitsElement otherBit) {
		if (position>otherBit.position){
			return 1;
		} else if (position<otherBit.position){
			return -1;
		} else {
			return 0;
		}
	}

}
