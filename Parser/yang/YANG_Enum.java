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

public class YANG_Enum extends SimpleNode {

    private String enumid = null;
    private YANG_Value value = null;
    private YANG_Status status = null;
    private YANG_Description description = null;
    private YANG_Reference reference = null;

    private boolean bracked = false;

  public YANG_Enum(int id) {
    super(id);
  }

  public YANG_Enum(yang p, int id) {
    super(p, id);
  }

    public void setEnum(String e){
	enumid = e;
    }

    public String getEnum(){
	return enumid;
    }

    public void setValue(YANG_Value v){
	value = v;
	bracked = true;
    }

    public YANG_Value getValue(){
	return value;
    }

    public void setStatus(YANG_Status s){
	status = s;
	bracked = true;
    }

    public YANG_Status getStatus(){
	return status;
    }

    public void setDescription(YANG_Description d){
	description = d;
	bracked = true;
    }

    public YANG_Description getDescription(){
	return description;
    }

    public void setReference(YANG_Reference r){
	reference = r;
	bracked = true;
    }

    public YANG_Reference getReference(){
	return reference;
    }

    public boolean isBracked(){
	return bracked;
    }
    
    public String toString(){
	String result = new String();
	result += "enum " + enumid;
	if(bracked){
	    result += " {\n";
	    if(value != null)
		result += value.toString() + "\n";
	    if(status != null)
		result += status.toString() + "\n";
	    if(description != null)
		result += description.toString() + "\n";
	    if(reference != null)
		result += reference.toString() + "\n";
	    result += " } ";
	}
	else
	    result += ";";
	return result;
    }

}
