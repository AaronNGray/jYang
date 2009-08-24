package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

import jyang.parser.YANG_Container;

@SuppressWarnings("serial")
public class ContainerNode extends YangInnerNode implements CheckableYangNode {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;

	private ValueCheck check = null;

	public ContainerNode(YANG_Container c) {
		definition = c;
	}

	public ContainerNode(String name) {
		YANG_Container ycontainer = new YANG_Container(-1);
		ycontainer.setContainer(name);
		definition = ycontainer;
	}

	public ValueCheck getCheck() {
		if (check == null) {
			check = new ValueCheck();
		}
		return check;
	}
	
	public void check(){
		for (YangNode child : descendantNodes){
			for (YangNode cchild : descendantNodes){
				if (child!=cchild && child.getName().equals(cchild.getName()) && child instanceof ListNode && cchild instanceof ListNode) {
					ListNode list = (ListNode) child;
					ListNode llist = (ListNode) cchild;
					if (list.hasSameKey(llist))
						list.getCheck().addUnitCheck(new UnitValueCheck("Duplicated Key : "+list.getKeysRepresentation()));
				}
			}
		}
	}

	public boolean equalsTo(ContainerNode node) {
		return (getNameSpace() + ":" + getName()).equals(node.getNameSpace()
				+ ":" + node.getName());
	}

	public ContainerNode cloneBody() {
		ContainerNode clone = new ContainerNode((YANG_Container) definition);
		return clone;
	}
	
	
	public String toString() {
		return getName();
	}

	public String getNodeType() {
		return "Container";
	}

	public ImageIcon getIcon() {

		if (check != null && !check.isOk()) {

			if (errorIcon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/containerError.png");
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

			if (icon == null) {
				InputStream is = getClass().getResourceAsStream(
						"/icons/container.png");
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
