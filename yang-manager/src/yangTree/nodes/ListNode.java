package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import yangTree.attributes.ValueCheck;

import applet.InfoPanel;

import jyang.parser.YANG_List;

@SuppressWarnings("serial")
public class ListNode extends YangInnerNode implements ListedYangNode {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;

	private int minElements = 0;
	private int maxElements = Integer.MAX_VALUE;
	
	private Map<String, String> keymap = new HashMap<String, String>();
	private ValueCheck check = null;

	public ListNode(YANG_List d) {
		definition = d;
		if (d.getMinElement()!=null)
			this.minElements = new Integer(d.getMinElement().getMinElement());
		if (d.getMaxElement()!=null && !d.getMaxElement().getMaxElement().equals("unbounded"))
			this.maxElements = new Integer(d.getMaxElement().getMaxElement());
	}

	public ListNode(YANG_List d, String[] keys) {
		definition = d;
		if (d.getMinElement()!=null)
			this.minElements = new Integer(d.getMinElement().getMinElement());
		if (d.getMaxElement()!=null && !d.getMaxElement().getMaxElement().equals("unbounded"))
			this.maxElements = new Integer(d.getMaxElement().getMaxElement());
		for (int i = 0; i < keys.length; i++) {
			keymap.put(keys[i], null);
		}
	}

	private ListNode(YANG_List d, Map<String, String> keymap) {
		definition = d;
		this.keymap = new HashMap<String, String>(keymap);
	}

	@Override
	public void addContent(YangNode node) {
		if (node instanceof LeafNode) {
			LeafNode leaf = (LeafNode) node;
			if (keymap.containsKey(leaf.getName())) {
				leaf.setIsKey(true);
				if (leaf.getValue() != null) {
					keymap.put(leaf.getName(), leaf.getValue());
				}
			}
		}
		nodes.add(node);
	}

	public ListNode cloneBody() {
		ListNode clone = new ListNode((YANG_List) definition, keymap);
		clone.setExpanded(isExpanded);
		return clone;
	}
	
	public ValueCheck getCheck() {
		if (check==null){
			check = new ValueCheck();
		}
		return check;
	}

	public int getMinElements() {
		return minElements;
	}

	public int getMaxElements() {
		return maxElements;
	}

	public int compareTo(ListedYangNode otherListedNode) {
		ListNode otherList = (ListNode) otherListedNode;
		for (String key : keymap.keySet()){
			if (keymap.get(key).compareTo(otherList.keymap.get(key))!=0)
				return keymap.get(key).compareTo(otherList.keymap.get(key));
		}
		return 0;
	}

	public boolean hasSameKey(Map<String,String> keymap){
		for (String key : this.keymap.keySet()) {
			if (!this.keymap.get(key).equals(keymap.get(key)))
				return false;
		}
		return true;
	}

	public boolean hasSameKey(ListNode otherList) {
		return hasSameKey(otherList.keymap);
	}

	public String getKeysRepresentation() {
		String result = "";
		for (String key : keymap.keySet()) {
			if (keymap.get(key) == null) {
				result += key + " , ";
			} else {
				result += key + ":" + keymap.get(key) + " , ";
			}
		}
		result = result.substring(0, result.length() - 3);
		return result;
	}
	
	@Override
	public String[] xmlFilter() {
		String result = super.xmlFilter()[0];
		for (String key : keymap.keySet()){
			result += "<"+key+">"+keymap.get(key)+"</"+key+">";
		}
		
		return new String[]{result,"</" + getName() + ">"};
	}

	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		panel.addTextField("Keys", getKeysRepresentation());
		if (minElements>0)
			panel.addTextField("Minimum number of elements", minElements+"");
		if (maxElements<Integer.MAX_VALUE)
			panel.addTextField("Maximum number of elements", maxElements+"");
	}

	public String toString() {
		if (keymap.size() == 0) {
			return definition.getBody();
		} else {
			return definition.getBody() + " [ " + getKeysRepresentation()
					+ " ]";
		}
	}

	public String getNodeType() {
		return "List";
	}

	public ImageIcon getIcon() {
		if (check == null || check.isOk()) {
			if (icon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/list.png");
				try {
					int lenght = is.available();
					byte[] buffer = new byte[lenght];
					is.read(buffer);
					icon = new ImageIcon(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return icon;
		} else {
			if (errorIcon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/listError.png");
				try {
					int lenght = is.available();
					byte[] buffer = new byte[lenght];
					is.read(buffer);
					errorIcon = new ImageIcon(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return errorIcon;
		}
	}

}
