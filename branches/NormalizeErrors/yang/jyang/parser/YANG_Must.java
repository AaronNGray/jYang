package jyang.parser;


public class YANG_Must extends ErrorTagedNode {

	private String must = null;
	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean bracked = false;


	public YANG_Must(int id) {
		super(id);
	}

	public YANG_Must(yang p, int id) {
		super(p, id);
	}

	public void setMust(String m) {
		must = m;
	}

	public String getMust() {
		return must;
	}


	public void check(YangContext context) {
	}

	public String toString() {
		String result = new String();
		result += "must " + must;
		if (bracked) {
			result += "{\n";
			result += super.toString();
		} else
			result += ";";

		return result;
	}

}
