package jyang.parser;


public abstract class YANG_DeviateUnitsDefault extends SimpleNode {

	private YANG_Units units = null;
	private YANG_Default ydefault = null;

	public YANG_DeviateUnitsDefault(int i) {
		super(i);
	}

	public YANG_DeviateUnitsDefault(yang p, int i) {
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
