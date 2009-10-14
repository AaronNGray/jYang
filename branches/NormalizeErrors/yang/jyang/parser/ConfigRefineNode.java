package jyang.parser;

import java.text.MessageFormat;

public abstract class ConfigRefineNode extends YANG_Refine {

	protected YANG_Config config = null;

	protected boolean b_config = false;

	public ConfigRefineNode(int id) {
		super(id);
	}

	public ConfigRefineNode(yang p, int id) {
		super(p, id);
	}

	public void setConfig(YANG_Config c){
		if (b_config)
			YangErrorManager.add(c.getLine(), c.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("ad2"), "config",
					getBody()));
		
		b_config = true;
		config = c;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public String toString() {
		String result = "";
		if (b_config)
			result += getConfig().toString();
		result += super.toString();
		return result;
	}

}
