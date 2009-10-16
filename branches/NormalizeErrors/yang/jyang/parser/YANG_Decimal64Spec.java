package jyang.parser;

public class YANG_Decimal64Spec extends SimpleYangNode {

	private String fractionDigit = null;

	public YANG_Decimal64Spec(int id) {
		super(id);
	}

	public YANG_Decimal64Spec(yang p, int id) {
		super(p, id);
	}
	public String getFractionDigit() {
		return fractionDigit;
	}

	public void setFractionDigit(String fractionDigit) {
		this.fractionDigit = fractionDigit;
	}
	
	public String toString() {
		return "fraction-digits " + fractionDigit;
		
	}


}