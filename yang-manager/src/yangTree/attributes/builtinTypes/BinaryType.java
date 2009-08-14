package yangTree.attributes.builtinTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jyang.parser.YANG_StringRestriction;
import jyang.parser.YANG_Type;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.restrictions.BinaryLengthRestriction;

@SuppressWarnings("serial")
public class BinaryType extends BuiltinType {
	
	public static final Pattern pattern = Pattern.compile("[A-Za-z0-9+/]*[=]{0,2}");
	
	public BinaryType(YANG_Type type){
		YANG_StringRestriction stringRestr = type.getStringRest();
		if (stringRestr != null) {
			if (stringRestr.getLength() != null)
				restrictionsList.add(new BinaryLengthRestriction(stringRestr
						.getLength()));
		}
	}

	@Override
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches() || value.length()%4!=0){
			result.addUnitCheck(new UnitValueCheck("This value does not match the base64 encoding scheme [RFC4648]."));
			return result;
		}
		result.addChecks(super.check(value));
		return result;
	}
	
	
	@Override
	public String getName() {
		return "Binary";
	}

}
