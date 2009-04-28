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


public class YANG_AnyXml extends  YANG_DataDefFullInfo implements
		YANG_CaseDef, YANG_ShortCase {

	private String anyxml = null;

	public YANG_AnyXml(int id) {
		super(id);
	}

	public YANG_AnyXml(yang p, int id) {
		super(p, id);
	}

	public void setAnyXml(String a) {
		anyxml = a;
	}

	public String getBody() {
		return getAnyXml();
	}

	public String getAnyXml() {
		return anyxml;
	}

	
	

	public boolean isBracked() {
		return super.isBracked();
	}

	public void check(YangContext context) throws YangParserException{
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					getConfig().getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "anyxml " + anyxml;
		if (isBracked()) {
			result += " {\n";
			result += super.toString();
			result += "\n}";
		} else
			result += ";";

		return result;
	}
}
