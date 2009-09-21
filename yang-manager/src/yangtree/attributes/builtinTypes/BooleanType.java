package yangtree.attributes.builtinTypes;

import jyang.parser.YANG_Type;
import yangtree.attributes.UnitValueCheck;
import yangtree.attributes.ValueCheck;

@SuppressWarnings("serial")
public class BooleanType extends BuiltinType {

	public BooleanType(YANG_Type type) {}
	
	@Override
	public ValueCheck check(String value) {
		ValueCheck result = super.check(value);
		if (!value.equals("true") && !value.equals("false"))
			result.addUnitCheck(new UnitValueCheck(
					"A boolean value must be \"true\" or \"false\"."));
		return result;
	}

	@Override
	public String getName() {
		return "Boolean";
	}

}
