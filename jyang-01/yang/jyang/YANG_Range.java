package jyang;
/*
 * Copyright 2008 Emmanuel Nataf, Olivier Festor
 * 
 * This file is part of jyang.

    jyang is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jyang is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with jyang.  If not, see <http://www.gnu.org/licenses/>.

 */

import java.util.regex.PatternSyntaxException;


public class YANG_Range extends SimpleNode implements YANG_NumericalRestriction {

	private String range = null;
	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private String[][] ranges = null;

	private boolean bracked = false;

	public YANG_Range(int id) {
		super(id);
	}

	public YANG_Range(yang p, int id) {
		super(p, id);
	}

	public void setRange(String r) throws YangParserException {
		range = r;
		setRanges();
	}

	public String getRange() {
		return range;
	}

	public void setErrMess(YANG_ErrorMessage e) {
		errmess = e;
		bracked = true;
	}

	public YANG_ErrorMessage getErrMess() {
		return errmess;
	}

	public void setErrAppTag(YANG_ErrorAppt e) {
		errapptag = e;
		bracked = true;
	}

	public YANG_ErrorAppt getErrAppTag() {
		return errapptag;
	}

	public void setDescription(YANG_Description d) {
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) {
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public boolean isBracked() {
		return bracked;
	}

	public String toString() {
		String result = new String();
		result += "range " + range;
		if (bracked) {
			result += "{\n";
			if (errmess != null)
				result += errmess + "\n";
			if (errapptag != null)
				result += errapptag + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

	private void setRanges() throws YangParserException {

		String range = YangBuiltInTypes.removeQuotes(getRange());
		String[] test = null;
		try {
			test = range.split("\\|");
		} catch (PatternSyntaxException pe) {
			// Cannot occurs
		}

		ranges = new String[test.length][2];

		for (int j = 0; j < test.length; j++) {
			if (test[j].indexOf("..") == -1) {
				ranges[j][0] = test[j];
				ranges[j][1] = test[j];
			} else {
				try {
					ranges[j] = test[j].split("\\.\\.");
					if (ranges[j].length != 2) {
						throw new YangParserException("@" + getLine() + ":"
								+ getCol() + ":bad range expression " + test[j]);
					}
				} catch (PatternSyntaxException pe) {
					// Cannot occurs
				}
			}
		}
		for (int i = 0; i < ranges.length; i++) {
			ranges[i][0] = ranges[i][0].trim();
			ranges[i][1] = ranges[i][1].trim();
		}

	}

	public String[][] getRangeIntervals() {
		return ranges;
	}
}
