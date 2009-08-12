package yangTree.nodes;

import java.io.Serializable;

import javax.swing.ImageIcon;

import yangTree.attributes.NameSpace;

import jyang.parser.YANG_DataDef;

/**
 * Represents a node in the YangTree. Such a node is either a {@link DataTree} or a {@link DataLeaf}.
 */

public abstract class DataNode implements Serializable {

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
	 * Returns the name of the type of this node.
	 */
	abstract public String getNodeType();

	/**
	 * Returns the icon used to display this node in a tree.
	 */
	abstract public ImageIcon getIcon();

}
