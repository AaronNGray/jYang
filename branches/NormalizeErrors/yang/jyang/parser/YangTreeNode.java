package jyang.parser;

/*
 * Copyright 2008 Emmanuel Nataf, Olivier Festor
 * 
 * This file is part of jyang.

 jyang is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 jyang is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with jyang.  If not, see <http://www.gnu.org/licenses/>.

 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class YangTreeNode implements java.io.Serializable {

	private YangTreeNode parent = null;
	private Vector<YangTreeNode> childs = new Vector<YangTreeNode>();
	private YANG_Body node = null;

	private YANG_Case casenode = null;

	public void setParent(YangTreeNode p) {
		parent = p;
	}

	public YangTreeNode getParent() {
		return parent;
	}

	public void addChild(YangTreeNode c) {
		childs.add(c);
	}

	public Vector<YangTreeNode> getChilds() {
		return childs;
	}

	public void setNode(YANG_Body n) {
		node = n;
	}

	public YANG_Case getCasenode() {
		return casenode;
	}

	public void setCasenode(YANG_Case casenode) {
		this.casenode = casenode;
	}

	public YANG_Body getNode() {
		return node;
	}

	public YANG_Body isInTree(YANG_Specification module, YangTreeNode root,
			Hashtable<String, YangTreeNode> importeds, String nid) {
		String[] nids = nid.split("/");
		YangTreeNode startnode = null;
		int starting = 0;
		boolean relativeXpath = false;
		if (nids[0].length() == 0)
			starting = 1;
		else
			relativeXpath = true;

		if (nids[starting].indexOf(':') != -1) {
			String prefix = nids[starting].substring(0, nids[starting]
					.indexOf(':'));
			if (prefix.compareTo(module.getPrefix().getPrefix()) == 0) {
				if (relativeXpath)
					startnode = this;
				else
					startnode = root;
			} else {
				boolean found = false;
				YANG_Import yimport = null;
				for (Enumeration<YANG_Import> ei = module.getImports()
						.elements(); ei.hasMoreElements() && !found;) {
					yimport = ei.nextElement();
					found = yimport.getPrefix().getPrefix().compareTo(prefix) == 0;
				}
				if (found) {
					startnode = importeds.get(yimport.getImportedModule());
				} else {
					System.err
							.println("Panic in isInTree of YangTreeNode, an import disapears !");
					System.exit(-1);
				}
			}
		} else {
			if (relativeXpath)
				startnode = this;
			else
				startnode = root;
		}

		boolean foundchild = false;
		boolean stop = false;
		for (int i = starting; i < nids.length && !stop; i++) {
			YangTreeNode child = null;
			boolean foundonechild = false;
			for (Enumeration<YangTreeNode> et = startnode.getChilds()
					.elements(); et.hasMoreElements() && !foundonechild;) {
				child = et.nextElement();
				if (child.getNode() instanceof YANG_Uses) {
					YANG_Uses uses = (YANG_Uses) child.getNode();
					boolean foundusedchild = false;
					for (YangTreeNode usedchild : child.getChilds()) {
						if (!foundusedchild) {
							if (nids[i].indexOf(':') != -1) {
								foundonechild = usedchild.getNode().getBody()
										.compareTo(
												nids[i].substring(nids[i]
														.indexOf(':') + 1)) == 0;
							} else {
								foundonechild = usedchild.getNode().getBody()
										.compareTo(nids[i]) == 0;
							}
							foundusedchild = foundonechild;
							if (foundusedchild)
								child = usedchild;
						}
					}
				} else if (nids[i].indexOf(':') != -1) {
					foundonechild = child.getNode().getBody().compareTo(
							nids[i].substring(nids[i].indexOf(':') + 1)) == 0;
				} else {
					foundonechild = child.getNode().getBody()
							.compareTo(nids[i]) == 0;
				}
			}
			if (foundonechild) {
				startnode = child;
				foundchild = true;
			} else {
				foundchild = false;
				stop = true;
			}
		}
		if (foundchild)
			return startnode.getNode();
		else
			return null;
	}

	public void check(YANG_Specification module, YangTreeNode root,
			YangTreeNode subroot, Hashtable<String, YangTreeNode> importeds) {

		if (node instanceof YANG_Uses) {
			YANG_Uses uses = (YANG_Uses) node;
			Hashtable<String, YANG_Refine> refnds = new Hashtable<String, YANG_Refine>();
			for (YANG_Refine ref : uses.getRefinements()) {
				String refnid = ref.getRefineNodeId();
				if (refnds.containsKey(refnid)) {
					YANG_Refine firstref = refnds.get(refnid);
					YangErrorManager.tadd(ref.getFileName(), ref.getLine(), ref
							.getCol(), "dup_child", refnid, firstref
							.getFileName(), firstref.getLine());
				} else
					refnds.put(ref.getRefineNodeId(), ref);
			}
			for (YANG_Refine ref : uses.getRefinements()) {
				String refnode = ref.getRefineNodeId();
				YANG_Body body = isInTree(module, root, importeds, refnode);
				if (body == null) {
					YangErrorManager.tadd(ref.getFileName(), ref.getLine(), ref
							.getCol(), "unknown", "node", refnode);
				} else {
					if (body instanceof YANG_Leaf) {
						YANG_Leaf refinedleaf = (YANG_Leaf) body;
						YANG_RefineLeaf refiningleaf = ((YANG_RefineAnyNode) ref)
								.getRefineLeaf();
						try {
							refiningleaf.check(refinedleaf);
						} catch (YangParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (body instanceof YANG_Container) {
						YANG_Container refinedcontainer = (YANG_Container) body;
						YANG_RefineContainer refiningcontainer = ((YANG_RefineAnyNode) ref)
								.getRefineContainer();
						refiningcontainer.check(refinedcontainer);
					} else if (body instanceof YANG_AnyXml) {
						YANG_AnyXml refinedanyxml = (YANG_AnyXml) body;
						YANG_RefineAnyXml refininganyxml = ((YANG_RefineAnyNode) ref)
								.getRefineAnyXml();
						try {
							refininganyxml.check(refinedanyxml);
						} catch (YangParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (body instanceof CaseDataDef) {
						CaseDataDef cddef = (CaseDataDef) body;
						YANG_Case refinedcase = cddef.getCase();
						YANG_RefineCase refiningcase = ((YANG_RefineAnyNode) ref)
								.getRefineCase();
						refiningcase.check(refinedcase);
					} else if (body instanceof YANG_Choice) {
						YANG_Choice refinedchoice = (YANG_Choice) body;
						YANG_RefineChoice refiningchoice = ((YANG_RefineAnyNode) ref)
								.getRefineChoice();
						try {
							refiningchoice.check(refinedchoice);
						} catch (YangParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (body instanceof YANG_List) {
						YANG_List refinedlist = (YANG_List) body;
						YANG_RefineList refininglist = ((YANG_RefineAnyNode) ref)
								.getRefineList();

						refininglist.check(refinedlist);
					} else if (body instanceof YANG_LeafList) {
						YANG_LeafList refinedleaflist = (YANG_LeafList) body;
						YANG_RefineLeafList refiningleaflist = ((YANG_RefineAnyNode) ref)
								.getRefineLeafList();
						try {
							refiningleaflist.check(refinedleaflist);
						} catch (YangParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
		if (node instanceof YANG_Augment) {
			YANG_Augment augment = (YANG_Augment) node;
			YANG_Body body = isInTree(module, root, importeds, augment
					.getAugment());
			if (body == null) {
				System.err.println(module.getName() + "@" + node.getLine()
						+ "." + node.getCol()
						+ ":augmented data node not found :"
						+ augment.getAugment());
			} else {
				try {
					augment.checkAugment(body);
				} catch (YangParserException ye) {
					System.err.println(module.getName() + ye.getMessage());
				}

			}
		} else if (node instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) node;
			YANG_Type type = leaf.getType();
			if (type.getLeafRef() != null) {
				YANG_LeafRefSpecification krs = type.getLeafRef();
				if (krs.getPath() != null) {
					YANG_Path path = krs.getPath();
					isInTree(module, root, importeds, path.getPath());
				}
			}
		} else if (node instanceof YANG_List) {
			YANG_List list = (YANG_List) node;
			for (Enumeration<YANG_Unique> eu = list.getUniques().elements(); eu
					.hasMoreElements();) {
				YANG_Unique unique = eu.nextElement();
				String[] uniques = unique.getUnique().split("\\s");
				for (int i = 0; i < uniques.length; i++)
					if (isInTree(module, root, importeds, uniques[i].trim()) == null) {
						YangErrorManager.tadd(node.getFileName(), unique
								.getLine(), unique.getCol(),
								"unique_not_found", uniques[i], node.getBody());
					}
			}
		}
		for (Enumeration<YangTreeNode> ey = childs.elements(); ey
				.hasMoreElements();)
			ey.nextElement().check(module, root, subroot, importeds);
	}

	public String toString() {
		String result = "";
		if (getParent() != null)
			result += node.getBody();
		if (childs.size() != 0)
			result += "\n   ";
		for (Enumeration<YangTreeNode> ey = childs.elements(); ey
				.hasMoreElements();)
			result += ", " + ey.nextElement().toString();
		if (childs.size() != 0)
			result += "\n   ";

		return result;
	}

}
