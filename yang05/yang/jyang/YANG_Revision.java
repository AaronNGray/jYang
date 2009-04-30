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

public class YANG_Revision extends SimpleNode {

    private String date = null;
    private YANG_Description description = null;

  public YANG_Revision(int id) {
    super(id);
  }

  public YANG_Revision(yang p, int id) {
    super(p, id);
  }
    public void setDate(String d){
	date = d;
    }

    public String getDate(){
	return date;
    }

    public void setDescription(YANG_Description d){
	description = d;
    }

    public YANG_Description getDescription(){
	return description;
    }

    public String toString(){
	String result = new String();
	result += "revision " + date;
	if(description != null)
	    result += "{\n" + description.toString() + "\n}";
	else
	    result += ";";
	return result;
    }


}
