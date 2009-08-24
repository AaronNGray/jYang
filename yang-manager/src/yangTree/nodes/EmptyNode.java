package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import applet.InfoPanel;

/**
 * A node used to represent that there is no node...
 */
@SuppressWarnings("serial")
public class EmptyNode extends YangLeaf {
	
	private static ImageIcon icon = null;
	
	public String description;
	
	public EmptyNode(String description){
		this.description = description;
	}

	@Override
	public void buildInfoPanel(InfoPanel panel){
		panel.clean();
		panel.setTitleText(description);
	}
	
	public String toString(){
		return description;
	}

	@Override
	public YangNode cloneBody() {
		return this;
	}

	@Override
	public ImageIcon getIcon() {
		if (icon == null) {
			InputStream is = getClass().getResourceAsStream(
					"/icons/noLeaf.png");
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

	@Override
	public String getNodeType() {
		return "";
	}

	@Override
	public void setValue(String value) {}

}
