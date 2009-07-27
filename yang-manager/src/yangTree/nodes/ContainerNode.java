package yangTree.nodes;


import java.util.Enumeration;

import jyang.parser.YANG_Container;

public class ContainerNode extends DataTree {

	private int keyleaflist = 0;

	public ContainerNode(YANG_Container c) {
		definition = c;
	}
	
	public ContainerNode(String name){
		YANG_Container ycontainer = new YANG_Container(-1);
		ycontainer.setContainer(name);
		definition = ycontainer; 
	}
	
	public boolean equalsTo(ContainerNode node){
		return (getNameSpace()+":"+getName()).equals(node.getNameSpace()+":"+node.getName());
	}
	
	public ContainerNode cloneBody(){
		return new ContainerNode((YANG_Container) definition);
	}

	public String toString() {
		return getName();
	}

	@Override
	public String getNodeType() {
		return "Container";
	}

}
