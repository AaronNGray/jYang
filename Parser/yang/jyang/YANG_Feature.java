/* Generated By:JJTree: Do not edit this line. YANG_Feature.java */

package jyang;

import java.util.Vector;

public class YANG_Feature extends YANG_Body {

	private String feature = null;

	private Vector<YANG_IfFeature> iffeatures = new Vector<YANG_IfFeature>();

	private YANG_Status status = null;

	private YANG_Description description = null;

	private YANG_Reference reference = null;

	private boolean b_status = false, b_description = false,
			b_reference = false;

	public YANG_Feature(int id) {
		super(id);
	}

	public YANG_Feature(yang p, int id) {
		super(p, id);
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description is already defined in feature " + feature, d
							.getLine(), d.getCol());
		this.description = description;
		b_description = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void setReference(YANG_Reference r)  throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference is already defined in feature " + feature, r
							.getLine(), r.getCol());
		this.reference = r;
		b_reference = true;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setStatus(YANG_Status s)   throws YangParserException {
		if (b_status)
			throw new YangParserException(
					"Status is already defined in feature " + feature, s
							.getLine(), s.getCol());
		this.status = s;
		b_status = true;
	}

	public Vector<YANG_IfFeature> getIffeatures() {
		return iffeatures;
	}
	
	public void addIfFeature(YANG_IfFeature ife) {
		iffeatures.add(ife);
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBody() {
		
		return feature;
	}

}
