package jyang.parser;

public class YANG_Include extends ImportIncludeNode implements YANG_Linkage {

	private String include = null;



	public YANG_Include(int id) {
		super(id);
	}

	public YANG_Include(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		include = unquote(s);
	}

	public String getIncludedModule() {
		return include;
	}
	
	public boolean isBracked() {
		return super.isBracked();
	}

	public String toString() {
		String result = "";
		result += " include " + include;
		if (isBracked())
			result += "{\n" + super.toString() + "\n}";
		else
			result += ";";
		return result;
	}

}
