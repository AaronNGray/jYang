package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import applet.Util;

import yangTree.attributes.LeafType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.builtinTypes.BuiltinType;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataLeaf {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;
	private static ImageIcon warningIcon = null;


	public LeafListNode(YANG_LeafList d) {
		definition = d;
	}

	public void setType(LeafType type) {
		this.type = type;
	}

	public LeafListNode cloneBody() {
		LeafListNode result = new LeafListNode((YANG_LeafList) definition);
		result.setType(type);
		return result;
	}

	public String toString() {
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}

	public void setValue(String value) {
		if (value != null) {
			this.value = Util.cleanValueString(value);
			check = type.getBuiltinType().check(this.value);
		} else {
			check = new ValueCheck();
			if (type.getDefaultValue() != null) {
				this.value = type.getDefaultValue();
				check.addUnitCheck(new UnitValueCheck(
						"No value retrieved ; type default value assumed.",
						false));
			} else {
				check.addUnitCheck(new UnitValueCheck("No value retrieved.",
						false));
			}
		}
	}

	public String getNodeType() {
		return "Leaf list";
	}

	public ImageIcon getIcon() {

		if (check != null && !check.isOk()) {
			
			if (check.isCritical()){
				if (errorIcon == null) {
					InputStream is = getClass().getResourceAsStream(
							"/icons/leaflistError.png");
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
							"/icons/leaflistWarning.png");
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

			if (icon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/leaflist.png");
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
}
