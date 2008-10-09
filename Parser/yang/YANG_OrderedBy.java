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

public class YANG_OrderedBy extends SimpleNode {

	private String orderedby = null;

	public YANG_OrderedBy(int id) {
		super(id);
	}

	public YANG_OrderedBy(yang p, int id) {
		super(p, id);
	}

	public void setOrderedBy(String o) throws YangParserException {
		if (o == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in ordered-by value");
		String ot = YangBuiltInTypes.removeQuotesAndTrim(o);
		if (ot.compareTo("system") != 0 && ot.compareTo("user") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":config value not correct " + ot);
		orderedby = ot;
	}

	public String getOrderedBy() {
		return orderedby;
	}

	public String toString() {
		return "ordered-by " + orderedby + ";";
	}

}
