package yangTree.attributes.builtinTypes;

import java.util.LinkedList;

import jyang.parser.YANG_Bit;
import jyang.parser.YANG_Type;
import yangTree.attributes.LeafType;

public class BitsType extends LeafType {

	// TODO : check uniqueness and range of enumeration element value.

	private LinkedList<BitsElement> elements = new LinkedList<BitsElement>();

	public BitsType(YANG_Type type) {
		int defaultValue = 0;
		for (YANG_Bit bit : type.getBitSpec().getBits()) {
			if (bit.getPosition() == null) {
				defaultValue++;
				elements.add(new BitsElement(bit.getBit(),
						defaultValue, bit.getDescription().getDescription()));
			} else {
				Integer value = new Integer(bit.getPosition().getPosition());
				if (value > defaultValue)
					defaultValue = value + 1;
				elements.add(new BitsElement(bit.getBit(), value,
						bit.getDescription().getDescription()));
			}
		}
	}

	@Override
	public String getName() {
		String result = "Enumeration";
		for (BitsElement elt : elements){
			result += "\n\t*"+elt.getName()+" ("+elt.getDescription()+")";
		}
		return result;
	}

}
