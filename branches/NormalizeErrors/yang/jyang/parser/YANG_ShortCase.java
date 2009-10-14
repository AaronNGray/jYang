package jyang.parser;


public interface YANG_ShortCase  extends YangNode{

    public void check(YangContext context) throws YangParserException;
    public String getBody();

}
