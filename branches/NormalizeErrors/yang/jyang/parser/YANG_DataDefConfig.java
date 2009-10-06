package jyang.parser;


public abstract class YANG_DataDefConfig extends YANG_DataDefInfoWhen {
	
	private YANG_Config config = null;
	
	protected boolean b_config = false;

	public YANG_DataDefConfig(int id) {
		super(id);
	}
	public YANG_DataDefConfig(yang p, int id) {
		super(p, id);
	}
	
	public boolean isBracked(){
		return super.isBracked() || b_config;
	}
	
	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in  " + getBody(), c
							.getLine(), c.getCol());
		b_config = true;
		config = c;
	}

	public YANG_Config getConfig() {
		return config;
	}
	
	public String toString(){
		String result = "";
		if (b_config)
			result += getConfig().toString();
		result += super.toString();
		return result;
	}

}
