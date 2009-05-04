package jyang.parser;


public class YANG_Argument extends SimpleNode {

    private String argument = null;
    private YANG_Yin yin = null;

    private boolean bracked = false;

  public YANG_Argument(int id) {
    super(id);
  }

  public YANG_Argument(yang p, int id) {
    super(p, id);
  }

    public void setArgument(String a){
	argument = a;
    }

    public String getArgument(){
	return argument;
    }

    public void setYin(YANG_Yin y){
	yin = y;
	bracked = true;
    }

    public YANG_Yin getYin(){
	return yin;
    }

    public boolean isBracked(){
	return bracked;
    }

    public String toString(){
	String result = new String();
	result += "argument " + argument;
	if(yin != null)
	    result += "{" + yin.toString() + "}";
	else
	    result += ";";
	return result;
    }


}