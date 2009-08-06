package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Type;
import yangTree.attributes.BuiltinType;

public class EmptyType extends BuiltinType {
	
	public EmptyType(){}

	@Override
	public String getName() {
		return "Empty";
	}

	@Override
	public String getContent() {
		return null;
	}

}
