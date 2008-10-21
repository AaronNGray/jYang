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

public class YANG_Config extends SimpleNode {

	private String config = null;

	public YANG_Config(int id) {
		super(id);
	}

	public YANG_Config(yang p, int id) {
		super(p, id);
	}

	public void setConfig(String c) throws YangParserException {
		if (c == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in config value");
		String ct = YangBuiltInTypes.removeQuotesAndTrim(c);
		if (ct.compareTo("true") != 0 && ct.compareTo("false") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":config value not correct : " + ct);
		config = c;
	}

	public String getConfig() {
		return config;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "config " + config + ";";
	}
}
