package jyang.parser;

import java.text.MessageFormat;

public abstract class MandatoryRefineNode extends ConfigRefineNode {

	private YANG_Mandatory mandatory = null;

	protected boolean b_mandatory = false;

	public MandatoryRefineNode(int id) {
		super(id);
	}

	public MandatoryRefineNode(yang p, int id) {
		super(p, id);
	}

	public void setMandatory(YANG_Mandatory m) {
		if (b_mandatory)
			YangErrorManager.add(m.getLine(), m.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("ad2"), "mandatory",
					getBody()));
		/*
		 * throw new YangParserException( "Mandatory already defined in " +
		 * getBody(), m .getLine(), m.getCol());
		 */
		b_mandatory = true;
		mandatory = m;
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public String toString(){
		String result = "";
		if (b_mandatory)
			result += getMandatory().toString() + "\n";
		result += super.toString() + "\n";
		return result;
	}

}
