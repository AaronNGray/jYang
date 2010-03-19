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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YANG_Unique extends SimpleYangNode {

	private Pattern desc_sch_nid = null;

	private String unique = null;

	public YANG_Unique(int id) {
		super(id);
		try {
			desc_sch_nid = Pattern
					.compile("(([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*((/([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*)+)?(\\s)*)*");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public YANG_Unique(yang p, int id) {
		super(p, id);
	}

	public void setUnique(String u){
		String un = unquote(u);
		Matcher m = desc_sch_nid.matcher(un);
		if (m.matches())
			unique = un;
		else
			YangErrorManager.tadd(filename, getLine(), getCol(), "unique_exp");
	}

	public String getUnique() {
		return unique;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "unique " + unique + ";";
	}

}
