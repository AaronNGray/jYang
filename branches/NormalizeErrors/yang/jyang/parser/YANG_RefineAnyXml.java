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
import java.util.Enumeration;


public class YANG_RefineAnyXml extends MandatoryRefineNode {

	private String refineanyxml = null;

	public YANG_RefineAnyXml(int id) {
		super(id);
	}

	public YANG_RefineAnyXml(yang p, int id) {
		super(p, id);
	}

	public void setRefineAnyXml(String r) {
		refineanyxml = r;
	}

	public String getBody() {
		return getRefineAnyXml();
	}

	public String getRefineAnyXml() {
		return refineanyxml;
	}

	
	public void check(YangContext context, YANG_AnyXml ax) throws YangParserException {
		YANG_Config parentConfig = getParentConfig();
	if (b_config){
		if (parentConfig.getConfigStr().compareTo("false") == 0 &&
				config.getConfigStr().compareTo("true") == 0)
			throw new YangParserException("@" + getLine() + "." + getCol() +
					":config to true and parent config to false");
	}
	else {
		if (ax.getConfig() != null){
			if (parentConfig.getConfigStr().compareTo("false") == 0 &&
					ax.getConfig().getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true but parent config to false");
			
		}
	}
		
	}

	public void check(YangContext context, YANG_Grouping grouping)
			throws YangParserException {
		boolean found = false;
		YANG_AnyXml ax = null;
		for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs().elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_AnyXml) {
				ax = (YANG_AnyXml) ddef;
				found = ax.getAnyXml().compareTo(getRefineAnyXml()) == 0;
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine any-xml " + getRefineAnyXml()
					+ " is not in the used grouping " + grouping.getGrouping());

		YANG_Config parentConfig = getParentConfig();
		if (b_config){
			if (parentConfig.getConfigStr().compareTo("false") == 0 &&
					config.getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (ax.getConfig() != null){
				if (parentConfig.getConfigStr().compareTo("false") == 0 &&
						ax.getConfig().getConfigStr().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "." + getCol() +
							":config to true in the grouping " +
							grouping.getBody() + " at line " + grouping.getLine() +
							"but parent config to false");
				
			}
		}

	}

	public String toString() {
		String result = new String();
		result += "any-xml " + refineanyxml;
		if (isBracked()) {
			result += "{\n";
			result += super.toString();
			result += "}";
		} else
			result += super.toString() + ";";
		return result;
	}

}
