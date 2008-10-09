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


public interface YANG_Header extends Node{

    public String toString();
}

/*
public class YANG_ModuleHeader extends SimpleNode {

    private YANG_YangVersion version = null;
    private YANG_NameSpace namespace = null;
    private YANG_Prefix prefix = null;

  public YANG_ModuleHeader(int id) {
    super(id);
  }

  public YANG_ModuleHeader(yang p, int id) {
    super(p, id);
  }

    public void setVersion(YANG_YangVersion v){
	version = v;
    }

    public void setNameSpace(YANG_NameSpace n){
	namespace = n;
    }

    public void setPrefix(YANG_Prefix p){
	prefix = p;
    }

    public String toString(){
	String header = new String();
	if(version != null)
	    header += " " + version.toString() + "\n";
	if(namespace != null)
	    header += " " + namespace.toString() + "\n";
	if(prefix != null)
	    header += " " + prefix.toString() + "\n";
	return header;
    }

}
*/
