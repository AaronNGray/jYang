package jyang.parser;

public class YANG_When extends SimpleYangNode {

	private String when = null;

	public YANG_When(int id) {
		super(id);
	}

	public YANG_When(yang p, int id) {
		super(p, id);
	}

	public void setWhen(String w) {
		when = w;
	}

	public String getWhen() {
		return when;
	}

	public void check(YangContext context) {
	}

	public String toString() {
		return "when " + when + ";";
	}

}