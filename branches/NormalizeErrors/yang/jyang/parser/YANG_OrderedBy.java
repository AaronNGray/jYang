package jyang.parser;


public class YANG_OrderedBy extends SimpleYangNode {

	private String orderedby = null;

	public YANG_OrderedBy(int id) {
		super(id);
	}

	public YANG_OrderedBy(yang p, int id) {
		super(p, id);
	}

	public void setOrderedBy(String o) throws YangParserException {
		if (o == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":Panic in ordered-by value");
		String ot = YangBuiltInTypes.removeQuotesAndTrim(o);
		if (ot.compareTo("system") != 0 && ot.compareTo("user") != 0)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":config value not correct " + ot);
		orderedby = ot;
	}

	public String getOrderedBy() {
		return orderedby;
	}

	public String toString() {
		return "ordered-by " + orderedby + ";";
	}

}
