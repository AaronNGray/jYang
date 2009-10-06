package jyang.parser;


public class YANG_Base extends SimpleNode {

	private String base = null;

	public YANG_Base(int id) {
		super(id);
	}

	public YANG_Base(yang p, int id) {
		super(p, id);
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}
}