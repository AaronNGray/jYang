package jyang.parser;

public abstract class Deviate extends SimpleYangNode {

	private YANG_Units units = null;
	private YANG_Default ydefault = null;

	boolean b_units = false, b_default = false;

	public Deviate(int i) {
		super(i);
	}

	public Deviate(yang p, int i) {
		super(p, i);
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setUnits(YANG_Units u) {
		if (!b_units) {
			this.units = u;
			b_units = true;
		} else
			YangErrorManager.add(u.getLine(), u.getCol(),
					YangErrorManager.messages.getString("units"));
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	public void setDefault(YANG_Default d) {
		if (!b_default) {
			this.ydefault = d;
			b_default = true;
		} else
			YangErrorManager.add(d.getLine(), d.getCol(),
					YangErrorManager.messages.getString("default"));
	}
	
	public boolean isBracked() {
		return b_units || b_default;
	}
	
	public String toString() {
		String result = "";
		if (b_units)
			result += units.toString() + "\n";
		if (b_default)
			result += ydefault.toString() + "\n";
		return result;
	}

}
