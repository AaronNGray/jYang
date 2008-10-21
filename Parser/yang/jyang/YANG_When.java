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

public class YANG_When extends SimpleNode {

    private String when = null;

  public YANG_When(int id) {
    super(id);
  }

  public YANG_When(yang p, int id) {
    super(p, id);
  }

    public void setWhen(String w){
	when = w;
    }

    public String getWhen(){
	return when;
    }

    public void check(YangContext context){
    }

    public String toString(){
	return "when " + when + ";";
    }

}
