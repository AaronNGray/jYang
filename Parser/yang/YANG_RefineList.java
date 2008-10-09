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

public class YANG_RefineList extends YANG_Refinement {

	private String refinelist = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Config config = null;
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_Refinement> refinements = new Vector<YANG_Refinement>();

	private boolean bracked = false;

	private boolean b_config = false, b_min = false, b_max = false,
			b_description = false, b_reference = false;

	public YANG_RefineList(int id) {
		super(id);
	}

	public YANG_RefineList(yang p, int id) {
		super(p, id);
	}

	public void setRefineList(String r) {
		refinelist = r;
	}

	public String getBody() {
		return getRefineList();
	}

	public String getRefineList() {
		return refinelist;
	}

	public void addMust(YANG_Must m) {
		musts.add(m);
		bracked = true;
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in refine list " + refinelist, c
							.getLine(), c.getCol());
		b_config = true;
		config = c;
		bracked = true;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMinElement(YANG_MinElement m) throws YangParserException {
		if (b_min)
			throw new YangParserException(
					"Min element already defined in refine list " + refinelist,
					m.getLine(), m.getCol());
		b_min = true;
		min = m;
		bracked = true;
	}

	public YANG_MinElement getMinElement() {
		return min;
	}

	public void setMaxElement(YANG_MaxElement m) throws YangParserException {
		if (b_max)
			throw new YangParserException(
					"Max element already defined in refine list " + refinelist,
					m.getLine(), m.getCol());
		b_max = true;
		max = m;
		bracked = true;
	}

	public YANG_MaxElement getMaxElement() {
		return max;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description  already defined in refine list " + refinelist,
					d.getLine(), d.getCol());
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
					"Reference  already defined in refine list " + refinelist,
					r.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addRefinement(YANG_Refinement r) {
		refinements.add(r);
		bracked = true;
	}

	public Vector<YANG_Refinement> getRefinements() {
		return refinements;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context, YANG_List list, String ug)
			throws YangParserException {
		for (Enumeration<YANG_Refinement> er = getRefinements().elements(); er
				.hasMoreElements();) {
			YANG_Refinement refine = er.nextElement();
			refine.setUsedGrouping(ug);
			refine.setParent(this);
			refine.check(context, list);
		}

	}

	public void check(YangContext context, YANG_Grouping grouping)
			throws YangParserException {
		boolean found = false;
		YANG_List list = null;
		for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs().elements(); edd
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = edd.nextElement();
			if (ddef instanceof YANG_List) {
				list = (YANG_List) ddef;
				found = list.getList().compareTo(getRefineList()) == 0;
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine list " + getRefineList()
					+ " is not in the used grouping " + grouping.getGrouping());

		YANG_Config parentConfig = getParentConfig();
		if (b_config) {
			if (parentConfig.getConfig().compareTo("false") == 0
					&& config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		} else {
			if (list.getConfig() != null) {
				if (parentConfig.getConfig().compareTo("false") == 0
						&& list.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":config to true in the grouping "
							+ grouping.getBody() + " at line "
							+ grouping.getLine() + "but parent config to false");

			}
		}
		for (Enumeration<YANG_Refinement> er = getRefinements().elements(); er
				.hasMoreElements();) {
			YANG_Refinement refine = er.nextElement();
			try {
				refine.setUsedGrouping(" from the used grouping "
						+ grouping.getGrouping() + " at line "
						+ grouping.getLine());
				refine.setParent(this);
				refine.check(context, list);
			} catch (YangParserException ye) {
				throw new YangParserException(ye.getMessage()
						+ " from the used grouping " + grouping.getGrouping()
						+ " at line " + grouping.getLine());
			}
		}

	}

	public String toString() {
		String result = new String();
		result += "list " + refinelist;
		if (bracked) {
			result += "{\n";
			for (Enumeration<YANG_Must> em = musts.elements(); em
					.hasMoreElements();)
				result += em.nextElement().toString() + "\n";
			if (config != null)
				result += config.toString() + "\n";
			if (min != null)
				result += min.toString() + "\n";
			if (max != null)
				result += max.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			for (Enumeration<YANG_Refinement> er = refinements.elements(); er
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
