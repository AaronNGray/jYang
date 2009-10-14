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


public class YANG_RefineCase extends YANG_Refine {

	private String refinecase = null;
	
	private Vector<YANG_Refine> refinements = new Vector<YANG_Refine>();

	private boolean bracked = false;

	public YANG_RefineCase(int id) {
		super(id);
	}

	public YANG_RefineCase(yang p, int id) {
		super(p, id);
	}

	public void setRefineCase(String c) {
		refinecase = c;
	}

	public String getBody() {
		return getRefineCase();

	}

	public String getRefineCase() {
		return refinecase;
	}


	public void addRefinement(YANG_Refine r) {
		refinements.add(r);
		bracked = true;
	}

	public Vector<YANG_Refine> getRefinements() {
		return refinements;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext c, YANG_Grouping g) throws YangParserException {}
	
	
	
	public void check(YangContext context, YANG_Choice choice) throws YangParserException {
		boolean found = false;
		YANG_Case ycase = null;
		YANG_ShortCase yscase = null;
		for (Enumeration <YANG_Case> ec = choice.getCases().elements(); ec.hasMoreElements() && !found;){
			ycase = ec.nextElement();
			found = ycase.getCase().compareTo(getRefineCase()) == 0;
		}
		for (Enumeration <YANG_ShortCase> esc = choice.getShortCases().elements(); esc.hasMoreElements() && !found;){
			yscase = esc.nextElement();
			found = yscase.getBody().compareTo(getRefineCase()) == 0;
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine case " 
					+ getRefineCase()
					+ " is not a case of the choice "
					+ choice.getChoice()
					+ " at line " 
					+ choice.getLine()
					+ " " + usedgrouping);
		
		for (Enumeration<YANG_Refine> er = getRefinements().elements(); er.hasMoreElements();){
			YANG_Refine ref = er.nextElement();
			ref.setUsedGrouping(usedgrouping);
			ref.setParent(this);
			if (ycase != null)
				ref.check(context, ycase);
			else if (yscase != null)
				ref.check(context, yscase);
		}
	}

	public String toString() {
		String result = new String();
		result += "case " + refinecase;
		if (bracked) {
			result += "{\n";
			for (Enumeration<YANG_Refine> er = refinements.elements(); er
					.hasMoreElements();)
				result += er.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub
		
	}

}
