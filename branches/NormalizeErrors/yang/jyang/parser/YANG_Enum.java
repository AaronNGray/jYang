package jyang.parser;


public class YANG_Enum extends SimpleNode {

    private String enumid = null;
    private YANG_Value value = null;
    private YANG_Status status = null;
    private YANG_Description description = null;
    private YANG_Reference reference = null;

    private boolean bracked = false;

  public YANG_Enum(int id) {
    super(id);
  }

  public YANG_Enum(yang p, int id) {
    super(p, id);
  }

    public void setEnum(String e){
	enumid = e;
    }

    public String getEnum(){
	return enumid;
    }

    public void setValue(YANG_Value v){
	value = v;
	bracked = true;
    }

    public YANG_Value getValue(){
	return value;
    }

    public void setStatus(YANG_Status s){
	status = s;
	bracked = true;
    }

    public YANG_Status getStatus(){
	return status;
    }

    public void setDescription(YANG_Description d){
	description = d;
	bracked = true;
    }

    public YANG_Description getDescription(){
	return description;
    }

    public void setReference(YANG_Reference r){
	reference = r;
	bracked = true;
    }

    public YANG_Reference getReference(){
	return reference;
    }

    public boolean isBracked(){
	return bracked;
    }
    
    public String toString(){
	String result = new String();
	result += "enum " + enumid;
	if(bracked){
	    result += " {\n";
	    if(value != null)
		result += value.toString() + "\n";
	    if(status != null)
		result += status.toString() + "\n";
	    if(description != null)
		result += description.toString() + "\n";
	    if(reference != null)
		result += reference.toString() + "\n";
	    result += " } ";
	}
	else
	    result += ";";
	return result;
    }

}
