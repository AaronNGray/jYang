package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Type;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

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
