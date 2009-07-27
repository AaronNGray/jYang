package yangTree.nodes;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataNode {
	
	private String value;
	private String type;
	

	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	public String getValue() {
		return value;
	}

	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	
	public String toString(String prefix) {
		String result = prefix + "leaf-list " + definition.getBody() + "\n";
		
		return result;
		
	}
	
	public String toString(){
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}
		

	public void setValue(String v) {
		value = v;
	}


	@Override
	public String getNodeType() {
		return "Leaf list";
	}
}
