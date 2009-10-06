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


public class YANG_Choice extends YANG_DataDefConfig {

	private String choice = null;
	private YANG_Default ydefault = null;
	private YANG_Mandatory mandatory = null;
	private Vector<YANG_ShortCase> shorts = new Vector<YANG_ShortCase>();
	private Vector<YANG_Case> cases = new Vector<YANG_Case>();

	private boolean b_default = false,  b_mandatory = false;

	public YANG_Choice(int id) {
		super(id);
	}

	public YANG_Choice(yang p, int id) {
		super(p, id);
	}

	public void setChoice(String c) {
		choice = c;
	}

	public String getBody() {
		return getChoice();
	}

	public String getChoice() {
		return choice;
	}

	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException("Default already defined in Choice "
					+ choice, d.getLine(), d.getCol());
		b_default = true;
		ydefault = d;
	}

	public YANG_Default getDefault() {
		return ydefault;
	}
	
	
	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException(
					"Mandatory already defined in Choice " + choice, m
							.getLine(), m.getCol());
		b_mandatory = true;
		mandatory = m;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	
	public void addShortCase(YANG_ShortCase s) throws YangParserException {

		shorts.add(s);
	}

	public Vector<YANG_ShortCase> getShortCases() {
		return shorts;
	}

	public void addCase(YANG_Case c) {
		cases.add(c);
	}

	public Vector<YANG_Case> getCases() {
		return cases;
	}

	public boolean isBracked() {
		return super.isBracked() || b_default || b_mandatory || cases.size() != 0 || shorts.size() != 0;
	}

	private void trackMandatory(YANG_Case c) throws YangParserException {
		for (Enumeration<YANG_CaseDef> ecd = c.getCaseDefs().elements(); ecd
				.hasMoreElements();) {
			YANG_CaseDef cdef = ecd.nextElement();
			if (cdef instanceof YANG_AnyXml) {
				try {
					checkMandatory((YANG_AnyXml) cdef);
				} catch (YangParserException ye) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":no mandatory anyxml in default case ");
				}
			} else if (cdef instanceof YANG_Leaf) {
				try {
					checkMandatory((YANG_Leaf) cdef);
				} catch (YangParserException ye) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":no mandatory leaf in default case ");
				}

			} else if (cdef instanceof YANG_Augment) {
				YANG_Augment augment = (YANG_Augment) cdef;
				trackMandatory(augment.getDataDefs());
			} else if (cdef instanceof YANG_Container) {
				YANG_Container container = (YANG_Container) cdef;
				trackMandatory(container.getDataDefs());
			} else if (cdef instanceof YANG_List) {
				YANG_List list = (YANG_List) cdef;
				trackMandatory(list.getDataDefs());
			}
		}
	}

	private void trackMandatory(YANG_ShortCase scase)
			throws YangParserException {
		if (scase instanceof YANG_AnyXml) {
			try {
				checkMandatory((YANG_AnyXml) scase);
			} catch (YangParserException ye) {
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":no mandatory anyxml in default case of choice "
						+ getBody());
			}
		} else if (scase instanceof YANG_Leaf) {
			try {
				checkMandatory((YANG_Leaf) scase);
			} catch (YangParserException ye) {
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":no mandatory leaf in default case of choice "
						+ getBody());
			}
		} else if (scase instanceof YANG_Container) {
			trackMandatory(((YANG_Container) scase).getDataDefs());
		} else if (scase instanceof YANG_List) {
			trackMandatory(((YANG_List) scase).getDataDefs());
		}

	}

	private void trackMandatory(Vector<YANG_DataDef> ddefs)
			throws YangParserException {
		for (Enumeration<YANG_DataDef> edd = ddefs.elements(); edd
				.hasMoreElements();) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_AnyXml)
				checkMandatory((YANG_AnyXml) ddef);
			else if (ddef instanceof YANG_Augment)
				trackMandatory(((YANG_Augment) ddef).getDataDefs());
			else if (ddef instanceof YANG_Choice) {
				YANG_Choice choice = (YANG_Choice) ddef;
				if (choice.getMandatory() != null)
					if (YangBuiltInTypes.removeQuotesAndTrim(
							choice.getMandatory().getMandatory()).compareTo(
							"true") == 0)
						throw new YangParserException(
								"@"
										+ getLine()
										+ "."
										+ getCol()
										+ ":no mandatory choice in default case of choice "
										+ getBody());
				for (Enumeration<YANG_Case> ec = choice.getCases().elements(); ec
						.hasMoreElements();)
					trackMandatory(ec.nextElement());
				for (Enumeration<YANG_ShortCase> esc = choice.getShortCases()
						.elements(); esc.hasMoreElements();)
					trackMandatory(esc.nextElement());

			} else if (ddef instanceof YANG_Container)
				trackMandatory(((YANG_Container) ddef).getDataDefs());
			else if (ddef instanceof YANG_Leaf)
				checkMandatory((YANG_Leaf) ddef);
			else if (ddef instanceof YANG_List)
				trackMandatory(((YANG_List) ddef).getDataDefs());
		}
	}

	private void checkMandatory(YANG_AnyXml ax) throws YangParserException {
		if (ax.getMandatory() != null)
			if (YangBuiltInTypes.removeQuotesAndTrim(
					ax.getMandatory().getMandatory()).compareTo("true") == 0)
				throw new YangParserException("");
	}

	private void checkMandatory(YANG_Leaf leaf) throws YangParserException {
		if (leaf.getMandatory() != null)
			if (YangBuiltInTypes.removeQuotesAndTrim(
					leaf.getMandatory().getMandatory()).compareTo("true") == 0)
				throw new YangParserException("");

	}

	public void checkDefault(YangContext context, YANG_Default ydefault)
			throws YangParserException {
		if (ydefault == null)
			return;
		String defval = YangBuiltInTypes.removeQuotesAndTrim(ydefault
				.getDefault());
		boolean found = false;
		for (Enumeration<YANG_Case> ec = cases.elements(); ec.hasMoreElements()
				&& !found;) {
			YANG_Case ycase = ec.nextElement();
			String casename = YangBuiltInTypes.removeQuotesAndTrim(ycase
					.getCase());
			found = casename.compareTo(defval) == 0;
			if (found)
				trackMandatory(ycase);
		}
		for (Enumeration<YANG_ShortCase> ec = shorts.elements(); ec
				.hasMoreElements()
				&& !found;) {
			YANG_ShortCase scase = ec.nextElement();
			String casename = YangBuiltInTypes.removeQuotesAndTrim(scase
					.getBody());
			found = casename.compareTo(defval) == 0;
			if (found)
				trackMandatory(scase);
		}
		if (!found)
			throw new YangParserException("@" + ydefault.getLine() + "."
					+ ydefault.getCol() + ":default case " + defval
					+ " is not a case of choice " + getChoice());
	}

	public void check(YangContext context) throws YangParserException {
		
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					getConfig().getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		

		if (b_default)
			checkDefault(context, getDefault());

		Vector<String> caseids = new Vector<String>();

		for (Enumeration<YANG_Case> ec = cases.elements(); ec.hasMoreElements();) {
			YANG_Case ycase = ec.nextElement();

			if (caseids.contains(ycase.getCase()))
				throw new YangParserException("@" + ycase.getLine() + "."
						+ ycase.getCol() + ":case " + ycase.getCase()
						+ " already defined");
			else
				caseids.add(ycase.getCase());
		}
		
	}

	public String toString() {
		String result = new String();
		result += "choice " + choice;
		if (isBracked()) {
			result += " {\n";
			if (ydefault != null)
				result += ydefault.toString() + "\n";
			if (mandatory != null)
				result += mandatory.toString() + "\n";
			result += super.toString();
			for (Enumeration<YANG_ShortCase> es = shorts.elements(); es
					.hasMoreElements();)
				result += es.nextElement().toString() + "\n";
			for (Enumeration<YANG_Case> ec = cases.elements(); ec
					.hasMoreElements();)
				result += ec.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

}
