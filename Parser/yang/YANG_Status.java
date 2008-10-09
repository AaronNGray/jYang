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

public class YANG_Status extends SimpleNode {

	private String status = null;

	public YANG_Status(int id) {
		super(id);
	}

	public YANG_Status(yang p, int id) {
		super(p, id);
	}

	public void setStatus(String s) throws YangParserException {
		if (s == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in status value");
		String st = YangBuiltInTypes.removeQuotesAndTrim(s);
		if (st.compareTo("current") != 0 && st.compareTo("obsolete") != 0
				&& st.compareTo("deprecated") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":status value not correct : " + s);
		status = st;
	}

	public String getStatus() {
		return status;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "status " + status + ";";
	}

}
