package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_NumericalRestriction;
import jyang.parser.YANG_Range;
import jyang.parser.YANG_Type;

import yangTree.attributes.LeafType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.restrictions.RangeRestriction;

public class IntegerTypes extends LeafType {
	
	//TODO : Handle default value "special" representation

	private String name;
	private int byteRange;
	private boolean positiveOnly;

	public IntegerTypes(YANG_Type type) {

		name = type.getType();
		String byteR;
		if (name.charAt(0) == 'u') {
			positiveOnly = true;
			byteR = name.substring(3);
		} else {
			positiveOnly = false;
			byteR = name.substring(4);
		}

		byteRange = new Integer(byteR);

		if (byteRange != 8 && byteRange != 16 && byteRange != 32
				&& byteRange != 64) {
			throw new IllegalArgumentException("Invalid IntegerType format : "
					+ name);
		}

		YANG_NumericalRestriction numRestr = type.getNumRest();
		if (numRestr != null) {
			if (numRestr instanceof YANG_Range)
				restrictionsList.add(new RangeRestriction(
						((YANG_Range) numRestr)));
		}

	}

	@Override
	public ValueCheck check(String value) {
		ValueCheck result = super.check(value);
		Long decimalValue = new Long(value);
		long byteMaxValue = (long) Math.pow(2, byteRange);
		if (positiveOnly) {
			if (decimalValue < 0 || decimalValue > byteMaxValue - 1)
				result.addUnitCheck(new UnitValueCheck("value out of "
						+ getName() + " type range"));
		} else {
			if (decimalValue < byteMaxValue / 2
					|| decimalValue > (byteMaxValue / 2) - 1)
				result.addUnitCheck(new UnitValueCheck("value out of "
						+ getName() + " type range"));
		}
		return result;
	}

	@Override
	public String getName() {
		return name;
	}

}
