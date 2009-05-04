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



public class YANG_Module extends YANG_Specification {

	private String name;

	/**
	 * Header flags
	 */
	private int nbheader = 0;
	private boolean b_yangversion = false, b_namespace = false,
			b_prefix = false;
	/**
	 * Meta statement flags
	 */
	private int nbmeta = 0;
	private boolean organization = false, contact = false, description = false,
			reference = false;

	public YANG_Module(int id) {
		super(id);
	}

	public YANG_Module(yang p, int id) {
		super(p, id);
	}

	public void setName(String n) {
		name = n;
	}

	public String getModule() {
		return name;
	}

	public String getName() {
		return getModule();
	}

	public void addHeader(YANG_Header m) throws YangParserException {

		if (nbheader == 3)
			throw new YangParserException(
					"No more than three headers in module" + name, m.getLine(),
					m.getCol());
		if (m instanceof YANG_YangVersion) {
			if (b_yangversion)
				throw new YangParserException(
						"Yang version already defined in " + name, m.getLine(),
						m.getCol());

			b_yangversion = true;
			yangversion = (YANG_YangVersion) m;

		}
		if (m instanceof YANG_NameSpace) {
			if (b_namespace)
				throw new YangParserException("Name space already defined in "
						+ name, m.getLine(), m.getCol());
			b_namespace = true;
			namespace = (YANG_NameSpace) m;
		}
		if (m instanceof YANG_Prefix) {
			if (b_prefix)
				throw new YangParserException("Prefix  already defined in "
						+ name, m.getLine(), m.getCol());
			b_prefix = true;
			prefix = (YANG_Prefix) m;
		}
		nbheader++;
		headers.add(m);
	}

	public void addMeta(YANG_Meta m) throws YangParserException {
		if (nbmeta == 4)
			throw new YangParserException(
					"No more than four meta statement in  module " + name, m
							.getLine(), m.getCol());
		if (m instanceof YANG_Organization) {
			if (organization)
				throw new YangParserException(
						"Organization already defined in  module " + name, m
								.getLine(), m.getCol());
			else
				organization = true;
		}
		if (m instanceof YANG_Contact) {
			if (contact)
				throw new YangParserException(
						"Contact already defined in  module " + name, m
								.getLine(), m.getCol());
			else
				contact = true;
		}
		if (m instanceof YANG_Description) {
			if (description)
				throw new YangParserException(
						"Description already defined in  module " + name, m
								.getLine(), m.getCol());
			else
				description = true;
		}
		if (m instanceof YANG_Reference) {
			if (reference)
				throw new YangParserException(
						"Reference already defined in  module " + name, m
								.getLine(), m.getCol());
			else
				reference = true;
		}
		nbmeta++;
		metas.add(m);
	}

	/**
	 * Check the presence of the namespace and the prefix statements
	 */
	public void checkHeader(String[] p) throws YangParserException {
		if (!b_namespace && b_prefix)
			throw new YangParserException("Name space not defined in module "
					+ name);
		if (b_namespace && !b_prefix)
			throw new YangParserException("Prefix not defined in module "
					+ name);
		if (!b_namespace && !b_prefix)
			throw new YangParserException(
					"Name space and Prefix not defined in module " + name);
	}

	/**
	 * Check if included yang specifications are accessible and have a correct
	 * syntax and if they are submodules and if they belong to this module
	 */
	protected void checkInclude(String[] paths)
			throws YangParserException {
		Vector<YANG_Specification> included = getIncludedSubModules(paths);
		for (Enumeration<YANG_Specification> es = included.elements(); es
				.hasMoreElements();) {
			YANG_Specification includedspec = es.nextElement();
			if (!(includedspec instanceof YANG_SubModule))
				throw new YangParserException(
						"Only submodule can be included : "
								+ includedspec.getName()
								+ " is not a submodule");
			else {
				YANG_SubModule submod = (YANG_SubModule) includedspec;

				if (!submod.getBelong().getBelong().equals(getModule()))
					throw new YangParserException("Included submodule "
							+ submod.getSubModule()
							+ " does not belongs to module " + getName());
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