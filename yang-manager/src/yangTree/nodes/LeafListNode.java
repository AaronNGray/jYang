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

public class LeafListNode extends YangLeaf implements ListedYangNode {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;
	private static ImageIcon warningIcon = null;

	private int minElements = 0;
	private int maxElements = Integer.MAX_VALUE;

	public LeafListNode(YANG_LeafList d) {
		definition = d;
		if (d.getMinElement()!=null)
			this.minElements = new Integer(d.getMinElement().getMinElement());
		if (d.getMaxElement()!=null && !d.getMaxElement().getMaxElement().equals("unbounded"))
			this.maxElements = new Integer(d.getMaxElement().getMaxElement());
	}

	public int getMinElements() {
		return minElements;
	}

	public int getMaxElements() {
		return maxElements;
	}

	@Override
	public int compareTo(ListedYangNode otherListedNode) {
		LeafListNode otherNode = (LeafListNode) otherListedNode;
		return value.compareTo(otherNode.getValue());
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
