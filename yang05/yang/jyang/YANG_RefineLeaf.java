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

public class YANG_RefineLeaf extends YANG_Refinement {

	private String refineleaf = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Default ydefault = null;
	private YANG_Config config = null;
	private YANG_Mandatory mandatory = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;

	private boolean b_default = false, b_config = false, b_mandatory = false,
			b_description = false, b_reference = false;

	public YANG_RefineLeaf(int id) {
		super(id);
	}

	public YANG_RefineLeaf(yang p, int id) {
		super(p, id);
	}

	public void setRefineLeaf(String r) {
		refineleaf = r;
	}

	public String getBody() {
		return getRefineLeaf();
	}

	public String getRefineLeaf() {
		return refineleaf;
	}

	public void addMust(YANG_Must m) {
		musts.add(m);
		bracked = true;
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}

	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException(
					"Default already defined in refine leaf " + refineleaf, d
							.getLine(), d.getCol());
		b_default = true;
		ydefault = d;
		bracked = true;
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in refine leaf " + refineleaf, c
							.getLine(), c.getCol());
		b_config = true;
		config = c;
		bracked = true;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException(
					"Mandatory already defined in refine leaf " + refineleaf, m
							.getLine(), m.getCol());
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
					"Description already defined in refine leaf " + refineleaf,
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
					"Reference already defined in refine leaf " + refineleaf, r
							.getLine(), r.getCol());
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

	public void check(YangContext context, YANG_Leaf leaf)
			throws YangParserException {
		if (getDefault() != null)
			getDefault().check(context, leaf.getType());
		
		YANG_Config parentConfig = getParentConfig();
		if (b_config){
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (leaf.getConfig() != null){
				if (parentConfig.getConfig().compareTo("false") == 0 &&
						leaf.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "." + getCol() +
							":config to true in the grouping  but parent config to false");
				
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
				found = leaf.getLeaf().compareTo(getRefineLeaf()) == 0;
				if (found)
					check(context, leaf);
			}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":refine leaf " + getRefineLeaf() + " is not a leaf ");

		YANG_Config parentConfig = getParentConfig();
		if (b_config){
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		else {
			if (leaf.getConfig() != null){
				if (parentConfig.getConfig().compareTo("false") == 0 &&
						leaf.getConfig().getConfig().compareTo("true") == 0)
					throw new YangParserException("@" + getLine() + "." + getCol() +
							":config to true in the grouping  but parent config to false");
				
			}
		}
		
	}

	public String toString() {
		String result = new String();
		result += "leaf " + refineleaf;
		if (bracked) {
			result += "{\n";
			for (Enumeration<YANG_Must> em = musts.elements(); em
					.hasMoreElements();)
				result += em.nextElement().toString() + "\n";
			if (ydefault != null)
				result += ydefault.toString() + "\n";
			if (config != null)
				result += config.toString() + "\n";
			if (mandatory != null)
				result += mandatory.toString() + "\n";
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
