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



public class YANG_Leaf extends YANG_DataDefFullInfo implements YANG_CaseDef,
		YANG_ShortCase {

	private String leaf = null;
	private YANG_Type type = null;
	private YANG_Units units = null;
	private YANG_Default ydefault = null;

	private boolean b_type = false, b_units = false, b_default = false;

	public YANG_Leaf(int id) {
		super(id);
	}

	public YANG_Leaf(yang p, int id) {
		super(p, id);
	}

	public void setLeaf(String l) {
		leaf = l;
	}

	public String getBody() {
		return getLeaf();
	}

	public String getLeaf() {
		return leaf;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (b_type)
			throw new YangParserException("Type already defined in leaf "
					+ leaf, t.getLine(), t.getCol());
		b_type = true;
		type = t;
	}

	public YANG_Type getType() {
		return type;
	}

	public void setUnits(YANG_Units u) throws YangParserException {
		if (b_units)
			throw new YangParserException("Unit already defined in leaf "
					+ leaf, u.getLine(), u.getCol());
		b_units = true;
		units = u;
	}

	public YANG_Units getUnits() {
		return units;
	}

	
	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException("Default already defined in leaf "
					+ leaf, d.getLine(), d.getCol());
		b_default = true;
		ydefault = d;
	}

	public YANG_Default getDefault() {
		return ydefault;
	}
	
	public boolean isBracked(){
		return super.isBracked() || b_default || b_type || b_units;
	}

	

	public void check(YangContext context) throws YangParserException {
		if (!b_type)
			throw new YangParserException("Type statement not present in leaf "
					+ leaf, getLine(), getCol());
		
		getType().check(context);
		
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					getConfig().getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
		if (b_mandatory)
			if (getMandatory().getMandatory().compareTo("true") == 0
					&& b_default)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":no default value permitted when mandatory is true");
		if (b_default)
			getDefault().check(context, getType());

		else {
			YANG_TypeDef defining = context.getBaseType(getType());
			while (defining != null) {
				if (defining.getDefault() != null) {
					try {
						getType().checkValue(context,
								defining.getDefault().getDefault());
						defining = null;
					} catch (YangParserException ye) {
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":default value "
								+ defining.getDefault().getDefault()
								+ " does no more match with current leaf " + getLeaf());
					}
				} else {
					defining = context.getBaseTypeDef(defining);
				}
			}
		}

	}

	public String toString() {
		String result = new String();
		result += "leaf " + leaf + "{\n";
		if (type != null)
			result += type.toString() + "\n";
		if (units != null)
			result += units.toString() + "\n";
		if (ydefault != null)
			result += ydefault.toString() + "\n";
		result += super.toString() + "\n";
		result += "}";

		return result;
	}
	
	public YANG_Leaf clone() {
		YANG_Leaf cl = new YANG_Leaf(parser, id);
		cl.setLeaf(getLeaf());
		
		try {
			cl.setType(getType());
			cl.setUnits(getUnits());
		} catch (YangParserException e) {
			e.printStackTrace();
		}
		return cl;
	}

}
