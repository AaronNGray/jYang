package jyang.parser;


public class YANG_MaxElement extends SimpleNode {

    private String max = null;

  public YANG_MaxElement(int id) {
    super(id);
  }

  public YANG_MaxElement(yang p, int id) {
    super(p, id);
  }

    public void setMaxElement(String m){
	max = m;
    }

    public String getMaxElement(){
	return max;
    }

    public void check(YangContext context){
    }

    public String toString(){
	return "max-elements " + max + ";";
    }
}
