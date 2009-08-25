package yangTree.nodes;

import java.io.Serializable;
import javax.swing.ImageIcon;
import applet.InfoPanel;

import yangTree.attributes.NameSpace;
import yangTree.attributes.ValueCheck;

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
	 * Checks the node for possible errors or warnings and consequently modify the ValueCheck.
	 */
	public abstract void check();

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
	 * Perform the <code>check()</code> operation on this node, and do the same for all its children.
	 */
	public void checkSubtree(){
		check();
	}

	/**
	 * Returns the XML representation of this node needed by a netconf agent in
	 * the XMLFilter of a request.
	 * 
	 * @return an array of two
	 *         <code>String</code>s : the first element is the opening tag and the second element is the closing tag.
	 */
	public String[] xmlFilter() {
		String result = "<" + getName() + " ";
		if (nameSpace!=null && nameSpace.getNameSpace() != null) {
			result = result + nameSpace.getXMLArg();
		}
		result += ">";
		return new String[]{result,"</" + getName() + ">"};
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
	 * Returns an empty clone of this node. If this node is an inner node, the clone will have no child; if this node is a leaf, the clone will have no value.<br>
	 * All other attributes, such as type or namespace are kept.
	 */
	public abstract YangNode cloneBody();
	

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
	 */
	abstract public String getXMLRepresentation();

}
