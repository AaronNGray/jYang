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

public class YANG_RefineContainer extends MustRefineNode {

	private YANG_Presence presence = null;

	private boolean b_presence = false;

	public YANG_RefineContainer(int id) {
		super(id);
	}

	public YANG_RefineContainer(yang p, int id) {
		super(p, id);
	}


	public void setPresence(YANG_Presence p) {
		if (!b_presence) {
			b_presence = true;
			presence = p;
		} else
			YangErrorManager
			.add(filename, p.getLine(), p.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("unex_kw"),
					"presence"));
	}

	public YANG_Presence getPresence() {
		return presence;
	}

	public void icheck(YangContext context, YANG_Container container, String ug)
			throws YangParserException {
		/*
		 * for (Enumeration<YANG_Refine> er = getRefinements().elements(); er
		 * .hasMoreElements();) { YANG_Refine refine = er.nextElement();
		 * refine.setUsedGrouping(ug); refine.setParent(this);
		 * refine.check(context, container); }
		 */
	}

	public void check(YangContext context, YANG_Grouping grouping)
			throws YangParserException {
		boolean found = false;
		YANG_Container container = null;
		for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs().elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_Container) {
				container = (YANG_Container) ddef;
				found = container.getContainer()
						.compareTo(getRefineNodeId()) == 0;
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine container " + getRefineNodeId()
					+ " is not in the used grouping " + grouping.getGrouping()
					+ " at line " + grouping.getLine());

		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfigStr().compareTo("false") == 0
					&& config.getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (container.getConfig() != null) {
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& container.getConfig().getConfigStr().compareTo(
								"true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":config to true in the grouping "
							+ grouping.getBody() + " at line "
							+ grouping.getLine()
							+ " but parent config to false");

			}
		}
		/*
		 * for (Enumeration<YANG_Refine> er = getRefinements().elements(); er
		 * .hasMoreElements();) { YANG_Refine refine = er.nextElement(); try {
		 * refine.setUsedGrouping(" from the used grouping " +
		 * grouping.getGrouping() + " at line " + grouping.getLine());
		 * refine.setParent(this); refine.check(context, container); } catch
		 * (YangParserException ye) { throw new
		 * YangParserException(ye.getMessage() + " from the used grouping " +
		 * grouping.getGrouping() + " at line " + grouping.getLine()); } }
		 */
	}

	public String toString() {
		String result = "";
		if (presence != null)
			result += presence.toString() + "\n";
		result += super.toString() + "\n";
		return result;
	}

}
