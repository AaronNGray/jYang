package jyang.parser;


public interface  YANG_CaseDataDef extends YangNode{
	public  void check(YangContext context) throws YangParserException;
    public  String getBody();

}
