package yangTree.attributes.builtinTypes;


import java.util.TreeSet;

import applet.Util;

import jyang.parser.YANG_Bit;
import jyang.parser.YANG_Type;
import yangTree.attributes.BuiltinType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;

public class BitsType extends BuiltinType {

	// TODO : check uniqueness and range of enumeration element value.

	private TreeSet<BitsElement> elements = new TreeSet<BitsElement>();

	public BitsType(YANG_Type type) {
		int defaultValue = 0;
		int choosedValue;
		for (YANG_Bit bit : type.getBitSpec().getBits()) {
			if (bit.getPosition() == null) {
				defaultValue++;
				choosedValue = defaultValue;
			} else {
				Integer value = new Integer(bit.getPosition().getPosition());
				if (value > defaultValue)
					defaultValue = value + 1;
				choosedValue = value;
			}
			if (bit.getDescription()!=null){
				elements.add(new BitsElement(bit.getBit(),
						choosedValue, bit.getDescription().getDescription()));
				} else {
					elements.add(new BitsElement(bit.getBit(),
							choosedValue));
				}
		}
	}
	
	@Override
	public ValueCheck check(String value){
		ValueCheck result = super.check(value);
		String[] bitsArray = value.split(" ");
		TreeSet<BitsElement> elets = new TreeSet<BitsElement>(elements);
		int i=0;
		while (elets.size()>0 && i<bitsArray.length){
			BitsElement elet = elets.pollFirst();
			if (elet.getName().equals(bitsArray[i])) i++;
		}
		if (i<bitsArray.length) result.addUnitCheck(new UnitValueCheck("Unknown or unsorted bit value : \""+bitsArray[i]+"\""));
		return result;
	}
	
	public String getName(){
		String result = "Bits {";
		for (BitsElement elt : elements){
			result += " "+elt.getName()+" |";
		}
		return result.substring(0, result.length()-2)+" }";
	}

	@Override
	public String getContent() {
		String result = "Bits : <ul>";
		for (BitsElement elt : elements){
			if (elt.getDescription()!=null){
				result += "<li>"+elt.getName()+" : <i>"+elt.getDescription()+"</i></li>";
			} else {
				result += "<li>"+elt.getName()+"</li>";
			}
		}
		return result+"</ul>";
	}

}
