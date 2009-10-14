package jyang.parser;


public class YANG_DeviateReplace extends DeviateAddReplace {

	private YANG_Type type = null;
	private boolean b_type = false;

	public YANG_DeviateReplace(int id) {
		super(id);
	}

	public YANG_DeviateReplace(yang p, int id) {
		super(p, id);
	}

	public YANG_Type getType() {
		return type;
	}

	public void setType(YANG_Type t)  throws YangParserException {
		if (b_type)
			throw new YangParserException(
					"Type already defined in  deviate-replace ", t
					.getLine(), t.getCol());
		this.type = t;
	}

}
