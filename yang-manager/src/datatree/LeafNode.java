package datatree;

import jyang.parser.YANG_Leaf;

public class LeafNode extends DataNode {
	
	private String value;
	
	public LeafNode(YANG_Leaf d, String v){
		value = v;
		definition = d;
	}
	
	public String getValue(){
		return value;
	}
	
	public String toString(){
		return "leaf " + definition.getBody() + "; value : " + value;
	}

}
