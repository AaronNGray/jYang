package jyang.parser;

public abstract class ErrorTagedNode extends DocumentedNode {

	private YANG_ErrorMessage errmess = null;
	private YANG_ErrorAppt errapptag = null;
	
	private boolean b_errmess = false, b_errapptag = false;
	

	public void setErrMess(YANG_ErrorMessage e) throws YangParserException {
		if (b_errmess)
			throw new YangParserException(
					"Error message already defined in must" , getLine(),
					getCol());
		b_errmess = true;
		bracked = true;
		errmess = e;
	}

	public YANG_ErrorMessage getErrMess() {
		return errmess;
	}

	public void setErrAppTag(YANG_ErrorAppt e) throws YangParserException {
		if (b_errapptag)
			throw new YangParserException(
					"Error App Tag already defined in must" , getLine(),
					getCol());
		b_errapptag = true;
		bracked = true;
		errapptag = e;
	}

	public YANG_ErrorAppt getErrAppTag() {
		return errapptag;
	}


	public boolean isBracked() {
		return bracked;
	}
	private boolean bracked = false;

	
	public ErrorTagedNode(int id) {
		super(id);
	}
	
	public ErrorTagedNode(yang p, int id) {
		super(p, id);
	}
	

	public String toString() {
		String result = new String();
		if (bracked) {
			result += "{\n";
			if (errmess != null)
				result += errmess + "\n";
			if (errapptag != null)
				result += errapptag + "\n";
			result += super.toString();
			result += "}";
		} else
			result += ";";
		return result;
	}

}
