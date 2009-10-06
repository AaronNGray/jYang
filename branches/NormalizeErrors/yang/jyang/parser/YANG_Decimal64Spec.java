
package jyang.parser;


public class YANG_Decimal64Spec extends SimpleNode {
	
	private String fractionDigit = null;
	private YANG_Range range = null;
	
  public YANG_Range getRange() {
		return range;
	}

	public void setRange(YANG_Range range) {
		this.range = range;
	}

public String getFractionDigit() {
		return fractionDigit;
	}

	public void setFractionDigit(String fractionDigit) {
		this.fractionDigit = fractionDigit;
	}

public YANG_Decimal64Spec(int id) {
    super(id);
  }

  public YANG_Decimal64Spec(yang p, int id) {
    super(p, id);
  }

}