package yangTree.attributes.restrictions;

import java.io.Serializable;
import java.util.LinkedList;

import jyang.parser.YANG_Range;
import yangTree.attributes.UnitValueCheck;

public class RangeRestriction extends Restriction {

	private LinkedList<Range> rangesList = new LinkedList<Range>();

	public RangeRestriction(YANG_Range range) {
		String[][] ranges = range.getRangeIntervals();
		for (int i = 0; i < ranges.length; i++) {
			rangesList.add(new Range(ranges[i]));
		}
	}

	@Override
	public UnitValueCheck check(String value) {

		Double decimalValue = new Double(value);
		boolean inRange = false;
		for (Range range : rangesList) {
			if (range.isInRange(decimalValue))
				inRange = true;
		}
		if (inRange) {
			return UnitValueCheck.checkOK();
		} else {
			return new UnitValueCheck(
					"This value is out of the permitted ranges.");
		}
	}

	public String getDescription() {
		String description = "Value must be in range : "
				+ rangesList.getFirst();
		for (int i = 1; i < rangesList.size(); i++) {
			description += " or " + rangesList.get(i);
		}
		return description;
	}

}
