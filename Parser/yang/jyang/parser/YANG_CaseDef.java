package jyang.parser;


public interface  YANG_CaseDef extends Node{
	public  void check(YangContext context) throws YangParserException;
    public  String getBody();

}
