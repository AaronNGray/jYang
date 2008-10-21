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

public class YANG_Pattern extends SimpleNode {

	private String pattern = null;
	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	public YANG_Pattern(int id) {
		super(id);
	}

	public YANG_Pattern(yang p, int id) {
		super(p, id);
	}

	public void setPattern(String p) {
		pattern = p;
	}

	public String getPattern() {
		return pattern;
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
		result += "pattern " + pattern;
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

}
