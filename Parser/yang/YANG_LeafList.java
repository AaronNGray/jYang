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

public class YANG_LeafList extends  YANG_DataDef
		implements YANG_CaseDef, YANG_ShortCase {

	private String leaflist = null;
	private YANG_Type type = null;
	private YANG_Units units = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Config config = null;
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	private YANG_OrderedBy ordered = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean b_type = false, b_units = false, b_config = false,
			b_min = false, b_max = false, b_ordered = false, b_status = false,
			b_description = false, b_reference = false;

	public YANG_LeafList(int id) {
		super(id);
	}

	public YANG_LeafList(yang p, int id) {
		super(p, id);
	}

	public void setLeafList(String l) {
		leaflist = l;
	}

	public String getBody() {
		return getLeafList();
	}

	public String getLeafList() {
		return leaflist;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (b_type)
			throw new YangParserException("Type already defined in leaf-list "
					+ leaflist, t.getLine(), t.getCol());
		b_type = true;
		type = t;
	}

	public YANG_Type getType() {
		return type;
	}

	public void setUnits(YANG_Units u) throws YangParserException {
		if (b_units)
			throw new YangParserException("Units already defined in leaf-list "
					+ leaflist, u.getLine(), u.getCol());
		b_units = true;
		units = u;
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in leaf-list " + leaflist, c
							.getLine(), c.getCol());
		b_config = true;
		config = c;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setMinElement(YANG_MinElement m) throws YangParserException {
		if (b_min)
			throw new YangParserException(
					"Min element already defined in leaf-list " + leaflist, m
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
					"Max element already defined in leaf-list " + leaflist, m
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
					"Ordered-by already defined in leaf-list " + leaflist, o
							.getLine(), o.getCol());
		b_ordered = true;
		ordered = o;
	}

	public YANG_OrderedBy getOrderedBy() {
		return ordered;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException(
					"Status already defined in leaf-list " + leaflist, s
							.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in leaf-list " + leaflist, d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in leaf-list " + leaflist, r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void check(YangContext context) throws YangParserException {
		if (!b_type)
			throw new YangParserException(
					"Type statement not present in leaf-list " + leaflist,
					getLine(), getCol());
		getType().check(context);
		
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "leaf-list " + leaflist + "{\n";
		if (type != null)
			result += type.toString() + "\n";
		if (units != null)
			result += units.toString() + "\n";
		for (Enumeration<YANG_Must> em = musts.elements(); em.hasMoreElements();)
			result += em.nextElement().toString() + "\n";
		if (config != null)
			result += config.toString() + "\n";
		if (min != null)
			result += min.toString() + "\n";
		if (max != null)
			result += max.toString() + "\n";
		if (ordered != null)
			result += ordered.toString() + "\n";
		if (status != null)
			result += status.toString() + "\n";
		if (description != null)
			result += description.toString() + "\n";
		if (reference != null)
			result += reference.toString() + "\n";
		result += "}\n";

		return result;
	}
}
