package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

import yangTree.ChoiceStillPresentException;

import jyang.parser.YANG_Choice;

@SuppressWarnings("serial")
public class ChoiceNode extends YangInnerNode {
	
	private static ImageIcon icon = null;

	public ChoiceNode(YANG_Choice choice){
		definition = choice;
	}
	
	public String toString(){
		return definition.getBody();
	}
	
	public ChoiceNode cloneBody(){
		ChoiceNode clone = new ChoiceNode((YANG_Choice) definition);
		return clone;
	}
	
	public String[] xmlFilter(){
		return new String[]{"",""};
	}
	
	
	public String getNodeType() {
		return "Choice";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/choice.png");
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
	
	public String getXMLRepresentation() throws ChoiceStillPresentException {
		throw new ChoiceStillPresentException();
	}
	
}
