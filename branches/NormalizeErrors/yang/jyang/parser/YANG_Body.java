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
import java.util.*;

public abstract class YANG_Body extends DocumentedNode {

	public abstract void check(YangContext context) throws YangParserException;

	public abstract String getBody();

	public YANG_Body(int id) {
		super(id);
	}

	public YANG_Body(yang p, int id) {
		super(p, id);
	}

	public String toString() {
		String result = "";
		result = super.toString() + "\n";
		return result;
	}

	protected void checkBody(YangContext context) throws YangParserException {

		Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();
		Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
		Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();

		YangContext lcontext = new YangContext(context.getImports(), context
				.getSpec());

		if (this instanceof YANG_Grouping) {

			YANG_Grouping grouping = (YANG_Grouping) this;

			//grouping.setChecked(true);
			typedefs = grouping.getTypeDefs();
			groupings = grouping.getGroupings();
			datadefs = grouping.getDataDefs();

		} else if (this instanceof YANG_Container) {
			YANG_Container container = (YANG_Container) this;
			typedefs = container.getTypeDefs();
			groupings = container.getGroupings();
			datadefs = container.getDataDefs();
		} else if (this instanceof YANG_List) {
			YANG_List list = (YANG_List) this;
			typedefs = list.getTypeDefs();
			groupings = list.getGroupings();
			datadefs = list.getDataDefs();
		} else if (this instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) this;
			Vector<YANG_Case> cases = choice.getCases();
			for (Enumeration<YANG_Case> ec = cases.elements(); ec
					.hasMoreElements();) {
				YANG_Case ycase = ec.nextElement();
				Vector<YANG_CaseDataDef> vcases = ycase.getCaseDefs();
				for (Enumeration<YANG_CaseDataDef> ecd = vcases.elements(); ecd
						.hasMoreElements();) {
					YANG_CaseDataDef cdef = ecd.nextElement();
					YANG_DataDef ddef = (YANG_DataDef) cdef;
					datadefs.add(ddef);
				}
			}
			Vector<YANG_ShortCase> scases = choice.getShortCases();
			for (Enumeration<YANG_ShortCase> esc = scases.elements(); esc
					.hasMoreElements();) {
				YANG_ShortCase ysc = esc.nextElement();
				YANG_DataDef ddef = (YANG_DataDef) ysc;
				datadefs.add(ddef);
			}
		} else if (this instanceof YANG_Uses) {
			/**
			 * There is nothing to do for uses statement The enclosing statement
			 * of this uses has already expanded the used grouping and check if
			 * no overlapping exists
			 */

		} else if (this instanceof YANG_Augment) {
			YANG_Augment augment = (YANG_Augment) this;
			datadefs = augment.getDataDefs();
			for (Enumeration<YANG_Case> ec = augment.getCases().elements(); ec
					.hasMoreElements();) {
				YANG_Case ycase = ec.nextElement();
				Vector<YANG_CaseDataDef> vcases = ycase.getCaseDefs();
				for (Enumeration<YANG_CaseDataDef> ecd = vcases.elements(); ecd
						.hasMoreElements();) {
					YANG_CaseDataDef cdef = ecd.nextElement();
					YANG_DataDef ddef = (YANG_DataDef) cdef;
					datadefs.add(ddef);
				}
			}
		} else if (this instanceof YANG_Rpc) {
			YANG_Rpc rpc = (YANG_Rpc) this;
			typedefs = rpc.getTypeDefs();
			groupings = rpc.getGroupings();
			if (rpc.getInput() != null) {
				YANG_Input input = rpc.getInput();
				typedefs.addAll(input.getTypeDefs());
				groupings.addAll(input.getGroupings());
				datadefs.addAll(input.getDataDefs());
			}
			if (rpc.getOutput() != null) {
				YANG_Output output = rpc.getOutput();
				typedefs.addAll(output.getTypeDefs());
				groupings.addAll(output.getGroupings());
				datadefs.addAll(output.getDataDefs());
			}
		} else if (this instanceof YANG_Notification) {
			YANG_Notification notif = (YANG_Notification) this;
			typedefs = notif.getTypeDefs();
			groupings = notif.getGroupings();
			datadefs = notif.getDataDefs();
		}

		for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
				.hasMoreElements();) {
			YANG_TypeDef typedef = (YANG_TypeDef) et.nextElement();
			lcontext.addNode(typedef);
		}

		for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
				.hasMoreElements();) {
			YANG_Grouping g = (YANG_Grouping) eg.nextElement();
			lcontext.addNode(g);
		}

		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();)
			lcontext.addNode(ed.nextElement());

		context.addSubContext(lcontext);

		for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
				.hasMoreElements();) {
			YANG_Body body = (YANG_Body) et.nextElement();
			body.setParent(this);
			YangContext clcts = context.clone();
			try {
				body.checkBody(clcts);
			} catch (YangParserException e) {
				System.err
						.println(context.getSpec().getName() + e.getMessage());
			}
		}

		for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
				.hasMoreElements();) {
			YANG_Body body = (YANG_Body) eg.nextElement();
			body.setParent(this);
			YangContext clcts = context.clone();
			body.checkBody(clcts);
		}

		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();) {
			YANG_Body body = (YANG_Body) ed.nextElement();

			if (body instanceof YANG_Uses) {
				YangContext lusedcontext = new YangContext(context.getImports(), context
						.getSpec());

				body.setParent(this);
				YANG_Uses uses = (YANG_Uses) body;
				uses.check(context);
				YANG_Grouping g = uses.getGrouping();
				Vector<YANG_Grouping> usedgroupings = g.getGroupings();
				Vector<YANG_TypeDef> usedtypedefs = g.getTypeDefs();
				Vector<YANG_DataDef> useddatadefs = g.getDataDefs();
				try {

					for (Enumeration<YANG_TypeDef> et = usedtypedefs.elements(); et
							.hasMoreElements();) {
						YANG_TypeDef typedef = (YANG_TypeDef) et.nextElement();
						lusedcontext.addNode(typedef.clone());
					}
					for (Enumeration<YANG_Grouping> eg = usedgroupings
							.elements(); eg.hasMoreElements();) {
						YANG_Grouping ug = (YANG_Grouping) eg.nextElement();
						lusedcontext.addNode(ug.clone());
					}
					for (Enumeration<YANG_DataDef> ued = useddatadefs
							.elements(); ued.hasMoreElements();) {
						YANG_DataDef ddef = ued.nextElement();
						ddef.setParent(this);
						lusedcontext.addNode(ddef);
					}
				} catch (YangParserException e) {
					System.err
							.println(context.getSpec().getName()
									+ "@"
									+ body.getLine()
									+ "."
									+ body.getCol()
									+ " used grouping "
									+ g.getBody()
									+ " is already used  or one common node is in this context");

				}

				context.addSubContext(lusedcontext);

			} else {
				body.setParent(this);
				YangContext clcts = context.clone();
				try {
					body.checkBody(clcts);
				} catch (YangParserException e) {
					System.err.println(context.getSpec().getName()
							+ e.getMessage());
				}
			}
		}

		for (Enumeration<YANG_Unknown> eu = getUnknowns().elements(); eu
				.hasMoreElements();) {
			YANG_Body body = (YANG_Body) eu.nextElement();
			body.setParent(this);
			YangContext clcts = context.clone();
			/*
			 * try { body.checkBody(clcts); } catch (YangParserException e) {
			 * System.err .println(context.getSpec().getName() +
			 * e.getMessage()); }
			 */
		}

		try {
			check(context);
		} catch (YangParserException e) {
			System.err.println(context.getSpec().getName() + e.getMessage());
		}
	}

	public void builtTreeNode(YangTreeNode root) {
		Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

		if (this instanceof YANG_Container) {
			YANG_Container container = (YANG_Container) this;
			datadefs = container.getDataDefs();

		} else if (this instanceof YANG_List) {
			YANG_List list = (YANG_List) this;
			datadefs = list.getDataDefs();

		} else if (this instanceof YANG_Rpc) {
			YANG_Rpc rpc = (YANG_Rpc) this;
			if (rpc.getInput() != null)
				datadefs.addAll(rpc.getInput().getDataDefs());
			if (rpc.getOutput() != null)
				datadefs.addAll(rpc.getOutput().getDataDefs());

		} else if (this instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) this;
			for (Enumeration<YANG_Case> ec = choice.getCases().elements(); ec
					.hasMoreElements();) {
				YANG_Case ycase = ec.nextElement();
				CaseDataDef caseddef = new CaseDataDef(ycase);
				datadefs.add(caseddef);
			}
			for (Enumeration<YANG_ShortCase> ec = choice.getShortCases()
					.elements(); ec.hasMoreElements();) {
				datadefs.add((YANG_DataDef) ec.nextElement());
			}

		} else if (this instanceof CaseDataDef) {
			CaseDataDef caseddef = (CaseDataDef) this;

			for (Enumeration<YANG_CaseDataDef> ecddef = caseddef.getCaseDefs()
					.elements(); ecddef.hasMoreElements();) {
				YANG_CaseDataDef cddef = ecddef.nextElement();
				if (cddef instanceof YANG_Leaf)
					datadefs.add((YANG_Leaf) cddef);
				else if (cddef instanceof YANG_LeafList)
					datadefs.add((YANG_LeafList) cddef);
				else if (cddef instanceof YANG_List)
					datadefs.add((YANG_List) cddef);
				else if (cddef instanceof YANG_Container)
					datadefs.add((YANG_Container) cddef);
				else if (cddef instanceof YANG_Uses)
					datadefs.add((YANG_Uses) cddef);
				else if (cddef instanceof YANG_AnyXml)
					datadefs.add((YANG_AnyXml) cddef);
			}

		} else if (this instanceof YANG_Uses) {
			YANG_Uses uses = (YANG_Uses) this;
			YANG_Grouping g = uses.getGrouping();
			for (Enumeration<YANG_DataDef> eddef = g.getDataDefs().elements(); eddef
					.hasMoreElements();) {
				datadefs.add(eddef.nextElement());
			}
		} else if (this instanceof YANG_Augment) {
			YANG_Augment augment = (YANG_Augment) this;
			datadefs = augment.getDataDefs();
			for (Enumeration<YANG_Case> ec = augment.getCases().elements(); ec
					.hasMoreElements();) {
				YANG_Case ycase = ec.nextElement();
				Vector<YANG_CaseDataDef> vcases = ycase.getCaseDefs();
				for (Enumeration<YANG_CaseDataDef> ecd = vcases.elements(); ecd
						.hasMoreElements();) {
					YANG_CaseDataDef cdef = ecd.nextElement();
					YANG_DataDef ddef = (YANG_DataDef) cdef;
					datadefs.add(ddef);
				}
			}
		} else if (this instanceof YANG_Notification) {
			YANG_Notification notif = (YANG_Notification) this;
			datadefs = notif.getDataDefs();
		}

		if (!(this instanceof YANG_Uses)) {
			for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
					.hasMoreElements();) {
				YANG_Body body = (YANG_Body) ed.nextElement();
				if (!(body instanceof YANG_Uses)) {
					YangTreeNode n = new YangTreeNode();
					n.setNode(body);
					n.setParent(root);
					root.addChild(n);
					body.builtTreeNode(n);
				} else {
					body.builtTreeNode(root);
				}
			}
		} else {
			YANG_Uses uses = (YANG_Uses) this;
			for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
					.hasMoreElements();) {
				YANG_Body body = (YANG_Body) ed.nextElement();
				if (!(body instanceof YANG_Uses)) {
					YangTreeNode n = new YangTreeNode();
					n.setNode(body);
					n.setParent(root);
					root.addChild(n);
					body.builtTreeNode(n);
				} else {
					if (((YANG_Uses) body).isChecked())
						body.builtTreeNode(root);
				}
			}
		}
	}

}
