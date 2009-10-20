package jyang.parser;

import java.text.MessageFormat;

public abstract class ConfigDataDef extends YANG_DataDef {

	public ConfigDataDef(int id) {
		super(id);
	}

	public ConfigDataDef(yang p, int id) {
		super(p, id);
	}

	private YANG_Config config = null;

	protected boolean b_config = false;

	public void setConfig(YANG_Config c) {
		if (!b_config) {
			b_config = true;
			config = c;
		} else
			YangErrorManager.add(c.getLine(), c.getCol(),
					YangErrorManager.messages.getString("config"));

	}

	public YANG_Config getConfig() {
		return config;
	}

	public String toString() {
		String result = "";
		result += "config " + config + ";";
		return result;
	}

}