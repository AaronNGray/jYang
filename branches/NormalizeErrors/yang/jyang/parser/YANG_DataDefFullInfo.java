package jyang.parser;


public abstract class YANG_DataDefFullInfo extends YANG_DataDefConfigMust {

	private YANG_Mandatory mandatory = null;
	
	protected boolean b_mandatory = false;

	public YANG_DataDefFullInfo(int id) {
		super(id);
	}
	public YANG_DataDefFullInfo(yang p, int id) {
		super(p, id);
	}
	
	public void setMandatory(YANG_Mandatory m) throws YangParserException {
		if (b_mandatory)
			throw new YangParserException(
					"Mandatory already defined in " + getBody(), m
							.getLine(), m.getCol());
		b_mandatory = true;
		mandatory = m;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}
	
	public boolean isBracked(){
		return super.isBracked() || b_mandatory;
	}
	
	public String toString(){
		String result = "";
		if (b_mandatory)
			result += getMandatory().toString() + "\n";
		result += super.toString() + "\n";
		return result;
	}


}
