public class YangParserException extends ParseException{

    public YangParserException(String m){
	super(m);
    }
    public YangParserException(String m, int l, int c){
	this(l+"."+c+":"+m);
    }
}
