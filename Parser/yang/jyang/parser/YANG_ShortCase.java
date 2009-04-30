package jyang.parser;


public interface YANG_ShortCase  extends Node{

    public void check(YangContext context) throws YangParserException;
    public String getBody();

}
