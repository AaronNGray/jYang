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
import java.util.*;

public class YANG_Uses extends YANG_DataDef implements YANG_CaseDef {

	private String uses = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_Refinement> refinements = new Vector<YANG_Refinement>();

	private boolean bracked = false;

	private boolean b_status = false, b_description = false,
			b_reference = false;

	public YANG_Uses(int id) {
		super(id);
	}

	public YANG_Uses(yang p, int id) {
		super(p, id);
	}

	public void setUses(String u) {
		uses = u;
	}

	public String getBody() {
		return getUses();
	}

	public String getUses() {
		return uses;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in uses "
					+ uses, s.getLine(), s.getCol());
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
					"Description already defined in uses " + uses, d.getLine(),
					d.getCol());
		b_description = true;
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException("Reference already defined in uses "
					+ uses, r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addRefinement(YANG_Refinement r) throws YangParserException {

		refinements.add(r);
		bracked = true;
	}

	public Vector<YANG_Refinement> getRefinements() {
		return refinements;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context) throws YangParserException {
		YANG_Grouping grouping = context.getUsedGrouping(this);
		String gping = grouping.getGrouping();

		if (YangBuiltInTypes.isBuiltIn(gping))
			System.err.println(context.getSpec().getName() + "@" + getLine()
					+ "." + getCol() + ":a built-in type cannot be used : "
					+ uses);
		else {
			if (!context.isGroupingDefined(this)) {
				System.err.println(context.getSpec().getName() + "@"
						+ getLine() + "." + getCol() + ":grouping " + uses
						+ " cannot be found");
			}
		}

		for (Enumeration<YANG_Refinement> er = refinements.elements(); er
				.hasMoreElements();) {
			YANG_Refinement ref = er.nextElement();
			try {
				ref.setUsedGrouping("from the used grouping "
						+ grouping.getGrouping() + " at line "
						+ grouping.getLine());
				ref.setParent(this);
				ref.check(context, grouping);
			} catch (YangParserException ye) {
				System.err.println(context.getModuleSpecName()
						+ ye.getMessage());
			}
		}
	}

	public String toString() {
		String result = new String();
		result += "uses " + uses;
		if (bracked) {
			result += "{\n";
			if (status != null)
				result += status.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			for (Enumeration<YANG_Refinement> er = refinements.elements(); er
					.hasMoreElements();)
				result += er.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";

		return result;
	}

}
