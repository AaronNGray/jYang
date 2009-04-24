package datatree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jyang.YANG_List;

public class ListNode extends DataNode {

	private Vector<DataNode> entry = new Vector<DataNode>();
	private String keyvalue;
	
	public ListNode(YANG_List d) {
		definition = d;
	}

	public void setEntry(Vector<DataNode> e) {
		entry = e;
	}

	public String toString() {
		String result = "list " + definition.getBody() + " key : " + keyvalue + "\n";
		for (Enumeration<DataNode> es = entry.elements(); es.hasMoreElements();)
			result += es.nextElement().toString() + "\n";
		return result;
	}

	public void setKey(String kv) {
		keyvalue = kv;
		
	}

}
