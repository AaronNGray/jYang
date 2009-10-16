package jyang.parser;

public abstract class ErrorTagedNode extends DocumentedNode {

	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;

	private boolean b_errmess = false, b_errapptag = false;

	public void setErrMess(YANG_ErrorMessage e) throws YangParserException {
		if (!b_errmess) {
			b_errmess = true;
			errmess = e;
		} else
			YangErrorManager.add(e.getLine(), e.getCol(),
					YangErrorManager.messages.getString("errmess"));
	}

	public YANG_ErrorMessage getErrMess() {
		return errmess;
	}

	public void setErrAppTag(YANG_ErrorAppt e) throws YangParserException {
		if (!b_errapptag) {
			b_errapptag = true;
			errapptag = e;
		} else
			YangErrorManager.add(e.getLine(), e.getCol(),
					YangErrorManager.messages.getString("errapp"));
	}

	public YANG_ErrorAppt getErrAppTag() {
		return errapptag;
	}

	public boolean isBracked() {
		return b_errapptag || b_errmess || super.isBracked();
	}

	public ErrorTagedNode(int id) {
		super(id);
	}

	public ErrorTagedNode(yang p, int id) {
		super(p, id);
	}

	public String toString() {
		String result = "";
		result += super.toString();
		if (b_errmess)
			result += errmess + "\n";
		if (b_errapptag)
			result += errapptag + "\n";
		return result;
	}

}
