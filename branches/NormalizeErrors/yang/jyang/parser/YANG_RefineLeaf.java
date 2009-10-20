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

public class YANG_RefineLeaf extends MustRefineNode {

	private YANG_Default ydefault = null;
	private YANG_Mandatory mandatory = null;

	private boolean b_default = false, b_mandatory = false;

	public YANG_RefineLeaf(int id) {
		super(id);
	}

	public YANG_RefineLeaf(yang p, int id) {
		super(p, id);
	}


	public void setDefault(YANG_Default d) {
		if (!b_default) {
			b_default = true;
			ydefault = d;
		} else
			YangErrorManager.add(d.getLine(), d.getCol(),
					YangErrorManager.messages.getString("default"));
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	public void setMandatory(YANG_Mandatory m) {
		if (!b_mandatory) {
			b_mandatory = true;
			mandatory = m;
		} else
			YangErrorManager.add(m.getLine(), m.getCol(),
					YangErrorManager.messages.getString("mandatory"));
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}


	public void check(YangContext context, YANG_Leaf leaf)
			throws YangParserException {
		if (getDefault() != null)
			getDefault().check(context, leaf.getType());

		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfigStr().compareTo("false") == 0
					&& config.getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (leaf.getConfig() != null) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& leaf.getConfig().getConfigStr().compareTo("true") == 0)
					throw new YangParserException(
							"@"
									+ getLine()
									+ "."
									+ getCol()
									+ ":config to true in the grouping  but parent config to false");

			}
		}

	}

	public void check(YangContext context, YANG_Grouping grouping)
			throws YangParserException {
		try {
			check(context, grouping.getDataDefs());
		} catch (YangParserException ye) {
			throw new YangParserException(ye.getMessage() + usedgrouping);
		}
	}

	public void check(YangContext context, Vector<YANG_DataDef> vddef)
			throws YangParserException {
		boolean found = false;
		YANG_Leaf leaf = null;
		for (Enumeration<YANG_DataDef> edd = vddef.elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_Leaf) {
				leaf = (YANG_Leaf) ddef;
				found = leaf.getLeaf().compareTo(getRefineNodeId()) == 0;
				if (found)
					check(context, leaf);
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine leaf " + getRefineNodeId() + " is not a leaf ");

		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfigStr().compareTo("false") == 0
					&& config.getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (leaf.getConfig() != null) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& leaf.getConfig().getConfigStr().compareTo("true") == 0)
					throw new YangParserException(
							"@"
									+ getLine()
									+ "."
									+ getCol()
									+ ":config to true in the grouping  but parent config to false");

			}
		}

	}

	public String toString() {
		String result = "";
		if (b_default)
			result += ydefault.toString() + "\n";
		if (b_mandatory)
			result += mandatory.toString() + "\n";
		result += super.toString() + "\n";
		return result;
	}

}