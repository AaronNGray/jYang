package jyang.parser;

public class YANG_Value extends SimpleYangNode {

	private String value = null;

	public YANG_Value(int id) {
		super(id);
	}

	public YANG_Value(yang p, int id) {
		super(p, id);
	}

	public void setValue(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return "value " + value + ";";
	}

}
