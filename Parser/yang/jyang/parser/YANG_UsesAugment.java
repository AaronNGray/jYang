
package jyang.parser;

import java.util.Enumeration;
import java.util.Vector;


public class YANG_UsesAugment extends YANG_DataDefInfoWhen {

	private String usesaugment = null;
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();
	private Vector<YANG_Case> cases = new Vector<YANG_Case>();

	public YANG_UsesAugment(int id) {
		super(id);
	}

	public YANG_UsesAugment(yang p, int id) {
		super(p, id);
	}

	public void setUsesAugment(String ua) {
		usesaugment = ua;
	}

	public String getUsesAugment() {
		return usesaugment;
	}

	public String getBody() {
		return getUsesAugment();
	}

	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}

	public void addCase(YANG_Case c) {
		cases.add(c);
	}

	public Vector<YANG_Case> getCases() {
		return cases;
	}

	public boolean isBracked() {
		return super.isBracked() || datadefs.size() != 0 || cases.size() != 0;
	}

	public String toString() {
		String result = "";
		result += "augment " + getUsesAugment() + " {\n";
		if (isBracked()) {
			result += super.toString() + "\n";
			for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
					.hasMoreElements();)
				result += ed.nextElement().toString() + "\n";
			for (Enumeration<YANG_Case> ec = cases.elements(); ec
					.hasMoreElements();)
				result += ec.nextElement().toString() + "\n";
		}
		return result;
	}

}
