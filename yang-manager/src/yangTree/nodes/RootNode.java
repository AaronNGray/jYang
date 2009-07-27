package yangTree.nodes;


import java.util.Enumeration;
import java.util.LinkedList;


public class RootNode extends ContainerNode {
	
	private String name = "root";
	
	public RootNode(){
		super("");
	}
	
	public RootNode(String name){
		super("");
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

}
