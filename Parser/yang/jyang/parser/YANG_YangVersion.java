package jyang.parser;


public class YANG_YangVersion extends SimpleNode implements YANG_Header {

    private String version = null;

  public YANG_YangVersion(int id) {
    super(id);
  }

  public YANG_YangVersion(yang p, int id) {
    super(p, id);
  }

    public void setVersion(String v){
	version = v;
    }

    public String getYangVersion(){
	return version;
    }
    
    public String toString(){
	return "yang-version " + version + ";";
    }


}
