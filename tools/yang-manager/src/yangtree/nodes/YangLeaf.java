package yangtree.nodes;

import applet.InfoPanel;

import yangtree.attributes.LeafType;
import yangtree.attributes.ValueCheck;

/**
 * Represents a leaf in a YangTree.
 * @see YangNode
 * 
 */
@SuppressWarnings("serial")
public abstract class YangLeaf extends YangNode {

	protected String value = null;
	protected LeafType type;

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
	
	public void check() {
		if (check==null)
			check = new ValueCheck();
		if (value!=null)
			check.addChecks(type.getBuiltinType().check(this.value));
	}
	
	/**
	 * Returns an empty clone (i.e. without value) of this leaf.<br>
	 * All other other attributes, such as type or restrictions, are kept.
	 */
	public abstract YangLeaf cloneBody();
	
	public YangLeaf cloneTree(){
		return cloneBody();
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
