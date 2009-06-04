package datatree;


import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataTree {
	
	private int localkey = 0;
	
	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	public void addLeaf(LeafNode l){
		nodes.put(localkey + ":" + l.getName(), l);
		localkey++;
	}

	
	
	public String toString(String prefix) {
		String result = prefix + "leaf-list " + definition.getBody() + "\n";
		for(Enumeration<DataNode> es = nodes.elements(); es.hasMoreElements();)
			result += es.nextElement().toString(prefix + " ") + " | ";
		return result;
		
	}
	
	public String toString(){
		return "leaf-list " + definition.getBody();
	}
}
