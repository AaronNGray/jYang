package yangtree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import jyang.parser.CaseDataDef;


@SuppressWarnings("serial")
public class CaseNode extends YangInnerNode {
	
	private static ImageIcon icon = null;


	public CaseNode(CaseDataDef c) {
		definition = c;
	}

	public String toString() {
		return definition.getBody();
	}

	@Override
	public CaseNode cloneBody() {
		CaseNode clone = new CaseNode((CaseDataDef) definition);
		return clone;
	}
	
	public String[] xmlFilter(){
		return new String[]{"",""};
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
