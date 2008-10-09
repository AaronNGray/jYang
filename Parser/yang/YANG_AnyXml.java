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


public class YANG_AnyXml extends  YANG_DataDef implements
		YANG_CaseDef, YANG_ShortCase {

	private String anyxml = null;
	private YANG_Config config = null;
	private YANG_Mandatory mandatory = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	private boolean b_config = false, b_mandatory = false, b_status = false,
			b_description = false, b_reference = false;

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

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException("Config already defined in any-xml "
					+ anyxml, c.getLine(), c.getCol());
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
					"Mandatory already defined in any-xml " + anyxml, m
							.getLine(), m.getCol());
		b_mandatory = true;
		mandatory = m;
		bracked = true;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in any-xml "
					+ anyxml, s.getLine(), s.getCol());
		b_status = true;
		status = s;
		bracked = true;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in any-xml " + anyxml, d
							.getLine(), d.getCol());
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
					"Reference already defined in any-xml " + anyxml, r
							.getLine(), r.getCol());
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

	public void check(YangContext context) throws YangParserException{
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "anyxml " + anyxml;
		if (bracked) {
			result += " {\n";
			if (config != null)
				result += config.toString() + "\n";
			if (mandatory != null)
				result += mandatory.toString() + "\n";
			if (status != null)
				result += status.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			result += "\n}";
		} else
			result += ";";

		return result;
	}
}
