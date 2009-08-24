package yangTree.nodes;

import applet.InfoPanel;

import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;

/**
 * Represents a leaf in a YangTree.
 * 
 * 
 */
@SuppressWarnings("serial")
public abstract class YangLeaf extends YangNode {

	protected String value = null;
	protected LeafType type;
	protected ValueCheck check = null;

	/**
	 * Returns the value of this leaf if such exists, otherwise returns
	 * <code>null</code>.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the type of this leaf.
	 */
	public LeafType getType() {
		return type;
	}

	public ValueCheck getCheck() {
		return check;
	}
	
	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		
		panel.addTypePanel(type);
		
		if (panel.isTreeFilled())
			panel.addValuePanel(this);
		
	}

	/**
	 * Sets the value of this leaf.
	 */
	public abstract void setValue(String value);
	
	public String getXMLRepresentation(){
		if (value == null){
			return "<"+getName()+"></"+getName()+">";
		} else  {
			return "<"+getName()+">"+value+"</"+getName()+">";
		}
	}
	

}
