package datatree;


import java.util.List;
import java.util.Vector;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataTree {
	
	
	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	public void addLeaf(LeafNode l){
		nodes.put(l.getName(), l);
	}
}
