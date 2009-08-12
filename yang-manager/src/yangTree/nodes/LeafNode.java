package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import applet.Util;

import yangTree.attributes.LeafType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;

import jyang.parser.YANG_Leaf;

public class LeafNode extends DataLeaf {

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
			check = type.getBuiltinType().check(this.value);
		} else {
			check = new ValueCheck();
			if (isKey){
				check.addUnitCheck(new UnitValueCheck("No value have been retrieved for this list-key leaf."));
				return;
			}
			if (mandatory){
				check.addUnitCheck(new UnitValueCheck("No value have been retrieved for this mandatory leaf."));
				return;
			}
			if (type.getDefaultValue() != null) {
				this.value = type.getDefaultValue();
				check.addUnitCheck(new UnitValueCheck(
						"No value retrieved ; type default value assumed.",
						false));
			} else if (defaultValue != null) {
				this.value = defaultValue;
				check.addUnitCheck(new UnitValueCheck(
						"No value retrieved ; leaf default value assumed.",
						false));
			} else {
				check.addUnitCheck(new UnitValueCheck("No value retrieved.",
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

	public LeafNode cloneBody() {
		LeafNode result = new LeafNode((YANG_Leaf) definition);
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
