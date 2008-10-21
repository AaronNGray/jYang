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

public class YANG_Yin extends SimpleNode {

    private String yin = null;

  public YANG_Yin(int id) {
    super(id);
  }

  public YANG_Yin(yang p, int id) {
    super(p, id);
  }

    public String getYin(){
	return yin;
    }

    public void setYin(String y) throws YangParserException
{
		if(!(y.compareTo("true") == 0 || y.compareTo("false") == 0 ||
	     y.compareTo("\"true\"") == 0 || y.compareTo("\"false\"") == 0))
	    throw new YangParserException("A yin statement must be true or false in yin-element ",
	    getLine(),getCol());
	yin = y;
    }

    public String toString(){
	return "yin-element " + yin + ";";
    }
}
