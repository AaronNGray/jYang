/* Generated By:JJTree: Do not edit this line. YANG_Identity.java */

package jyang;

public class YANG_Identity extends YANG_Body {

	private String identity = null;

	private YANG_Base base = null;

	private YANG_Status status = null;

	private YANG_Description description = null;

	private YANG_Reference reference = null;

	private boolean b_base = false, b_status = false, b_description = false,
			b_reference = false;

	public YANG_Identity(int id) {
		super(id);
	}

	public YANG_Identity(yang p, int id) {
		super(p, id);
	}

	public void setIdentity(String i) {
		identity = i;
	}

	public void setBase(YANG_Base b)  throws YangParserException {
		if (b_base)
			throw new YangParserException(
					"Base is already defined in identity " + identity, b
							.getLine(), b.getCol());
		base = b;
		b_base = true;
	}

	public void setStatus(YANG_Status s)  throws YangParserException{
		if (b_status)
			throw new YangParserException(
					"Status is already defined in identity " + identity, s
							.getLine(), s.getCol());
		status = s;
		b_status = true;
	}

	public void setDescription(YANG_Description d)  throws YangParserException{
		if (b_description)
			throw new YangParserException(
					"Description is already defined in identity " + identity, d
							.getLine(), d.getCol());
		description = d;
		b_description = true;
	}

	public void setReference(YANG_Reference r)  throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference is already defined in identity " + identity, r
							.getLine(), r.getCol());
		reference = r;
		b_reference = true;
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBody() {
		return identity;
	}

}