package yangTree.attributes.restrictions;

import java.util.LinkedList;

import jyang.parser.YANG_Length;

import yangTree.attributes.UnitValueCheck;

public class LengthRestriction extends Restriction {
	
private LinkedList<Range> rangesList = new LinkedList<Range>(); 
	
	public LengthRestriction(YANG_Length length){
		String[][] ranges = length.getLengthIntervals();
		for (int i=0;i<ranges.length;i++){
			rangesList.add(new Range(ranges[i]));
		}
	}

	@Override
	public UnitValueCheck check(String value) {
		Double decimalValue = new Double(value);
		boolean inRange = false;
		for (Range range : rangesList){
			if (range.isInRange(decimalValue)) inRange=true;
		}
		if (inRange){
			return UnitValueCheck.checkOK();
		} else {
			return new UnitValueCheck("This value length is not permitted");
		}
	}
	
	public String getDescription(){
		String description = "Value length must be in range : "+rangesList.getFirst();
		for (int i=1;i<rangesList.size();i++){
			description += " or "+rangesList.get(i);
		}
		return description;
	}
	
	private class Range {
		
		private int min;
		private int max;
		
		public Range(String[] minmax){
			this.min = new Integer(minmax[0]);
			this.max = new Integer(minmax[1]);
		}
		
		public boolean isInRange(Double value){
			return value<max && value>min;
		}
		
		public String toString(){
			return "["+min+".."+max+"]";
		}
		
	}

}
