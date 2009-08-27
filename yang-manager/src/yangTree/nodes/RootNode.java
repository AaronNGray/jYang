package yangTree.nodes;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import yangTree.ChoiceStillPresentException;

import applet.InfoPanel;

@SuppressWarnings("serial")
public class RootNode extends ContainerNode {

	private static ImageIcon icon = null;

	private String name = "root";
	private TreePath path = null;

	/**
	 * Creates a default RootNode.
	 */
	public RootNode() {
		super("root");
	}

	/***
	 * Creates a RootNode with a specific name.
	 * 
	 * @param name
	 *            : the name of the node.
	 */
	public RootNode(String name) {
		super("root");
		this.name = name;
	}

	/**
	 * Sets the path of this RootNode in a "super" yang tree in which this node
	 * is placed.
	 * 
	 * @param path
	 *            : the path of this node in a yang tree.
	 */
	public void setPath(TreePath path) {
		this.path = path;
	}

	/**
	 * Returns the path of a node in the super YangTree in which this RootNode
	 * is placed.
	 * 
	 * @param subPath
	 *            : the path of a node in the tree which root is this node.
	 * @return : the path of the node in the super tree which contains this
	 *         RootNode.
	 */
	public TreePath getSuperPath(TreePath subPath) {
		if (path == null || path.getPathCount() == 0)
			return subPath;
		TreePath result = path;
		Object[] subPathArray = subPath.getPath();
		for (int i = 1; i < subPathArray.length; i++) {
			result = result.pathByAddingChild(subPathArray[i]);
		}
		return result;
	}

	/**
	 * Removes checks then performs new checks for all the nodes of this tree.
	 */
	public void recheckAll() {
		uncheckSubtree();
		checkSubtree();
	}

	public void setName(String name) {
		this.name = name;
	}

	public RootNode cloneBody() {
		RootNode result = new RootNode(name);
		result.setNameSpace(nameSpace);
		result.setPath(path);
		return result;
	}

	public String toString() {
		return name;
	}

	public void buildInfoPanel(InfoPanel infos) {
		infos.setHelpInfo();
	}

	public ImageIcon getIcon() {
		if (icon == null) {
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
	public String getXMLRepresentation() throws ChoiceStillPresentException {

		String result = "";
		for (YangNode node : descendantNodes) {
			if (node instanceof YangInnerNode){
				result += ((YangInnerNode) node).getXMLReplaceRepresentation();
			} else {
				result += node.getXMLRepresentation();
			}
		}
		
		if (path != null) {
			String[] pathXML = buildPathXML(path);
			result = pathXML[0] + result + pathXML[1];
		}
		return result;

	}

	private static String[] buildPathXML(TreePath path) {
		if (path.getPathCount()==1)
			return new String[]{"",""};
		YangNode node = (YangNode) path.getLastPathComponent();
		String[] nextPath = buildPathXML(path.getParentPath());
		return new String[] { nextPath[0] + node.xmlFilter()[0] , node.xmlFilter()[1] + nextPath[1] };
	}

}
