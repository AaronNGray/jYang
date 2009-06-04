package datatree;

import java.util.Hashtable;

public abstract class DataTree extends DataNode {
	
	protected Hashtable<String, DataNode> nodes = new Hashtable<String, DataNode>();
	
	public Hashtable<String, DataNode> getNodes(){
		return nodes;
	}

}
