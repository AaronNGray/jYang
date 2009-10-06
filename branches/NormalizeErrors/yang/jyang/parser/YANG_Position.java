package jyang.parser;


public class YANG_Position extends SimpleNode {

    private String position = null;

  public YANG_Position(int id) {
    super(id);
  }

  public YANG_Position(yang p, int id) {
    super(p, id);
  }

    public void setPosition(String p){
	position = p;
    }

    public String getPosition(){
	return position;
    }

    public String toString(){
	return position + ";";
    }
}
