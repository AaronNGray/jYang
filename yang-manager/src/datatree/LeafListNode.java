package datatree;


import java.util.List;
import java.util.Vector;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataNode {
	
	private Vector<LeafNode> leaves = new Vector<LeafNode>();
	
	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	public void addLeaf(LeafNode l){
		leaves.add(l);
	}
}
