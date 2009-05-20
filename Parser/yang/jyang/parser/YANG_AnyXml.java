package jyang.parser;



public class YANG_AnyXml extends  YANG_DataDefFullInfo implements
		YANG_CaseDef, YANG_ShortCase {

	private String anyxml = null;

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

	
	

	public boolean isBracked() {
		return super.isBracked();
	}

	public void check(YangContext context) throws YangParserException{
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					getConfig().getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "anyxml " + anyxml;
		if (isBracked()) {
			result += " {\n";
			result += super.toString();
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
