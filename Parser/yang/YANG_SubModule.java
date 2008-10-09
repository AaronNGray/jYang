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

public class YANG_SubModule extends YANG_Specification {

	/**
	 * Header flags
	 */
	private int nbheader = 0;
	private boolean b_yangversion = false, b_belong = false;
	/**
	 * Meta statement flags
	 */
	private int nbmeta = 0;
	private boolean organization = false, contact = false, description = false,
			reference = false;

	public YANG_SubModule(int id) {
		super(id);
	}

	public YANG_SubModule(yang p, int id) {
		super(p, id);
	}

	private String submodule = null;

	private YANG_Belong belong = null;

	public void setSubModule(String n) {
		submodule = n;
	}

	public String getSubModule() {
		return submodule;
	}

	public String getName() {
		return getSubModule();
	}

	public void addHeader(YANG_Header m) throws YangParserException {

		if (nbheader == 2)
			throw new YangParserException(
					"No more than two headers in  submodule " + submodule, m
							.getLine(), m.getCol());
		if (m instanceof YANG_YangVersion) {
			if (b_yangversion)
				throw new YangParserException(
						"Yang version already defined in submodule "
								+ submodule, m.getLine(), m.getCol());
			else
				b_yangversion = true;
			yangversion = (YANG_YangVersion) m;
		}
		if (m instanceof YANG_Belong) {
			if (b_belong)
				throw new YangParserException(
						"Belonging module already defined in submodule "
								+ submodule, m.getLine(), m.getCol());
			else
				b_belong = true;
			belong = (YANG_Belong) m;

		}
		headers.add(m);
	}

	public YANG_Belong getBelong() {
		return belong;
	}

	public void addMeta(YANG_Meta m) throws YangParserException {
		if (nbmeta == 4)
			throw new YangParserException(
					"No more than four meta statement in  submodule "
							+ submodule, m.getLine(), m.getCol());
		if (m instanceof YANG_Organization) {
			if (organization)
				throw new YangParserException(
						"Organization already defined in  submodule "
								+ submodule, m.getLine(), m.getCol());
			else
				organization = true;
		}
		if (m instanceof YANG_Contact) {
			if (contact)
				throw new YangParserException(
						"Contact already defined in  submodule " + submodule, m
								.getLine(), m.getCol());
			else
				contact = true;
		}
		if (m instanceof YANG_Description) {
			if (description)
				throw new YangParserException(
						"Description already defined in submodule " + submodule,
						m.getLine(), m.getCol());
			else
				description = true;
		}
		if (m instanceof YANG_Reference) {
			if (reference)
				throw new YangParserException(
						"Reference already defined in  submodule " + submodule,
						m.getLine(), m.getCol());
			else
				reference = true;
		}
		nbmeta++;
		metas.add(m);
	}

	/**
	 * Check if the belongs-to statement is present and if it refers to an
	 * existing module. The presence of the module and its syntax are checked
	 * but no semantical checking.
	 */
	public void checkHeader(String[] p) throws YangParserException {
		if (!b_belong)
			throw new YangParserException(
					"Belonging module must be defined in submodule "
							+ submodule);
		YANG_Specification belonged = getExternal(p, belong.getBelong());
		if (!(belonged instanceof YANG_Module))
			throw new YangParserException(belonged.getName()
					+ " is not a module", belonged.getLine(), belonged.getCol());
	}

	/**
	 * Check if included submodule belong to the same module than this submodule
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

				if (!submod.getBelong().getBelong().equals(
						getBelong().getBelong()))
					throw new YangParserException("Included submodule "
							+ submod.getSubModule() + " in submodule "
							+ getSubModule()
							+ " does not belongs to the same module "
							+ getBelong().getBelong());
				includeds.add(submod);
			}
		}
	}

	public String toString() {
		String result = new String();
		result += "submodule " + submodule + " {\n";
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
