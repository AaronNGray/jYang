package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import jyang.parser.YANG_LeafList;

public class LeafListNode extends DataNode {
	
	private static ImageIcon icon = null;
	
	private String value;
	private String type;
	

	public LeafListNode(YANG_LeafList d){
		definition = d;
	}
	
	public String getValue() {
		return value;
	}

	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public LeafListNode cloneBody(){
		LeafListNode result = new LeafListNode((YANG_LeafList) definition);
		result.setType(type);
		return result;
	}
	
	public String toString(){
		if (value == null) {
			return definition.getBody();
		} else {
			return definition.getBody() + " : " + value;
		}
	}
		
	public void setValue(String v) {
		value = v;
	}
	
	public String getNodeType() {
		return "Leaf list";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/leaflist.png");
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
