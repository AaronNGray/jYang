package yangTree.attributes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.tree.TreePath;

import applet.Util;

import yangTree.nodes.YangNode;
import yangTree.nodes.YangInnerNode;
import yangTree.nodes.LeafNode;
import yangTree.nodes.ListNode;

import yangTree.nodes.RootNode;

/**
 * Represents an absolute path in a YangTree.
 */
@SuppressWarnings("serial")
public class YangTreePath implements Serializable {

	private String path;
	private RootNode root;
	private LinkedList<LeafNode> leavesAtPath = null;

	/**
	 * Creates a path at the root.
	 */
	public YangTreePath(RootNode root) {
		this.path = "/";
		this.root = root;
	}
	
	/**
	 * Creates a YangTreePath from a TreePath.
	 */ 
	public YangTreePath(RootNode root, YangNode[] nodesArray){
		this.root = root;
		this.path = "/";
		for (int i=0;i<nodesArray.length;i++){
			path += nodesArray[i].getName() + "/";
		}
	}

	private YangTreePath(String path, RootNode root) {
		this.path = path;
		this.root = root;
	}

	public YangTreePath clone() {
		return new YangTreePath(path, root);
	}

	/**
	 * Appends a node to this path
	 */
	public void appendChild(String child) {
		path += child + "/";
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
	 * For example, if this path is "/a/b/" and the relative path is "../c", the result will be "/a/c/".<br>
	 * <br>
	 * <b>WARNING</b> : Due to relative list-key values considerations, use <u>only</u> this method when the tree is <u>totally</u> filled.
	 */
	public YangTreePath buildRelativePath(String relativePath){
		
		String result = path ;
		if (relativePath.startsWith("/")) {
			result = relativePath;
		} else {
			String cutPath = relativePath;
			while (cutPath.startsWith("../")) {
				cutPath = cutPath.substring(3);
				result = result.substring(0, result.substring(0, result.length()-1).lastIndexOf("/"));
			}
			result += "/" + cutPath;
		}
		if (!result.endsWith("/"))
			result += "/";

		while (result.indexOf("current") != -1) {
			String cutPath = result
					.substring(result.indexOf("current") + 7);
			String subPath = cutPath.substring(cutPath.indexOf("/")+1, cutPath.indexOf("]"));	
			YangTreePath subSolvedPath = buildRelativePath(subPath);
			String value = subSolvedPath.getLeavesAtThisPath().getFirst().getValue();
			result = result.substring(0, result.indexOf("current"))
					+ value + cutPath.substring(cutPath.indexOf("]"));
		}

		return new YangTreePath(result,root);
	}

	/*
	 * Recursive method used to retrieve leaves at a specific path, starting from a specific node.
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
			String firstArg = stringToParse.substring(stringToParse
					.indexOf("[") + 1, stringToParse.indexOf("]"));
			String[] args = firstArg.split("=");
			result.put(Util.cleanValueString(args[0]), Util
					.cleanValueString(args[1]));
			stringToParse = stringToParse
					.substring(stringToParse.indexOf("]") + 1);
		}

		return result;

	}

	public String toString() {
		return path;
	}

}
