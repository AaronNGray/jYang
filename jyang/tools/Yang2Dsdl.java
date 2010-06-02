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
import jyang.parser.YANG_Unique;
import jyang.parser.YANG_Units;
import jyang.parser.YANG_Uses;
import jyang.parser.YANG_Value;
import jyang.parser.YANG_When;

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

	private void gDefines(Hashtable<String, YANG_Specification> specs,
			PrintStream out, String indent) {
		for (YANG_Specification spec : specs.values()) {
			for (YANG_Body body : spec.getBodies())
				gBodies(spec, body, out, spec.getName(), indent);

		}
		for (YANG_Specification spec : specs.values()) {
			for (YANG_Body body : spec.getBodies())
				gExternalBodies(spec, body, out, spec.getName(), indent);

		}

	}

	private void gExternalBodies(YANG_Specification spec, YANG_Body body,
			PrintStream out, String prefix , String indent) {

		String specname = spec.getName();
		if (body instanceof YANG_TypeDef) {
			YANG_TypeDef td = (YANG_TypeDef) body;
			YANG_Type type = td.getType();
			gPrefixedType(spec, type, out, indent);

		} else if (body instanceof YANG_Leaf) {
			YANG_Leaf leaf = (YANG_Leaf) body;
			YANG_Type type = leaf.getType();
			gPrefixedType(spec, type, out, indent);

		} else if (body instanceof YANG_Uses) {

			YANG_Uses uses = (YANG_Uses) body;
			out.print(indent + LB + DEFINE + " " + "name=\"");
			out.print(PRE + specname + SEP + uses.getGrouping());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);

		} else if (body instanceof YANG_Augment) {
		}
	}

	private void gPrefixedType(YANG_Specification spec, YANG_Type type,
			PrintStream out, String indent) {

		if (type.isPrefixed()) {
			String tpref = type.getPrefix();
			for (YANG_Import i : spec.getImports()) {
				if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
					String specnameprefix = i.getName();
					out.print(indent + LB + DEFINE + " " + "name=\"");
					out.print(specnameprefix + SEP + type.getSuffix());
					out.println("\"" + RB);

					YANG_Specification importedspec = i.getImportedmodule();

					out.println(indent + LB + "/" + DEFINE + RB);

				}
			}

		}

	}

	private void gBodies(YANG_Specification spec, YANG_Body body,
			PrintStream out, String prefix, String indent) {

		

		if (body instanceof YANG_TypeDef) {
			YANG_TypeDef td = (YANG_TypeDef) body;
			out.print(indent + LB + DEFINE + " " + "name=\"");
			out.print(prefix + SEP + td.getTypeDef());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);

		} else if (body instanceof YANG_Grouping) {
			YANG_Grouping grouping = (YANG_Grouping) body;
			out.print(indent + LB + DEFINE + " " + "name=\"");
			out.print(PRE + prefix + SEP + grouping.getGrouping());
			out.println("\"" + RB);
			out.println(indent + LB + "/" + DEFINE + RB);

			gTGD(spec, grouping.getTypeDefs(), grouping.getGroupings(),
					grouping.getDataDefs(), out, prefix + SEP
							+ grouping.getGrouping(), indent);

		} else if (body instanceof YANG_Container) {
			YANG_Container container = (YANG_Container) body;
			gTGD(spec, container.getTypeDefs(), container.getGroupings(),
					container.getDataDefs(), out, prefix + SEP
							+ container.getContainer(), indent);

		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			gTGD(spec, list.getTypeDefs(), list.getGroupings(), list
					.getDataDefs(), out, prefix + SEP + list.getList(),
					indent);

		}

	}

	private void gTGD(YANG_Specification spec, Vector<YANG_TypeDef> typeDefs,
			Vector<YANG_Grouping> groupings, Vector<YANG_DataDef> dataDefs,
			PrintStream out, String prefix, String indent) {

		for (YANG_Body lbody : groupings)
			gBodies(spec, lbody, out, prefix,  indent);
		for (YANG_Body ltd : typeDefs)
			gBodies(spec, ltd, out, prefix,indent);
		for (YANG_Body ldd : dataDefs)
			gBodies(spec, ldd, out, prefix,indent);
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