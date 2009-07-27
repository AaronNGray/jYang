package yangTree.nodes;

import java.io.Serializable;

public class NameSpace implements Serializable{
	
	private String prefix = null;
	private String nameSpace = null;
	
	public NameSpace(String nameSpace){
		this.nameSpace = nameSpace;
	}
	
	public NameSpace(String prefix,String nameSpace){
		this.prefix = prefix;
		this.nameSpace = nameSpace;
	}
	
	public NameSpace(String nameSpace, boolean isAPrefix){
		if (isAPrefix){
			this.prefix = nameSpace;
		} else {
			this.nameSpace = nameSpace;
		}
	}
	
	public NameSpace mergeWith(NameSpace prefixNameSpace){
		if (prefix==null){
			return new NameSpace(prefixNameSpace.getPrefix(),nameSpace);
		} else {
			return new NameSpace(prefix,prefixNameSpace.getNameSpace());
		}
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public String getNameSpace(){
		return nameSpace;
	}
	
	public String toString(){
		return prefix+":"+nameSpace;
	}
	
	public String getXMLArg(){
		return "xmlns=\""+nameSpace+"\"";
	}

}
