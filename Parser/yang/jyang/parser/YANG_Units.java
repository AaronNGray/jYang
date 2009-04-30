package jyang.parser;


public class YANG_Units extends SimpleNode {

    private String units = null;

  public YANG_Units(int id) {
    super(id);
  }

  public YANG_Units(yang p, int id) {
    super(p, id);
  }

    public void setUnits(String u){
	units = u;
    }

    public String getUnits(){
	return units;
    }

    public String toString(){
	return "units " + units + ";";
    }

}
