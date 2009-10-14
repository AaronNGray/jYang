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



public class YANG_LeafList extends  ListedDataDef{

	private String leaflist = null;
	private YANG_Type type = null;
	private YANG_Units units = null;

	private boolean b_type = false, b_units = false;

	public YANG_LeafList(int id) {
		super(id);
	}

	public YANG_LeafList(yang p, int id) {
		super(p, id);
	}

	public void setLeafList(String l) {
		leaflist = l;
	}

	public String getBody() {
		return getLeafList();
	}

	public String getLeafList() {
		return leaflist;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (b_type)
			throw new YangParserException("Type already defined in leaf-list "
					+ leaflist, t.getLine(), t.getCol());
		b_type = true;
		type = t;
	}

	public YANG_Type getType() {
		return type;
	}

	public void setUnits(YANG_Units u) throws YangParserException {
		if (b_units)
			throw new YangParserException("Units already defined in leaf-list "
					+ leaflist, u.getLine(), u.getCol());
		b_units = true;
		units = u;
	}

	public YANG_Units getUnits() {
		return units;
	}


	public boolean isBracked(){
		return super.isBracked() || b_type || b_units;
	}

	

	public void check(YangContext context) throws YangParserException {
		if (!b_type)
			throw new YangParserException(
					"Type statement not present in leaf-list " + leaflist,
					getLine(), getCol());
		getType().check(context);
		
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfigStr().compareTo("false") == 0 &&
					getConfig().getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "leaf-list " + leaflist + "{\n";
		if (b_type)
			result += type.toString() + "\n";
		if (b_units)
			result += units.toString() + "\n";
		result += super.toString() + "\n";
		result += super.toString() + "\n";
		result += "}\n";

		return result;
	}
}
