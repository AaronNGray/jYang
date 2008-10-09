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

public class YANG_ErrorAppt extends SimpleNode {

	private String errorappt = null;

	public YANG_ErrorAppt(int id) {
		super(id);
	}

	public YANG_ErrorAppt(yang p, int id) {
		super(p, id);
	}

	public void setErrorAppt(String e) {
		errorappt = e;
	}

	public String getErrorAppt() {
	  return errorappt;
  }
	
	public String toString(){
		return "error-appt-tag " + errorappt + ";";
	}
}
