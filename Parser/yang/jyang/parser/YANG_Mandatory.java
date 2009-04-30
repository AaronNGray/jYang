package jyang.parser;


public class YANG_Mandatory extends SimpleNode {

    private String mandatory = null;

  public YANG_Mandatory(int id) {
    super(id);
  }

  public YANG_Mandatory(yang p, int id) {
    super(p, id);
  }

    public void setMandatory(String m){
	mandatory = m;
    }

    public String getMandatory(){
	return mandatory;
    }

    public void check(YangContext context){
    }

    public String toString(){
	return "mandatory " + mandatory + ";";
    }

}
