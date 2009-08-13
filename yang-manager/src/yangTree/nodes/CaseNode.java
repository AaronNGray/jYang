package yangTree.nodes;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import jyang.parser.YANG_Case;


public class CaseNode extends YangInnerNode {
	
	private static ImageIcon icon = null;


	public CaseNode(YANG_Case c) {
		definition = c;
	}

	public void addContent(YangNode d) {
		nodes.add(d);
	}

	public String toString() {
		return definition.getBody();
	}
	
	public CaseNode cloneBody(){
		return new CaseNode((YANG_Case) definition);
	}

	public String getNodeType() {
		return "Case";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/case.png");
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
