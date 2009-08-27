package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import applet.InfoPanel;

/**
 * A node used to represent that a specific leaf in the specification tree have no occurrence in the data tree.
 */
@SuppressWarnings("serial")
public class EmptyNode extends YangLeaf {
	
	private static ImageIcon icon = null;
	
	private String description;
	
	/**
	 * Creates an new <code>EmptyNode</code>.
	 * @param description : the text associated with this node when displayed.
	 */
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
	public EmptyNode cloneBody() {
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
		return null;
	}

	@Override
	public void setValue(String value) {}

}
