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

public class YANG_Presence extends SimpleNode {

    private String presence = null;

  public YANG_Presence(int id) {
    super(id);
  }

  public YANG_Presence(yang p, int id) {
    super(p, id);
  }

    public void setPresence(String p){
	presence = p;
    }

    public String getPresence(){
	return presence;
    }
    
    public void check(YangContext context){
    }

    public String toString(){
	return "presence " + presence + ";";
    }

}
