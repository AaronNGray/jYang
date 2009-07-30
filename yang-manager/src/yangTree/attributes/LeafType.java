package yangTree.attributes;

import java.util.LinkedList;

import jyang.parser.YANG_Type;
import yangTree.attributes.builtinTypes.IntegerTypes;
import yangTree.attributes.restrictions.Restriction;

public abstract class LeafType {
	
	protected LinkedList<Restriction> restrictionsList = new LinkedList<Restriction>();
	
	public static LeafType buildType(YANG_Type type){
		if (type.getType().contains("int")) return new IntegerTypes(type);
		return null;
	}
	
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		for (Restriction restriction : restrictionsList){
			result.addUnitCheck(restriction.check(value));
		}
		return result;
	}
	
	public abstract String getName();

	
}
