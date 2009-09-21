package yangtree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import applet.InfoPanel;
import applet.Util;

import yangtree.attributes.LeafType;
import yangtree.attributes.UnitValueCheck;
import jyang.parser.YANG_Leaf;

@SuppressWarnings("serial")
public class LeafNode extends YangLeaf {

	private static ImageIcon standardIcon = null;
	private static ImageIcon isKeyIcon = null;
	private static ImageIcon errorIcon = null;
	private static ImageIcon warningIcon = null;
	private static ImageIcon isKeyErrorIcon = null;
	private static ImageIcon isKeyWarningIcon = null;

	private boolean isKey = false;
	private boolean mandatory = false;
	private String defaultValue = null;
	private String description = null;
	
	private boolean noRetrievedValue = true;
	
	public LeafNode(YANG_Leaf d) {
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

	public void setTypeDef(LeafType type) {
		this.type = type;
	}

	public void setValue(String value) {
		if (value != null) {
			this.value = Util.cleanValueString(value);
			noRetrievedValue = false;
		}
	}
	
	public void check(){
		super.check();
		if (noRetrievedValue){
			if (isKey){
				check.addUnitCheck(new UnitValueCheck("No value have been specified for this list-key leaf."));
				return;
			}
			if (type.getDefaultValue() != null) {
				this.value = type.getDefaultValue();
				check.addUnitCheck(new UnitValueCheck(
						"No value specified ; type default value assumed.",
						false));
			} else if (defaultValue != null) {
				this.value = defaultValue;
				check.addUnitCheck(new UnitValueCheck(
						"No value specified ; leaf default value assumed.",
						false));
			} else if (mandatory){
				check.addUnitCheck(new UnitValueCheck("No value have been specified for this mandatory leaf."));
				return;
			} else {
				check.addUnitCheck(new UnitValueCheck("No value specified.",
						false));
			}
		}
	}


	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = Util.cleanValueString(defaultValue);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsKey(boolean isKey) {
		this.isKey = isKey;
	}
	
	public LeafNode cloneBody(){
		LeafNode clone = new LeafNode((YANG_Leaf) definition);
		clone.setDefaultValue(defaultValue);
		clone.setDescription(description);
		clone.setIsKey(isKey);
		clone.setMandatory(mandatory);
		clone.setNameSpace(nameSpace);
		clone.setTypeDef(type);
		return clone;
	}

	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		if (isMandatory()) 
			panel.addTextField("Mandatory", "Yes");
		
		if (getDefaultValue() != null)
			panel.addTextField("Default value", getDefaultValue());
		
		if (getType().getDefaultValue() != null) {
			panel.addTextField("Default value", getType()
					.getDefaultValue());
		}
		if (getDescription() != null) {
			String description = getDescription();
			Pattern pattern = Pattern.compile("[\n\t]");
			Matcher matcher = pattern.matcher(description);
			description = matcher.replaceAll("");
			panel.addTextArea("Description", description);
		}
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

			if (check != null && !check.isOk()) {

				if (check.isCritical()) {
					if (isKeyErrorIcon == null) {
						InputStream is = getClass().getResourceAsStream(
								"/icons/leafkeyError.png");
						try {
							int lenght = is.available();
							byte[] buffer = new byte[lenght];
							is.read(buffer);
							isKeyErrorIcon = new ImageIcon(buffer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return isKeyErrorIcon;
				} else {
					if (isKeyWarningIcon == null) {
						InputStream is = getClass().getResourceAsStream(
								"/icons/leafkeyWarning.png");
						try {
							int lenght = is.available();
							byte[] buffer = new byte[lenght];
							is.read(buffer);
							isKeyWarningIcon = new ImageIcon(buffer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return isKeyWarningIcon;
				}

			} else {

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
			}

		} else {

			if (check != null && !check.isOk()) {

				if (check.isCritical()) {
					if (errorIcon == null) {
						InputStream is = getClass().getResourceAsStream(
								"/icons/leafError.png");
						try {
							int lenght = is.available();
							byte[] buffer = new byte[lenght];
							is.read(buffer);
							errorIcon = new ImageIcon(buffer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return errorIcon;
				} else {
					if (warningIcon == null) {
						InputStream is = getClass().getResourceAsStream(
								"/icons/leafWarning.png");
						try {
							int lenght = is.available();
							byte[] buffer = new byte[lenght];
							is.read(buffer);
							warningIcon = new ImageIcon(buffer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return warningIcon;
				}

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

}
