import java.util.Enumeration;
import java.util.Vector;

public class YANG_StringRestriction extends SimpleNode {

	private YANG_Length length = null;
	private Vector<YANG_Pattern> patterns = new Vector<YANG_Pattern>();

	private boolean b_length = false, b_pattern = false;

	public YANG_StringRestriction(int id) {
		super(id);
	}

	public YANG_StringRestriction(yang p, int id) {
		super(p, id);
	}

	public void setLength(YANG_Length l) throws YangParserException {
		if (b_length)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":length already defined");
		b_length = true;
		length = l;
	}

	public YANG_Length getLength() {
		return length;
	}

	public void addPattern(YANG_Pattern p)  throws YangParserException{
		
		patterns.add(p);
	}

	public Vector<YANG_Pattern> getPatterns() {
		return patterns;
	}

	public String toString() {
		String result = new String();
		if (length != null)
			result += length.toString() + "\n";
		for (Enumeration<YANG_Pattern> ep = patterns.elements(); ep.hasMoreElements();)
			result += ep.nextElement().toString() + "\n";
		return result;
	}
}
