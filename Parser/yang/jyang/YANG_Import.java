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

public class YANG_Import extends SimpleNode implements YANG_Linkage {

	private String importstr = null;
	private YANG_Prefix prefix = null;
	private YANG_Revision revision = null;
	
	private boolean b_prefix = false, b_revision = false;

	public YANG_Import(int id) {
		super(id);
	}

	public YANG_Import(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		importstr = s;
	}

	public String getImportedModule() {
		return importstr;
	}

	public void setPrefix(YANG_Prefix p)  throws YangParserException {
		if (b_prefix)
			throw new YangParserException(
					"Prefix is already defined in import " + importstr, p
							.getLine(), p.getCol());
		prefix = p;
		b_prefix = true;
	}

	public YANG_Prefix getPrefix() {
		return prefix;
	}

	public String toString() {
		return " import " + importstr + " {" + prefix.toString() + "}";
	}

	public YANG_Revision getRevision() {
		return revision;
	}

	public void setRevision(YANG_Revision r)  throws YangParserException  {
		if (b_revision)
			throw new YangParserException(
					"Revision is already defined in import " + importstr, r
							.getLine(), r.getCol());
		this.revision = r;
		b_revision = true;
	}

}
