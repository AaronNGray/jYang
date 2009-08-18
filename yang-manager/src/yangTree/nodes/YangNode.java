package yangTree.nodes;

import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import applet.InfoPanel;

import yangTree.attributes.NameSpace;

import jyang.parser.YANG_DataDef;

/**
 * Represents a node in the YangTree.
 */

@SuppressWarnings("serial")
public abstract class YangNode implements Serializable {

	public YANG_DataDef definition;
	protected NameSpace nameSpace;

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
		if (this instanceof CheckedYangNode) {
			panel.addValueCheckPanel(((CheckedYangNode) this).getCheck());
		}
	}
	

	/**
	 * Returns the name of the type of this node.
	 */
	abstract public String getNodeType();

	/**
	 * Returns the icon used to display this node in a tree.
	 */
	abstract public ImageIcon getIcon();

}
