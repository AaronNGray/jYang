package yangTree.nodes;

import java.io.Serializable;

import javax.swing.ImageIcon;

import applet.InfoPanel;

import yangTree.attributes.NameSpace;

import jyang.parser.YANG_DataDef;

/**
 * Represents a node in the YangTree.
 */

@SuppressWarnings("serial")
public abstract class YangNode implements Serializable {

	public YANG_DataDef definition;
	private NameSpace nameSpace;

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
	 */
	public String xmlFilter() {
		String result = "<" + getName() + " ";
		if (nameSpace.getNameSpace() != null) {
			result = result + nameSpace.getXMLArg();
		}
		result = result + ">\r\n\r\n</" + getName() + ">";
		return result;
	}
	
	/**
	 * Builds the infoPanel that will display informations about this node.
	 * 
	 * @param panel : The InfoPanel that must be filled with informations.
	 */
	public void buildInfoPanel(InfoPanel panel){
		panel.clean();
		panel.setTitleText(getNodeType() + " : " + getName());
		if (this instanceof CheckedYangNode){
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
