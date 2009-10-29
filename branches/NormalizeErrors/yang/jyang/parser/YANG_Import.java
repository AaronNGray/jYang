package jyang.parser;

import java.text.MessageFormat;

public class YANG_Import extends ImportIncludeNode implements YANG_Linkage {

	private String importstr = null;
	private YANG_Prefix prefix = null;

	private boolean b_prefix = false;

	public YANG_Import(int id) {
		super(id);
	}

	public YANG_Import(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		importstr = s;
	}

	public String getImportedModule() {
		return importstr;
	}

	public void setPrefix(YANG_Prefix p) {
		if (!b_prefix) {
			prefix = p;
			b_prefix = true;
		} else
			YangErrorManager
			.add(filename, p.getLine(), p.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("unex_kw"),
					"prefix"));
	}

	public YANG_Prefix getPrefix() {
		return prefix;
	}

	public String toString() {
		String result = " import " + importstr + " {" + prefix.toString();
		result += super.toString() + "\n";
		result += "}";
		return result;

	}

}
