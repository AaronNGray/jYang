package jyang.parser;


public abstract class Deviate extends SimpleYangNode {

	private YANG_Units units = null;
	private YANG_Default ydefault = null;

	public Deviate(int i) {
		super(i);
	}

	public Deviate(yang p, int i) {
		super(p, i);
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setUnits(YANG_Units units) {
		this.units = units;
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	public void setDefault(YANG_Default ydefault) {
		this.ydefault = ydefault;
	}

}
