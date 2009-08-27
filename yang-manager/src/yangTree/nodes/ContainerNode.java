package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import jyang.parser.YANG_Container;

@SuppressWarnings("serial")
public class ContainerNode extends YangInnerNode {

	private static ImageIcon icon = null;
	private static ImageIcon errorIcon = null;


	public ContainerNode(YANG_Container c) {
		definition = c;
	}

	public ContainerNode(String name) {
		YANG_Container ycontainer = new YANG_Container(-1);
		ycontainer.setContainer(name);
		definition = ycontainer;
	}

	public boolean equalsTo(ContainerNode node) {
		return (getNameSpace() + ":" + getName()).equals(node.getNameSpace()
				+ ":" + node.getName());
	}

	public ContainerNode cloneBody() {
		ContainerNode clone = new ContainerNode((YANG_Container) definition);
		clone.setNameSpace(nameSpace);
		if (specificationNode==null){
			clone.specificationNode = this;
		} else {
			clone.specificationNode = specificationNode;
		}
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
