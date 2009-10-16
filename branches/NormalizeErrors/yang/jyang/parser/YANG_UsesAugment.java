package jyang.parser;

import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YANG_UsesAugment extends FeaturedNode {

	private String usesaugment = null;
	private Vector<YANG_Case> cases = new Vector<YANG_Case>();

	private Pattern dsni = null;

	public YANG_UsesAugment(int id) {
		super(id);
		
		try {
			dsni = Pattern
					.compile("([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*((/([_A-Za-z][._A-Za-z0-9]*:)?[_A-Za-z][._A-Za-z0-9]*)+)?");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public YANG_UsesAugment(yang p, int id) {
		super(p, id);
	}

	public void setUsesAugment(String ua)  {
		String aa = YangBuiltInTypes.removeQuotesAndTrim(ua);
		Matcher m = dsni.matcher(aa);
		if (m.matches())
			usesaugment = aa;
		else
			YangErrorManager.add(getLine(), getCol(),
					YangErrorManager.messages.getString("uses_augment_exp"));
	}

	public String getUsesAugment() {
		return usesaugment;
	}

	public String getBody() {
		return getUsesAugment();
	}

	public void addCase(YANG_Case c) {
		cases.add(c);
	}

	public Vector<YANG_Case> getCases() {
		return cases;
	}

	public boolean isBracked() {
		return super.isBracked() ||  cases.size() != 0;
	}

	public String toString() {
		String result = "";
		result += "augment " + getUsesAugment();
		if (isBracked()) {
			result += "{\n";
			result += super.toString() + "\n";
			for (Enumeration<YANG_Case> ec = cases.elements(); ec
					.hasMoreElements();)
				result += ec.nextElement().toString() + "\n";
		}
		else
			result += ";";
		return result;
	}

}
