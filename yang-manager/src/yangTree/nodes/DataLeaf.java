package yangTree.nodes;

import javax.swing.ImageIcon;

import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;

public abstract class DataLeaf extends DataNode {

	protected String value = null;
	protected LeafType type;
	protected ValueCheck check = null;
	
	public String getValue(){
		return value;
	}
	
	public LeafType getType(){
		return type;
	}
	
	public ValueCheck getCheck(){
		return check;
	}
	
	public abstract void setValue(String value);
	

}
