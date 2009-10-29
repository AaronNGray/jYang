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
import java.text.MessageFormat;
import java.util.*;

public class YANG_RefineChoice extends MandatoryRefineNode {

	private YANG_Default ydefault = null;

	private boolean b_default = false;

	public YANG_RefineChoice(int id) {
		super(id);
	}

	public YANG_RefineChoice(yang p, int id) {
		super(p, id);
	}


	public void setDefault(YANG_Default d) throws YangParserException {
		if (!b_default) {
			b_default = true;
			ydefault = d;
		} else
			YangErrorManager
			.add(filename, d.getLine(), d.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("unex_kw"),
					"default"));
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	public void check(YangContext context, YANG_Choice choice, String ug)
			throws YangParserException {
		if (getDefault() != null)
			getDefault().check(context, choice);
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
				found = choice.getChoice().compareTo(getRefineNodeId()) == 0;
				if (found)
					check(context, choice, usedgrouping);
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine choice " + getRefineNodeId() + usedgrouping);
		// + " is not in the used grouping " + grouping.getGrouping()
		// + " at line " + grouping.getLine());

		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfigStr().compareTo("false") == 0
					&& config.getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (choice.getConfig() != null) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& choice.getConfig().getConfigStr().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":config to true in the grouping "
							+ grouping.getBody() + " at line "
							+ grouping.getLine() + "but parent config to false");

			}
		}
		/*
		 * for (Enumeration<YANG_RefineCase> er = getRefineCases().elements();
		 * er .hasMoreElements();) { YANG_RefineCase rcase = er.nextElement();
		 * try { rcase.setUsedGrouping(" from the used grouping " +
		 * grouping.getGrouping() + " at line " + grouping.getLine());
		 * rcase.setParent(this); rcase.check(context, choice); } catch
		 * (YangParserException ye) { throw new
		 * YangParserException(ye.getMessage() + usedgrouping); // +
		 * " from the used grouping " + grouping.getGrouping() // + " at line "
		 * + grouping.getLine()); } }
		 */
	}

	public String toString() {
		String result = "";
		if (b_default)
			result += ydefault.toString() + "\n";
		result += super.toString() + "\n";

		return result;
	}

}
