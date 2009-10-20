package jyang.parser;

import java.text.MessageFormat;

public abstract class StatuedBody extends YANG_Body {

	public StatuedBody(int id) {
		super(id);
	}

	public StatuedBody(yang p, int id) {
		super(p, id);
	}

	private YANG_Status status = null;
	private boolean b_status = false;

	public void setStatus(YANG_Status s) {
		if (!b_status) {
			b_status = true;
			status = s;
		} else
			YangErrorManager.add(s.getLine(), s.getCol(),
					YangErrorManager.messages.getString("status"));

	}

	public YANG_Status getStatus() {
		return status;
	}

	public boolean isBracked() {
		return super.isBracked() || b_status;
	}

	public String toString() {
		String result = "";
		result += "status " + status + ";";
		return result;
	}

}
