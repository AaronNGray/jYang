package datatree;


import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataNode {
	
	private String 		value;
	
	public String getValue() {
		return value;
	}


	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	
	public String toString(String prefix) {
		String result = prefix + "leaf-list " + definition.getBody() + "\n";
		
		return result;
		
	}
	
	public String toString(){
		return "leaf-list " + definition.getBody() + " " + value;
	}

	public void setValue(String v) {
		value = v;
	}
}
