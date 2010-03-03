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

public class YANG_RefineLeafList extends ListedRefineNode {

	public YANG_RefineLeafList(int id) {
		super(id);
	}

	public YANG_RefineLeafList(yang p, int id) {
		super(p, id);
	}

	public void check(YANG_LeafList leaflist)
			throws YangParserException {
		YangContext context = leaflist.getContext();
		YANG_Config parentConfig = getParentConfig();
		if (parentConfig != null)
			if (b_config) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& config.getConfigStr().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":config to true and parent config to false");
			} else {
				if (leaflist.getConfig() != null) {
					if (parentConfig.getConfigStr().compareTo("false") == 0
							&& leaflist.getConfig().getConfigStr().compareTo(
									"true") == 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol()
								+ ":config to true and parent config to false");

				}
			}
	}

	public void check(YangContext c, YANG_Grouping grouping)
			throws YangParserException {
		boolean found = false;
		YANG_LeafList leaflist = null;
		for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs().elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_LeafList) {
				leaflist = (YANG_LeafList) ddef;
				found = leaflist.getLeafList().compareTo(getRefineNodeId()) == 0;
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine leaf-list " + getRefineNodeId()
					+ " is not in the used grouping " + grouping.getGrouping());

		YANG_Config parentConfig = getParentConfig();
		if (parentConfig != null)
			if (b_config) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& config.getConfigStr().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":config to true and parent config to false");
			} else {
				if (leaflist.getConfig() != null) {
					if (parentConfig.getConfigStr().compareTo("false") == 0
							&& leaflist.getConfig().getConfigStr().compareTo(
									"true") == 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":config to true in the grouping "
								+ grouping.getBody() + " at line "
								+ grouping.getLine()
								+ " but parent config to false");

				}
			}

	}

	public String toString() {
		String result = "";
		result += super.toString() + "\n";
		return result;
	}

}
