package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Pattern;
import jyang.parser.YANG_StringRestriction;
import jyang.parser.YANG_Type;
import yangTree.attributes.restrictions.LengthRestriction;
import yangTree.attributes.restrictions.PatternRestriction;

public class StringType extends BuiltinType {

	public StringType(YANG_Type type) {
		YANG_StringRestriction stringRestr = type.getStringRest();
		if (stringRestr != null) {
			if (stringRestr.getLength() != null)
				restrictionsList.add(new LengthRestriction(stringRestr
						.getLength()));
			if (stringRestr.getPatterns() != null) {
				for (YANG_Pattern pattern : stringRestr.getPatterns()) {
					restrictionsList.add(new PatternRestriction(pattern));
				}
			}
		}
	}

	@Override
	public String getName() {
		return "String";
	}

}
