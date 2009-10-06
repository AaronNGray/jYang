package jyang.parser;


public class YANG_Status extends SimpleNode {

	private String status = null;

	public YANG_Status(int id) {
		super(id);
	}

	public YANG_Status(yang p, int id) {
		super(p, id);
	}

	public void setStatus(String s) throws YangParserException {
		if (s == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in status value");
		String st = YangBuiltInTypes.removeQuotesAndTrim(s);
		if (st.compareTo("current") != 0 && st.compareTo("obsolete") != 0
				&& st.compareTo("deprecated") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":status value not correct : " + s);
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
