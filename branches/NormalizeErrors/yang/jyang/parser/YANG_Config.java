package jyang.parser;


public class YANG_Config extends SimpleNode {

	private String config = null;

	public YANG_Config(int id) {
		super(id);
	}

	public YANG_Config(yang p, int id) {
		super(p, id);
	}

	public void setConfig(String c) throws YangParserException {
		if (c == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in config value");
		String ct = YangBuiltInTypes.removeQuotesAndTrim(c);
		if (ct.compareTo("true") != 0 && ct.compareTo("false") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":config value not correct : " + ct);
		config = c;
	}

	public String getConfig() {
		return config;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "config " + config + ";";
	}
}
