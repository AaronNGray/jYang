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

public class YANG_RefineChoice extends YANG_Refinement {

	private String refinechoice = null;
	private YANG_Default ydefault = null;
	private YANG_Config config = null;
	private YANG_Mandatory mandatory = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_RefineCase> refinecases = new Vector<YANG_RefineCase>();

	private boolean bracked = false;

	private boolean b_default = false, b_config = false, b_mandatory = false,
			b_description = false, b_reference = false;

	public YANG_RefineChoice(int id) {
		super(id);
	}

	public YANG_RefineChoice(yang p, int id) {
		super(p, id);
	}

	public void setRefineChoice(String c) {
		refinechoice = c;
	}

	public String getBody() {
		return getRefineChoice();
	}

	public String getRefineChoice() {
		return refinechoice;
	}

	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException(
					"Default already defined in refin choice " + refinechoice,
					d.getLine(), d.getCol());
		b_default = true;
		ydefault = d;
		bracked = true;
	}

	public YANG_Default getDefault() {
		return ydefault;
	}
	
	public void setConfig(YANG_Config d) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in refin choice " + refinechoice,
					d.getLine(), d.getCol());
		b_config = true;
		config = d;
		bracked = true;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException(
					"Mandatory already defined in refin choice " + refinechoice,
					m.getLine(), m.getCol());
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
					"Description already defined in refin choice "
							+ refinechoice, d.getLine(), d.getCol());
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
					"Reference already defined in refin choice " + refinechoice,
					r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addRefineCase(YANG_RefineCase r) {
		refinecases.add(r);
		bracked = true;
	}

	public Vector<YANG_RefineCase> getRefineCases() {
		return refinecases;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context, YANG_Choice choice, String ug)
			throws YangParserException {
		if (getDefault() != null)
			getDefault().check(context, choice);
		for (Enumeration<YANG_RefineCase> er = getRefineCases().elements(); er
				.hasMoreElements();) {
			YANG_RefineCase rcase = er.nextElement();
			rcase.setUsedGrouping(ug);
			rcase.setParent(this);
			rcase.check(context, choice);
		}
	}

	public void check(YangContext context, YANG_Grouping grouping)
			throws YangParserException {
		boolean found = false;
		YANG_Choice choice = null;
		for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs().elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_Choice) {
				choice = (YANG_Choice) ddef;
				found = choice.getChoice().compareTo(getRefineChoice()) == 0;
				if (found)
					check(context, choice, usedgrouping);
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine choice " + getRefineChoice() + usedgrouping);
//					+ " is not in the used grouping " + grouping.getGrouping()
//					+ " at line " + grouping.getLine());
		
		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfig().compareTo("false") == 0
					&& config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (choice.getConfig() != null) {
				if (parentConfig.getConfig().compareTo("false") == 0
						&& choice.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":config to true in the grouping "
							+ grouping.getBody() + " at line "
							+ grouping.getLine() + "but parent config to false");

			}
		}
		
		for (Enumeration<YANG_RefineCase> er = getRefineCases().elements(); er
				.hasMoreElements();) {
			YANG_RefineCase rcase = er.nextElement();
			try {
				rcase.setUsedGrouping(" from the used grouping "
						+ grouping.getGrouping() + " at line "
						+ grouping.getLine());
				rcase.setParent(this);
				rcase.check(context, choice);
			} catch (YangParserException ye) {
				throw new YangParserException(ye.getMessage() + usedgrouping);
//						+ " from the used grouping " + grouping.getGrouping()
//						+ " at line " + grouping.getLine());
			}
		}
	}

	public String toString() {
		String result = new String();
		result += "choice " + refinechoice;
		if (bracked) {
			result += "{\n";
			if (ydefault != null)
				result += ydefault.toString() + "\n";
			if (b_config)
				result += config.toString() + "\n";
			if (mandatory != null)
				result += mandatory.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			for (Enumeration<YANG_RefineCase> er = refinecases.elements(); er
					.hasMoreElements();)
				result += er.nextElement().toString() + "\n";
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
