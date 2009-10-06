package jyang.parser;


public class YANG_Contact extends SimpleNode implements YANG_Meta{

    private String contact = null;

  public YANG_Contact(int id) {
    super(id);
  }

  public YANG_Contact(yang p, int id) {
    super(p, id);
  }

    public void setContact(String c){
	contact = c;
    }

    public String getContact(){
	return contact;
    }

    public String toString(){
	return "contact " + contact + ";";
    }

}
