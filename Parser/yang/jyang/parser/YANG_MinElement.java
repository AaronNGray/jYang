package jyang.parser;


public class YANG_MinElement extends SimpleNode {

    private String min = null;

  public YANG_MinElement(int id) {
    super(id);
  }

  public YANG_MinElement(yang p, int id) {
    super(p, id);
  }

    public void setMinElement(String m){
	min = m;
    }

    public String getMinElement(){
	return min;
    }

    public void check(YangContext context){
    }

    public String toString(){
	return "min-elements " + min + ";";
    }

}