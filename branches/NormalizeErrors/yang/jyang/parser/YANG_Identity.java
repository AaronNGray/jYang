/* Generated By:JJTree: Do not edit this line. YANG_Identity.java */

package jyang.parser;


public class YANG_Identity extends StatuedBody {

	private String identity = null;

	private YANG_Base base = null;


	public String getIdentity() {
		return identity;
	}

	public YANG_Base getBase() {
		return base;
	}

	private boolean b_base = false;

	public YANG_Identity(int id) {
		super(id);
	}

	public YANG_Identity(yang p, int id) {
		super(p, id);
	}

	public void setIdentity(String i) {
		identity = i;
	}

	public void setBase(YANG_Base b)  {
		if (!b_base) {
		base = b;
		b_base = true;
		}
		else
			YangErrorManager.add(b.getLine(), b.getCol(),
					YangErrorManager.messages.getString("base"));
	}
	
	public boolean isBracked() {
		return b_base || super.isBracked();
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBody() {
		return identity;
	}
	
	public String toString() {
		String result = "";
		result += "identity " + identity;
		if (isBracked()) {
			result += "\n{";
			result += super.toString() + "\n";
			result += "}";
		}
		else
			result += ";";
		return result;
	}

}
