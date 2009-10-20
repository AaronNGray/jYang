package jyang.parser;

import java.text.MessageFormat;
import java.util.Vector;

public abstract class StatuedNode extends DocumentedNode {

	private YANG_Status status = null;
	private boolean b_status = false;

	public StatuedNode(int i) {
		super(i);
	}

	public StatuedNode(yang p, int id) {
		super(p, id);
	}

	public void setStatus(YANG_Status s) {
		if (b_status)
			YangErrorManager.add(s.getLine(), s.getCol(),
					YangErrorManager.messages.getString("status"));
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void check(YangContext context) throws YangParserException {
	}

	public String toString() {
		String result = "";
		if (b_status)
			result += "status " + status + ";";
		result += super.toString() + "\n";
		return result;
	}

}