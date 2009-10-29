package jyang.parser;

public class YANG_Status extends SimpleYangNode {

	private String status = null;

	public YANG_Status(int id) {
		super(id);
	}

	public YANG_Status(yang p, int id) {
		super(p, id);
	}

	public void setStatus(String s) throws YangParserException {
		String st = YangBuiltInTypes.removeQuotesAndTrim(s);
		if (st.compareTo("current") != 0 && st.compareTo("obsolete") != 0
				&& st.compareTo("deprecated") != 0)
			YangErrorManager.add(filename, getLine(), getCol(), YangErrorManager.messages
					.getString("status_exp"));
		status = st;
	}

	public String getStatus() {
		return status;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "status " + status + ";";
	}

}
