package jyang;
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

public class YANG_RefineLeafList extends YANG_Refinement {

	private String refineleaflist = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Config config = null;
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	private boolean b_config = false, b_min = false, b_max = false,
			b_description = false, b_reference = false;

	public YANG_RefineLeafList(int id) {
		super(id);
	}

	public YANG_RefineLeafList(yang p, int id) {
		super(p, id);
	}

	public void setRefineLeafList(String r) {
		refineleaflist = r;
	}

	public String getBody() {
		return getRefineLeafList();
	}

	public String getRefineLeafList() {
		return refineleaflist;
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
					"Config already defined in refine leaf list "
							+ refineleaflist, c.getLine(), c.getCol());
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
					"Min element already defined in refine leaf list "
							+ refineleaflist, m.getLine(), m.getCol());
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
					"Max element already defined in refine leaf list "
							+ refineleaflist, m.getLine(), m.getCol());
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
					"Description already defined in refine leaf list "
							+ refineleaflist, d.getLine(), d.getCol());
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
					"Reference already defined in refine leaf list "
							+ refineleaflist, r.getLine(), r.getCol());
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
	
	public void check(YangContext context, YANG_LeafList leaflist) throws YangParserException {
		YANG_Config parentConfig = getParentConfig();
		if (b_config){
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (leaflist.getConfig() != null){
				if (parentConfig.getConfig().compareTo("false") == 0 &&
						leaflist.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "." + getCol() +
							":config to true and parent config to false");
				
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
				found = leaflist.getLeafList().compareTo(getRefineLeafList()) == 0;
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine leaf-list " + getRefineLeafList()
					+ " is not in the used grouping " + grouping.getGrouping());

		YANG_Config parentConfig = getParentConfig();
		if (b_config){
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (leaflist.getConfig() != null){
				if (parentConfig.getConfig().compareTo("false") == 0 &&
						leaflist.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "." + getCol() +
							":config to true in the grouping " +
							grouping.getBody() + " at line " + grouping.getLine() +
							" but parent config to false");
				
			}
		}

	}

	public String toString() {
		String result = new String();
		result += "leaf-list " + refineleaflist;
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