package datatree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jyang.YANG_Container;

public class ContainerNode extends DataNode {
	
	private Vector<DataNode> containeds = new Vector<DataNode>();
	
	public ContainerNode(YANG_Container c){
		definition = c;
	}
	
	public void addContent(DataNode d){
		containeds.add(d);
	}
	
	public String toString(){
		String result = "container " + definition.getBody() + "\n";
		for(Enumeration<DataNode> es = containeds.elements(); es.hasMoreElements();)
			result += es.nextElement().toString() + " | ";
		return result;
	}
	

}
