package yangTree.attributes.restrictions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yangTree.attributes.UnitValueCheck;
import jyang.parser.YANG_Length;

@SuppressWarnings("serial")
public class BinaryLengthRestriction extends LengthRestriction {
	
	public BinaryLengthRestriction(YANG_Length length){
		super(length);
	}
	
	@Override
	public UnitValueCheck check(String value){
		Pattern endPattern = Pattern.compile("=");
		Matcher matcher = endPattern.matcher(value);
		int i =0;
		while (matcher.find()){
			i++;
		}
		long bitsLength = (value.length()/4)*3 - i;
		Double length = new Double(bitsLength);
		boolean inRange = false;
		for (Range range : rangesList){
			if (range.isInRange(length)) inRange=true;
		}
		if (inRange){
			return UnitValueCheck.checkOK();
		} else {
			return new UnitValueCheck("This value length (i.e. number of octets) is not permitted.");
		}
	}
	
	@Override
	public String getDescription(){
		String description = "Value number of octets must be in range : "+rangesList.getFirst();
		for (int i=1;i<rangesList.size();i++){
			description += " or "+rangesList.get(i);
		}
		return description;
	}

}
