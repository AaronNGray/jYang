package jyang.parser;


public class YANG_ErrorMessage extends SimpleYangNode {
	
	private String errormessage = null;
	
  public YANG_ErrorMessage(int id) {
    super(id);
  }

  public YANG_ErrorMessage(yang p, int id) {
    super(p, id);
  }
  
  public void setErrorMessage(String e){
	  errormessage = e;
  }
  
  public String getErrorMessage() {
	  return errormessage;
  }
  

	public String toString(){
		return "error-message " + errormessage + ";";
	}

}
