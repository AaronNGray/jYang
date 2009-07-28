package yangTree.nodes;


import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import jyang.parser.YANG_Leaf;

public class LeafNode extends DataNode {
	
	private static ImageIcon icon = null;
	
	private String value;
	private String type;
	private boolean mandatory;
	private String defaultValue;
	private String defaultType;
	private String description;
	
	public LeafNode(YANG_Leaf d){
		definition = d;
	}
	
	public LeafNode(YANG_Leaf d, String value){
		this.value = value;
		definition = d;
	}
	
	public LeafNode(YANG_Leaf d, String value, boolean mandatory, String defaultValue, String defaultType, String description){
		this.value = value;
		definition = d;
		this.mandatory = mandatory;
		this.defaultValue = defaultValue;
		this.defaultType = defaultType;
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public boolean isMandatory(){
		return mandatory;
	}
	
	public String getDefaultValue(){
		return defaultValue;
	}
	
	public String getDefaultType(){
		return defaultType;
	}
	
	public String getValue(){
		return value;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public LeafNode cloneBody(){
		LeafNode result = new LeafNode((YANG_Leaf) definition, value, mandatory, defaultValue, defaultType, description);
		result.setType(type);
		return result;
	}
	
	public String toString(String prefix){
		return prefix + "leaf " + definition.getBody() + "; value : " + value;
	}
	
	public String toString(){
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}

	public String getNodeType() {
		return "Leaf";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/leaf.png");
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
	}

}
