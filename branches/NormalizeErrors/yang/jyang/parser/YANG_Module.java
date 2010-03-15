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

public class YANG_Module extends YANG_Specification {

	private String name;

	/**
	 * Header flags
	 */
	private int nbheader = 0;
	private boolean b_yangversion = false, b_namespace = false,
			b_prefix = false;

	public YANG_Module(int id) {
		super(id);
	}

	public YANG_Module(yang p, int id) {
		super(p, id);
	}

	public void setName(String n) {
		name = unquote(n);
	}

	public String getModule() {
		return name;
	}

	public String getName() {
		return getModule();
	}

	public void addHeader(YANG_Header m) {

		if (m instanceof YANG_YangVersion) {
			if (!b_yangversion) {
				b_yangversion = true;
				yangversion = (YANG_YangVersion) m;
			} else
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "version");

		}
		if (m instanceof YANG_NameSpace) {
			if (!b_namespace) {
				b_namespace = true;
				namespace = (YANG_NameSpace) m;
			} else
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "namespace");

		}
		if (m instanceof YANG_Prefix) {
			if (!b_prefix) {
				b_prefix = true;
				prefix = (YANG_Prefix) m;
			} else
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "prefix");

		}
		nbheader++;
		headers.add(m);
	}

	/**
	 * Check the presence of the namespace and the prefix statements
	 */
	public void checkHeader(String[] p) throws YangParserException {
		if (!b_namespace && b_prefix)
			throw new YangParserException("Name space not defined in module "
					+ name);
		if (b_namespace && !b_prefix)
			YangErrorManager.tadd(getFileName(), getLine(), getCol(),
					"expected", "prefix");
		if (!b_namespace && !b_prefix)
			throw new YangParserException(
					"Name space and Prefix not defined in module " + name);
	}

	/**
	 * Check if included yang specifications are accessible and have a correct
	 * syntax and if they are submodules and if they belong to this module
	 */
	protected void checkInclude(String[] paths) throws YangParserException {
		Vector<YANG_Specification> included = getIncludedSubModules(paths);
		for (Enumeration<YANG_Specification> es = included.elements(); es
				.hasMoreElements();) {
			YANG_Specification includedspec = es.nextElement();

			int l = 0, c = 0;
			for (YANG_Linkage lk : linkages) {
				if (lk.getName().compareTo(includedspec.getName()) == 0) {
					l = lk.getLine();
					c = lk.getCol();
				}
			}
			if (!(includedspec instanceof YANG_SubModule)) {
				YangErrorManager.tadd(getFileName(), l, c, "not_submodule",
						includedspec.getName());
			} else {
				YANG_SubModule submod = (YANG_SubModule) includedspec;

				if (!submod.getBelong().getBelong().equals(getModule()))
					YangErrorManager.tadd(getFileName(), l, c, "not_belong",
							submod.getSubModule(), getName());
				else
					includeds.add(submod);
			}
		}
	}

	public String toString() {
		String result = new String();
		result += "module " + name + " {\n";
		for (Enumeration<YANG_Header> eh = headers.elements(); eh
				.hasMoreElements();)
			result += eh.nextElement().toString() + "\n";
		for (Enumeration<YANG_Linkage> el = linkages.elements(); el
				.hasMoreElements();)
			result += el.nextElement().toString() + "\n";
		for (Enumeration<YANG_Meta> em = metas.elements(); em.hasMoreElements();)
			result += em.nextElement().toString() + "\n";
		for (Enumeration<YANG_Revision> er = revisions.elements(); er
				.hasMoreElements();)
			result += er.nextElement().toString() + "\n";
		for (Enumeration<YANG_Body> eb = bodies.elements(); eb
				.hasMoreElements();) {
			result += eb.nextElement().toString() + "\n";
		}
		result += "}";
		return result;
	}

}
