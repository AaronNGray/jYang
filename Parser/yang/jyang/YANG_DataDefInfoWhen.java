package jyang;

public abstract class YANG_DataDefInfoWhen extends YANG_DataDefInfo {

	private YANG_When when = null;

	private boolean b_when = false;

	public YANG_DataDefInfoWhen(int id) {
		super(id);
	}

	public YANG_DataDefInfoWhen(yang p, int id) {
		super(p, id);
	}

	public boolean isBracked() {
		return super.isBracked() || b_when;
	}

	public YANG_When getWhen() {
		return when;
	}

	public void setWhen(YANG_When w) throws YangParserException {
		if (b_when)
			throw new YangParserException("When already defined in "
					+ getBody(), w.getLine(), w.getCol());
		b_when = true;
		this.when = w;
	}

	public String toString() {
		String result = "";

		if (b_when)
			result += getWhen().toString() + "\n";
		result += super.toString() + "\n";

		return result;
	}

}
