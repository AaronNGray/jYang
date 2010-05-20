package jyang.tools;

import java.io.PrintStream;

import jyang.parser.YANG_Module;
import jyang.parser.YANG_Specification;

public class Yang2Dsdl {
	

	public Yang2Dsdl(YANG_Specification n, String[] paths, PrintStream out) {

		// try {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		if (n instanceof YANG_Module) {
			out.println("<module xmlns=\"urn:ietf:params:xml:ns:yin:1\"");
		} else {
			out.println("<submodule xmlns=\"urn:ietf:params:xml:ns:yin:1\"");
		}
	}

}
