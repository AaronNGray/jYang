package jyang.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class YANG_Pattern extends SimpleYangNode {

	private String pattern = null;
	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private Pattern regexp = null;

	private boolean bracked = false;

	public YANG_Pattern(int id) {
		super(id);
	}

	public YANG_Pattern(yang p, int id) {
		super(p, id);
	}

	public void setPattern(String p) throws YangParserException {
		
		pattern = p;
		String canopattern = pattern.replaceAll("IsBasicLatin",
		"InBasicLatin");
		pattern = canopattern;
		try {
			regexp = Pattern.compile(YangBuiltInTypes.removeQuotesAndTrim(pattern));
		} catch (PatternSyntaxException pse) {
			throw new YangParserException("@" + getLine() + "." + getCol() + ":"+
					pse.getMessage());
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setErrMess(YANG_ErrorMessage e) {
		errmess = e;
		bracked = true;
	}

	public YANG_ErrorMessage getErrMess() {
		return errmess;
	}

	public void setErrAppTag(YANG_ErrorAppt e) {
		errapptag = e;
		bracked = true;
	}

	public YANG_ErrorAppt getErrAppTag() {
		return errapptag;
	}

	public void setDescription(YANG_Description d) {
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) {
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public boolean isBracked() {
		return bracked;
	}
	
	public void checkExp(String exp) throws YangParserException{
		Matcher m = regexp.matcher(exp);
		if (!m.matches())
			
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":incorrect expression : \"" + exp + "\" does not match with  regular expression " + pattern);
	}

	public String toString() {
		String result = new String();
		result += "pattern " + pattern;
		if (bracked) {
			result += "{\n";
			if (errmess != null)
				result += errmess + "\n";
			if (errapptag != null)
				result += errapptag + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

}
