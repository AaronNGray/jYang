package jyang.parser;


public class YANG_Presence extends SimpleNode {

    private String presence = null;

  public YANG_Presence(int id) {
    super(id);
  }

  public YANG_Presence(yang p, int id) {
    super(p, id);
  }

    public void setPresence(String p){
	presence = p;
    }

    public String getPresence(){
	return presence;
    }
    
    public void check(YangContext context){
    }

    public String toString(){
	return "presence " + presence + ";";
    }

}