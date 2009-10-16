package jyang.parser;

public class YANG_Yin extends SimpleYangNode {

	private String yin = null;

	public YANG_Yin(int id) {
		super(id);
	}

	public YANG_Yin(yang p, int id) {
		super(p, id);
	}

	public String getYin() {
		return yin;
	}

	public void setYin(String y) {
		if (!(y.compareTo("true") == 0 || y.compareTo("false") == 0
				|| y.compareTo("\"true\"") == 0 || y.compareTo("\"false\"") == 0))

			YangErrorManager.add(getLine(), getCol(), YangErrorManager.messages
					.getString("yin_exp"));
		yin = y;
	}

	public String toString() {
		return "yin-element " + yin + ";";
	}
}
