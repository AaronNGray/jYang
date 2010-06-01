package yangtree.attributes.copy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import applet.Util;

import yangtree.nodes.LeafNode;
import yangtree.nodes.ListNode;
import yangtree.nodes.YangInnerNode;
import yangtree.nodes.YangNode;
/**
 * Represents an absolute path in a YangTree.
 */
@SuppressWarnings("serial")
public class YangTreePath implements Serializable {

	private String path;
	private String mandatoryNode = null;
	private YangNode root = null;
	private LinkedList<LeafNode> leavesAtPath = null;

	/**
	 * Creates an new empty path.
	 */
	public YangTreePath() {
	}

	/**
	 * Creates a new empty path that specifies a mandatory node.
	 */
	public YangTreePath(String mandatoryNode) {
		this.mandatoryNode = mandatoryNode;
	}

	private YangTreePath(String path, YangNode root, String mandatoryNode) {
		this.path = path;
		this.root = root;
		this.mandatoryNode = mandatoryNode;
	}

	/**
	 * Returns <code>true</code> if this path contains its mandatory node, or if
	 * no mandatory node have been specified. Returns <code>false</code>
	 * otherwise.
	 */
	public boolean isPathReachable() {
		if (mandatoryNode==null)
			return true;
		return path.contains("/"+mandatoryNode+"/");
	}

	/**
	 * Returns a new path built by appending a node to this path
	 */
	public YangTreePath pathByAppendingChild(YangNode child) {
		if (root==null) {
			return new YangTreePath("/",child,mandatoryNode);
		}
		return new YangTreePath(path + child.getName() + "/", root, mandatoryNode);
	}

	/**
	 * Gets all the leaves that are at this path in the tree.
	 */
	public LinkedList<LeafNode> getLeavesAtThisPath() {
		if (leavesAtPath == null)
			leavesAtPath = getNodesAtPath(path, root);
		return leavesAtPath;
	}

	/**
	 * Builds a new path given a relative path from this path.<br>
	 * For example, if this path is "/a/b/" and the relative path is "../c", the
	 * result will be "/a/c/".<br>
	 * <br>
	 * <b>WARNING</b> : Due to relative list-key values considerations, use
	 * <u>only</u> this method when the tree is <u>totally</u> filled.
	 * @throws ReferenceLeafNotRetrievedException If the result do not contain the mandatory node.
	 */
	public YangTreePath buildRelativePath(String relativePath) throws ReferenceLeafNotRetrievedException {

		String result = path;
		if (relativePath.startsWith("/")) {
			result = relativePath;
		} else {
			String cutPath = relativePath;
			while (cutPath.startsWith("../")) {
				cutPath = cutPath.substring(3);
				result = result.substring(0, result.substring(0, result.length() - 1).lastIndexOf("/"));
			}
			result += "/" + cutPath;
		}
		if (!result.endsWith("/"))
			result += "/";

		while (result.indexOf("current") != -1) {
			String cutPath = result.substring(result.indexOf("current") + 7);
			String subPath = cutPath.substring(cutPath.indexOf("/") + 1, cutPath.indexOf("]"));
			YangTreePath subSolvedPath = buildRelativePath(subPath);
			String value = subSolvedPath.getLeavesAtThisPath().getFirst().getValue();
			result = result.substring(0, result.indexOf("current")) + value + cutPath.substring(cutPath.indexOf("]"));
		}
		
		YangTreePath pathResult =  new YangTreePath(result, root, mandatoryNode);
		if (!pathResult.isPathReachable())
			throw new ReferenceLeafNotRetrievedException();
		return pathResult;
	}

	/*
	 * Recursive method used to retrieve leaves at a specific path, starting
	 * from a specific node.
	 */
	private static LinkedList<LeafNode> getNodesAtPath(String currentPath, YangNode node) {

		LinkedList<LeafNode> result = new LinkedList<LeafNode>();
		if (currentPath.equals("/")) {
			result.add((LeafNode) node);
			return result;
		}

		String tempPath = currentPath.substring(1);
		String[] nodes = tempPath.split("/");
		String currentNode = nodes[0];

		String nodeName = currentNode;
		Map<String, String> predicates = null;
		if (currentNode.indexOf("[") != -1) {
			nodeName = currentNode.substring(0, currentNode.indexOf("["));
			predicates = getPredicates(currentNode);
		}

		LinkedList<YangNode> nodesMatched = new LinkedList<YangNode>();
		for (YangNode cnode : ((YangInnerNode) node).getDescendantNodes()) {
			if (cnode.getName().equals(nodeName)) {
				if (cnode instanceof ListNode && predicates != null) {
					if (((ListNode) cnode).hasSameKey(predicates))
						nodesMatched.add(cnode);
				} else {
					nodesMatched.add(cnode);
				}
			}
		}

		tempPath = tempPath.substring(tempPath.indexOf("/"));

		for (YangNode child : nodesMatched) {
			result.addAll(getNodesAtPath(tempPath, child));
		}
		return result;

	}

	/**
	 * Parses a string to build a map of predicates keys and values.
	 */
	private static Map<String, String> getPredicates(String stringToParse) {

		Map<String, String> result = new HashMap<String, String>();

		while (stringToParse.indexOf("[") != -1) {
			String firstArg = stringToParse.substring(stringToParse.indexOf("[") + 1, stringToParse.indexOf("]"));
			String[] args = firstArg.split("=");
			result.put(Util.cleanValueString(args[0]), Util.cleanValueString(args[1]));
			stringToParse = stringToParse.substring(stringToParse.indexOf("]") + 1);
		}

		return result;

	}

	public String toString() {
		return path;
	}

}
