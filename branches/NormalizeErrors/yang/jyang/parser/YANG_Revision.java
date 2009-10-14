package jyang.parser;


public class YANG_Revision extends SimpleYangNode {

    private String date = null;
    private YANG_Description description = null;

  public YANG_Revision(int id) {
    super(id);
  }

  public YANG_Revision(yang p, int id) {
    super(p, id);
  }
    public void setDate(String d){
	date = d;
    }

    public String getDate(){
	return date;
    }

    public void setDescription(YANG_Description d){
	description = d;
    }

    public YANG_Description getDescription(){
	return description;
    }

    public String toString(){
	String result = new String();
	result += "revision " + date;
	if(description != null)
	    result += "{\n" + description.toString() + "\n}";
	else
	    result += ";";
	return result;
    }


}
