package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

import com.sun.org.apache.xml.internal.utils.SuballocatedByteVector;

import jyang.parser.YANG_List;

public class ListNode extends DataTree implements CheckedNode {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;

	private Map<String, String> keymap = new HashMap<String, String>();
	private ValueCheck check = null;

	public ListNode(YANG_List d) {
		definition = d;
	}

	public ListNode(YANG_List d, String[] keys) {
		definition = d;
		for (int i = 0; i < keys.length; i++) {
			keymap.put(keys[i], null);
		}
	}

	private ListNode(YANG_List d, Map<String, String> keymap) {
		definition = d;
		this.keymap = new HashMap<String, String>(keymap);
	}

	@Override
	public void addContent(DataNode node) {
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

	public void addUnitCheck(UnitValueCheck unitCheck) {
		if (check == null)
			check = new ValueCheck();
		check.addUnitCheck(unitCheck);
	}

	public ListNode cloneBody() {
		return new ListNode((YANG_List) definition, keymap);
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

	@Override
	public ValueCheck getCheck() {
		return check;
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
