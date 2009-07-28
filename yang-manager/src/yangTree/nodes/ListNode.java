package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ImageIcon;

import jyang.parser.YANG_List;

public class ListNode extends DataTree {
	
	private static ImageIcon icon = null;

	private String keyvalue;
	private String keyname;
	
	public ListNode(YANG_List d) {
		definition = d;
	}
	
	public ListNode(YANG_List d, String keyname) {
		definition = d;
		this.keyname = keyname;
	}
	
	public String getKeyName(){
		return keyname;
	}
	
	public String getKeyValue(){
		return keyvalue;
	}
	
	@Override
	public void addContent(DataNode node){
		if (node instanceof LeafNode){
			LeafNode leaf = (LeafNode) node;
			if (leaf.getName().equals(keyname)){
				leaf.setIsKey(true);
				if (leaf.getValue()!=null) keyvalue=leaf.getValue();
			}
		}
		nodes.add(node);
	}

	public void setKeyValue(String kv) {
		keyvalue = kv;
		
	}
	
	public ListNode cloneBody(){
		return new ListNode((YANG_List) definition,keyname);
	}
	
	public String toString(){
		if (keyvalue==null){
			return definition.getBody();
		} else {
			return definition.getBody()+" : "+keyvalue;
		}
	}

	public String getNodeType() {
		return "List";
	}
	
	public ImageIcon getIcon(){
		if (icon==null){
			InputStream is = getClass().getResourceAsStream("/icons/list.png");
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
