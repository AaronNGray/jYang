package jyang.parser;

public class YANG_Enum extends StatuedNode {

	private String enumid = null;
	private YANG_Value value = null;

	private boolean b_value = false;

	public YANG_Enum(int id) {
		super(id);
	}

	public YANG_Enum(yang p, int id) {
		super(p, id);
	}

	public void setEnum(String e) {
		enumid = e;
	}

	public String getEnum() {
		return enumid;
	}

	public void setValue(YANG_Value v) {
		if (!b_value) {
			value = v;
			b_value = true;
		} else
			YangErrorManager.add(v.getLine(), v.getCol(),
					YangErrorManager.messages.getString("value"));
	}

	public YANG_Value getValue() {
		return value;
	}

	public boolean isBracked() {
		return b_value || super.isBracked();
	}

	public String toString() {
		String result = "";
		result += "enum " + enumid;
		if (isBracked()) {
			result += " {\n";
			if (value != null)
				result += value.toString() + "\n";
			result += super.toString() + "\n";
			result += " } ";
		} else
			result += ";";
		return result;
	}

}
