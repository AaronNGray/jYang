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
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Vector;

import jyang.parser.*;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Yang2Dsdl {

	private String INDENT = "   ";

	private final static String RELAXNG_NS = "http://relaxng/org/ns/structure/1.0";
	private final static String NETMOD_NS = "urn:ietf:params:xml:ns:netmod:dsdl-annotations:1";

	private final static String LB = "<";
	private final static String RB = ">";
	private final static String NS = "ns";
	private final static String RNG = "rng";
	private final static String NMA = "nma";
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
	private final static String DEFINE = "define";
	private final static String REF = "ref";
	private final static String DATA = "data";
	private final static String PARAM = "param";
	private final static String CHOICE = "choice";

	private final static String MANDATORY = "mandatory";
	private final static String IMPLICIT = "implicit";

	private DocumentBuilder docb = null;
	private Document document = null;

	private static final String NMADEF = "default";

	public Yang2Dsdl(Hashtable<String, YANG_Specification> specs,
			PrintStream out) {

		try {
			docb = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// should not arrive
			e.printStackTrace();
		}

		document = docb.newDocument();
		Element root = document.createElementNS(RNG, GRAMMAR);
		document.appendChild(root);

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.print(LB + RNG + ":" + GRAMMAR + " ");
		out.println("xmlns:" + RNG + "=\"" + RELAXNG_NS + "\"");
		out.print(INDENT + "xmlns:" + NMA + "=\"" + NETMOD_NS + "\"");
		for (YANG_Specification spec : specs.values()) {
			out.print("\n" + INDENT + "xmlns:" + spec.getPrefix().getPrefix()
					+ "=\"" + spec.getNameSpace().getNameSpace() + "\"");
		}
		out.println(RB);

		Element start = document.createElementNS(RNG, START);
		root.appendChild(start);

		Element defines = document.createElementNS(RNG, "defines");
		gDefines(specs, defines);

		for (YANG_Specification spec : specs.values()) {
			gSpec(spec, start);
		}

		NodeList nl = defines.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			start.appendChild(nl.item(i).cloneNode(true));
		}

		DOMSource domSource = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e1) {

			e1.printStackTrace();
		}
		try {
			transformer.transform(domSource, result);
		} catch (TransformerException e1) {

			e1.printStackTrace();
		}
		String stringResult = writer.toString();

		System.out.println(stringResult);

	}

	private Hashtable<YANG_TypeDef, String> definestypedefs = new Hashtable<YANG_TypeDef, String>();
	private Hashtable<YANG_Grouping, String> definesgroupings = new Hashtable<YANG_Grouping, String>();

	private void gDefines(Hashtable<String, YANG_Specification> specs,
			Element elt) {
		for (YANG_Specification spec : specs.values()) {
			for (YANG_Body body : spec.getBodies()) {
				looksForTypesAndGroupings(spec, body, spec.getName());
			}
			for (YANG_TypeDef t : definestypedefs.keySet())
				gTypeDef(t, elt);
			for (YANG_Grouping g : definesgroupings.keySet())
				gGrouping(spec, g, elt);
		}
	}

	private void gUses(YANG_Specification spec, YANG_Uses uses, Element elt) {
		if (uses.getRefinements().size() == 0
				&& uses.getUsesAugments().size() == 0) {
			String usedgrouping = definesgroupings.get(uses.getGrouping());
			Element ref = document.createElementNS(RNG, REF);
			ref.setAttribute("name", usedgrouping);
			elt.appendChild(ref);
		} else {
			for (YANG_DataDef ddef : uses.getGrouping().getDataDefs())
				expandRefinement(spec, ddef, uses.getRefinements(), elt);

		}

	}

	private void expandRefinement(YANG_Specification spec, YANG_DataDef ddef,
			Vector<YANG_RefineAnyNode> refs, Element elt) {
		boolean generated = false;
		for (YANG_RefineAnyNode ref : refs) {
			String refinednodepath = ref.getRefineNodeId();
			YANG_DataDef refinednode = isInSubTree(spec, ddef, refinednodepath);
			if (refinednode != null) {
				gDataDef(spec, ddef, refinednode, ref, refinednodepath, elt);
			} else {
				if (!generated) {
					gDataDef(spec, ddef, elt);
					generated = true;
				}
			}
		}
	}

	private void gDataDef(YANG_Specification spec, YANG_DataDef ddef,
			YANG_DataDef refinednode, YANG_RefineAnyNode ref,
			String descendant, Element elt) {

		String[] path = descendant.split("/");

		if (ddef instanceof YANG_Uses) {
			YANG_Grouping g = ((YANG_Uses) ddef).getGrouping();
			YANG_DataDef result = null;
			for (YANG_DataDef gddef : g.getDataDefs()) {
				YANG_DataDef lr = isInSubTree(spec, gddef, descendant);
				if (lr != null)
					result = lr;
			}
		} else if (ddef instanceof YANG_Choice) {

		} else if (path.length == 1) {
			if (ddef.getBody().compareTo(path[0]) == 0)
				if (refinednode instanceof YANG_Leaf) {
					YANG_Leaf leaf = (YANG_Leaf) refinednode;
					YANG_Leaf cleaf = null;
					YANG_RefineLeaf rl = ref.getRefineLeaf();
					if (rl.getDefault() != null) {
						cleaf = leaf.clone();
						cleaf.setDefault(rl.getDefault());
					}
					gLeaf(spec, cleaf, elt, "");
				}

		} else {
			String descendantname = descendant.substring(descendant
					.indexOf("/") + 1);
			if (ddef.getBody().compareTo(path[0]) == 0) {
				if (ddef instanceof YANG_Container) {
					Element element = document.createElementNS(RNG, ELT);
					element.setAttribute("name", spec.getPrefix().getPrefix()
							+ ":" + ddef.getBody());
					elt.appendChild(element);
					for (YANG_DataDef dddef : ((YANG_Container) ddef)
							.getDataDefs()) {
						gDataDef(spec, dddef, refinednode, ref, descendantname,
								element);
					}
				} else if (ddef instanceof YANG_List) {
					for (YANG_DataDef dddef : ((YANG_List) ddef).getDataDefs()) {
						gDataDef(spec, dddef, refinednode, ref, descendantname,
								elt);
					}
				}
			}
		}
	}

	private YANG_DataDef isInSubTree(YANG_Specification spec,
			YANG_DataDef ddef, String descendant) {
		String[] path = descendant.split("/");
		if (ddef instanceof YANG_Uses) {
			YANG_Grouping g = ((YANG_Uses) ddef).getGrouping();
			YANG_DataDef result = null;
			for (YANG_DataDef gddef : g.getDataDefs()) {
				YANG_DataDef lr = isInSubTree(spec, gddef, descendant);
				if (lr != null)
					result = lr;
			}
			return result;
		} else if (ddef instanceof YANG_Choice) {
			return null;
		} else if (path.length == 1) {
			if (ddef.getBody().compareTo(path[0]) == 0)
				return ddef;
			else
				return null;
		} else {
			String descendantname = descendant.substring(descendant
					.indexOf("/") + 1);
			if (ddef.getBody().compareTo(path[0]) == 0) {
				Vector<YANG_DataDef> dddef = new Vector<YANG_DataDef>();
				if (ddef instanceof YANG_Container)
					dddef = ((YANG_Container) ddef).getDataDefs();
				else if (ddef instanceof YANG_List)
					dddef = ((YANG_List) ddef).getDataDefs();
				YANG_DataDef result = null;
				for (YANG_DataDef ld : dddef) {
					YANG_DataDef ldd = isInSubTree(spec, ld, descendantname);
					if (ldd != null)
						result = ldd;
				}
				return result;
			}
			return null;
		}

	}

	private void gGrouping(YANG_Specification spec, YANG_Grouping g, Element elt) {

		Element define = document.createElementNS(RNG, DEFINE);
		define.setAttribute("name", definesgroupings.get(g));
		elt.appendChild(define);
		if (g.getDataDefs().size() > 1) {
			Element interleave = document.createElementNS(RNG, INTLV);
			define.appendChild(interleave);
			define = interleave;
		}
		for (YANG_DataDef ddef : g.getDataDefs())
			gDataDef(spec, ddef, define);
	}

	private void gDataDef(YANG_Specification spec, YANG_DataDef ddef,
			Element elt) {

		if (ddef instanceof YANG_Leaf)
			gLeaf(spec, (YANG_Leaf) ddef, elt, "");
		else if (ddef instanceof YANG_Uses)
			gUses(spec, (YANG_Uses) ddef, elt);
		else if (ddef instanceof YANG_Container)
			gContainer(spec, (YANG_Container) ddef, elt);
		else if (ddef instanceof YANG_List)
			gList(spec, (YANG_List) ddef, elt);
		else if (ddef instanceof YANG_LeafList)
			gLeafList(spec, (YANG_LeafList) ddef, elt);
		else if (ddef instanceof YANG_Choice)
			gChoice(spec, (YANG_Choice) ddef, elt);

	}

	private void gChoice(YANG_Specification spec, YANG_Choice choice,
			Element elt) {

		Element choiceelt = document.createElementNS(RNG, CHOICE);
		boolean isOptional = true;

		if (choice.getMandatory() != null) {
			if (choice.getMandatory().getMandatory().compareTo(
					YangBuiltInTypes.ytrue) == 0) {
				choiceelt.setAttribute(MANDATORY, choice.getMandatory()
						.getMandatory());
				isOptional = false;
			}
		}

		if (isOptional) {
			Element optional = document.createElementNS(RNG, OPT);
			elt.appendChild(optional);
			elt = optional;
		}
		elt.appendChild(choiceelt);

		String defaultcase = "";
		if (choice.getDefault() != null)
			defaultcase = choice.getDefault().getDefault();

		for (YANG_Case ycase : choice.getCases()) {
			Element interleave = document.createElementNS(RNG, INTLV);
			if (ycase.getCase().compareTo(defaultcase) == 0)
				interleave.setAttribute(IMPLICIT, "true");
			choiceelt.appendChild(interleave);
			gCase(spec, ycase, interleave);
		}

		for (YANG_ShortCase yscase : choice.getShortCases()) {
			if (yscase.getBody().compareTo(defaultcase) == 0)
				gShortCase(spec, yscase, choiceelt, true);
			else
				gShortCase(spec, yscase, choiceelt, false);
		}

	}

	private void gShortCase(YANG_Specification spec, YANG_ShortCase ysc, Element elt, boolean b) {
		if (ysc instanceof YANG_Container)
			gContainer(spec,(YANG_Container)ysc,elt);
		else if (ysc instanceof YANG_List)
			gList(spec, (YANG_List) ysc, elt);
		else if (ysc instanceof YANG_LeafList)
			gLeafList(spec,(YANG_LeafList)ysc, elt);
		else if (ysc instanceof YANG_AnyXml)
			gAnyXml(spec, (YANG_AnyXml)ysc, elt);
		else if (ysc instanceof YANG_Choice)
			gChoice(spec, (YANG_Choice)ysc, elt);
		else if (ysc instanceof YANG_Leaf)
			gLeaf(spec, (YANG_Leaf)ysc, elt, "");
		
	}

	private void gAnyXml(YANG_Specification spec, YANG_AnyXml ysc, Element elt2) {
		// TODO Auto-generated method stub
		
	}

	private void gCase(YANG_Specification spec, YANG_Case ycase, Element elt) {
		for (YANG_DataDef ddef : ycase.getDataDefs())
			gDataDef(spec, ddef, elt);

	}

	private void gChoiceInRpc(YANG_Specification spec, YANG_Choice choice,
			Element elt) {

	}

	private void gLeafList(YANG_Specification spec, YANG_LeafList ddef,
			Element elt) {
		// TODO Auto-generated method stub

	}

	private void gKeyDataDef(YANG_Specification spec, YANG_DataDef ddef,
			Element elt) {

		if (ddef instanceof YANG_Leaf)
			gKeyLeaf(spec, (YANG_Leaf) ddef, elt, "");

	}

	private void gList(YANG_Specification spec, YANG_List list, Element elt) {

		Element zom = null;

		boolean zeroOrMore = false;
		boolean oneOrMore = false;
		if (list.getMinElement() == null)
			zeroOrMore = true;
		else if (list.getMinElement().getMinElementInt() == 0)
			zeroOrMore = true;
		else if (list.getMinElement().getMinElementInt() > 0)
			oneOrMore = true;
		if (zeroOrMore) {
			zom = document.createElementNS(RNG, ZoM);
			elt.appendChild(zom);
			elt = zom;
		} else if (oneOrMore) {
			zom = document.createElementNS(RNG, OoM);
			elt.appendChild(zom);
			elt = zom;
		}

		Element element = document.createElementNS(RNG, ELT);
		element.setAttribute("name", spec.getPrefix().getPrefix() + ":"
				+ list.getList());
		String[] kl = list.getKey().getKeyLeaves();
		for (int i = 0; i < kl.length; i++)
			element.setAttribute("key", spec.getPrefix().getPrefix() + ":"
					+ kl[i]);
		elt.appendChild(element);

		YANG_DataDef[] kdd = list.getKeyDataDefs();
		for (YANG_DataDef ddef : kdd) {
			gKeyDataDef(spec, ddef, element);
		}

		YANG_DataDef[] nkdd = list.getNonKeyDataDefs();
		if (nkdd.length > 1) {
			Element interleave = document.createElementNS(RNG, INTLV);
			element.appendChild(interleave);
			element = interleave;
		}

		for (YANG_DataDef ddef : nkdd) {
			gDataDef(spec, ddef, element);
		}

	}

	private void gContainer(YANG_Specification spec, YANG_Container cont,
			Element elt) {
		if (cont.getPresence() != null || !isOneMandatory(cont.getDataDefs())) {
			Element optional = document.createElementNS(RNG, OPT);
			elt.appendChild(optional);
			elt = optional;
		}
		Element element = document.createElementNS(RNG, ELT);
		element.setAttribute("name", spec.getPrefix().getPrefix() + ":"
				+ cont.getContainer());
		elt.appendChild(element);

		if (cont.getDataDefs().size() > 1) {
			Element interleave = document.createElementNS(RNG, INTLV);
			element.appendChild(interleave);
			element = interleave;
		}
		for (YANG_DataDef ddef : cont.getDataDefs())
			gDataDef(spec, ddef, element);

	}

	private boolean isOneMandatory(Vector<YANG_DataDef> dataDefs) {

		for (YANG_DataDef ddef : dataDefs) {
			if (ddef instanceof YANG_Leaf) {
				YANG_Leaf leaf = (YANG_Leaf) ddef;
				if (leaf.getMandatory() != null)
					if (leaf.getMandatory().getMandatory().compareTo(
							YangBuiltInTypes.ytrue) == 0)
						return true;
			} else if (ddef instanceof YANG_AnyXml) {
				YANG_AnyXml axml = (YANG_AnyXml) ddef;
				if (axml.getMandatory() != null)
					if (axml.getMandatory().getMandatory().compareTo(
							YangBuiltInTypes.ytrue) == 0)
						return true;
			} else if (ddef instanceof ListedDataDef) {
				ListedDataDef list = (ListedDataDef) ddef;
				if (list.getMinElement() != null)
					if (list.getMinElement().getMinElementInt() > 0)
						return true;
			} else if (ddef instanceof YANG_Choice) {
				YANG_Choice choice = (YANG_Choice) ddef;
				if (choice.getMandatory() != null)
					if (choice.getMandatory().getMandatory().compareTo(
							YangBuiltInTypes.ytrue) == 0)
						return true;
			}

		}
		return false;
	}

	private void gTypeDef(YANG_TypeDef td, Element elt) {

		Element define = document.createElementNS(RNG, DEFINE);
		define.setAttribute("name", definestypedefs.get(td));
		elt.appendChild(define);
		gType(td.getType(), define);
	}

	private void gType(YANG_Type type, Element elt) {

		if (!YangBuiltInTypes.isBuiltIn(type.getType())) {
			YANG_TypeDef ttd = type.getTypedef();
			Element ref = document.createElementNS(RNG, REF);
			ref.setAttribute("type", definestypedefs.get(ttd));
			elt.appendChild(ref);
			gRestrictions(type, ref);

		} else {
			if (type.getUnionSpec() != null) {

				Element choice = document.createElementNS(RNG, "choice");
				elt.appendChild(choice);
				YANG_UnionSpecification uspec = type.getUnionSpec();
				for (YANG_Type ut : uspec.getTypes()) {
					Element ref = document.createElementNS(RNG, REF);
					ref.setAttribute("name", definestypedefs.get(ut
							.getTypedef()));
					choice.appendChild(ref);
				}
			} else {
				gRestrictions(type, elt);
			}
		}

	}

	private void gRestrictions(YANG_Type type, Element elt) {

		String[][] lengths = new String[0][0];

		if (type.getStringRest() != null) {
			YANG_StringRestriction strest = type.getStringRest();

			if (strest.getLength() != null) {
				lengths = strest.getLength().getLengthIntervals();
			}
			if (lengths.length > 1) {
				Element choice = document.createElementNS(RNG, CHOICE);
				elt.appendChild(choice);
				for (String[] l : lengths) {
					Element data = document.createElementNS(RNG, DATA);
					data
							.setAttribute("type", yangType2DsdlType(type
									.getType()));
					choice.appendChild(data);
					gLength(l, data);
					for (YANG_Pattern pat : strest.getPatterns()) {
						gPattern(pat, data);
					}
				}
			} else if (lengths.length == 1) {
				Element data = document.createElementNS(RNG, DATA);
				data.setAttribute("type", yangType2DsdlType(type.getType()));
				elt.appendChild(data);
				gLength(lengths[0], data);
				for (YANG_Pattern pat : strest.getPatterns()) {
					gPattern(pat, data);
				}
			} else {
				Element data = document.createElementNS(RNG, DATA);
				data.setAttribute("type", yangType2DsdlType(type.getType()));
				elt.appendChild(data);
				for (YANG_Pattern pat : strest.getPatterns()) {
					gPattern(pat, data);
				}
			}
		} else {
			Element data = document.createElementNS(RNG, DATA);
			data.setAttribute("type", yangType2DsdlType(type.getType()));
			elt.appendChild(data);
		}
	}

	private void gLength(String[] l, Element elt) {

		if (l[0].compareTo("0") != 0
				&& l[0].compareTo(YangBuiltInTypes.uint64ub.toString()) != 0) {
			if (l[0].compareTo(l[1]) == 0) {
				Element param = document.createElementNS(RNG, PARAM);
				param.setAttribute("length", l[0]);
				elt.appendChild(param);
			} else {
				Element paramin = document.createElementNS(RNG, PARAM);
				paramin.setAttribute("minLength", l[0]);
				elt.appendChild(paramin);
				if (l[1].compareTo(YangBuiltInTypes.max) != 0) {
					Element paramax = document.createElementNS(RNG, PARAM);
					paramax.setAttribute("maxLength", l[1]);
					elt.appendChild(paramax);
				}
			}
		}

	}

	private String yangType2DsdlType(String type) {
		if (type.compareTo(YangBuiltInTypes.int8) == 0)
			return "byte";
		else if (type.compareTo(YangBuiltInTypes.int16) == 0)
			return "short";
		else if (type.compareTo(YangBuiltInTypes.int32) == 0)
			return "int";
		else if (type.compareTo(YangBuiltInTypes.int64) == 0)
			return "long";
		else if (type.compareTo(YangBuiltInTypes.uint8) == 0)
			return "unsignedfByte";
		else if (type.compareTo(YangBuiltInTypes.uint16) == 0)
			return "unsignedShort";
		else if (type.compareTo(YangBuiltInTypes.uint32) == 0)
			return "unsignedInt";
		else if (type.compareTo(YangBuiltInTypes.uint64) == 0)
			return "unsignedLong";
		else if (type.compareTo(YangBuiltInTypes.string) == 0)
			return "string";
		else if (type.compareTo(YangBuiltInTypes.binary) == 0)
			return "base64Binary";
		else
			return "unknown_type";
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

			if (t.getType().compareTo(YangBuiltInTypes.union) == 0) {
				YANG_UnionSpecification u = t.getUnionSpec();
				for (YANG_Type ut : u.getTypes()) {
					if (ut.getTypedef() != null) {
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
				if (t.getTypedef() != null) {
					YANG_TypeDef dtd = t.getTypedef();
					looksForTypesAndGroupings(spec, dtd, prefix);
					t = dtd.getType();
				}
			}

		} else if (body instanceof YANG_LeafList) {
			YANG_Type t = ((YANG_LeafList) body).getType();
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
				if (t.getTypedef() != null) {
					YANG_TypeDef dtd = t.getTypedef();
					looksForTypesAndGroupings(spec, dtd, prefix);
					t = dtd.getType();
				}
			}

		} else if (body instanceof YANG_Grouping) {
			prefix += SEP + body.getBody();
			YANG_Grouping y = (YANG_Grouping) body;
			definesgroupings.put(y, PRE + prefix);
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

	private void gType(YANG_Specification spec, YANG_Type type, Element elt,
			String prefix) {

		if (YangBuiltInTypes.isBuiltIn(type.getType())) {
			if (type.getUnionSpec() != null) {

				Element choice = document.createElementNS(RNG, CHOICE);
				elt.appendChild(choice);

				YANG_UnionSpecification uspec = type.getUnionSpec();
				for (YANG_Type ut : uspec.getTypes()) {
					Element ref = document.createElementNS(RNG, REF);
					ref.setAttribute("name", prefix + SEP + ut.getSuffix());
					choice.appendChild(ref);
				}
			} else {
				Element data = document.createElementNS(RNG, DATA);
				data.setAttribute("type", yangType2DsdlType(type.getType()));
				elt.appendChild(data);
				if (type.getStringRest() != null) {
					YANG_StringRestriction strest = type.getStringRest();
					for (YANG_Pattern pat : strest.getPatterns()) {
						gPattern(pat, data);
					}
				}
			}
		} else {
			String specnameprefix = prefix;

			if (type.isPrefixed()) {
				String tpref = type.getPrefix();
				for (YANG_Import i : spec.getImports()) {
					if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
						specnameprefix = i.getName();
					}
				}
			}
			Element ref = document.createElementNS(RNG, REF);
			ref.setAttribute("name", specnameprefix + SEP + type.getType());
			elt.appendChild(ref);
		}
	}

	private void gPattern(YANG_Pattern pat, Element elt) {
		Element param = document.createElementNS(RNG, PARAM);
		param.setAttribute("pattern", pat.getPattern());
		elt.appendChild(param);
	}

	private YANG_Type gLeaf(YANG_Specification spec, YANG_Leaf leaf,
			Element elt, String prefix) {

		YANG_Type type = leaf.getType();
		boolean isOptional = true;
		if (leaf.getMandatory() != null)
			if (leaf.getMandatory().getMandatory().compareTo("true") == 0)
				isOptional = false;

		Element element = document.createElementNS(RNG, ELT);
		element.setAttribute("name", spec.getPrefix().getPrefix() + ":"
				+ leaf.getLeaf());
		if (leaf.getDefault() != null)
			element.setAttribute(NMADEF, leaf.getDefault().getDefault());

		gType(spec, type, element, prefix);

		if (isOptional) {
			Element optional = document.createElementNS(RNG, OPT);
			elt.appendChild(optional);
			optional.appendChild(element);
		} else
			elt.appendChild(element);
		return type;
	}

	private YANG_Type gKeyLeaf(YANG_Specification spec, YANG_Leaf leaf,
			Element elt, String prefix) {

		YANG_Type type = leaf.getType();

		Element element = document.createElementNS(RNG, ELT);
		element.setAttribute("name", spec.getPrefix().getPrefix() + ":"
				+ leaf.getLeaf());
		if (leaf.getDefault() != null)
			element.setAttribute(NMADEF, leaf.getDefault().getDefault());

		gType(spec, type, element, prefix);

		elt.appendChild(element);
		return type;
	}

	private void gNotifications(YANG_Specification specs, PrintStream out,
			String iNDENT2) {

	}

	private void gRpcs(YANG_Specification specs, PrintStream out, String iNDENT2) {

	}

	private void gSpec(YANG_Specification spec, Element elt) {
		Element grammar = document.createElementNS(RNG, GRAMMAR);
		grammar.setAttribute("module", spec.getName());
		grammar.setAttribute(NS, spec.getNameSpace().getNameSpace());
		elt.appendChild(grammar);

		for (YANG_Body body : spec.getBodies())
			if (body instanceof YANG_DataDef)
				gDataDef(spec, (YANG_DataDef) body, grammar);

		int nbrpc = 0;
		for (YANG_Body b : spec.getBodies())
			if (b instanceof YANG_Rpc)
				nbrpc++;

	}
}