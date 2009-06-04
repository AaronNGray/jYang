package datatree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jyang.parser.YANG_List;

public class ListNode extends DataTree {

	private String keyvalue;
	
	public ListNode(YANG_List d) {
		definition = d;
	}
	
	public String getName(){
		return super.getName() + ":" + keyvalue;
	}

	public void setEntry(Vector<DataNode> e) {
		for (Enumeration<DataNode> en = e.elements(); en.hasMoreElements();){
			DataNode n = en.nextElement();
			nodes.put(n.getName(), n);
		}
	}

	public String toString(String prefix) {
		String result = prefix + "list " + definition.getBody() + " key : " + keyvalue + "\n";
		for (Enumeration<DataNode> es = nodes.elements(); es.hasMoreElements();)
			result += es.nextElement().toString(prefix + "  ") + "\n";
		return result;
	}

	public void setKey(String kv) {
		keyvalue = kv;
		
	}
	
	public String toString(){
		return "list " + definition.getBody();
	}

}
