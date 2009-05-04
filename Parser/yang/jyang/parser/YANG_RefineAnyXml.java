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


public class YANG_RefineAnyXml extends YANG_Refinement {

	private String refineanyxml = null;
	private YANG_Config config = null;
	private YANG_Mandatory mandatory = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	private boolean b_config = false, b_mandatory = false,
			b_description = false, b_reference = false;

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

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in refine any-xml " + refineanyxml,
					c.getLine(), c.getCol());
		b_config = true;
		config = c;
		bracked = true;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException(
					"Mandatory already defined in refine any-xml "
							+ refineanyxml, m.getLine(), m.getCol());
		b_mandatory = true;
		mandatory = m;
		bracked = true;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in refine any-xml "
							+ refineanyxml, d.getLine(), d.getCol());
		b_description = true;
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in refine any-xml "
							+ refineanyxml, r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public boolean isBracked() {
		return bracked;
	}
	
	public void check(YangContext context, YANG_AnyXml ax) throws YangParserException {
		YANG_Config parentConfig = getParentConfig();
	if (b_config){
		if (parentConfig.getConfig().compareTo("false") == 0 &&
				config.getConfig().compareTo("true") == 0)
			throw new YangParserException("@" + getLine() + "." + getCol() +
					":config to true and parent config to false");
	}
	else {
		if (ax.getConfig() != null){
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					ax.getConfig().getConfig().compareTo("true") == 0)
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
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (ax.getConfig() != null){
				if (parentConfig.getConfig().compareTo("false") == 0 &&
						ax.getConfig().getConfig().compareTo("true") == 0)
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
		if (bracked) {
			result += "{\n";
			if (config != null)
				result += config.toString() + "\n";
			if (mandatory != null)
				result += mandatory.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
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