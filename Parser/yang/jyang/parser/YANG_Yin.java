package jyang.parser;


public class YANG_Yin extends SimpleNode {

    private String yin = null;

  public YANG_Yin(int id) {
    super(id);
  }

  public YANG_Yin(yang p, int id) {
    super(p, id);
  }

    public String getYin(){
	return yin;
    }

    public void setYin(String y) throws YangParserException
{
		if(!(y.compareTo("true") == 0 || y.compareTo("false") == 0 ||
	     y.compareTo("\"true\"") == 0 || y.compareTo("\"false\"") == 0))
	    throw new YangParserException("A yin statement must be true or false in yin-element ",
	    getLine(),getCol());
	yin = y;
    }

    public String toString(){
	return "yin-element " + yin + ";";
    }
}
