import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import yangTree.nodes.*;

import jyang.parser.*;

public class YangSchemaTreeGenerator {

	private jyang parser = null;

	public YangSchemaTreeGenerator(String[] args) {
		String ip = args[0];

		int nbPrefixes = Integer.parseInt(args[1]);

		String[] prefixes = new String[2 * nbPrefixes];
		for (int i = 0; i < nbPrefixes * 2; i++) {
			prefixes[i] = args[i + 2];
		}

		int nbmodules = Integer.parseInt(args[nbPrefixes * 2 + 2]);

		String[] yarg = new String[nbmodules];

		for (int i = 0; i < nbmodules; i++) {
			yarg[i] = args[i + nbPrefixes * 2 + 3];
		}

		String modulesArgs[] = new String[3 * nbmodules];
		for (int i = 0; i < 3 * nbmodules; i++) {
			modulesArgs[i] = args[3 + nbPrefixes * 2 + nbmodules + i];
		}

		parser = new jyang(yarg);
		Hashtable<String, YANG_Specification> specs = parser.getYangsSpecs();
		Map<String, DataTree> treeMap = new HashMap<String, DataTree>();
		for (Enumeration<YANG_Specification> especs = specs.elements(); especs
				.hasMoreElements();) {
			YANG_Specification spec = especs.nextElement();
			treeMap.put(spec.getName(), buildModuleTree(spec));
		}
		DataNode schemaTree = buildGeneralTree(modulesArgs, prefixes, treeMap);

		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(ip + ".yang.byte"));
			os.writeObject(schemaTree);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * Link the different module Trees, and build the namespaces.
	 */
	public static DataNode buildGeneralTree(String[] modulesArgs,
			String[] prefixes, Map<String, DataTree> treeMap) {

		LinkedList<ContainerNode> usedNodes = new LinkedList<ContainerNode>();
		RootNode rootNode = new RootNode("Yang Specifications");

		for (int i = 0; i < modulesArgs.length; i = i + 3) {
			String[] pathNodes = modulesArgs[i + 1].split("/");
			ContainerNode lastUsedNode = rootNode;
			boolean mismatchFound = false;

			for (int j = 1; j < pathNodes.length; j++) {
				String[] splitNodeName = pathNodes[j].split(":");
				ContainerNode currentNode;
				if (splitNodeName.length == 1) {
					currentNode = new ContainerNode(splitNodeName[0]);
				} else {
					currentNode = new ContainerNode(splitNodeName[1]);
					String prefixNS = splitNodeName[0];
					currentNode
							.setNameSpace(new NameSpace(prefixNS, true));
					// Searching for the NS prefix in the prefix list
					for (int k = 0; k < prefixes.length; k = k + 2) {
						if (prefixNS.equals(prefixes[k])) {
							currentNode.setNameSpace(new NameSpace(
									prefixNS, prefixes[k + 1]));
						}
					}
				}

				if (mismatchFound) {

					lastUsedNode.addContent(currentNode);
					usedNodes.add(currentNode);
					lastUsedNode = currentNode;

				} else {

					ContainerNode match = null;
					for (ContainerNode node : usedNodes) {
						if (node.equalsTo(currentNode)) {
							match = node;
						}
					}

					if (match == null) {
						mismatchFound = true;
						usedNodes.add(currentNode);
						lastUsedNode.addContent(currentNode);
						lastUsedNode = currentNode;
					} else {
						lastUsedNode = match;
					}

				}

			}
			NameSpace moduleNS = new NameSpace(modulesArgs[i + 2],
					false);
			DataTree subroot = treeMap.get(modulesArgs[i]);

			// Special handling if two linked containers have the same name : in
			// this case, there are merged.
			DataNode secondSubRoot = subroot.getNodes().getFirst();
			if (subroot.getNodes().size() == 1
					&& lastUsedNode.toString().equals(secondSubRoot.toString())) {
				lastUsedNode.setNameSpace(lastUsedNode.getNameSpace()
						.mergeWith(moduleNS));
				subroot = (DataTree) secondSubRoot;
			}

			// If a module have a prefix and an explicit namespace, they are
			// merged.
			if (lastUsedNode.getNameSpace().getPrefix() == null) {
				lastUsedNode.setNameSpace(moduleNS);
			} else {
				lastUsedNode.setNameSpace(lastUsedNode.getNameSpace()
						.mergeWith(moduleNS));
			}

			for (DataNode node : subroot.getNodes()) {
				lastUsedNode.addContent(node);
			}
		}

		return rootNode;
	}

	public static DataTree buildModuleTree(YANG_Specification spec) {
		YangTreeNode ytn = spec.getSchemaTree();
		RootNode rootNode = new RootNode();
		Vector<YangTreeNode> childs = ytn.getChilds();
		for (YangTreeNode child : childs) {
			rootNode.addContent(buildModuleTree(child));
		}
		return rootNode;
	}

	public static DataNode buildModuleTree(YangTreeNode ytn) {

		YANG_Body body = ytn.getNode();

		if (body instanceof YANG_Container) {
			YANG_Container cont = (YANG_Container) body;
			ContainerNode node = new ContainerNode(cont);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addContent(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			ListNode node = new ListNode(list,list.getKey().getKey());
			Vector<YangTreeNode> childs = ytn.getChilds();
			Vector<DataNode> elements = new Vector<DataNode>();
			for (YangTreeNode child : childs) {
				elements.add(buildModuleTree(child));
			}
			node.setEntry(elements);

			return node;

		} else if (body instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) body;
			ChoiceNode node = new ChoiceNode(choice);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addContent(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof YANG_Case) {
			YANG_Case ycase = (YANG_Case) body;
			CaseNode node = new CaseNode(ycase);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				if (child.getNode() instanceof YANG_Leaf){
					YANG_Leaf leaf = (YANG_Leaf) child.getNode();
					if (!leaf.getType().getType().equalsIgnoreCase("empty")){
						node.addContent(buildModuleTree(child));
					}
				} else {
					node.addContent(buildModuleTree(child));
				}
			}

			return node;

		} else if (body instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) body;
			
			String leafdefault = null;
			if (leaf.getDefault() != null)
				leafdefault = leaf.getDefault().getDefault();

			String typeleafdefault = null;
			if (leaf.getType().getTypedef() != null)
				if (leaf.getType().getTypedef().getDefault() != null)
					typeleafdefault = leaf.getType().getTypedef()
							.getDefault().getDefault();
			
			boolean mandatory = false;
			if (leaf.getMandatory()!=null){
				mandatory = !leaf.getMandatory().getMandatory().equals("false");
			}
			
			String description = null;
			if (leaf.getDescription()!=null){
				description = leaf.getDescription().getDescription();
			}
			
			LeafNode node = new LeafNode(leaf, null, mandatory, leafdefault, typeleafdefault, description);
			node.setType(leaf.getType().getType());
			return node;

		} else if (body instanceof YANG_LeafList) {
			YANG_LeafList leafList = (YANG_LeafList) body;
			LeafListNode node = new LeafListNode(leafList);
			node.setType(leafList.getType().getType());
			return node;

		}

		System.err.println("Unexcepted YangTreeNode format : "
				+ body.getClass());
		return null;

	}

	public static void main(String[] argv) {
		new YangSchemaTreeGenerator(argv);
	}

}
