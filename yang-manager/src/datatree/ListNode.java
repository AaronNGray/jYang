package datatree;

import java.util.Hashtable;

import jyang.YANG_List;

public class ListNode extends DataNode {
	
	private class Entry {
		Hashtable<String, DataNode> entry = new Hashtable<String, DataNode>();
		Entry(Hashtable<String, DataNode> e){
			entry = e;
		}
	}
	
	private Hashtable<String, Entry> entries = new Hashtable<String, Entry>();
	
	public ListNode(YANG_List d){
		definition = d;
	}
	
	public void addEntry(String i, Hashtable<String, DataNode> e){
		Entry entry = new Entry(e);
		entries.put(i, entry);
	}

}
