package yangTree.attributes.builtinTypes;

import java.io.Serializable;
import java.util.LinkedList;

import jyang.parser.YANG_Type;
import yangTree.attributes.LeafType;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.attributes.builtinTypes.*;
import yangTree.attributes.restrictions.Restriction;

/**
 * Represents a YANG built-in type.
 * @see LeafType
 */
public abstract class BuiltinType implements Serializable {

	protected LinkedList<Restriction> restrictionsList = new LinkedList<Restriction>();

	/**
	 * Checks if a given type is a YANG built-in type.
	 */
	public static boolean isBuiltinType(String type) {
		if (type == null) {
			System.err.println("Unknow TypeDef");
			return true;
		}
		if (type.equals("int8") || type.equals("int16") || type.equals("int32")
				|| type.equals("int64"))
			return true;
		if (type.equals("uint8") || type.equals("uint16")
				|| type.equals("uint32") || type.equals("uint64"))
			return true;
		if (type.equals("decimal64") || type.equals("string")
				|| type.equals("boolean") || type.equals("enumeration")
				|| type.equals("bits") || type.equals("binary")
				|| type.equals("empty") || type.equals("union")
				|| type.equals("leafref"))
			return true;
		return false;
	}

	/**
	 * Matches a built-in type from a YANG type.
	 */
	public static BuiltinType buildType(YANG_Type type) {
		if (!isBuiltinType(type.getType())) {
			System.err.println("Unknow built-in type : " + type.getType());
			return null;
		}
		if (type.getType().contains("int"))
			return new IntegerTypes(type);
		if (type.getType().equals("decimal64"))
			return new Decimal64Type(type);
		if (type.getType().equals("string"))
			return new StringType(type);
		if (type.getType().equals("boolean"))
			return new BooleanType(type);
		if (type.getType().equals("enumeration"))
			return new EnumerationType(type);
		if (type.getType().equals("bits"))
			return new BitsType(type);
		if (type.getType().equals("binary"))
			return new BinaryType(type);
		if (type.getType().equals("empty"))
			return new EmptyType();
		if (type.getType().equals("union"))
			return new UnionType(type);
		if (type.getType().equals("leafref"))
			return new LeafrefType(type);
		return null;
	}

	/**
	 * Checks if a value is correct given its type and restrictions.
	 */
	public ValueCheck check(String value) {
		ValueCheck result = new ValueCheck();
		for (Restriction restriction : restrictionsList) {
			result.addUnitCheck(restriction.check(value));
		}
		return result;
	}

	/**
	 * Returns true if a value have at least one restriction.
	 */
	public boolean hasRestrictions() {
		return restrictionsList.size() > 0;
	}

	/**
	 * Returns a HTML-formatted description of the list of restrictions.
	 */
	public String getRestrictionsDescription() {
		String result = "<ul>";
		for (Restriction restriction : restrictionsList) {
			result += "<li>" + restriction.getDescription() + "</li>";
		}
		return result + "</ul>";
	}

	/**
	 * Returns a very short description of the type.
	 */
	public abstract String getName();

	/**
	 * Returns a HTML-formatted complete description of the type.
	 */
	public String getContent() {
		return getName();
	}

}
