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

	public YANG_RefineCase(int id) {
		super(id);
	}

	public YANG_RefineCase(yang p, int id) {
		super(p, id);
	}



	public void check(YangContext c, YANG_Grouping g) throws YangParserException {}
	
	
	
	public void check(YangContext context, YANG_Choice choice) throws YangParserException {
		boolean found = false;
		YANG_Case ycase = null;
		YANG_ShortCase yscase = null;
		for (Enumeration <YANG_Case> ec = choice.getCases().elements(); ec.hasMoreElements() && !found;){
			ycase = ec.nextElement();
			found = ycase.getCase().compareTo(getRefineNodeId()) == 0;
		}
		for (Enumeration <YANG_ShortCase> esc = choice.getShortCases().elements(); esc.hasMoreElements() && !found;){
			yscase = esc.nextElement();
			found = yscase.getBody().compareTo(getRefineNodeId()) == 0;
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine case " 
					+ getRefineNodeId()
					+ " is not a case of the choice "
					+ choice.getChoice()
					+ " at line " 
					+ choice.getLine()
					+ " " + usedgrouping);
		
		
	}

	public String toString() {
		String result = "";
		result += super.toString() + "\n";
		return result;
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub
		
	}

}
