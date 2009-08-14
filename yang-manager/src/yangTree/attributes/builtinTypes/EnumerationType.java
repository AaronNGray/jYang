package yangTree.attributes.builtinTypes;

import java.util.LinkedList;

import jyang.parser.YANG_Enum;
import jyang.parser.YANG_Type;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

@SuppressWarnings("serial")
public class EnumerationType extends BuiltinType {

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
		if (!found) result.addUnitCheck(new UnitValueCheck("This value is not an element of the enumeration.")); 
		return result;
	}
	
	public String getName(){
		String result = "Enumeration {";
		for (EnumerationElement elt : elements){
				result += " "+elt.getName()+" |";
			}
		return result.substring(0, result.length()-2)+" }";
	}

	@Override
	public String getContent() {
		String result = "Enumeration : <ul>";
		for (EnumerationElement elt : elements){
			if (elt.getDescription()!=null) {
				result += "<li>"+elt.getName()+" : <i>"+elt.getDescription()+"</i></li>";
			} else {
				result += "<li>"+elt.getName()+"</li>";
			}
		}
		return result+"</ul>";
	}

}
