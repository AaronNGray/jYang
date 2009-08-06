package yangTree.attributes;

import java.io.Serializable;
import java.util.LinkedList;

import jyang.parser.YANG_Type;
import yangTree.attributes.builtinTypes.*;
import yangTree.attributes.restrictions.Restriction;

public abstract class BuiltinType implements Serializable{
	
	protected LinkedList<Restriction> restrictionsList = new LinkedList<Restriction>();
	
	/*
	 * Match a built-in type from a YANG type.
	 */
	public static BuiltinType buildType(YANG_Type type){
		if (type.getType().contains("int")) return new IntegerTypes(type);
		if (type.getType().equals("decimal64")) return new Decimal64Type(type);
		if (type.getType().equals("string")) return new StringType(type);
		if (type.getType().equals("boolean")) return new BooleanType(type);
		if (type.getType().equals("enumeration")) return new EnumerationType(type);
		if (type.getType().equals("bits")) return new BitsType(type);
		if (type.getType().equals("binary")) return new BinaryType(type);
		if (type.getType().equals("empty")) return new EmptyType();
		if (type.getType().equals("union")) return new UnionType(type);
		System.err.println("Unknow built-in type");
		return null;
	}
	
	/*
	 * Check if a value is permitted given his type and restrictions.
	 */
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		for (Restriction restriction : restrictionsList){
			result.addUnitCheck(restriction.check(value));
		}
		return result;
	}
	
	/*
	 * Return true if a value have at least one restriction.
	 */
	public boolean hasRestrictions(){
		return restrictionsList.size()>0;
	}
	
	/*
	 * Return a HTML-formatted description of the list of restrictions.
	 */
	public String getRestrictionsDescription(){
		String result = "<ul>";
		for (Restriction restriction : restrictionsList){
			result += "<li>"+restriction.getDescription()+"</li>";
		}
		return result+"</ul>";
	}
	
	/*
	 * Return a very short description of the type.
	 */
	public abstract String getName();
	
	/*
	 * Return a HTML-formatted complete description of the type.
	 */
	public String getContent(){
		return getName();
	}

	
}
