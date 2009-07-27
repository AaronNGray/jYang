package yangTree.nodes;

import java.util.LinkedList;

public abstract class DataTree extends DataNode {
	
	protected LinkedList<DataNode> nodes = new LinkedList<DataNode>();
	
	public LinkedList<DataNode> getNodes(){
		return nodes;
	}
	
	public abstract DataTree cloneBody();
	
	public void addContent(DataNode node){
		nodes.add(node);
	}

}
