package yangTree.nodes;


import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

import applet.InfoPanel;


@SuppressWarnings("serial")
public class RootNode extends ContainerNode {
	
	private static ImageIcon icon = null;
	
	private String name = "root";
	
	public RootNode(){
		super("root");
	}
	
	public RootNode(String name){
		super("root");
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public void buildInfoPanel(InfoPanel infos){
		infos.setHelpInfo();
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
