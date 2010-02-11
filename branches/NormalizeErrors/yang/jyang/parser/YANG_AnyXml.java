package jyang.parser;

import java.text.MessageFormat;

public class YANG_AnyXml extends MustDataDef implements YANG_CaseDataDef,
		YANG_ShortCase {

	private String anyxml = null;
	private YANG_Mandatory mandatory = null;
	private boolean b_mandatory = false;

	public YANG_AnyXml(int id) {
		super(id);
	}

	public YANG_AnyXml(yang p, int id) {
		super(p, id);
	}

	public void setAnyXml(String a) {
		anyxml = a;
	}

	public String getBody() {
		return getAnyXml();
	}

	public String getAnyXml() {
		return anyxml;
	}

	public void setMandatory(YANG_Mandatory m) {
		if (!b_mandatory) {
			b_mandatory = true;
			mandatory = m;
		} else
			YangErrorManager.tadd(filename, m.getLine(), m.getCol(), "unex_kw",
					"mandatory");
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public boolean isBracked() {
		return super.isBracked() || b_mandatory;
	}

	public void check(YangContext context) throws YangParserException {
		if (b_config) {
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfigStr().compareTo("false") == 0
					&& getConfig().getConfigStr().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":config to true and parent config to false");
		}

	}

	public String toString() {
		String result = new String();
		result += "anyxml " + anyxml;
		if (isBracked()) {
			result += " {\n";
			if (b_mandatory)
				result += mandatory.toString() + "\n";
			result += super.toString() + "\n";
			result += "\n}";
		} else
			result += ";";

		return result;
	}

	public YANG_AnyXml clone() {
		YANG_AnyXml ca = new YANG_AnyXml(parser, id);
		ca.setAnyXml(getAnyXml());
		return ca;
	}
}
