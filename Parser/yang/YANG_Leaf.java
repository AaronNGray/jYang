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

public class YANG_Leaf extends YANG_DataDef implements YANG_CaseDef,
		YANG_ShortCase {

	private String leaf = null;
	private YANG_Type type = null;
	private YANG_Units units = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Default ydefault = null;
	private YANG_Config config = null;
	private YANG_Mandatory mandatory = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean b_type = false, b_units = false, b_default = false,
			b_config = false, b_mandatory = false, b_status = false,
			b_description = false, b_reference = false;

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

	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
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

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException("Config already defined in leaf "
					+ leaf, c.getLine(), c.getCol());
		b_config = true;
		config = c;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException("Mandatory already defined in leaf "
					+ leaf, m.getLine(), m.getCol());
		b_mandatory = true;
		mandatory = m;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in leaf "
					+ leaf, s.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in leaf " + leaf, d.getLine(),
					d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException("Reference already defined in leaf "
					+ leaf, r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void check(YangContext context) throws YangParserException {
		if (!b_type)
			throw new YangParserException("Type statement not present in leaf "
					+ leaf, getLine(), getCol());
		
		getType().check(context);
		
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
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
		for (Enumeration<YANG_Must> em = musts.elements(); em.hasMoreElements();)
			result += em.nextElement().toString() + "\n";
		if (ydefault != null)
			result += ydefault.toString() + "\n";
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
		result += "}";

		return result;
	}

}
