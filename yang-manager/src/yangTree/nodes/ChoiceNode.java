package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import jyang.parser.YANG_Choice;

public class ChoiceNode extends DataTree {
	
	private static ImageIcon icon = null;

	public ChoiceNode(YANG_Choice choice){
		definition = choice;
	}
	
	
	public String toString(){
		return definition.getBody();
	}
	
	
	public String getNodeType() {
		return "Choice";
	}

	public DataTree cloneBody() {
		return new ChoiceNode((YANG_Choice) definition);
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
	
}
