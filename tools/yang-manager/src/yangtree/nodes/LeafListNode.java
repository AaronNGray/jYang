package yangtree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import applet.InfoPanel;
import applet.Util;

import yangtree.attributes.LeafType;

import jyang.parser.YANG_LeafList;

@SuppressWarnings("serial")
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

	@Override
	public boolean equalsOccurrence(ListedYangNode otherOccurrence) {
		if (!(otherOccurrence instanceof LeafListNode))
			return false;
		LeafListNode otherLeafList = (LeafListNode) otherOccurrence;
		if (value==null || otherLeafList.value==null)
			return false;
		return value.equals(otherLeafList.value);
	}

	public void setType(LeafType type) {
		this.type = type;
	}
	
	public LeafListNode cloneBody(){
		LeafListNode clone = new LeafListNode((YANG_LeafList) definition);
		clone.setType(type);
		return clone;
	}

	public String toString() {
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}

	public void setValue(String value) {
		if (value != null)
			this.value = Util.cleanValueString(value);
	}
	
	@Override
	public void buildInfoPanel(InfoPanel panel){
		super.buildInfoPanel(panel);
		if (minElements>0)
			panel.addTextField("Minimum number of elements", minElements+"");
		if (maxElements<Integer.MAX_VALUE)
			panel.addTextField("Maximum number of elements", maxElements+"");
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
