package yangTree.attributes;

import java.io.Serializable;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import yangTree.nodes.DataNode;
import yangTree.nodes.DataTree;
import yangTree.nodes.LeafNode;
import yangTree.nodes.RootNode;

public class YangTreePath implements Serializable {

	private String path;
	public RootNode root;

	public YangTreePath(RootNode root) {
		this.path = "";
		this.root = root;
	}

	private YangTreePath(String path,  RootNode root) {
		this.path = path;
		this.root = root;
	}

	public void appendChild(String child) {
		path += "/"+child;
	}

	private void appendPath(String otherPath) {
		path += otherPath;
	}

	private void removeChild() {
		path = path.substring(0, path.lastIndexOf("/"));
	}

	public YangTreePath buildRootPath(String relativePath) {
		YangTreePath result = new YangTreePath(path, root);
		String tempPath = relativePath;
		while (tempPath.startsWith("../")) {
			tempPath = tempPath.substring(3);
			result.removeChild();
		}
		result.appendPath("/" + tempPath);
		return result;
	}

	private LeafNode solvePath(DataNode node) {

		if (path.equals("/"))
			return (LeafNode) node;

		String tempPath = path.substring(1);
		String[] nodes = tempPath.split("/");
		DataNode match = null;
		for (DataNode cnode : ((DataTree) node).getNodes()) {
			if (cnode.getName().equals(nodes[0]))
				match = cnode;
		}
		if (match == null) return null;
		if (tempPath.indexOf("/")==-1){
			tempPath="/";
		} else {
			tempPath = tempPath.substring(tempPath.indexOf("/"));
		}
		YangTreePath newPath = new YangTreePath(tempPath, root);
		return newPath.solvePath(match);

	}
	
	public LeafNode solvePath(){
		return solvePath(root);
	}

	public String toString() {
		return path;
	}

}
