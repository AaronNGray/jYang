package datatree;

import java.util.Hashtable;

public class DataTree {
	
	private Hashtable<String, DataNode> nodes = new Hashtable<String, DataNode>();
	
	public Hashtable<String, DataNode> getNodes(){
		return nodes;
	}

}
