package yangTree.nodes;

import java.util.LinkedList;
import java.util.Vector;

import jyang.parser.YANG_List;

public class ListNode extends DataTree {

	private String keyvalue;
	private String keyname;
	
	public ListNode(YANG_List d) {
		definition = d;
	}
	
	public ListNode(YANG_List d, String keyname) {
		definition = d;
		this.keyname = keyname;
	}
	
	public String getKeyName(){
		return keyname;
	}
	
	public String getKeyValue(){
		return keyvalue;
	}
	

	public void setEntry(Vector<DataNode> e) {
		nodes = new LinkedList<DataNode>(e);
	}

	public void setKeyValue(String kv) {
		keyvalue = kv;
		
	}
	
	public ListNode cloneBody(){
		return new ListNode((YANG_List) definition,keyname);
	}
	
	public String toString(){
		if (keyvalue==null){
			return definition.getBody();
		} else {
			return definition.getBody()+" : "+keyvalue;
		}
	}

	@Override
	public String getNodeType() {
		return "List";
	}

}
