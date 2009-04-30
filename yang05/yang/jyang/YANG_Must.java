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

public class YANG_Must extends SimpleNode {

	private String must = null;
	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	private boolean b_errmess = false, b_errapptag = false,
			b_description = false, b_reference = false;

	public YANG_Must(int id) {
		super(id);
	}

	public YANG_Must(yang p, int id) {
		super(p, id);
	}

	public void setMust(String m) {
		must = m;
	}

	public String getMust() {
		return must;
	}

	public void setErrMess(YANG_ErrorMessage e) throws YangParserException {
		if (b_errmess)
			throw new YangParserException(
					"Error message already defined in must" + must, getLine(),
					getCol());
		b_errmess = true;
		bracked = true;
		errmess = e;
	}

	public YANG_ErrorMessage getErrMess() {
		return errmess;
	}

	public void setErrAppTag(YANG_ErrorAppt e) throws YangParserException {
		if (b_errapptag)
			throw new YangParserException(
					"Error App Tag already defined in must" + must, getLine(),
					getCol());
		b_errapptag = true;
		bracked = true;
		errapptag = e;
	}

	public YANG_ErrorAppt getErrAppTag() {
		return errapptag;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException("Description already defined in must"
					+ must, d.getLine(), d.getCol());
		b_description = true;
		bracked = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException("Reference already defined in must"
					+ must, r.getLine(), r.getCol());
		b_reference = true;
		bracked = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		String result = new String();
		result += "must " + must;
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
		} else
			result += ";";

		return result;
	}

}
