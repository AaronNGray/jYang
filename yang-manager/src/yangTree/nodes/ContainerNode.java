package yangTree.nodes;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.swing.ImageIcon;

import jyang.parser.YANG_Container;

public class ContainerNode extends DataTree {
	
	private static ImageIcon icon = null;


	public ContainerNode(YANG_Container c) {
		definition = c;
	}
	
	public ContainerNode(String name){
		YANG_Container ycontainer = new YANG_Container(-1);
		ycontainer.setContainer(name);
		definition = ycontainer; 
	}
	
	public boolean equalsTo(ContainerNode node){
		return (getNameSpace()+":"+getName()).equals(node.getNameSpace()+":"+node.getName());
	}
	
	public ContainerNode cloneBody(){
		return new ContainerNode((YANG_Container) definition);
	}

	public String toString() {
		return getName();
	}

	public String getNodeType() {
		return "Container";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/container.png");
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
