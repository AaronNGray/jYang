package jyang.parser;

import java.text.MessageFormat;

public class YANG_Config extends SimpleYangNode {

	private String config = null;

	public YANG_Config(int id) {
		super(id);
	}

	public YANG_Config(yang p, int id) {
		super(p, id);
	}

	public void setConfig(String c) {
		String ct = unquote(c);
		if (ct.compareTo("true") != 0 && ct.compareTo("false") != 0)
			YangErrorManager.tadd(filename, getLine(), getCol(), "config_expr");
		config = c;
	}

	public String getConfigStr() {
		return config;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "config " + config + ";";
	}
}
