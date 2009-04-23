package datatree;

import java.util.Hashtable;

import jyang.YANG_Container;

public class ContainerNode extends DataNode {
	
	private Hashtable<String, DataNode> containeds = new Hashtable<String, DataNode>();
	
	public ContainerNode(YANG_Container c){
		definition = c;
	}
	
	public void addContent(DataNode d){
		containeds.put(d.getName(), d);
	}
	

}
