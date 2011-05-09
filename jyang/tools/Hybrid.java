package jyang.tools;

import javax.xml.parsers.*;

import jyang.parser.YANG_Specification;

import org.w3c.dom.*;

public class Hybrid  {
	private final static String XMLNS = "xmlns";
	private final static String RELAXNG_NS = "http://relaxng/org/ns/structure/1.0";
	private final static String NETMOD_NS = "urn:ietf:params:xml:ns:netmod:dsdl-annotations:1";

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


	private Document doc = null;
	
	public Hybrid(){
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = doc.createElementNS(RELAXNG_NS, GRAMMAR);
			doc.appendChild(root);
			root.setAttribute(XMLNS + ":" + NMA, NMA_URI);
			root.setAttribute(XMLNS + ":" + DC, DC_URI);
			root.setAttribute(XMLNS + ":" + A, A_URI);

			Element start = doc.createElementNS(RELAXNG_NS, START);
			root.appendChild(start);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Element newDefines() {
		return doc.createElementNS(RELAXNG_NS, DEFINE);
	}

	public Element newGrammar(YANG_Specification spec) {
		Element grammar = doc.createElementNS(RELAXNG_NS, GRAMMAR);
		grammar.setAttribute("module", spec.getName());
		grammar.setAttribute(NS, spec.getNameSpace().getNameSpace());
		return grammar;
	}
	

}
