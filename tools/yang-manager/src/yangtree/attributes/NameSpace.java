package yangtree.attributes;

import java.io.Serializable;

/**
 * Represents the namespace of a node. It can be a prefix, a complete namespace or both.
 * 
 */
@SuppressWarnings("serial")
public class NameSpace implements Serializable{
	
	private String prefix = null;
	private String nameSpace = null;
	
	/**
	 * Creates a new namespace.
	 */
	public NameSpace(String nameSpace){
		this.nameSpace = nameSpace;
	}
	
	/**
	 * Creates a complete namespace that know the prefix used to reference it.
	 */
	public NameSpace(String prefix,String nameSpace){
		this.prefix = prefix;
		this.nameSpace = nameSpace;
	}
	
	/**
	 * Creates a new namespace. It can be either a complete namespace or just a prefix.
	 * @param nameSpace : the namespace.
	 * @param isAPrefix : specifies is this namespace is just a prefix or a complete namespace.
	 */
	public NameSpace(String nameSpace, boolean isAPrefix){
		if (isAPrefix){
			this.prefix = nameSpace;
		} else {
			this.nameSpace = nameSpace;
		}
	}
	
	/**
	 * Merge this NameSpace with a prefix so that the new NameSpace know the prefix used to reference it.
	 * @param prefixNameSpace : the prefix.
	 * @return a NameSpace that link this namespace with the given prefix.
	 */
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
	
	/**
	 * Returns the XML-formated designation of this NameSpace.
	 */
	public String getXMLArg(){
		return "xmlns=\""+nameSpace+"\"";
	}

}
