package yangTree.attributes;

import java.io.Serializable;
import java.util.LinkedList;

import jyang.parser.YANG_Type;
import yangTree.attributes.builtinTypes.*;
import yangTree.attributes.restrictions.Restriction;

public abstract class LeafType implements Serializable{
	
	protected LinkedList<Restriction> restrictionsList = new LinkedList<Restriction>();
	
	public static LeafType buildType(YANG_Type type){
		if (type.getType().contains("int")) return new IntegerTypes(type);
		if (type.getType().equals("decimal64")) return new Decimal64Type(type);
		if (type.getType().equals("string")) return new StringType(type);
		if (type.getType().equals("boolean")) return new BooleanType(type);
		if (type.getType().equals("enumeration")) return new EnumerationType(type);
		if (type.getType().equals("bits")) return new BitsType(type);
		System.err.println("Unknow built-in type");
		return null;
	}
	
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		for (Restriction restriction : restrictionsList){
			result.addUnitCheck(restriction.check(value));
		}
		return result;
	}
	
	public String getRestrictionsDescription(){
		String result = "";
		for (Restriction restriction : restrictionsList){
			result += restriction.getDescription()+"\n";
		}
		return result;
	}
	
	public abstract String getName();

	
}
