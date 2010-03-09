package jyang.parser;

import java.text.MessageFormat;

public class YANG_AnyXml extends MustDataDef implements 
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
		anyxml = unquote(a);
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

	public void check(YangContext context) {
		if (b_config) {
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig != null)
				if (parentConfig.getConfigStr().compareTo("false") == 0
						&& getConfig().getConfigStr().compareTo("true") == 0)
					YangErrorManager.tadd(filename, getLine(), getCol(),
							"config_parent", "anyxml", anyxml);
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
		YANG_AnyXml cl = new YANG_AnyXml(parser, id);
		cl.setAnyXml(getAnyXml());
		cl.setFileName(getFileName());
		cl.setCol(getCol());
		cl.setLine(getLine());
		cl.setContext(getContext());
		cl.setConfig(getConfig());
		cl.setDescription(getDescription());
		cl.setMandatory(getMandatory());
		cl.setIfFeature(getIfFeatures());
		cl.setMusts(getMusts());
		cl.setUnknowns(getUnknowns());
		cl.setReference(getReference());
		cl.setStatus(getStatus());
		cl.setWhen(getWhen());
		return cl;
	}
}
