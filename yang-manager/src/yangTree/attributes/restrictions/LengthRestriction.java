package yangTree.attributes.restrictions;

import java.io.Serializable;
import java.util.LinkedList;

import jyang.parser.YANG_Length;

import yangTree.attributes.UnitValueCheck;

public class LengthRestriction extends Restriction {
	
protected LinkedList<Range> rangesList = new LinkedList<Range>(); 
	
	public LengthRestriction(YANG_Length length){
		String[][] ranges = length.getLengthIntervals();
		for (int i=0;i<ranges.length;i++){
			rangesList.add(new Range(ranges[i]));
		}
	}

	@Override
	public UnitValueCheck check(String value) {
		Double length = new Double(value.length());
		boolean inRange = false;
		for (Range range : rangesList){
			if (range.isInRange(length)) inRange=true;
		}
		if (inRange){
			return UnitValueCheck.checkOK();
		} else {
			return new UnitValueCheck("This value length (i.e. number of characters) is not permitted");
		}
	}
	
	public String getDescription(){
		String description = "Value length must be in range : "+rangesList.getFirst();
		for (int i=1;i<rangesList.size();i++){
			description += " or "+rangesList.get(i);
		}
		return description;
	}

}
