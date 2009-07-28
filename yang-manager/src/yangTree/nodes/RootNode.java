package yangTree.nodes;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.ImageIcon;


public class RootNode extends ContainerNode {
	
	private static ImageIcon icon = null;
	
	private String name = "root";
	
	public RootNode(){
		super("");
	}
	
	public RootNode(String name){
		super("");
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/root.png");
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
