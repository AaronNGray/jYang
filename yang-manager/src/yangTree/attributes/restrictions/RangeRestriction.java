package yangTree.attributes.restrictions;

import java.util.LinkedList;

import jyang.parser.YANG_Range;
import yangTree.attributes.UnitValueCheck;

public class RangeRestriction extends Restriction {
	
	private LinkedList<Range> rangesList = new LinkedList<Range>(); 
	
	public RangeRestriction(YANG_Range range){
		errorMessage = range.getErrMess().getErrorMessage();
		String[][] ranges = range.getRangeIntervals();
		for (int i=0;i<ranges.length;i++){
			rangesList.add(new Range(ranges[i]));
		}
	}

	@Override
	public UnitValueCheck check(String value) {
		
		Double decimalValue = new Double(value);
		for (Range range : rangesList){
			if (!range.isInRange(decimalValue)) return new UnitValueCheck("Value is not in range "+range);
		}
		return UnitValueCheck.checkOK();
	}
	
	public String getDescription(){
		String description = "Value must be in range : "+rangesList.getFirst();
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
