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

public class YANG_Extension extends  YANG_Body{

    private String extension = null;
    private YANG_Argument argument = null;
    private YANG_Status status = null;
    private YANG_Description description = null;
    private YANG_Reference reference = null;

    private boolean bracked = false;

    private boolean b_argument = false,
	b_status = false, b_description = false,
	b_reference = false;

  public YANG_Extension(int id) {
    super(id);
  }

  public YANG_Extension(yang p, int id) {
    super(p, id);
  }

    public void setExtension(String e){
	extension = e;
    }
    
    public String getBody(){
    		return getExtension();
    }

    public String getExtension(){
	return extension;
    }

    public void setArgument(YANG_Argument a) throws YangParserException {
	if(b_argument)
	    throw new YangParserException("Argument already defined in extension " +
					  extension, getLine(), getCol());
	b_argument = true;
	argument = a;
	bracked = true;
    }

    public YANG_Argument getArgument(){
	return argument;
    }

    public void setStatus(YANG_Status s) throws YangParserException {
	if(b_status)
	    throw new YangParserException("Status already defined in extension " +
					  extension, getLine(), getCol());
	b_status = true;
	status = s;
	bracked = true;
    }

    public YANG_Status getStatus(){
	return status;
    }

    public void setDescription(YANG_Description d) throws YangParserException {
	if(b_description)
	    throw new YangParserException("Status already defined in extension " +
					  extension, getLine(), getCol());
	b_description = true;
	description = d;
	bracked = true;
    }

    public YANG_Description getDescription(){
	return description;
    }

    public void setReference(YANG_Reference r) throws YangParserException {
	if(b_reference)
	    throw new YangParserException("Reference already defined in extension " +
					  extension, getLine(), getCol());
	b_reference = true;
	reference = r;
	bracked = true;
    }

    public YANG_Reference getReference(){
	return reference;
    }

    public boolean isBracked(){
	return bracked;
    }
    
    public void check(YangContext context){
    }

    public String toString(){
	String result = new String();
	result += "extension " + extension;
	if(bracked){
	    result += "{\n";
	    if(argument != null)
		result +=  argument.toString() + "\n";
	    if(status != null)
		result += status.toString() + "\n";
	    if(description != null)
		result += description.toString() + "\n";
	    if(reference != null)
		result += reference.toString() + "\n";
	    result += "}";
	}
	else
	    result += ";";
	return result;
    }


}
