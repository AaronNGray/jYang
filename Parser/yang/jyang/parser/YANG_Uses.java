package jyang.parser;

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


public class YANG_Uses extends YANG_DataDefInfoWhen implements YANG_CaseDef {

	private String uses = null;
	private Vector<YANG_Refinement> refinements = new Vector<YANG_Refinement>();
	private Vector<YANG_UsesAugment> usesaugments = new Vector<YANG_UsesAugment>();

	private YANG_Grouping grouping = null;

	public YANG_Grouping getGrouping() {
		return grouping;
	}

	public void setGrouping(YANG_Grouping g) {
		this.grouping = g;
	}

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

	public boolean isBracked() {
		return super.isBracked() || refinements.size() != 0
				|| usesaugments.size() != 0;
	}

	public void addRefinement(YANG_Refinement r) throws YangParserException {

		refinements.add(r);
	}

	public Vector<YANG_Refinement> getRefinements() {
		return refinements;
	}

	public void addUsesAugment(YANG_UsesAugment ua) {
		usesaugments.add(ua);
	}
	
	private boolean checked = false;
	    
	public void check(YangContext context) throws YangParserException {
		if (checked)
			return;
		if (!context.isGroupingDefined(this)) {
			System.err
					.println(context.getSpec().getName() + "@" + getLine()
							+ "." + getCol() + ":grouping " + uses
							+ " cannot be found");
		} else {
			setGrouping(context.getUsedGrouping(this));
			String gping = getGrouping().getGrouping();

			if (YangBuiltInTypes.isBuiltIn(gping))
				System.err.println(context.getSpec().getName() + "@"
						+ getLine() + "." + getCol()
						+ ":a built-in type cannot be used : " + uses);

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
		checked = true;
	}

	public String toString() {
		String result = new String();
		result += "uses " + uses;
		if (isBracked()) {
			result += "{\n";
			result += super.toString() + "\n";
			for (Enumeration<YANG_Refinement> er = refinements.elements(); er
					.hasMoreElements();)
				result += er.nextElement().toString() + "\n";
			for (Enumeration<YANG_UsesAugment> er = usesaugments.elements(); er
					.hasMoreElements();)
				result += er.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";

		return result;
	}

}
