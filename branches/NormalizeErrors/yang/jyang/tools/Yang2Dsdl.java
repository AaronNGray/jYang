package jyang.tools;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

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
	private final static String RPCMETHODS = "rpc-methods";
	private final static String RPCMETHOD = "rpc-method";
	private final static String INPUT = "input";
	private final static String OUTPUT = "output";
	private final static String NOTIFS = "notifications";
	private final static String NOTIF = "notification";	
	private final static String RNG = "rng";
	private final static String DEFINE = "define";
		
	private String defines = "";

	public Yang2Dsdl(Hashtable<String, YANG_Specification> specs, PrintStream out) {


		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println(LB + NMT + ":" + NETMODTREE);
		out.print(INDENT + "xmlns:" + NMT + "=\"urn:ietf:params:xml:ns:netmod:conceptual-tree:1\"");
		for (YANG_Specification spec : specs.values()){
			out.print("\n" + INDENT + "xmlns:" + spec.getPrefix().getPrefix() + "=\"" + spec.getNameSpace().getNameSpace() + "\"");
		}
		out.println(RB);
		
		out.println(INDENT + LB + NMT + ":" + TOP + RB);
		gData(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + TOP + RB);
		
		out.println(INDENT + LB + NMT + ":" + RPCMETHODS + RB);
		gRpcs(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + RPCMETHODS +  RB);

		out.println(INDENT + LB + NMT + ":" + NOTIFS + RB);
		gNotifications(specs, out, INDENT);
		out.println(INDENT + LB + "/" + NMT + ":" + NOTIFS +  RB);
		
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