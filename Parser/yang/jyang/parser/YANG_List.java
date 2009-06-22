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



public class YANG_List extends YANG_DataDefConfigMust implements YANG_CaseDef,
		YANG_ShortCase {

	private String list = null;
	private YANG_Key key = null;
	private Vector<YANG_Unique> uniques = new Vector<YANG_Unique>();
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	private YANG_OrderedBy orderedby = null;
	private Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
	private Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

	private boolean b_key = false, b_min = false,
			b_max = false, b_ordered = false;

	public YANG_List(int id) {
		super(id);
	}

	public YANG_List(yang p, int id) {
		super(p, id);
	}

	public void setList(String l) {
		list = l;
	}

	public String getBody() {
		return getList();
	}

	public String getList() {
		return list;
	}

	public void setKey(YANG_Key t) throws YangParserException {
		if (b_key)
			throw new YangParserException(
					"Key already defined in list " + list, t.getLine(), t
							.getCol());
		b_key = true;
		key = t;
	}

	public YANG_Key getKey() {
		return key;
	}

	public void addUnique(YANG_Unique u) {
		uniques.add(u);
	}

	public Vector<YANG_Unique> getUniques() {
		return uniques;
	}


	public void setMinElement(YANG_MinElement m) throws YangParserException {
		if (b_min)
			throw new YangParserException(
					"Min element already defined in leaf-list " + list, m
							.getLine(), m.getCol());
		b_min = true;
		min = m;
	}

	public YANG_MinElement getMinElement() {
		return min;
	}

	public void setMaxElement(YANG_MaxElement m) throws YangParserException {
		if (b_max)
			throw new YangParserException(
					"Max element already defined in leaf-list " + list, m
							.getLine(), m.getCol());
		b_max = true;
		max = m;
	}

	public YANG_MaxElement getMaxElement() {
		return max;
	}

	public void setOrderedBy(YANG_OrderedBy o) throws YangParserException {
		if (b_ordered)
			throw new YangParserException(
					"Ordered-by already defined in leaf-list " + list, o
							.getLine(), o.getCol());
		b_ordered = true;
		orderedby = o;
	}

	public YANG_OrderedBy getOrderedBy() {
		return orderedby;
	}

	public void addTypeDef(YANG_TypeDef t) {
		typedefs.add(t);
	}

	public Vector<YANG_TypeDef> getTypeDefs() {
		return typedefs;
	}

	public void addGrouping(YANG_Grouping g) {
		groupings.add(g);
	}

	public Vector<YANG_Grouping> getGroupings() {
		return groupings;
	}

	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}
	
	public boolean isBracked(){
		return super.isBracked() || b_key || b_max || b_min || b_ordered || datadefs.size() != 0 || groupings.size() != 0;
	}

	public void check(YangContext context) throws YangParserException {
		if (!b_key) {
			if (b_config) {
				if (getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":key not present in list "
							+ getList() + " with config true");
			} else {
				YANG_Config parentConfig = getParentConfig();
				if (parentConfig.getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":key not present in list "
							+ getList() + " with parent config true");
			}
		} else {

			if (b_config) {
				YANG_Config parentConfig = getParentConfig();
				if (parentConfig.getConfig().compareTo("false") == 0
						&& getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":config to true and parent config to false");
			}

			String[] kleafs = getKey().getKeyLeaves();
			for (int i = 0; i < kleafs.length; i++) {
				for (int j = i + 1; j < kleafs.length; j++)
					if (kleafs[i].compareTo(kleafs[j]) == 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":dupplicate key leaf in list "
								+ getList());
				boolean found = false;
				String configlist = null;
				if (!b_config) {
					configlist = getParentConfig().getConfig();
				} else
					configlist = getConfig().getConfig();
				for (Enumeration<YANG_DataDef> ed = getDataDefs().elements(); !found
						&& ed.hasMoreElements();) {
					YANG_DataDef dd = ed.nextElement();
					found = findKey(context, kleafs[i], dd);
					if(found){
						if (dd instanceof YANG_Leaf) {
							YANG_Leaf leaf = (YANG_Leaf) dd;
							if (context.getBuiltInType(leaf.getType()) != null) {
								if (YangBuiltInTypes.empty.compareTo(context
										.getBuiltInType(leaf.getType())) == 0)
									throw new YangParserException(
											"@"
													+ getLine()
													+ "."
													+ getCol()
													+ ":key leaf "
													+ kleafs[i]
													+ " can not be of the empty type in list "
													+ getList());
								String configkeyleaf = null;
								if (leaf.getConfig() == null)
									configkeyleaf = configlist;
								else
									configkeyleaf = YangBuiltInTypes
											.removeQuotesAndTrim(leaf
													.getConfig().getConfig());
								if (configlist.compareTo(configkeyleaf) != 0)
									throw new YangParserException(
											"@"
													+ getLine()
													+ "."
													+ getCol()
													+ ":key leaf "
													+ kleafs[i]
													+ " has not the same config of the list "
													+ getList());
							}
						}
					}
				}
				if (!found)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":key leaf " + kleafs[i]
							+ " not found in list " + getList());
			}
		}
		/*
		 * for (Enumeration<YANG_Unique> eu = getUniques().elements(); eu
		 * .hasMoreElements();) { YANG_Unique unique = eu.nextElement(); }
		 */
		if (datadefs.size() == 0)
			throw new YangParserException("@" + getLine() + ":" + getCol()
					+ ":no data in list " + list);

		/*
		 * 
		 * Hashtable<String , YANG_DataDef> nodes = new Hashtable<String,
		 * YANG_DataDef>(); for (Enumeration <YANG_DataDef> edd =
		 * getDataDefs().elements(); edd.hasMoreElements();){ YANG_DataDef ddef
		 * = edd.nextElement(); if (!(ddef instanceof YANG_Uses)){
		 * nodes.put(ddef.getBody(), ddef); } if (ddef instanceof YANG_Uses){
		 * YANG_Uses uses = (YANG_Uses) ddef; for (Enumeration <YANG_Refinement>
		 * er = uses.getRefinements().elements(); er.hasMoreElements();){
		 * YANG_Refinement ref = er.nextElement(); if
		 * (nodes.containsKey(ref.getBody())) throw new YangParserException("@"
		 * + ref.getLine() + "." + ref.getCol() + ":refinement " + ref.getBody()
		 * + " is already defined in list " + getBody()); } } }
		 */
	}

	private boolean findKey(YangContext context, String k, YANG_DataDef dd) {
		
		if (dd.getBody().compareTo(k) == 0)
			return true;
		if (dd instanceof YANG_Uses) {
			YANG_Uses uses = (YANG_Uses) dd;			
			YANG_Grouping grouping = context.getUsedGrouping(uses);
			for (Enumeration<YANG_DataDef> edd = grouping.getDataDefs()
					.elements(); edd.hasMoreElements();) {
				YANG_DataDef gdd = edd.nextElement();
				if (findKey(context, k, gdd))
					return true;
			}
			return false;
		}
		if (dd instanceof YANG_Choice) {
			YANG_Choice c = (YANG_Choice) dd;
			for (Enumeration<YANG_Case> ecases = c.getCases().elements(); ecases
					.hasMoreElements();) {
				YANG_Case ca = ecases.nextElement();
				for (Enumeration<YANG_CaseDef> ecdef = ca.getCaseDefs()
						.elements(); ecdef.hasMoreElements();) {
					YANG_CaseDef cdef = ecdef.nextElement();
					if (cdef instanceof YANG_DataDef)
						if(findKey(context, k, (YANG_DataDef)cdef))
							return true;
				}
			}
			return false;
		}
		return false;
	}

	public String toString() {
		String result = new String();
		result += "list " + list + "{\n";
		if (key != null)
			result += key.toString() + "\n";
		for (Enumeration<YANG_Unique> eu = uniques.elements(); eu
				.hasMoreElements();)
			result += eu.nextElement().toString() + "\n";
		if (min != null)
			result += min.toString() + "\n";
		if (max != null)
			result += max.toString() + "\n";
		if (orderedby != null)
			result += orderedby.toString() + "\n";
		for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
				.hasMoreElements();)
			result += et.nextElement().toString() + "\n";
		for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
				.hasMoreElements();)
			result += eg.nextElement().toString() + "\n";
		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();)
			result += ed.nextElement().toString() + "\n";
		result += super.toString() + "\n";
		result += "}\n";

		return result;
	}

}
