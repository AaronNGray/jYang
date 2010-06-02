package jyang.parser;

public class YANG_Revision extends DocumentedNode {

	private String date = null;


	public YANG_Revision(int id) {
		super(id);
	}

	public YANG_Revision(yang p, int id) {
		super(p, id);
	}

	public void setDate(String d) {
		date = unquote(d);
	}

	public String getDate() {
		return date;
	}


	public String toString() {
		String result = new String();
		result += "revision " + date;
		result += super.toString();
		return result;
	}

}
