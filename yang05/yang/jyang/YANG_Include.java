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

public class YANG_Include extends SimpleNode implements YANG_Linkage {

	private String include = null;
	
	private YANG_Revision revision = null;
	
	public YANG_Revision getRevision() {
		return revision;
	}

	public void setRevision(YANG_Revision revision) {
		this.revision = revision;
	}

	public YANG_Include(int id) {
		super(id);
	}

	public YANG_Include(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		include = s;
	}

	public String getIncludedModule() {
		return include;
	}

	public String toString() {
		return " include " + include + ";";
	}

}