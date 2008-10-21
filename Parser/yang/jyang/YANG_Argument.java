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

public class YANG_Argument extends SimpleNode {

    private String argument = null;
    private YANG_Yin yin = null;

    private boolean bracked = false;

  public YANG_Argument(int id) {
    super(id);
  }

  public YANG_Argument(yang p, int id) {
    super(p, id);
  }

    public void setArgument(String a){
	argument = a;
    }

    public String getArgument(){
	return argument;
    }

    public void setYin(YANG_Yin y){
	yin = y;
	bracked = true;
    }

    public YANG_Yin getYin(){
	return yin;
    }

    public boolean isBracked(){
	return bracked;
    }

    public String toString(){
	String result = new String();
	result += "argument " + argument;
	if(yin != null)
	    result += "{" + yin.toString() + "}";
	else
	    result += ";";
	return result;
    }


}
