package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Decimal64Spec;
import jyang.parser.YANG_Type;
import yangTree.attributes.LeafType;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.restrictions.RangeRestriction;

public class Decimal64 extends LeafType {
	
	private int fractionDigits = 18;
	
	public Decimal64(YANG_Type type){
		
		YANG_Decimal64Spec spec = type.getDec64Spec();
		if (spec!=null){
			if (spec.getFractionDigit()!=null) fractionDigits= new Integer(spec.getFractionDigit());
			if (spec.getRange()!=null) restrictionsList.add(new RangeRestriction(spec.getRange()));
		}
		
	}
	
	public ValueCheck check(String value){
		ValueCheck result = super.check(value);
		Long decimalValue = new Long(value);
		double mustBeInteger = decimalValue*((long) Math.pow(10, fractionDigits));
		if (mustBeInteger!=Math.floor(mustBeInteger)){
			Double fixedValue = Math.floor(mustBeInteger)*Math.pow(10, -fractionDigits);
			result.addUnitCheck(new UnitValueCheck("Too many digits (max = "+fractionDigits+")",fixedValue.toString()));
		}
		return result;
	}

	@Override
	public String getName() {
		return "Decimal64";
	}

}
