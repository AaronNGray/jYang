package jyang.parser;

import java.util.Enumeration;

public class YANG_DeviateReplace extends DeviateAddReplace {

	private YANG_Type type = null;
	private boolean b_type = false;

	public YANG_DeviateReplace(int id) {
		super(id);
	}

	public YANG_DeviateReplace(yang p, int id) {
		super(p, id);
	}

	public YANG_Type getType() {
		return type;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (!b_type) {
			this.type = t;
			b_type = true;
		} else
			YangErrorManager.add(t.getLine(), t.getCol(),
					YangErrorManager.messages.getString("type"));
	}

	public boolean isBracked() {
		return b_type || super.isBracked();
	}

	public String toString() {
		String result = "deviate add";
		if (isBracked()) {
			result += "{\n";
			if (b_type)
				result += type.toString() + "\n";
			result += super.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

}
