package jyang.parser;


public class YANG_NameSpace extends SimpleNode implements YANG_Header {

    private String namespace = null;

  public YANG_NameSpace(int id) {
    super(id);
  }

  public YANG_NameSpace(yang p, int id) {
    super(p, id);
  }

    public void setNameSpace(String n){
	namespace = n;
    }

    public String getNameSpace(){
	return namespace;
    }

    public String toString(){
	return "namespace " + namespace +";";
    }

}