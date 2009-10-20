package jyang.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class YANG_Pattern extends ErrorTagedNode {

	private String pattern = null;

	private Pattern regexp = null;

	public YANG_Pattern(int id) {
		super(id);
	}

	public YANG_Pattern(yang p, int id) {
		super(p, id);
	}

	public void setPattern(String p) {

		pattern = p;
		String canopattern = pattern.replaceAll("IsBasicLatin", "InBasicLatin");
		pattern = canopattern;
		try {
			regexp = Pattern.compile(YangBuiltInTypes
					.removeQuotesAndTrim(pattern));
		} catch (PatternSyntaxException pse) {

			YangErrorManager.add(getLine(), getCol(), YangErrorManager.messages
					.getString("pattern_exp"));
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void checkExp(String exp) throws YangParserException {
		Matcher m = regexp.matcher(exp);
		if (!m.matches())

			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":incorrect expression : \"" + exp
					+ "\" does not match with  regular expression " + pattern);
	}

	public boolean isBracked() {
		return super.isBracked();
	}

	public String toString() {
		String result = new String();
		result += "pattern " + pattern;
		if (isBracked()) {
			result += "{\n";
			result += super.toString();
			result += "}";
		} else
			result += ";";
		return result;
	}

}