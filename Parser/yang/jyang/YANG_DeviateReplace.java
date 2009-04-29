package jyang;

public class YANG_DeviateReplace extends YANG_DeviateConfig {

	private YANG_Type type = null;

	public YANG_DeviateReplace(int id) {
		super(id);
	}

	public YANG_DeviateReplace(yang p, int id) {
		super(p, id);
	}

	public YANG_Type getType() {
		return type;
	}

	public void setType(YANG_Type type) {
		this.type = type;
	}

}
