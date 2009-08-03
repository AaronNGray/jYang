package yangTree.attributes.builtinTypes;

import java.util.LinkedList;

import jyang.parser.YANG_Enum;
import jyang.parser.YANG_Type;

import yangTree.attributes.LeafType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

public class EnumerationType extends LeafType {

	// TODO : check uniqueness and range of enumeration element value.

	private LinkedList<EnumerationElement> elements = new LinkedList<EnumerationElement>();

	public EnumerationType(YANG_Type type) {
		int defaultValue = 0;
		int choosedValue ;
		for (YANG_Enum yenum : type.getEnums()) {
			if (yenum.getValue() == null) {
				defaultValue++;
				choosedValue = defaultValue;
			} else {
				Integer value = new Integer(yenum.getValue().getValue());
				if (value > defaultValue)
					defaultValue = value + 1;
				choosedValue = value;
			}
			if (yenum.getDescription()!=null){
				elements.add(new EnumerationElement(yenum.getEnum(),
						choosedValue, yenum.getDescription().getDescription()));
				} else {
					elements.add(new EnumerationElement(yenum.getEnum(),
							choosedValue));
				}
		}
	}
	
	@Override
	public ValueCheck check(String value){
		ValueCheck result = super.check(value);
		boolean found = false;
		for (EnumerationElement elt : elements){
			if (elt.getName().equals(value)) found=true;
		}
		if (!found) result.addUnitCheck(new UnitValueCheck("This value is not an element of the enumeration")); 
		return result;
	}

	@Override
	public String getName() {
		String result = "Enumeration : ";
		for (EnumerationElement elt : elements){
			result += "\n\t*"+elt.getName()+" ("+elt.getDescription()+")";
		}
		return result;
	}

}
