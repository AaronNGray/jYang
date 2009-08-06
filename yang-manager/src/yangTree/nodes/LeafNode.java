package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import yangTree.attributes.LeafType;

import jyang.parser.YANG_Leaf;

public class LeafNode extends DataNode {

	private static ImageIcon standardIcon = null;
	private static ImageIcon isKeyIcon = null;

	private boolean isKey = false;
	private String value;
	private LeafType type;
	private boolean mandatory;
	private String defaultValue;
	private String description;

	public LeafNode(YANG_Leaf d) {
		definition = d;
	}

	public LeafNode(YANG_Leaf d, String value) {
		this.value = value;
		definition = d;
	}

	public String getDescription() {
		return description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public boolean isKey() {
		return isKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getValue() {
		return value;
	}

	public LeafType getTypeDef() {
		return type;
	}

	public void setTypeDef(LeafType type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setMandatory(boolean mandatory){
		this.mandatory = mandatory;
	}
	
	public void setDefaultValue(String defaultValue){
		this.defaultValue = defaultValue;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

	public void setIsKey(boolean isKey) {
		this.isKey = isKey;
	}


	public LeafNode cloneBody() {
		LeafNode result = new LeafNode((YANG_Leaf) definition, value);
		result.setDefaultValue(defaultValue);
		result.setDescription(description);
		result.setMandatory(mandatory);
		result.setTypeDef(type);
		result.setIsKey(isKey);
		return result;
	}

	public String toString(String prefix) {
		return prefix + "leaf " + definition.getBody() + "; value : " + value;
	}

	public String toString() {
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}

	public String getNodeType() {
		return "Leaf";
	}

	public ImageIcon getIcon() {

		if (isKey) {

			if (isKeyIcon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/leafkey.png");
				try {
					int lenght = is.available();
					byte[] buffer = new byte[lenght];
					is.read(buffer);
					isKeyIcon = new ImageIcon(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return isKeyIcon;
			
		} else {

			if (standardIcon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/leaf.png");
				try {
					int lenght = is.available();
					byte[] buffer = new byte[lenght];
					is.read(buffer);
					standardIcon = new ImageIcon(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return standardIcon;
			
		}
	}

}
