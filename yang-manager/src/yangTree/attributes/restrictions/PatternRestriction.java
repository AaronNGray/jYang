package yangTree.attributes.restrictions;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import jyang.parser.YANG_Pattern;
import jyang.parser.YangBuiltInTypes;

import yangTree.attributes.UnitValueCheck;

@SuppressWarnings("serial")
public class PatternRestriction extends Restriction {
	
	private Pattern pattern;
	
	public PatternRestriction(YANG_Pattern ypattern){
		String canopattern = ypattern.getPattern().replaceAll("IsBasicLatin",
		"InBasicLatin");
		pattern = Pattern.compile(YangBuiltInTypes.removeQuotesAndTrim(canopattern));
	}

	@Override
	public UnitValueCheck check(String value) {
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()){
			return UnitValueCheck.checkOK();
		} else {
			return new UnitValueCheck("This value does not match the required pattern : "+pattern+" .");
		}
	}

	@Override
	public String getDescription() {
		return "Value must match the following pattern : "+pattern;
	}

}
