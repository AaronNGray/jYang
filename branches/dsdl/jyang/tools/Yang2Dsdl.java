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
import java.sql.Date;
import java.text.DateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Calendar;
import java.util.Enumeration;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Yang2Dsdl {

	private final static String XMLNS = "xmlns";
	private final static String RELAXNG_NS = "http://relaxng/org/ns/structure/1.0";

	private final static String LB = "<";
	private final static String RB = ">";
	private final static String NS = "ns";
	private final static String RNG = "rng";
	private final static String NMA = "nma";
	private final static String NMA_URI = "urn:ietf:params:xml:ns:netmod:dsdl-annotations:1";
	private final static String DC = "dc";
	private final static String DC_URI = "http://purl.org/dc/terms";
	private final static String A = "a";
	private final static String A_URI = "http://relaxng.org/ns/compatibility/annotations/1.0";
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

	private static final String DEFAULT = "default";

	private static final String ANYXML = "__anyxml__";

	private static final String ATTRIBUTE = "attribute";

	private static final String ANYNAME = "anyName";

	private static final String TEXT = "text";

	private static final String CONFIG = "config";

	private static final String MUST = "must";

	private static final String ASSERT = "assert";

	private static final String ERRORMSG = "error-message";

	private static final String ERRORAPPTAG = "error-app-tag";

	private static final String DOC = "documentation";

	private static final String DTLIB = "datatypeLibrary";

	private static final String DTLIB_URI = "http://www.w3.org/2001/XMLSchema=datatypes";

	private static final String CREATOR = "creator";

	private static final String JYANG = "jYang 1.0, DSDL plugin";

	private static final String DATE = "date";
	private static final String SOURCE = "source";
	private static final String YANGMODULE = "YANG module";
	private static final String RPCS = "rpcs";
	private static final String NOTIFICATIONS = "notifications";
	private static final String EMPTY = "empty";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String BASE64BINARY = "base64Binary";
	private static final String TYPE = "type";
	private static final String LIST = "list";
	private static final String VALUE = "value";
	private static final String NAME = "name";
	private static final String STRING = "string";
	private static final String INSTID = "instance-identifier";
	private static final String REQINST = "require-instance";
	private static final String LEAFREF = "leafref";
	private static final String TOTALDIGIT = "totalDigits";
	private static final String _19 = "19";
	private static final String FRACTDIGIT = "fractionDigits";
	private static final String STATUS = "status";

	private Hashtable<YANG_TypeDef, String> definestypedefs = new Hashtable<YANG_TypeDef, String>();
	private Hashtable<YANG_Grouping, String> definesgroupings = new Hashtable<YANG_Grouping, String>();
	private boolean defining = false;
	private String prefix = "";

	private boolean isThereOneAnyXml = false;

	public Yang2Dsdl(Hashtable<String, YANG_Specification> specs,
			PrintStream out) {

		try {
			docb = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// should not arrive
			e.printStackTrace();
		}

		document = docb.newDocument();
		Element grammar = document.createElementNS(RELAXNG_NS, GRAMMAR);
		document.appendChild(grammar);

		grammar.setAttribute(XMLNS + ":" + NMA, NMA_URI);
		grammar.setAttribute(XMLNS + ":" + DC, DC_URI);
		grammar.setAttribute(XMLNS + ":" + A, A_URI);
		grammar.setAttribute(DTLIB, DTLIB_URI);

		for (YANG_Specification spec : specs.values()) {
			grammar.setAttribute(XMLNS + ":" + spec.getPrefix().getPrefix(),
					spec.getNameSpace().getNameSpace());

		}
		Element creator = document.createElementNS(DC_URI, CREATOR);
		creator.setTextContent(JYANG);
		grammar.appendChild(creator);

		Element date = document.createElementNS(DC_URI, DATE);
		date.setTextContent(Calendar.getInstance().get(Calendar.YEAR) + "-"
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-"
				+ +Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		grammar.appendChild(date);

		Element start = document.createElementNS(RELAXNG_NS, START);
		grammar.appendChild(start);

		Element defines = document.createElementNS(RELAXNG_NS, "defines");

		gDefines(specs, defines);

		for (YANG_Specification spec : specs.values()) {
			gSpec(spec, start);
		}

		NodeList nl = defines.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			grammar.appendChild(nl.item(i).cloneNode(true));
		}

		if (isThereOneAnyXml)
			grammar.appendChild(gDefineAnyXml());

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

		out.println(stringResult);

	}

	private Node gDefineAnyXml() {
		Element define = document.createElementNS(RELAXNG_NS, DEFINE);
		define.setAttribute("name", ANYXML);
		Element zOm = document.createElementNS(RELAXNG_NS, ZoM);
		define.appendChild(zOm);
		Element choice = document.createElementNS(RELAXNG_NS, CHOICE);
		zOm.appendChild(choice);
		Element attribute = document.createElementNS(RELAXNG_NS, ATTRIBUTE);
		choice.appendChild(attribute);
		Element anyname = document.createElementNS(RELAXNG_NS, ANYNAME);
		attribute.appendChild(anyname);
		Element element = document.createElementNS(RELAXNG_NS, ELT);
		Element anyname2 = document.createElementNS(RELAXNG_NS, ANYNAME);
		element.appendChild(anyname2);
		Element ref = document.createElementNS(RELAXNG_NS, REF);
		ref.setAttribute(NMA + ":" + "name", ANYXML);
		element.appendChild(ref);
		choice.appendChild(element);
		Element text = document.createElementNS(RELAXNG_NS, TEXT);
		choice.appendChild(text);

		return define;
	}

	private void gDefines(Hashtable<String, YANG_Specification> specs,
			Element parent) {
		defining = true;
		for (YANG_Specification spec : specs.values()) {

			for (YANG_Body body : spec.getBodies()) {
				looksForTypesAndGroupings(spec, body, spec.getName());
			}
			for (YANG_TypeDef t : definestypedefs.keySet())
				gTypeDef(t, parent);
			for (YANG_Grouping g : definesgroupings.keySet())
				gGrouping(spec, g, parent);
		}
		defining = false;
	}

	private void gUses(YANG_Specification spec, YANG_Uses uses, Element parent) {
		if (uses.getRefinements().size() == 0
				&& uses.getUsesAugments().size() == 0) {
			String usedgrouping = definesgroupings.get(uses.getGrouping());
			Element ref = document.createElementNS(RELAXNG_NS, REF);
			ref.setAttribute("name", usedgrouping);
			parent.appendChild(ref);
		} else {
			for (YANG_DataDef ddef : uses.getGrouping().getDataDefs())
				expandRefinement(spec, ddef, uses.getRefinements(), parent);

		}

	}

	private void expandRefinement(YANG_Specification spec, YANG_DataDef ddef,
			Vector<YANG_RefineAnyNode> refs, Element parent) {
		boolean generated = false;
		for (YANG_RefineAnyNode ref : refs) {
			String refinednodepath = ref.getRefineNodeId();
			YANG_DataDef refinednode = isInSubTree(spec, ddef, refinednodepath);
			if (refinednode != null) {
				gDataDef(spec, ddef, refinednode, ref, refinednodepath, parent);
			} else {
				if (!generated) {
					gDataDef(spec, ddef, parent);
					generated = true;
				}
			}
		}
	}

	private void gDataDef(YANG_Specification spec, YANG_DataDef ddef,
			YANG_DataDef refinednode, YANG_RefineAnyNode ref,
			String descendant, Element parent) {

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
					gLeaf(spec, cleaf, parent);
				}

		} else {
			String descendantname = descendant.substring(descendant
					.indexOf("/") + 1);
			if (ddef.getBody().compareTo(path[0]) == 0) {
				if (ddef instanceof YANG_Container) {
					Element element = document.createElementNS(RELAXNG_NS, ELT);
					element.setAttribute("name", spec.getPrefix().getPrefix()
							+ ":" + ddef.getBody());
					parent.appendChild(element);
					for (YANG_DataDef dddef : ((YANG_Container) ddef)
							.getDataDefs()) {
						gDataDef(spec, dddef, refinednode, ref, descendantname,
								element);
					}
				} else if (ddef instanceof YANG_List) {
					for (YANG_DataDef dddef : ((YANG_List) ddef).getDataDefs()) {
						gDataDef(spec, dddef, refinednode, ref, descendantname,
								parent);
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

	private void gGrouping(YANG_Specification spec, YANG_Grouping g,
			Element parent) {

		Element define = document.createElementNS(RELAXNG_NS, DEFINE);
		define.setAttribute("name", definesgroupings.get(g));
		parent.appendChild(define);
		if (g.getDataDefs().size() > 1) {
			Element interleave = document.createElementNS(RELAXNG_NS, INTLV);
			define.appendChild(interleave);
			define = interleave;
		}
		for (YANG_DataDef ddef : g.getDataDefs())
			gDataDef(spec, ddef, define);
	}

	private void gDataDef(YANG_Specification spec, YANG_DataDef ddef,
			Element parent) {

		if (ddef instanceof YANG_AnyXml)
			gAnyXml(spec, (YANG_AnyXml) ddef, parent);
		else if (ddef instanceof YANG_Leaf)
			gLeaf(spec, (YANG_Leaf) ddef, parent);
		else if (ddef instanceof YANG_Uses)
			gUses(spec, (YANG_Uses) ddef, parent);
		else if (ddef instanceof YANG_Container)
			gContainer(spec, (YANG_Container) ddef, parent);
		else if (ddef instanceof YANG_List)
			gList(spec, (YANG_List) ddef, parent);
		else if (ddef instanceof YANG_LeafList)
			gLeafList(spec, (YANG_LeafList) ddef, parent);
		else if (ddef instanceof YANG_Choice)
			gChoice(spec, (YANG_Choice) ddef, parent);

	}

	private void gConfigDataDef(ConfigDataDef cddef, Element parent) {
		if (cddef.getConfig() != null)
			parent.setAttributeNS(NMA_URI, CONFIG, String.valueOf(cddef
					.isConfig()));
		if (cddef instanceof MustDataDef)
			gMustDataDef((MustDataDef) cddef, parent);
	}

	private void gMustDataDef(MustDataDef mddef, Element parent) {
		if (mddef.getMusts().size() > 0)
			for (YANG_Must must : mddef.getMusts()) {
				Element mustelt = document.createElement(NMA + ":" + MUST);
				mustelt.setAttribute(ASSERT, gXPath(must.getMust()));
				parent.appendChild(mustelt);
				if (must.getErrAppTag() != null) {
					Element errapptag = document.createElement(NMA + ":"
							+ ERRORAPPTAG);
					errapptag
							.setTextContent(must.getErrAppTag().getErrorAppt());
					mustelt.appendChild(errapptag);
				}
				if (must.getErrMess() != null) {
					Element errmsg = document.createElement(NMA + ":"
							+ ERRORMSG);
					errmsg.setTextContent(must.getErrMess().getErrorMessage());
					mustelt.appendChild(errmsg);
				}
				parent.appendChild(mustelt);
			}
	}

	private String gXPath(String xp) {

		xp = xp.replaceAll("<", "&lt;");
		xp = xp.replaceAll(">", "&gt;");

		if (!defining)
			return xp;
		String[] s = xp.split("/");
		String result = "";
		for (int i = 0; i < s.length - 1; i++)
			result += "$pref:" + s[i] + "/";
		result += "$pref:" + s[s.length - 1];
		return result;
	}

	private void gChoice(YANG_Specification spec, YANG_Choice choice,
			Element parent) {

		Element choiceelt = document.createElementNS(RELAXNG_NS, CHOICE);
		choiceelt.setAttribute(MANDATORY, String.valueOf(choice.isMandatory()));

		gDescription(choice, choiceelt);

		gConfigDataDef(choice, parent);

		if (choice.isMandatory()) {
			Element optional = document.createElementNS(RELAXNG_NS, OPT);
			parent.appendChild(optional);
			parent = optional;
		}
		parent.appendChild(choiceelt);

		String defaultcase = "";
		if (choice.getDefault() != null)
			defaultcase = choice.getDefault().getDefault();

		for (YANG_Case ycase : choice.getCases()) {
			Element interleave = document.createElementNS(RELAXNG_NS, INTLV);
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

	private void gShortCase(YANG_Specification spec, YANG_ShortCase ysc,
			Element parent, boolean b) {
		if (ysc instanceof YANG_Container)
			gContainer(spec, (YANG_Container) ysc, parent);
		else if (ysc instanceof YANG_List)
			gList(spec, (YANG_List) ysc, parent);
		else if (ysc instanceof YANG_LeafList)
			gLeafList(spec, (YANG_LeafList) ysc, parent);
		else if (ysc instanceof YANG_AnyXml)
			gAnyXml(spec, (YANG_AnyXml) ysc, parent);
		else if (ysc instanceof YANG_Choice)
			gChoice(spec, (YANG_Choice) ysc, parent);
		else if (ysc instanceof YANG_Leaf)
			gLeaf(spec, (YANG_Leaf) ysc, parent);

	}

	private void gAnyXml(YANG_Specification spec, YANG_AnyXml axml,
			Element parent) {

		isThereOneAnyXml = true;

		if (!axml.isMandatory()) {
			Element optional = document.createElementNS(RELAXNG_NS, OPT);
			parent.appendChild(optional);
			parent = optional;
		}

		Element element = document.createElementNS(RELAXNG_NS, ELT);
		String aprefix = "";
		if (!defining)
			aprefix = prefix + ":";
		element.setAttribute(NMA + ":" + "name", aprefix + axml.getAnyXml());
		parent.appendChild(element);

		gDescription(axml, element);

		gConfigDataDef(axml, element);

		Element ref = document.createElementNS(RELAXNG_NS, REF);
		ref.setAttribute(NMA + ":" + "name", ANYXML);
		element.appendChild(ref);

	}

	private void gDescription(DocumentedNode desc, Element parent) {
		if (desc.getDescription() != null) {
			Element documentation = document.createElement(A + ":" + DOC);
			documentation
					.setTextContent(desc.getDescription().getDescription());
			parent.appendChild(documentation);
		}

	}

	private void gCase(YANG_Specification spec, YANG_Case ycase, Element parent) {
		for (YANG_DataDef ddef : ycase.getDataDefs())
			gDataDef(spec, ddef, parent);

	}

	private void gChoiceInRpc(YANG_Specification spec, YANG_Choice choice,
			Element parent) {

	}

	private void gKeyDataDef(YANG_Specification spec, YANG_DataDef ddef,
			Element parent) {

		if (ddef instanceof YANG_Leaf)
			gKeyLeaf(spec, (YANG_Leaf) ddef, parent);

	}

	private Element gListedParent(ListedDataDef l, Element parent) {
		Element zom = null;

		boolean zeroOrMore = false;
		boolean oneOrMore = false;
		if (l.getMinElement() == null)
			zeroOrMore = true;
		else if (l.getMinElement().getMinElementInt() == 0)
			zeroOrMore = true;
		else if (l.getMinElement().getMinElementInt() > 0)
			oneOrMore = true;
		if (zeroOrMore) {
			zom = document.createElementNS(RELAXNG_NS, ZoM);
			parent.appendChild(zom);
			parent = zom;
		} else if (oneOrMore) {
			zom = document.createElementNS(RELAXNG_NS, OoM);
			parent.appendChild(zom);
			parent = zom;
		}
		return parent;

	}

	private void gList(YANG_Specification spec, YANG_List list, Element parent) {

		parent = gListedParent(list, parent);

		String kprefix = "";
		if (!defining)
			kprefix = prefix + ":";

		Element element = document.createElementNS(RELAXNG_NS, ELT);
		element.setAttribute("name", kprefix + list.getList());
		String[] kl = list.getKey().getKeyLeaves();

		for (int i = 0; i < kl.length; i++)
			element.setAttribute(NMA + ":" + "key", kprefix + kl[i]);

		gListedAttribute(list, element);

		parent.appendChild(element);

		YANG_DataDef[] kdd = list.getKeyDataDefs();
		for (YANG_DataDef ddef : kdd) {
			gKeyDataDef(spec, ddef, element);
		}

		YANG_DataDef[] nkdd = list.getNonKeyDataDefs();
		if (nkdd.length > 1) {
			Element interleave = document.createElementNS(RELAXNG_NS, INTLV);
			element.appendChild(interleave);
			element = interleave;
		}

		for (YANG_DataDef ddef : nkdd) {
			gDataDef(spec, ddef, element);
		}

	}

	private void gLeafList(YANG_Specification spec, YANG_LeafList leaflist,
			Element parent) {
		parent = gListedParent(leaflist, parent);

		Element element = document.createElementNS(RELAXNG_NS, ELT);
		parent.appendChild(element);

		String llprefix = "";
		if (!defining)
			llprefix = prefix + ":";

		element.setAttribute("name", llprefix + leaflist.getLeafList());
		element.setAttributeNS(NMA, "leaf-list", "true");

		gListedAttribute(leaflist, element);

		gType(leaflist.getType(), element);

	}

	private void gListedAttribute(ListedDataDef l, Element parent) {

		gDescription(l, parent);
		gConfigDataDef(l, parent);

		if (l.getMinElement() != null) {
			if (l.getMinElement().getMinElementInt() > 1)
				parent.setAttribute(NMA + ":" + "min-elements", l
						.getMinElement().getMinElement());
		}
		if (l.getMaxElement() != null) {
			if (!(l.getMaxElement().getMaxElement().compareTo(
					YangBuiltInTypes.UNBONDED) == 0))
				parent.setAttribute(NMA + ":" + "max-elements", l
						.getMaxElement().getMaxElement());
		}

	}

	private void gContainer(YANG_Specification spec, YANG_Container cont,
			Element parent) {

		if (cont.getPresence() != null || !isOneMandatory(cont.getDataDefs())) {
			Element optional = document.createElementNS(RELAXNG_NS, OPT);
			parent.appendChild(optional);
			parent = optional;
		}
		Element element = document.createElementNS(RELAXNG_NS, ELT);

		gDescription(cont, element);

		String cprefix = "";
		if (!defining)
			cprefix = prefix + ":";

		element.setAttribute("name", cprefix + cont.getContainer());

		if (cont.getPresence() == null && !isOneMandatory(cont.getDataDefs())
				&& isOneImplicit(cont.getDataDefs())) {
			element.setAttributeNS(NMA_URI, IMPLICIT, TRUE);
		}
		parent.appendChild(element);

		gConfigDataDef(cont, element);

		if (cont.getDataDefs().size() > 1) {
			Element interleave = document.createElementNS(RELAXNG_NS, INTLV);
			element.appendChild(interleave);
			element = interleave;
		}
		for (YANG_DataDef ddef : cont.getDataDefs())
			gDataDef(spec, ddef, element);

	}

	private boolean isOneImplicit(Vector<YANG_DataDef> dataDefs) {

		for (YANG_DataDef ddef : dataDefs) {
			if (ddef instanceof YANG_Leaf) {
				YANG_Leaf leaf = (YANG_Leaf) ddef;
				if (leaf.getDefault() != null)
					return true;
				if (leaf.getType().getTypedef() != null) {
					return leaf.getType().getTypedef().getDefault() != null;
				}
			} else if (ddef instanceof YANG_Container) {
				YANG_Container cont = (YANG_Container) ddef;
				return cont.getPresence() == null
						&& !isOneMandatory(cont.getDataDefs())
						&& isOneImplicit(cont.getDataDefs());
			}
		}
		return false;
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
				return choice.isMandatory();

			} else if (ddef instanceof YANG_Container) {
				YANG_Container cont = (YANG_Container) ddef;
				return cont.getPresence() == null
						&& isOneMandatory(cont.getDataDefs());
			}

		}
		return false;
	}

	private void gTypeDef(YANG_TypeDef td, Element parent) {

		Element define = document.createElementNS(RELAXNG_NS, DEFINE);
		define.setAttribute(NAME, definestypedefs.get(td));
		if (td.getDefault() != null)
			define.setAttributeNS(NMA_URI, DEFAULT, td.getDefault()
					.getDefault());
		parent.appendChild(define);
		gType(td.getType(), define);
	}

	private void gType(YANG_Type type, Element parent) {

		if (!YangBuiltInTypes.isBuiltIn(type.getType())) {
			if (isRestrictedType(type)) {
				gRestrictions(type, parent);
			} else {
				YANG_TypeDef ttd = type.getTypedef();
				Element ref = document.createElementNS(RELAXNG_NS, REF);
				ref.setAttribute(NAME, definestypedefs.get(ttd));
				parent.appendChild(ref);
			}

		} else {

			// type is a built in type

			if (type.getUnionSpec() != null) {

				Element choice = document.createElementNS(RELAXNG_NS, CHOICE);
				parent.appendChild(choice);
				YANG_UnionSpecification uspec = type.getUnionSpec();
				for (YANG_Type ut : uspec.getTypes()) {
					Element ref = document.createElementNS(RELAXNG_NS, REF);
					ref
							.setAttribute(NAME, definestypedefs.get(ut
									.getTypedef()));
					choice.appendChild(ref);
				}
			} else {

				String st = type.getType();

				if (st.compareTo(YangBuiltInTypes.empty) == 0) {
					Element empty = document.createElementNS(RELAXNG_NS, EMPTY);
					parent.appendChild(empty);
				} else if (st.compareTo(YangBuiltInTypes.yboolean) == 0) {
					Element choice = document.createElementNS(RELAXNG_NS,
							CHOICE);
					Element t = document.createElementNS(RELAXNG_NS, TRUE);
					Element f = document.createElementNS(RELAXNG_NS, FALSE);
					choice.appendChild(t);
					choice.appendChild(f);
					parent.appendChild(choice);
				} else if (st.compareTo(YangBuiltInTypes.binary) == 0) {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					data.setAttribute(TYPE, BASE64BINARY);
					parent.appendChild(data);
				} else if (st.compareTo(YangBuiltInTypes.bits) == 0) {
					Element list = document.createElementNS(RELAXNG_NS, LIST);
					parent.appendChild(list);

					YANG_BitSpecification bs = type.getBitSpec();
					for (YANG_Bit bit : bs.getBits()) {
						Element optional = document.createElementNS(RELAXNG_NS,
								OPT);
						Element value = document.createElementNS(RELAXNG_NS,
								VALUE);
						value.setTextContent(bit.getBit());
						optional.appendChild(value);
						list.appendChild(optional);
					}
				} else if (st.compareTo(YangBuiltInTypes.enumeration) == 0) {
					Element choice = document.createElementNS(RELAXNG_NS,
							CHOICE);
					parent.appendChild(choice);
					for (YANG_Enum yenum : type.getEnums()) {
						Element value = document.createElementNS(RELAXNG_NS,
								VALUE);
						value.setTextContent(yenum.getEnum());
						gSatus(yenum, value);
						choice.appendChild(value);
					}
				} else if (st.compareTo(YangBuiltInTypes.identityref) == 0) {
					Element ref = document.createElementNS(RELAXNG_NS, REF);
					parent.appendChild(ref);
					String idref = type.getIdentityRefSpec().getBase();
					if (type.getIdentityRefSpec().isPrefixed())
						idref = "__" + idref.replace(':', '_');
					else
						idref = "__" + prefix + "_" + idref;
					ref.setAttribute(NAME, idref);
				} else if (st.compareTo(YangBuiltInTypes.instanceidentifier) == 0) {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					parent.appendChild(data);
					data.setAttribute(TYPE, STRING);
					Element ii = document.createElementNS(NMA_URI, INSTID);
					parent.appendChild(ii);
					if (type.getInstanceIdentifierSpec() != null)
						ii.setAttribute(REQINST, type
								.getInstanceIdentifierSpec());
				} else if (st.compareTo(YangBuiltInTypes.leafref) == 0) {
					gType(type.getLeafRef().getReferencedTypeLeaf(), parent);
					parent.setAttribute(NMA + ":" + LEAFREF, type.getLeafRef()
							.getPath().getPath());
				} else if (YangBuiltInTypes.isInteger(st)) {
					gRestrictions(type, parent);
				} else if (st.compareTo(YangBuiltInTypes.string) == 0) {
					if (type.getStringRest() != null)
						gRestrictions(type, parent);
					else {
						Element data = document.createElementNS(RELAXNG_NS,
								DATA);
						data.setAttribute(TYPE, STRING);
						parent.appendChild(data);
					}
				} else if (st.compareTo(YangBuiltInTypes.decimal64) == 0) {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					data.setAttribute(TYPE, yangType2DsdlType(st));
					parent.appendChild(data);
					Element param = document.createElementNS(RELAXNG_NS, PARAM);
					param.setAttribute(NAME, TOTALDIGIT);
					param.setTextContent(_19);
					data.appendChild(param);
					Element param2 = document
							.createElementNS(RELAXNG_NS, PARAM);
					param2.setAttribute(NAME, FRACTDIGIT);
					param2.setTextContent(type.getDec64Spec()
							.getFractionDigit());
					data.appendChild(param2);
				}

			}
		}

	}

	private void gSatus(StatuedNode sb, Element value) {
		if (sb.getStatus() != null)
			value.setAttributeNS(NMA_URI, STATUS, sb.getStatus().getStatus());

	}

	private boolean isRestrictedType(YANG_Type type) {
		return type.getStringRest() != null || type.getNumRest() != null;
	}

	private void gRestrictions(YANG_Type type, Element parent) {

		YANG_Type ltype = type;
		String typestr = ltype.getType();
		while (!YangBuiltInTypes.isBuiltIn(typestr)) {
			YANG_TypeDef td = ltype.getTypedef();
			ltype = td.getType();
			typestr = ltype.getType();
		}
		typestr = yangType2DsdlType(typestr);

		if (type.getStringRest() != null) {
			String[][] lengths = new String[0][0];
			YANG_StringRestriction strest = type.getStringRest();

			if (strest.getLength() != null) {
				lengths = strest.getLength().getLengthIntervals();
			}
			if (lengths.length > 1) {
				Element choice = document.createElementNS(RELAXNG_NS, CHOICE);
				parent.appendChild(choice);
				for (String[] l : lengths) {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					data.setAttribute(TYPE, typestr);
					choice.appendChild(data);
					gLength(l, data);
					for (YANG_Pattern pat : strest.getPatterns()) {
						gPattern(pat, data);
					}
				}
			} else if (lengths.length == 1) {
				Element data = document.createElementNS(RELAXNG_NS, DATA);
				data.setAttribute(TYPE, typestr);
				parent.appendChild(data);
				gLength(lengths[0], data);
				for (YANG_Pattern pat : strest.getPatterns()) {
					gPattern(pat, data);
				}
			} else {
				Element data = document.createElementNS(RELAXNG_NS, DATA);
				data.setAttribute(TYPE, typestr);
				parent.appendChild(data);
				for (YANG_Pattern pat : strest.getPatterns()) {
					gPattern(pat, data);
				}
			}
		} else if (type.getNumRest() != null) {

			YANG_NumericalRestriction numrest = type.getNumRest();

			if (numrest instanceof YANG_Range) {
				YANG_Range range = (YANG_Range) numrest;
				String[][] ranges = new String[0][0];
				ranges = range.getRangeIntervals();
				if (ranges.length > 1) {
					Element choice = document.createElementNS(RELAXNG_NS,
							CHOICE);
					parent.appendChild(choice);
					for (String[] l : ranges) {
						Element data = document.createElementNS(RELAXNG_NS,
								DATA);
						data.setAttribute(TYPE, typestr);
						choice.appendChild(data);
						gRange(l, data);
					}
				} else if (ranges.length == 1) {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					data.setAttribute(TYPE, typestr);
					parent.appendChild(data);
					gRange(ranges[0], data);
				} else {
					Element data = document.createElementNS(RELAXNG_NS, DATA);
					data.setAttribute(TYPE, typestr);
					parent.appendChild(data);
				}
			}

		}

		/*
		 * else { Element data = document.createElementNS(RELAXNG_NS, DATA);
		 * data.setAttribute("type", yangType2DsdlType(type.getType()));
		 * parent.appendChild(data); }
		 */
	}

	private void gLength(String[] l, Element parent) {

		if (l[0].compareTo("0") != 0
				&& l[0].compareTo(YangBuiltInTypes.uint64ub.toString()) != 0) {
			if (l[0].compareTo(l[1]) == 0) {
				Element param = document.createElementNS(RELAXNG_NS, PARAM);
				param.setAttribute(NAME, "length");
				param.setTextContent(l[0]);
				parent.appendChild(param);
			} else {
				Element paramin = document.createElementNS(RELAXNG_NS, PARAM);
				paramin.setAttribute(NAME, "minLength");
				paramin.setTextContent(l[0]);
				parent.appendChild(paramin);
				if (l[1].compareTo(YangBuiltInTypes.max) != 0) {
					Element paramax = document.createElementNS(RELAXNG_NS,
							PARAM);
					paramax.setAttribute(NAME, "maxLength");
					paramax.setTextContent(l[1]);
					parent.appendChild(paramax);
				}
			}
		}

	}

	private void gRange(String[] l, Element parent) {

		if (l[0].compareTo(YangBuiltInTypes.min) != 0) {
			Element paramin = document.createElementNS(RELAXNG_NS, PARAM);
			paramin.setAttribute(NAME, "minInclusive");
			paramin.setTextContent(l[0]);
			parent.appendChild(paramin);
		}
		if (l[1].compareTo(YangBuiltInTypes.max) != 0) {
			Element paramax = document.createElementNS(RELAXNG_NS, PARAM);
			paramax.setAttribute(NAME, "maxInclusive");
			paramax.setTextContent(l[1]);
			parent.appendChild(paramax);
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
			return "unsignedByte";
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
		else if (type.compareTo(YangBuiltInTypes.decimal64) == 0)
			return "decimal";
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
						// prefix = i.getName();
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

	// private void gType(YANG_Specification spec, YANG_Type type, Element elt,
	// String prefix) {
	//
	// if (YangBuiltInTypes.isBuiltIn(type.getType())) {
	// if (type.getUnionSpec() != null) {
	//
	// Element choice = document.createElementNS(RELAXNG_NS, CHOICE);
	// elt.appendChild(choice);
	//
	// YANG_UnionSpecification uspec = type.getUnionSpec();
	// for (YANG_Type ut : uspec.getTypes()) {
	// Element ref = document.createElementNS(RELAXNG_NS, REF);
	// ref.setAttribute("name", prefix + SEP + ut.getSuffix());
	// choice.appendChild(ref);
	// }
	// } else {
	// Element data = document.createElementNS(RELAXNG_NS, DATA);
	// data.setAttribute("type", yangType2DsdlType(type.getType()));
	// elt.appendChild(data);
	// if (type.getStringRest() != null) {
	// YANG_StringRestriction strest = type.getStringRest();
	// for (YANG_Pattern pat : strest.getPatterns()) {
	// gPattern(pat, data);
	// }
	// }
	// }
	// } else {
	// String specnameprefix = prefix;
	//
	// if (type.isPrefixed()) {
	// String tpref = type.getPrefix();
	// for (YANG_Import i : spec.getImports()) {
	// if (i.getPrefix().getPrefix().compareTo(tpref) == 0) {
	// specnameprefix = i.getName();
	// }
	// }
	// }
	// Element ref = document.createElementNS(RELAXNG_NS, REF);
	// ref.setAttribute("name", specnameprefix + SEP + type.getType());
	// elt.appendChild(ref);
	// }
	// }

	private void gPattern(YANG_Pattern pat, Element parent) {
		Element param = document.createElementNS(RELAXNG_NS, PARAM);
		param.setAttribute("pattern", pat.getPattern());
		parent.appendChild(param);
	}

	private YANG_Type gLeaf(YANG_Specification spec, YANG_Leaf leaf,
			Element parent) {

		YANG_Type type = leaf.getType();

		Element element = document.createElementNS(RELAXNG_NS, ELT);

		String lprefix = "";
		if (!defining)
			lprefix = prefix + ":";

		element.setAttribute(NAME, lprefix + leaf.getLeaf());

		if (leaf.getDefault() != null)
			element.setAttributeNS(NMA_URI, DEFAULT, leaf.getDefault()
					.getDefault());
		else {
			if (isRestrictedType(type)) {
				YANG_Type ltype = type;
				YANG_TypeDef td = ltype.getTypedef();
				if (td != null) {
					boolean finish = false;
					while (!finish) {
						finish = td.getDefault() != null;
						if (!finish) {
							YANG_Type t = td.getType();
							td = t.getTypedef();
							finish = td == null;
						}
					}
					if (td != null) {
						element.setAttributeNS(NMA_URI, DEFAULT, td
								.getDefault().getDefault());
					}
				}
			}
		}

		gDescription(leaf, element);
		gConfigDataDef(leaf, element);
		gType(type, element);

		if (!leaf.isMandatory()) {
			Element optional = document.createElementNS(RELAXNG_NS, OPT);
			parent.appendChild(optional);
			optional.appendChild(element);
		} else
			parent.appendChild(element);
		return type;
	}

	private YANG_Type gKeyLeaf(YANG_Specification spec, YANG_Leaf leaf,
			Element parent) {

		YANG_Type type = leaf.getType();

		Element element = document.createElementNS(RELAXNG_NS, ELT);
		String kprefix = "";
		if (!defining)
			kprefix = prefix + ":";
		element.setAttribute("name", kprefix + leaf.getLeaf());
		if (leaf.getDefault() != null)
			element.setAttribute(NMA + ":" + DEFAULT, leaf.getDefault()
					.getDefault());

		gDescription(leaf, element);
		gConfigDataDef(leaf, element);

		gType(type, element);

		parent.appendChild(element);
		return type;
	}

	// private newElement()

	private void gSpec(YANG_Specification spec, Element parent) {

		prefix = spec.getPrefix().getPrefix();

		Element grammar = document.createElementNS(RELAXNG_NS, GRAMMAR);
		grammar.setAttribute("module", spec.getName());
		grammar.setAttribute(NS, spec.getNameSpace().getNameSpace());
		parent.appendChild(grammar);

		Element source = document.createElementNS(DC_URI, SOURCE);
		source.setTextContent(YANGMODULE + " '" + spec.getName() + "'");
		grammar.appendChild(source);
		Element start = document.createElementNS(RELAXNG_NS, START);
		grammar.appendChild(start);

		Element data = document.createElementNS(NMA_URI, DATA);
		start.appendChild(data);

		Element rpcs = document.createElementNS(NMA_URI, RPCS);
		start.appendChild(rpcs);
		Element notifications = document
				.createElementNS(NMA_URI, NOTIFICATIONS);
		start.appendChild(notifications);

		for (YANG_Body body : spec.getBodies())
			if (body instanceof YANG_DataDef)
				gDataDef(spec, (YANG_DataDef) body, data);
			else if (body instanceof YANG_Rpc)
				gRpcs((YANG_Rpc) body, rpcs);
			else if (body instanceof YANG_Notification)
				gNotifications((YANG_Notification) body, notifications);

	}

	private void gRpcs(YANG_Rpc body, Element rpc) {

	}

	private void gNotifications(YANG_Notification body, Element notification) {

	}

}