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

import java.util.*;

public class YANG_Case extends YANG_DataDef {

	private String ycase = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_CaseDef> casedatadefs = new Vector<YANG_CaseDef>();

	private boolean bracked = false;

	private boolean b_status = false, b_description = false,
			b_reference = false;

	public YANG_Case(int id) {
		super(id);
	}

	public YANG_Case(yang p, int id) {
		super(p, id);
	}

	public void setCase(String c) {
		ycase = c;
	}

	public String getCase() {
		return ycase;
	}

	public String getBody() {
		return getCase();
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in case "
					+ ycase, s.getLine(), s.getCol());
		b_status = true;
		status = s;
		bracked = true;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in case " + ycase,
					d.getLine(), d.getCol());
		b_description = true;
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException("Reference already defined in case "
					+ ycase, r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addCaseDef(YANG_CaseDef c) {
		casedatadefs.add(c);
		bracked = true;
	}

	public Vector<YANG_CaseDef> getCaseDefs() {
		return casedatadefs;
	}

	public boolean isBracked() {
		return true;
	}

	public String toString() {
		String result = new String();
		result += "case " + ycase;
		if (bracked) {
			result += " {\n";
			if (status != null)
				result += status.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			for (Enumeration<YANG_CaseDef> ec = casedatadefs.elements(); ec
					.hasMoreElements();)
				result += ec.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		
		
	}

}
