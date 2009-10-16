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



public class YANG_Output extends Io {


	public YANG_Output(int id) {
		super(id);
	}

	public YANG_Output(yang p, int id) {
		super(p, id);
	}

	public void check(YangContext context) throws YangParserException {
		if (datadefs.size() == 0)
			throw new YangParserException(
					"There must be at least one datadef in output "
							+ "\nat line " + getLine() + ", column " + getCol());
		
/*
		for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
				.hasMoreElements();)
			context.addNode(et.nextElement());
		for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
				.hasMoreElements();)
			context.addNode(eg.nextElement());
		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();)
			context.addNode(ed.nextElement());
		for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
				.hasMoreElements();) {
			YangContext cl = context.clone();
			et.nextElement().check(cl);
		}
		for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
				.hasMoreElements();) {
			YangContext cl = context.clone();
			eg.nextElement().checkBody(cl);
		}
		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();) {
			YangContext cl = context.clone();
			ed.nextElement().checkBody(cl);
		}
		*/
		
	}

	public String toString() {
		String result = new String();
		result += "output {\n";
		result += super.toString();
		result += "}";
		return result;
	}

}
