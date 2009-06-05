package datatree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jyang.parser.YANG_Container;

public class ContainerNode extends DataTree {

	private int keyleaflist = 0;

	public ContainerNode(YANG_Container c) {
		definition = c;
	}

	public void addContent(DataNode d) {
		if (d instanceof LeafListNode) {
			nodes.put(d.getName() + ":" + keyleaflist, d);
			keyleaflist++;
		} else
			nodes.put(d.getName(), d);
	}

	public String toString() {
		return "container " + definition.getBody();
	}

	public String toString(String prefix) {
		String result = prefix + "container " + definition.getBody() + "\n";
		for (Enumeration<DataNode> es = nodes.elements(); es.hasMoreElements();)
			result += es.nextElement().toString(prefix + " ") + " | ";
		return result;
	}

}
