package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Type;

public class EmptyType extends BuiltinType {
	
	public EmptyType(){}

	@Override
	public String getName() {
		return "Empty";
	}

}
