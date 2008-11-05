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

public class YANG_Belong extends SimpleNode implements YANG_Header{

    private String belong = null;
    private YANG_Prefix prefix = null;
    
  public YANG_Belong(int id) {
    super(id);
  }

  public YANG_Belong(yang p, int id) {
    super(p, id);
  }

    public void setBelong(String b){
	belong = b;
    }
    
    public void setPrefix(YANG_Prefix p){
    	prefix = p;
    }
    
    public String getBelong(){
    	return belong;
    }

    public YANG_Prefix getPrefix(){
    	return prefix;
    }
    
    public String toString(){
	return "belongs-to " + belong + "\n{ " + prefix + " }"; 
    }

}
