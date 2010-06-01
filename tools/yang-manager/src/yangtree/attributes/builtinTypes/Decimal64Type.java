package yangtree.attributes.builtinTypes;

import jyang.parser.YANG_Decimal64Spec;
import jyang.parser.YANG_Type;
import yangtree.attributes.UnitValueCheck;
import yangtree.attributes.ValueCheck;
import yangtree.attributes.restrictions.RangeRestriction;

@SuppressWarnings("serial")
public class Decimal64Type extends BuiltinType {
	
	private int fractionDigits = 18;
	
	public Decimal64Type(YANG_Type type){
		
		YANG_Decimal64Spec spec = type.getDec64Spec();
		if (spec!=null){
			if (spec.getFractionDigit()!=null) fractionDigits= new Integer(spec.getFractionDigit());
			// add by manu 19/10/9
			// decimal64 have no more range attribute
			//if (spec.getRange()!=null) restrictionsList.add(new RangeRestriction(spec.getRange()));
		}
		
	}
	
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		Double decimalValue = null;
		try {
			decimalValue = new Double(value);
		} catch (NumberFormatException e){
			result.addUnitCheck(new UnitValueCheck("This value does not present a correct number format."));
			return result;
		}
		double mustBeInteger = decimalValue*((long) Math.pow(10, fractionDigits));
		if (mustBeInteger!=Math.floor(mustBeInteger)){
			Double fixedValue = Math.floor(mustBeInteger)*Math.pow(10, -fractionDigits);
			result.addUnitCheck(new UnitValueCheck("Too many digits (max = "+fractionDigits+").",fixedValue.toString()));
		}
		result.addChecks(super.check(value));
		return result;
	}

	@Override
	public String getName() {
		return "Decimal64";
	}

}
