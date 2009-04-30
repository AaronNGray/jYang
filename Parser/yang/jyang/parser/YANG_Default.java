package jyang.parser;



public class YANG_Default extends SimpleNode {

	private String defaultstr = null;

	public YANG_Default(int id) {
		super(id);
	}

	public YANG_Default(yang p, int id) {
		super(p, id);
	}

	public void setDefault(String d) {
		defaultstr = d;
	}

	public String getDefault() {
		return defaultstr;
	}

	public void check(YangContext context, YANG_Type t)
			throws YangParserException {
		t.checkValue(context, getDefault());
	}

	public void check(YangContext context, YANG_RefineLeaf rleaf) {

	}

	public void check(YangContext context, YANG_Choice choice)
			throws YangParserException {
		choice.checkDefault(context, this);
	}

	public String toString() {
		return "default " + defaultstr + ";";
	}

}
