package yangtree.attributes.builtinTypes;

import jyang.parser.YANG_NumericalRestriction;
import jyang.parser.YANG_Range;
import jyang.parser.YANG_Type;

import yangtree.attributes.UnitValueCheck;
import yangtree.attributes.ValueCheck;
import yangtree.attributes.restrictions.RangeRestriction;

@SuppressWarnings("serial")
public class IntegerTypes extends BuiltinType {

	private String name;
	private int byteRange;
	private boolean positiveOnly;

	public IntegerTypes(YANG_Type type) {

		name = type.getType();
		String byteR;
		if (name.charAt(0) == 'u') {
			positiveOnly = true;
			byteR = name.substring(4);
		} else {
			positiveOnly = false;
			byteR = name.substring(3);
		}

		byteRange = new Integer(byteR);

		YANG_NumericalRestriction numRestr = type.getNumRest();
		if (numRestr != null) {
			if (numRestr instanceof YANG_Range)
				restrictionsList.add(new RangeRestriction(
						((YANG_Range) numRestr)));
		}

	}

	@Override
	public ValueCheck check(String value) {
		ValueCheck result = new ValueCheck();
		Long decimalValue = null;
		try {
		decimalValue = new Long(value);
		} catch (NumberFormatException e){
			result.addUnitCheck(new UnitValueCheck("This value does not present a correct integer format."));
			return result;
		}
		long byteMaxValue = (long) Math.pow(2, byteRange);
		if (positiveOnly) {
			if (decimalValue < 0 || decimalValue > byteMaxValue - 1)
				result.addUnitCheck(new UnitValueCheck("value out of "
						+ getName() + " type range"));
		} else {
			if (decimalValue < -byteMaxValue / 2
					|| decimalValue > (byteMaxValue / 2) - 1)
				result.addUnitCheck(new UnitValueCheck("value out of "
						+ getName() + " type range"));
		}
		result.addChecks(super.check(value));
		return result;
	}

	@Override
	public String getName() {
		return name;
	}

}
