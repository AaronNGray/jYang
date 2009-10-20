/**
###############################################################################
#                                                                             #
# YencaP software, LORIA-INRIA LORRAINE, MADYNES RESEARCH TEAM                #
# Copyright (C) 2009  Emmanuel NATAF                                          #
#                                                                             #
# This library is free software; you can redistribute it and/or               #
# modify it under the terms of the GNU Lesser General Public                  #
# License as published by the Free Software Foundation; either                #
# version 2.1 of the License, or (at your option) any later version.          #
#                                                                             #
# This library is distributed in the hope that it will be useful,             #
# but WITHOUT ANY WARRANTY; without even the implied warranty of              #
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU           #
# Lesser General Public License for more details.                             #
#                                                                             #
# You should have received a copy of the GNU Lesser General Public            #
# License along with this library; if not, write to the Free Software         #
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA   #
#                                                                             #
# Author Info:                                                                #
#   Name : Emmanuel Nataf and cyrille Cornu		                              #
#   Email: nataf@loria.fr										              #
#                                                                             #
###############################################################################
**/
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

import yangtree.attributes.LeafType;
import yangtree.attributes.NameSpace;
import yangtree.attributes.builtinTypes.BuiltinType;
import yangtree.nodes.*;
import yangtree.nodes.YangNode;

import jyang.parser.*;

/**
 * 
 * @author Cornu - nataf
 * 
 * This class generates a yangTreeNode from  yang specifications and xpath
 * It writes a file object for the netconf agent that has announced its yang 
 * capabilities
 *
 */
public class YangSchemaTreeGenerator {

	private jyang parser = null;

	private static YangContext context = null;

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
		if (!parser.isParsingOk())
			System.exit(-1);
		
		Hashtable<String, YANG_Specification> specs = parser.getYangsSpecs();
		Map<String, YangInnerNode> treeMap = new HashMap<String, YangInnerNode>();
		for (Enumeration<YANG_Specification> especs = specs.elements(); especs
				.hasMoreElements();) {
			YANG_Specification spec = especs.nextElement();
			try {
				context = spec.buildSpecContext(new String[0], null,
						new Vector<String>());
			} catch (YangParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			treeMap.put(spec.getName(), buildModuleTree(spec));
		}
		RootNode schemaTree = buildGeneralTree(modulesArgs, prefixes, treeMap);

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
	public static RootNode buildGeneralTree(String[] modulesArgs,
			String[] prefixes, Map<String, YangInnerNode> treeMap) {

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
					currentNode.setNameSpace(new NameSpace(prefixNS, true));
					// Searching for the NS prefix in the prefix list
					for (int k = 0; k < prefixes.length; k = k + 2) {
						if (prefixNS.equals(prefixes[k])) {
							currentNode.setNameSpace(new NameSpace(prefixNS,
									prefixes[k + 1]));
						}
					}
				}

				if (mismatchFound) {

					lastUsedNode.addChild(currentNode);
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
						lastUsedNode.addChild(currentNode);
						lastUsedNode = currentNode;
					} else {
						lastUsedNode = match;
					}

				}

			}
			NameSpace moduleNS = new NameSpace(modulesArgs[i + 2], false);
			YangInnerNode subroot = treeMap.get(modulesArgs[i]);

			// Special handling if two linked containers have the same name : in
			// this case, there are merged.
			YangNode secondSubRoot = subroot.getDescendantNodes().getFirst();
			if (subroot.getDescendantNodes().size() == 1
					&& lastUsedNode.toString().equals(secondSubRoot.toString())) {
				lastUsedNode.setNameSpace(lastUsedNode.getNameSpace()
						.mergeWith(moduleNS));
				subroot = (YangInnerNode) secondSubRoot;
			}

			// If a module have a prefix and an explicit namespace, they are
			// merged.
			if (lastUsedNode.getNameSpace().getPrefix() == null) {
				lastUsedNode.setNameSpace(moduleNS);
			} else {
				lastUsedNode.setNameSpace(lastUsedNode.getNameSpace()
						.mergeWith(moduleNS));
			}

			for (YangNode node : subroot.getDescendantNodes()) {
				lastUsedNode.addChild(node);
			}
		}

		return rootNode;
	}

	public static YangInnerNode buildModuleTree(YANG_Specification spec) {
		YangTreeNode ytn = spec.getSchemaTree();
		RootNode rootNode = new RootNode();
		Vector<YangTreeNode> childs = ytn.getChilds();
		for (YangTreeNode child : childs) {
			rootNode.addChild(buildModuleTree(child));
		}
		return rootNode;
	}
	
	

	public static YangNode buildModuleTree(YangTreeNode ytn) {

		YANG_Body body = ytn.getNode();

		if (body instanceof YANG_Container) {
			YANG_Container cont = (YANG_Container) body;
			ContainerNode node = new ContainerNode(cont);
			
			for (YANG_TypeDef typeDef : cont.getTypeDefs()){
				try {
					context.addNode(typeDef);
				} catch (YangParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addChild(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			ListNode node = new ListNode(list, list.getKey().getKeyLeaves());
			
			for (YANG_TypeDef typeDef : list.getTypeDefs()){
				try {
					context.addNode(typeDef);
				} catch (YangParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addChild(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) body;
			ChoiceNode node = new ChoiceNode(choice);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				
				node.addChild(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof CaseDataDef) {
			CaseDataDef ycase = (CaseDataDef) body;
			CaseNode node = new CaseNode(ycase);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addChild(buildModuleTree(child));
			}

			return node;

		} else if (body instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) body;
			LeafNode node = new LeafNode(leaf);

			if (leaf.getDefault() != null) {
				node.setDefaultValue(leaf.getDefault().getDefault());
			} else {
				if (leaf.getType().getTypedef() != null) {
					if (leaf.getType().getTypedef().getDefault() != null) {
						node.setDefaultValue(leaf.getType().getTypedef()
								.getDefault().getDefault());
					}
				}
			}

			if (leaf.getMandatory() != null) {
				node.setMandatory(!leaf.getMandatory().getMandatory().equals(
						"false"));
			}

			if (leaf.getDescription() != null) {
				node.setDescription(leaf.getDescription().getDescription());
			}

			LeafType typeDef = null;

			try {
				if (context.getTypeDef(leaf.getType()) != null) {
					YANG_TypeDef ytypeDef = context.getTypeDef(leaf.getType());
					

					while (!BuiltinType.isBuiltinType(ytypeDef.getType().getType())){
						ytypeDef = context.getTypeDef(ytypeDef.getType());
					}
					
					typeDef = new LeafType(ytypeDef,leaf.getType().getType());
				} else {
					typeDef = new LeafType(leaf.getType());
				}
			} catch (YangParserException e) {
				e.printStackTrace();
			}

			node.setTypeDef(typeDef);
			return node;

		} else if (body instanceof YANG_LeafList) {
			YANG_LeafList leafList = (YANG_LeafList) body;
			LeafListNode node = new LeafListNode(leafList);
			
			LeafType typeDef = null;

			try {
				if (context.getTypeDef(leafList.getType()) != null) {
					YANG_TypeDef ytypeDef = context.getTypeDef(leafList.getType());
					
					while (!BuiltinType.isBuiltinType(ytypeDef.getType().getType())){
						ytypeDef = context.getTypeDef(ytypeDef.getType());
					}
					
					typeDef = new LeafType(ytypeDef,leafList.getType().getType());
				} else {
					typeDef = new LeafType(leafList.getType());
				}
			} catch (YangParserException e) {
				e.printStackTrace();
			}
			
			node.setType(typeDef);
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
