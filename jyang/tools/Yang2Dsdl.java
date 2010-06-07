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
package jyang.tools;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jyang.parser.SimpleYangNode;
import jyang.parser.YANG_AnyXml;
import jyang.parser.YANG_Argument;
import jyang.parser.YANG_Augment;
import jyang.parser.YANG_Base;
import jyang.parser.YANG_Bit;
import jyang.parser.YANG_Body;
import jyang.parser.YANG_Case;
import jyang.parser.YANG_Choice;
import jyang.parser.YANG_Config;
import jyang.parser.YANG_Contact;
import jyang.parser.YANG_Container;
import jyang.parser.YANG_DataDef;
import jyang.parser.YANG_Default;
import jyang.parser.YANG_Description;
import jyang.parser.YANG_DeviateAdd;
import jyang.parser.YANG_DeviateDelete;
import jyang.parser.YANG_DeviateNotSupported;
import jyang.parser.YANG_DeviateReplace;
import jyang.parser.YANG_Deviation;
import jyang.parser.YANG_Enum;
import jyang.parser.YANG_ErrorMessage;
import jyang.parser.YANG_Extension;
import jyang.parser.YANG_Feature;
import jyang.parser.YANG_Grouping;
import jyang.parser.YANG_Identity;
import jyang.parser.YANG_IfFeature;
import jyang.parser.YANG_Import;
import jyang.parser.YANG_Include;
import jyang.parser.YANG_Input;
import jyang.parser.YANG_Key;
import jyang.parser.YANG_Leaf;
import jyang.parser.YANG_LeafList;
import jyang.parser.YANG_Length;
import jyang.parser.YANG_Linkage;
import jyang.parser.YANG_List;
import jyang.parser.YANG_Mandatory;
import jyang.parser.YANG_MaxElement;
import jyang.parser.YANG_Meta;
import jyang.parser.YANG_MinElement;
import jyang.parser.YANG_Module;
import jyang.parser.YANG_Must;
import jyang.parser.YANG_Notification;
import jyang.parser.YANG_NumericalRestriction;
import jyang.parser.YANG_OrderedBy;
import jyang.parser.YANG_Organization;
import jyang.parser.YANG_Output;
import jyang.parser.YANG_Pattern;
import jyang.parser.YANG_Presence;
import jyang.parser.YANG_Range;
import jyang.parser.YANG_Reference;
import jyang.parser.YANG_Refine;
import jyang.parser.YANG_RefineAnyNode;
import jyang.parser.YANG_Revision;
import jyang.parser.YANG_Rpc;
import jyang.parser.YANG_ShortCase;
import jyang.parser.YANG_Specification;
import jyang.parser.YANG_Status;
import jyang.parser.YANG_StringRestriction;
import jyang.parser.YANG_Type;
import jyang.parser.YANG_TypeDef;
import jyang.parser.YANG_UnionSpecification;
import jyang.parser.YANG_Unique;
import jyang.parser.YANG_Units;
import jyang.parser.YANG_Uses;
import jyang.parser.YANG_Value;
import jyang.parser.YANG_When;
import jyang.parser.YangBuiltInTypes;
import jyang.parser.YangTreeNode;

public class Yang2Dsdl {

	private String INDENT = "   ";

	private final static String LB = "<";
	private final static String RB = ">";
	private final static String NMT = "nmt";
	private final static String DC = "dc";
	private final static String GRAMMAR = "grammar";
	private final static String START = "start";
	private final static String INTLV = "interleave";
	private final static String OPT = "optional";
	private final static String ELT = "element";
	private final static String ZoM = "ZeroOrMore";
	private final static String OoM = "OneOrMore";

	private final static String NETMODTREE = "netmod-tree";
	private final static String TOP = "top";
	private final static String SEP = "__";
	private final static String PRE = "_";
	private final static String RPCMETHODS = "rpc-methods";
	private final static String RPCMETHOD = "rpc-method";
	private final static String INPUT = "input";
	private final static String OUTPUT = "output";
	private final static String NOTIFS = "notifications";
	private final static String NOTIF = "notification";
	private final static String RNG = "rng";
	private final static String DEFINE = "define";
	private final static String REF = "ref";
	private final static String DATA = "data";
	private final static String PARAM = "param";
	private final static String CHOICE = "choice";

	private String defines = "";

	public Yang2Dsdl(Hashtable<String, YANG_Specification> specs,
			PrintStream out) {

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println(LB + NMT + ":" + GRAMMAR);
		out.print(INDENT + "xmlns:" + NMT
				+ "=\"urn:ietf:params:xml:ns:netmod:conceptual-tree:1\"");
		for (YANG_Specification spec : specs.values()) {
			out.print("\n" + INDENT + "xmlns:" + spec.getPrefix().getPrefix()
					+ "=\"" + spec.getNameSpace().getNameSpace() + "\"");
		}
		out.println(RB);

		out.println(INDENT + LB + NMT + ":" + TOP + RB);
		gData(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + TOP + RB);

		out.println(INDENT + LB + NMT + ":" + RPCMETHODS + RB);
		gRpcs(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + RPCMETHODS + RB);

		out.println(INDENT + LB + NMT + ":" + NOTIFS + RB);
		gNotifications(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + NOTIFS + RB);

		gDefines(specs, out, INDENT);

		out.println(LB + "/" + NMT + ":" + GRAMMAR + RB);

	}

	private Hashtable<YANG_TypeDef, String> definestypedefs = new Hashtable<YANG_TypeDef, String>();
	private Hashtable<YANG_Grouping, String> definesgroupings = new Hashtable<YANG_Grouping, String>();

	private void gDefines(Hashtable<String, YANG_Specification> specs,
			PrintStream out, String indent) {
		for (YANG_Specification spec : specs.values()) {
			for (YANG_Body body : spec.getBodies()) {
				looksForTypesAndGroupings(spec, body, spec.getName());
				/*
				 * Vector<SimpleYangNode> r = gBodies(spec, body, out, spec
				 * .getName(), indent); for (SimpleYangNode n : r) { if (n
				 * instanceof YANG_Type) { gType(spec, (YANG_Type) n, out,
				 * spec.getName(), indent); } }
				 */
			}
			for (YANG_TypeDef t : definestypedefs.keySet())
				gTypeDef(t, out);
			for (YANG_Grouping g : definesgroupings.keySet())
				gGrouping(spec, g, out);
		}
	}

	private void gGrouping(YANG_Specification spec, YANG_Grouping g, PrintStream out) {
		out.println(LB + DEFINE + " name=\"" + definesgroupings.get(g) + "\""
				+ RB);
		for (YANG_DataDef ddef : g.getDataDefs()){
			if (ddef instanceof YANG_Leaf)
				gLeaf(spec, (YANG_Leaf)ddef, out, "",INDENT);
		}
		out.println(LB + "/" + DEFINE + RB);
		
	}

	private void gTypeDef(YANG_TypeDef td, PrintStream out) {
		out.println(LB + DEFINE + " name=\"" + definestypedefs.get(td) + "\""
				+ RB);
		YANG_Type type = td.getType();
		if (!YangBuiltInTypes.isBuiltIn(type.getType())) {

			YANG_TypeDef ttd = type.getTypedef();
			out.println(INDENT + LB + REF + " type=\""
					+ definestypedefs.get(ttd) + "\"" + RB);
			if (type.getStringRest() != null) {
				YANG_StringRestriction strest = type.getStringRest();
				for (YANG_Pattern pat : strest.getPatterns()) {
					gPattern(pat, out, INDENT + INDENT);
				}
			}
		} else {

			if (type.getUnionSpec() != null) {

				out.println(INDENT + LB + CHOICE + RB);
				YANG_UnionSpecification uspec = type.getUnionSpec();
				String utype = "";
				for (YANG_Type ut : uspec.getTypes()) {
					out
							.println(INDENT + INDENT + LB + REF + " name=\""
									+ definestypedefs.get(ut.getTypedef())
									+ "\"/" + RB);
				}
				out.println(INDENT + LB + "/" + CHOICE + RB);
			} else {
				out.println(INDENT + LB + DATA + " type=\"" + type.getType()
						+ "\"" + RB);
				if (type.getStringRest() != null) {
					YANG_StringRestriction strest = type.getStringRest();
					for (YANG_Pattern pat : strest.getPatterns()) {
						gPattern(pat, out, INDENT + INDENT);
					}
				}
				out.println(INDENT + LB + "/" + DATA + RB);
			}
		}
		out.println(LB + "/" + DEFINE + RB);

	}

	private void looksForTypesAndGroupings(YANG_Specification spec,
			YANG_Body body, String prefix) {
		
		Vector<YANG_TypeDef> typedefs = null;
		Vector<YANG_Grouping> groupings = null;
		Vector<YANG_DataDef> datadefs = null;

		if (body instanceof YANG_TypeDef) {
			String baseprefix = prefix;
			YANG_TypeDef td = (YANG_TypeDef) body;
			prefix += SEP + body.getBody();

			YANG_Type t = td.getType();

			if (t.isPrefixed()) {
				String tpref = t.getPrefix();
				for (YANG_Import i : spec.getImports()) {
					if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
						prefix = i.getName();
						spec = i.getImportedmodule();
					}
				}

			}
			definestypedefs.put(td, prefix);
			while (t.getTypedef() != null) {
				YANG_TypeDef dtd = t.getTypedef();
				looksForTypesAndGroupings(spec, dtd, baseprefix);
				t = dtd.getType();
			}
			if (t.getType().compareTo(YangBuiltInTypes.union) == 0) {
				YANG_UnionSpecification u = t.getUnionSpec();
				for (YANG_Type ut : u.getTypes()) {
					while (ut.getTypedef() != null) {
						YANG_TypeDef dtd = ut.getTypedef();
						looksForTypesAndGroupings(spec, dtd, baseprefix);
						ut = dtd.getType();
					}
				}
			}
		} else if (body instanceof YANG_Leaf) {
			YANG_Type t = ((YANG_Leaf) body).getType();
			if (t.isPrefixed()) {
				String tpref = t.getPrefix();
				for (YANG_Import i : spec.getImports()) {
					if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
						prefix = i.getName();
						spec = i.getImportedmodule();
					}
				}

				YANG_TypeDef td = t.getTypedef();
				looksForTypesAndGroupings(spec, td, prefix);
				while (t.getTypedef() != null) {
					YANG_TypeDef dtd = t.getTypedef();
					looksForTypesAndGroupings(spec, dtd, prefix);
					t = dtd.getType();
				}
			}

		} else if (body instanceof YANG_Grouping) {
			prefix += SEP + body.getBody();
			YANG_Grouping y = (YANG_Grouping) body;
			definesgroupings.put(y, prefix);
			typedefs = y.getTypeDefs();
			groupings = y.getGroupings();
			datadefs = y.getDataDefs();
		} else if (body instanceof YANG_Container) {
			prefix += SEP + body.getBody();
			YANG_Container y = (YANG_Container) body;
			typedefs = y.getTypeDefs();
			groupings = y.getGroupings();
			datadefs = y.getDataDefs();
		} else if (body instanceof YANG_List) {
			prefix += SEP + body.getBody();
			YANG_List y = (YANG_List) body;
			typedefs = y.getTypeDefs();
			groupings = y.getGroupings();
			datadefs = y.getDataDefs();
		}
		if (typedefs != null)
			looksInside(spec, typedefs, groupings, datadefs, prefix);
	}

	private void looksInside(YANG_Specification spec,
			Vector<YANG_TypeDef> typeDefs, Vector<YANG_Grouping> groupings,
			Vector<YANG_DataDef> dataDefs, String prefix) {

		for (YANG_Body lbody : groupings)
			looksForTypesAndGroupings(spec, lbody, prefix);
		for (YANG_Body ltd : typeDefs)
			looksForTypesAndGroupings(spec, ltd, prefix);
		for (YANG_Body ldd : dataDefs)
			looksForTypesAndGroupings(spec, ldd, prefix);
	}

	private void gExternalBodies(YANG_Specification spec, YANG_Body body,
			PrintStream out, String prefix, String indent) {

		String specname = spec.getName();

		if (body instanceof YANG_TypeDef) {
			YANG_TypeDef td = (YANG_TypeDef) body;
			YANG_Type type = td.getType();
			// gType(spec, type, out, indent);

		} else if (body instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) body;
			YANG_Type type = leaf.getType();
			// gType(spec, type, out, indent);

		} else if (body instanceof YANG_Uses) {

			YANG_Uses uses = (YANG_Uses) body;
			out.print(indent + LB + REF + " " + "name=\"");
			out.print(PRE + specname + SEP + uses.getGrouping().getBody());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);

		} else if (body instanceof YANG_Augment) {
		}
	}

	private void gType(YANG_Specification spec, YANG_Type type,
			PrintStream out, String prefix, String indent) {

		if (YangBuiltInTypes.isBuiltIn(type.getType())) {
			if (type.getUnionSpec() != null) {

				out.println(indent + LB + CHOICE + RB);
				YANG_UnionSpecification uspec = type.getUnionSpec();
				String utype = "";
				for (YANG_Type ut : uspec.getTypes()) {
					out.println(indent + INDENT + LB + REF + " name=\""
							+ prefix + SEP + ut.getSuffix() + "\"/" + RB);
				}
				out.println(indent + LB + "/" + CHOICE + RB);
			} else {
				out.println(indent + LB + DATA + " type=\"" + type.getType()
						+ "\"" + RB);
				if (type.getStringRest() != null) {
					YANG_StringRestriction strest = type.getStringRest();
					for (YANG_Pattern pat : strest.getPatterns()) {
						gPattern(pat, out, indent + INDENT);
					}
				}
			}
		} else {
			YANG_Specification defspec = spec;
			String specnameprefix = prefix;

			if (type.isPrefixed()) {
				String tpref = type.getPrefix();
				for (YANG_Import i : spec.getImports()) {
					if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
						specnameprefix = i.getName();
						defspec = i.getImportedmodule();
					}
				}
			}
			out.print(indent + LB + REF + " " + "name=\"");
			out.print(specnameprefix + SEP + type.getType());
			out.println("\"" + "/" + RB);

		}
	}

	private void gPattern(YANG_Pattern pat, PrintStream out, String indent) {
		out.print(indent + LB + PARAM + " name=\"pattern\"" + RB);
		out.print(pat.getPattern());
		out.println(LB + "/" + PARAM + RB);
	}

	private Vector<YANG_TypeDef> OldgType(YANG_Specification spec,
			YANG_Type type, PrintStream out, String prefix, String indent) {
		Vector<YANG_TypeDef> result = new Vector<YANG_TypeDef>();

		YANG_Specification defspec = null;
		String specnameprefix = null;
		boolean external = false;

		if (type.isPrefixed()) {
			String tpref = type.getPrefix();
			for (YANG_Import i : spec.getImports()) {
				if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
					specnameprefix = i.getName();
					defspec = i.getImportedmodule();
					external = true;
				}
			}
		}
		if (!external) {
			defspec = spec;
			specnameprefix = spec.getName();
		}
		// out.print(indent + LB + REF + " " + "name=\"");
		// out.print(specnameprefix + SEP + type.getSuffix());
		// out.println("\"" + "/" + RB);

		if (!YangBuiltInTypes.isBuiltIn(type.getType())) {
			YANG_TypeDef tdrec = type.getTypedef();
			YANG_Type trec = tdrec.getType();
			out.print(indent + LB + REF + " " + "name=\"");
			out.print(defspec.getName() + SEP + type.getType());
			out.println("\"" + "/" + RB);
		}
		// out.println(indent + LB + "/" + DEFINE + RB);

		if (type.getUnionSpec() != null) {
			out.println(indent + INDENT + LB + CHOICE + RB);
			YANG_UnionSpecification uspec = type.getUnionSpec();
			for (YANG_Type ut : uspec.getTypes()) {
				gType(defspec, ut, out, prefix, indent + INDENT);
			}
			out.println(indent + INDENT + LB + "/" + CHOICE + RB);
		} else {
			out.print(indent + LB + DEFINE + " " + "name=\"");
			out.print(defspec.getName() + SEP + type.getType());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);
		}
		return result;
	}

	private Vector<SimpleYangNode> gBodies(YANG_Specification spec,
			YANG_Body body, PrintStream out, String prefix, String indent) {

		Vector<SimpleYangNode> result = new Vector<SimpleYangNode>();

		if (body instanceof YANG_TypeDef) {
			YANG_TypeDef td = (YANG_TypeDef) body;
			result.add(gTypeDef(spec, td, out, prefix, indent + INDENT));

		} else if (body instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) body;
			result.add(gLeaf(spec, leaf, out, prefix, indent));

		} else if (body instanceof YANG_Grouping) {
			YANG_Grouping grouping = (YANG_Grouping) body;
			out.print(indent + LB + DEFINE + " " + "name=\"");
			out.print(PRE + prefix + SEP + grouping.getGrouping());
			out.println("\"" + RB);

			result = gTGD(spec, grouping.getTypeDefs(),
					grouping.getGroupings(), grouping.getDataDefs(), out,
					prefix + SEP + grouping.getGrouping(), indent + INDENT);

			out.println(indent + LB + "/" + DEFINE + RB);

		} else if (body instanceof YANG_Container) {
			YANG_Container container = (YANG_Container) body;
			result = gTGD(spec, container.getTypeDefs(), container
					.getGroupings(), container.getDataDefs(), out, prefix + SEP
					+ container.getContainer(), indent);

		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			result = gTGD(spec, list.getTypeDefs(), list.getGroupings(), list
					.getDataDefs(), out, prefix + SEP + list.getList(), indent);

		} else if (body instanceof YANG_Uses) {

			YANG_Uses uses = (YANG_Uses) body;
			out.print(indent + LB + REF + " " + "name=\"");
			out.print(PRE + prefix + SEP + uses.getGrouping().getBody());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);

		}
		return result;
	}

	private YANG_Type gLeaf(YANG_Specification spec, YANG_Leaf leaf,
			PrintStream out, String prefix, String indent) {

		YANG_Type type = leaf.getType();
		out.println(indent + LB + ELT + " name=\""
				+ spec.getPrefix().getPrefix() + ":" + leaf.getLeaf() + "\""
				+ RB);
		gType(spec, type, out, prefix, indent + INDENT);
		out.println(indent + LB + "/" + ELT + RB);
		return type;

	}

	private YANG_Type gTypeDef(YANG_Specification spec, YANG_TypeDef td,
			PrintStream out, String prefix, String indent) {
		out.print(indent + LB + DEFINE + " " + "name=\"");
		out.print(prefix + SEP + td.getTypeDef());
		out.println("\"" + RB);
		YANG_Type type = td.getType();
		gType(spec, type, out, prefix, indent);
		out.println(indent + LB + "/" + DEFINE + RB);
		if (YangBuiltInTypes.isBuiltIn(type.getType()))
			return null;
		else
			return type;

	}

	private Vector<SimpleYangNode> gTGD(YANG_Specification spec,
			Vector<YANG_TypeDef> typeDefs, Vector<YANG_Grouping> groupings,
			Vector<YANG_DataDef> dataDefs, PrintStream out, String prefix,
			String indent) {

		Vector<SimpleYangNode> result = new Vector<SimpleYangNode>();

		for (YANG_Body lbody : groupings)
			result.addAll(gBodies(spec, lbody, out, prefix, indent));
		for (YANG_Body ltd : typeDefs)
			result.addAll(gBodies(spec, ltd, out, prefix, indent));
		for (YANG_Body ldd : dataDefs)
			result.addAll(gBodies(spec, ldd, out, prefix, indent));
		return result;
	}

	private void gNotifications(Hashtable<String, YANG_Specification> specs,
			PrintStream out, String iNDENT2) {
		// TODO Auto-generated method stub

	}

	private void gRpcs(Hashtable<String, YANG_Specification> specs,
			PrintStream out, String iNDENT2) {
		// TODO Auto-generated method stub

	}

	private void gData(Hashtable<String, YANG_Specification> specs,
			PrintStream out, String iNDENT2) {
		// TODO Auto-generated method stub

	}
}