package yangTree.nodes;


import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import applet.InfoPanel;


@SuppressWarnings("serial")
public class RootNode extends ContainerNode {
	
	private static ImageIcon icon = null;
	
	private String name = "root";
	private TreePath path = null;
	
	/**
	 * Creates a default RootNode.
	 */
	public RootNode(){
		super("root");
	}
	
	/***
	 * Creates a RootNode with a specific name.
	 * @param name : the name of the node.
	 */
	public RootNode(String name){
		super("root");
		this.name = name;
	}
	
	/**
	 * Sets the path of this RootNode in a "super" yang tree in which this node is placed.
	 * @param path : the path of this node in a yang tree.
	 */
	public void setPath(TreePath path){
		this.path = path;
	}
	
	/**
	 * Returns the path of a node in the super YangTree in which this RootNode is placed.
	 * @param subPath : the path of a node in the tree which root is this node.
	 * @return : the path of the node in the super tree which contains this RootNode.
	 */
	public TreePath getSuperPath(TreePath subPath){
		if (path==null || path.getPathCount()==0)
			return subPath;
		TreePath result = path;
		Object[] subPathArray = subPath.getPath();
		for (int i=1;i<subPathArray.length;i++) {
			result = result.pathByAddingChild(subPathArray[i]);
		}
		return result;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public RootNode cloneBody(){
		RootNode result = new RootNode(name);
		result.setPath(path);
		return result;
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
	
	@Override
	public String getXMLRepresentation(){
		
		String result = "";
		if (path != null){
			Object[] arrayPath = path.getPath();
			for (int i=1;i<arrayPath.length;i++){
				result+="<"+((YangNode) arrayPath[i]).getName()+">";
			}
		}
		
		if (nameSpace!=null && nameSpace.getNameSpace() != null) {
			result = result + nameSpace.getXMLArg();
		}
		result += ">";
		
		for (YangNode child : getDescendantNodes()){
			result+= child.getXMLRepresentation();
		}
		
		if (path != null){
			Object[] arrayPath = path.getPath();
			for (int i=arrayPath.length-1;i>0;i--){
				result+="</"+((YangNode) arrayPath[i]).getName()+">";
			}
		}
		return result;
		
	}

}
