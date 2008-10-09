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

public class YANG_TypeDef extends YANG_Body {

	private String typedef = null;
	private YANG_Type ytype = null;
	private YANG_Units units = null;
	private YANG_Default defaultstr = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean b_type = false, b_units = false, b_default = false,
			b_status = false, b_description = false, b_reference = false;

	public YANG_TypeDef(int id) {
		super(id);
	}

	public YANG_TypeDef(yang p, int id) {
		super(p, id);
	}

	public void setTypedef(String t) {
		typedef = t;
	}

	public String getBody() {
		return getTypeDef();
	}

	public String getTypeDef() {
		return typedef;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (b_type)
			throw new YangParserException("Type already defined in typedef "
					+ typedef, t.getLine(), t.getCol());
		b_type = true;
		ytype = t;
	}

	public YANG_Type getType() {
		return ytype;
	}

	public void setUnits(YANG_Units u) throws YangParserException {
		if (b_units)
			throw new YangParserException("Units already defined in typedef "
					+ typedef, u.getLine(), u.getCol());
		b_units = true;
		units = u;
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in typedef "
					+ typedef, s.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in typedef " + typedef, d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException("Default already defined in typedef "
					+ typedef, d.getLine(), d.getCol());
		b_default = true;
		defaultstr = d;
	}

	public YANG_Default getDefault() {
		return defaultstr;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in typedef " + typedef, r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void check(YangContext context) throws YangParserException {
		if (!b_type)
			throw new YangParserException("Type statement expected in typedef "
					+ typedef, getLine(), getCol());
		
		getType().check(context);
		if(b_default)
			getDefault().check(context, getType());
		
		else {
			YANG_TypeDef defining = context.getBaseTypeDef(this);
			
			while(defining != null){
				if (defining.getDefault() != null){
					try {
						getType().checkValue(context, defining.getDefault().getDefault());
						defining = null;
					} catch (YangParserException ye){
						throw new YangParserException("@" + getLine() + "." + getCol()
								+ ":default value " +defining.getDefault().getDefault()+
								" does no more match with current typedef "+ getTypeDef());
					}
				} else 
					defining = context.getBaseTypeDef(defining);
			}
		}
			
	}

	public String toString() {
		String result = new String();
		result += "typedef " + typedef + "{\n";
		if (ytype != null)
			result += ytype.toString() + "\n";
		if (units != null)
			result += units.toString() + "\n";
		if (defaultstr != null)
			result += defaultstr.toString() + "\n";
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
