
package jyang;

public class YANG_Decimal64Spec extends SimpleNode {
	
	private String fractionDigit = null;
	
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