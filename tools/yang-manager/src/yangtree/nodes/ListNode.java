package yangtree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import yangtree.ChoiceStillPresentException;
import applet.InfoPanel;

import jyang.parser.YANG_List;

@SuppressWarnings("serial")
public class ListNode extends YangInnerNode implements ListedYangNode {

	// TODO : Check the "unique" substatement.

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;

	private int minElements = 0;
	private int maxElements = Integer.MAX_VALUE;

	private boolean mustBeCreated = false;

	private Map<String, LeafNode> keymap = new HashMap<String, LeafNode>();

	public ListNode(YANG_List d) {
		definition = d;
		if (d.getMinElement() != null)
			this.minElements = new Integer(d.getMinElement().getMinElement());
		if (d.getMaxElement() != null
				&& !d.getMaxElement().getMaxElement().equals("unbounded"))
			this.maxElements = new Integer(d.getMaxElement().getMaxElement());
	}

	public ListNode(YANG_List d, String[] keys) {
		definition = d;
		if (d.getMinElement() != null)
			this.minElements = new Integer(d.getMinElement().getMinElement());
		if (d.getMaxElement() != null
				&& !d.getMaxElement().getMaxElement().equals("unbounded"))
			this.maxElements = new Integer(d.getMaxElement().getMaxElement());
		for (int i = 0; i < keys.length; i++) {
			keymap.put(keys[i], null);
		}
	}

	private ListNode(YANG_List d, Map<String, LeafNode> keymap) {
		definition = d;
		this.keymap = new HashMap<String, LeafNode>(keymap);
	}

	public void setMustBeCreated(boolean mustBeCreated) {
		this.mustBeCreated = mustBeCreated;
	}

	public boolean isMustBeCreated() {
		return mustBeCreated;
	}

	@Override
	public void addChild(YangNode node) {
		if (node instanceof LeafNode) {
			LeafNode leaf = (LeafNode) node;
			if (keymap.containsKey(leaf.getName())) {
				leaf.setIsKey(true);
				keymap.put(leaf.getName(), leaf);
			}
		}
		descendantNodes.add(node);
	}

	/**
	 * Adds a child to this node only if it is not a key. If the child is a key,
	 * just adds it to the keymap.
	 * 
	 * @param node
	 *            : the child that will be added.
	 */
	public void addChildSilently(YangNode node) {
		if (node instanceof LeafNode) {
			LeafNode leaf = (LeafNode) node;
			if (keymap.containsKey(leaf.getName())) {
				leaf.setIsKey(true);
				if (leaf.getValue() != null) {
					keymap.put(leaf.getName(), leaf);
				}
			} else {
				descendantNodes.add(node);
			}
		} else {
			descendantNodes.add(node);
		}
	}

	public ListNode cloneBody() {
		ListNode clone = new ListNode((YANG_List) definition, keymap);
		clone.setNameSpace(nameSpace);
		if (specificationNode == null) {
			clone.specificationNode = this;
		} else {
			clone.specificationNode = specificationNode;
		}
		return clone;
	}

	/**
	 * Creates an empty clone of this list and its descendants.<br>
	 * <b>Note :</b> the clone will be marked to be created (operation="create")
	 * in the next netconf "edit-config" request.
	 * 
	 * @return An empty clone of this node, filled with empty clones of its
	 *         children.
	 * @see #cloneBody()
	 */
	public ListNode cloneTree() {
		ListNode result = (ListNode) super.cloneTree();
		result.setMustBeCreated(true);
		return result;
	}

	public int getMinElements() {
		return minElements;
	}

	public int getMaxElements() {
		return maxElements;
	}

	public int compareTo(ListedYangNode otherListedNode) {
		ListNode otherList = (ListNode) otherListedNode;
		for (String key : keymap.keySet()) {
			if (keymap.get(key).getValue().compareTo(
					otherList.keymap.get(key).getValue()) != 0)
				return keymap.get(key).getValue().compareTo(
						otherList.keymap.get(key).getValue());
		}
		return 0;
	}

	@Override
	public boolean equalsOccurrence(ListedYangNode otherOccurrence) {
		if (!(otherOccurrence instanceof ListNode))
			return false;
		ListNode otherList = (ListNode) otherOccurrence;
		return hasSameKey(otherList);
	}

	public boolean hasSameKey(Map<String, String> keymap) {
		for (String key : this.keymap.keySet()) {
			if (this.keymap.get(key).getValue() == null)
				return false;
			if (!this.keymap.get(key).getValue().equals(keymap.get(key)))
				return false;
		}
		return true;
	}

	public boolean hasSameKey(ListNode otherList) {
		for (String key : this.keymap.keySet()) {
			if (otherList.keymap.get(key) == null
					|| otherList.keymap.get(key).getValue() == null
					|| this.keymap.get(key) == null
					|| this.keymap.get(key).getValue() == null)
				return false;
			if (!this.keymap.get(key).getValue().equals(
					otherList.keymap.get(key).getValue()))
				return false;
		}
		return true;
	}

	public String getKeysRepresentation() {
		String result = "";
		for (String key : keymap.keySet()) {
			if (keymap.get(key) == null || keymap.get(key).getValue() == null) {
				result += key + " , ";
			} else {
				result += key + ":" + keymap.get(key).getValue() + " , ";
			}
		}
		result = result.substring(0, result.length() - 3);
		return result;
	}

	public String getKeyXMLRepresentation() {
		String result = "";
		for (String key : keymap.keySet()) {
			if (keymap.get(key) != null && keymap.get(key).getValue() != null)
				result += "<" + key + ">" + keymap.get(key).getValue() + "</"
						+ key + ">";
		}
		return result;
	}

	public String[] xmlFilter(boolean isKeyRequired) {
		String result = super.xmlFilter()[0];
		if (isKeyRequired) {
			for (String key : keymap.keySet()) {
				if (keymap.get(key) == null) {
					result += "<" + key + ">" + "</" + key + ">";
				} else {
					if (key.compareTo(keymap.get(key).toString()) != 0)
						result += "<" + key + ">" + keymap.get(key) + "</"
								+ key + ">";
					else
						result += "<" + key + ">" + "</" + key + ">";
				}
			}
		}

		return new String[] { result, "</" + getName() + ">" };
	}

	@Override
	public void buildInfoPanel(InfoPanel panel) {
		super.buildInfoPanel(panel);
		panel.addTextField("Keys", getKeysRepresentation());
		if (minElements > 0)
			panel.addTextField("Minimum number of elements", minElements + "");
		if (maxElements < Integer.MAX_VALUE)
			panel.addTextField("Maximum number of elements", maxElements + "");
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

	public String getXMLRepresentation() throws ChoiceStillPresentException {
		String result = "<" + getName();
		if (nameSpace != null && nameSpace.getNameSpace() != null) {
			result = result + nameSpace.getXMLArg();
		}
		/*
		if (mustBeCreated)
			result += " xmlns:xc=\"urn:ietf:params:xml:ns:netconf:base:1.0\" xc:operation=\"create\"";
		*/
		result += ">";
		for (YangNode child : getDescendantNodes()) {
			result += child.getXMLRepresentation();
		}
		for (String key : keymap.keySet()) {
			if (keymap.get(key) != null && getChildByName(key).size() == 0)
				result += "<" + key + ">" + keymap.get(key) + "</" + key + ">";
		}
		if (deleteOperations != null)
			result += deleteOperations.getOperations();
		return result + "</" + getName() + ">";
	}

	public String getXMLReplaceRepresentation()
			throws ChoiceStillPresentException {
		String result = "<" + getName();
		if (nameSpace != null && nameSpace.getNameSpace() != null) {
			result = result + nameSpace.getXMLArg();
		}
		if (mustBeCreated) {
			result += " xmlns:xc=\"urn:ietf:params:xml:ns:netconf:base:1.0\" xc:operation=\"create\"";
		} else {
			result += " xmlns:xc=\"urn:ietf:params:xml:ns:netconf:base:1.0\" xc:operation=\"replace\"";
		}
		result += ">";
		for (YangNode child : getDescendantNodes()) {
			result += child.getXMLRepresentation();
		}
		for (String key : keymap.keySet()) {
			if (keymap.get(key) != null && getChildByName(key).size() == 0)
				result += "<" + key + ">" + keymap.get(key) + "</" + key + ">";
		}
		if (deleteOperations != null)
			result += deleteOperations.getOperations();
		return result + "</" + getName() + ">";
	}

}
