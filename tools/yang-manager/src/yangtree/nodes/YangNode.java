package yangtree.nodes;

import java.io.Serializable;
import javax.swing.ImageIcon;
import applet.InfoPanel;

import yangtree.ChoiceStillPresentException;
import yangtree.attributes.NameSpace;
import yangtree.attributes.ValueCheck;

import jyang.parser.YANG_DataDef;

/**
 * Represents a node in the YangTree.
 */

@SuppressWarnings("serial")
public abstract class YangNode implements Serializable {

	public YANG_DataDef definition;
	protected NameSpace nameSpace;
	protected boolean isSelected = false;

	protected ValueCheck check = null;

	/**
	 * Returns the warnings and errors found on this node.
	 */
	public ValueCheck getCheck() {
		if (check == null) {
			check = new ValueCheck();
		}
		return check;
	}

	/**
	 * Returns the name of this node
	 */
	public String getName() {
		return definition.getBody();
	}

	public void setNameSpace(NameSpace nameSpace) {
		this.nameSpace = nameSpace;
	}

	public NameSpace getNameSpace() {
		return nameSpace;
	}

	/**
	 * Checks the node for possible errors or warnings and consequently modify
	 * the ValueCheck.
	 */
	public abstract void check();

	/**
	 * Performs the <code>check()</code> operation on this node, and repeats
	 * this operation for all descendants nodes.
	 */
	protected void checkSubtree() {
		check();
	}

	/**
	 * Removes all checks on this node and all its descendants.
	 */
	protected void uncheckSubtree() {
		check = new ValueCheck();
	}

	/**
	 * Returns the XML representation of this node needed by a netconf agent in
	 * the XMLFilter of a request.
	 * 
	 * @return An array of two <code>String</code>s : the first element is the
	 *         opening tag and the second element is the closing tag.
	 */
	public String[] xmlFilter() {
		String result = "<" + getName() + " ";
		if (nameSpace != null)
			if (nameSpace.getNameSpace() != null) {
				result = result + nameSpace.getXMLArg();
			}
		result += ">";
		return new String[] { result, "</" + getName() + ">" };
	}

	/**
	 * Builds the infoPanel that will display informations about this node.
	 * 
	 * @param panel
	 *            : The InfoPanel that must be filled with informations.
	 */
	public void buildInfoPanel(InfoPanel panel) {
		panel.clean();
		panel.setTitleText(getNodeType() + " : " + getName());
		panel.addValueCheckPanel(getCheck());
	}

	/**
	 * Returns an empty clone of this node. If this node is an inner node, the
	 * clone will have no child; if this node is a leaf, the clone will have no
	 * value.<br>
	 * All other attributes, such as type or namespace are kept.
	 * 
	 * @see #cloneTree()
	 */
	public abstract YangNode cloneBody();

	/**
	 * Creates an empty clone of this node and its descendants.<br>
	 * <b>Note :</b> Descendant <code>ListNode</code>s and
	 * <code>LeafListNode</code>s will be ignored and will <u>not</u> be cloned.
	 * 
	 * @return An empty clone of this node, filled with empty clones of its
	 *         children.
	 * @see #cloneBody()
	 */
	public abstract YangNode cloneTree();

	/**
	 * Returns the name of the type of this node.
	 */
	abstract public String getNodeType();

	/**
	 * Returns the icon used to display this node in a tree.
	 */
	abstract public ImageIcon getIcon();

	/**
	 * Returns the complete XML representation of this node.
	 * 
	 * @throws ChoiceStillPresentException
	 *             : if this node is a <code>ChoiceNode</code> of if there is
	 *             such node in its descendants.
	 */
	abstract public String getXMLRepresentation()
			throws ChoiceStillPresentException;

}
