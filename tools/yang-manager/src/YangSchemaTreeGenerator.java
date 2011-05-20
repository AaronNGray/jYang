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

import jyang.jyang;
import jyang.parser.*;
import jyang.*;

/**
 * 
 * @author Cornu - nataf
 * 
 *         This class generates a yangTreeNode from yang specifications and
 *         xpath It writes a file object for the netconf agent that has
 *         announced its yang capabilities
 * 
 */
public class YangSchemaTreeGenerator {

	private jyang parser = null;
	private int r;

	private static YangContext context = null;

	/**
	 * 
	 * @param args
	 *            : a list of parameters to build the tree node - IP address of
	 *            the NETCONF server - the number of prefixes the server will
	 *            recognize - one prefix - the urn or url of the prefix repeated
	 *            number or prefixes times - the number of yang modules - the
	 *            filename of each yang module - the name of each module, its
	 *            location in the DataStore and its urn
	 */
	public YangSchemaTreeGenerator(String[] args) {

		String ip = args[0];

		String[] yarg = new String[args.length - 1];

		for (int i = 1; i < args.length; i++)
			yarg[i - 1] = args[i];

		parser = new jyang(yarg);
		if (!parser.isParsingOk())
			System.exit(-1);

		Hashtable<String, YANG_Specification> specs = parser.getYangsSpecs();
		Map<String, YangInnerNode> treeMap = new HashMap<String, YangInnerNode>();
		for (Enumeration<YANG_Specification> especs = specs.elements(); especs
				.hasMoreElements();) {
			YANG_Specification spec = especs.nextElement();

				context = spec.buildSpecContext(new String[0],
						new Vector<String>());
				// TODO Auto-generated catch block


			treeMap.put(spec.getName(), buildModuleTree(spec));
		}
		RootNode schemaTree = buildGeneralTree(treeMap);

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
	public static RootNode buildGeneralTree(Map<String, YangInnerNode> treeMap) {

		RootNode rootNode = new RootNode("Yang Schema Tree");
		for (YangInnerNode subroot : treeMap.values())
			for (YangNode n : subroot.getDescendantNodes())
				rootNode.addChild(n);

		return rootNode;
	}

	public static YangInnerNode buildModuleTree(YANG_Specification spec) {
		YangTreeNode ytn = spec.getSchemaTree();
		// TODO : take care of sub-module
		YANG_Module m = (YANG_Module) spec;
		String prefix = m.getPrefix().getPrefix();
		String namespace = m.getNameSpace().getNameSpace();
		RootNode rootNode = new RootNode();
		Vector<YangTreeNode> childs = ytn.getChilds();
		for (YangTreeNode child : childs) {
			rootNode.addChild(buildModuleTree(child, prefix, namespace));
		}
		return rootNode;
	}

	public static YangNode buildModuleTree(YangTreeNode ytn, String... para) {

		YANG_Body body = ytn.getNode();
		

		if (body instanceof YANG_Container) {
			YANG_Container cont = (YANG_Container) body;
			ContainerNode node = new ContainerNode(cont);

//			
//			for (YANG_TypeDef typeDef : cont.getTypeDefs()) {
//				context.addNode(typeDef);
//			}

			
			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addChild(buildModuleTree(child));
			}
			if (para.length != 0)
				node.setNameSpace(new NameSpace(para[0], para[1]));
			return node;

		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			ListNode node = new ListNode(list, list.getKey().getKeyLeaves());
//
//			for (YANG_TypeDef typeDef : list.getTypeDefs()) {
//				context.addNode(typeDef);
//			}

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {
				node.addChild(buildModuleTree(child));
			}

			if (para.length != 0)
				node.setNameSpace(new NameSpace(para[0], para[1]));
			return node;

		} else if (body instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) body;
			ChoiceNode node = new ChoiceNode(choice);

			Vector<YangTreeNode> childs = ytn.getChilds();
			for (YangTreeNode child : childs) {

				node.addChild(buildModuleTree(child));
			}

			if (para.length != 0)
				node.setNameSpace(new NameSpace(para[0], para[1]));
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
				node.setMandatory(leaf.getMandatory().getMandatory().equals(
						YangBuiltInTypes.ytrue));
			}

			if (leaf.getDescription() != null) {
				node.setDescription(leaf.getDescription().getDescription());
			}

			LeafType typeDef = null;
			if (context.getTypeDef(leaf.getType()) != null) {
				YANG_TypeDef ytypeDef = context.getTypeDef(leaf.getType());

				while (!BuiltinType.isBuiltinType(ytypeDef.getType().getType())) {
					ytypeDef = context.getTypeDef(ytypeDef.getType());
				}

				typeDef = new LeafType(ytypeDef, leaf.getType().getType());
			} else {
				typeDef = new LeafType(leaf.getType());
			}

			node.setTypeDef(typeDef);

			if (para.length != 0)
				node.setNameSpace(new NameSpace(para[0], para[1]));
			return node;

		} else if (body instanceof YANG_LeafList) {
			YANG_LeafList leafList = (YANG_LeafList) body;
			LeafListNode node = new LeafListNode(leafList);

			LeafType typeDef = null;

			if (context.getTypeDef(leafList.getType()) != null) {
				YANG_TypeDef ytypeDef = context.getTypeDef(leafList.getType());

				while (!BuiltinType.isBuiltinType(ytypeDef.getType().getType())) {
					ytypeDef = context.getTypeDef(ytypeDef.getType());
				}

				typeDef = new LeafType(ytypeDef, leafList.getType().getType());
			} else {
				typeDef = new LeafType(leafList.getType());
			}

			node.setType(typeDef);

			if (para.length != 0)
				node.setNameSpace(new NameSpace(para[0], para[1]));
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
