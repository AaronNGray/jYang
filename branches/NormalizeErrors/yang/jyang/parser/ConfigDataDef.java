package jyang.parser;

import java.text.MessageFormat;

public abstract class ConfigDataDef extends YANG_DataDef {

	public ConfigDataDef(int id) {
		super(id);
	}

	public ConfigDataDef(yang p, int id) {
		super(p, id);
	}

	protected YANG_Config config = null;

	protected boolean b_config = false;

	public void setConfig(YANG_Config c) {
		if (!b_config) {
			b_config = true;
			config = c;
		} else
			YangErrorManager.tadd(filename, c.getLine(), c.getCol(), "unex_kw",
					"config");

	}

	public YANG_Config getConfig() {
		return config;
	}

	public String toString() {
		String result = "";
		if (b_config)
			result += "config " + config + ";";
		return result;
	}

}
