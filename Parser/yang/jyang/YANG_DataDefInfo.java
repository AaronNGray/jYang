package jyang;

import java.util.Vector;

public abstract class YANG_DataDefInfo extends YANG_DataDef {

	private Vector<YANG_IfFeature> ifFeatures = new Vector<YANG_IfFeature>();
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	
	private boolean b_status = false,
			b_description = false, b_reference = false;


	public YANG_DataDefInfo(int id) {
		super(id);
	}

	public YANG_DataDefInfo(yang p, int id) {
		super(p, id);
	}

	@Override
	public void check(YangContext context) throws YangParserException {
	}
	
	public Vector<YANG_IfFeature> getIfFeatures() {
		return ifFeatures;
	}
	
	public void addIfFeature(YANG_IfFeature i){
		ifFeatures.add(i);
	}

	
	public boolean isBracked(){
		return b_status || b_description || b_reference;
	}
	
	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in  "
					+ getBody(), s.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in " + getBody(), d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in " + getBody(), r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}
	
	public String toString(){
		String result = "";
		if (b_status)
			result += getStatus().toString() + "\n";
		if (b_description)
			result += getDescription() + "\n";
		if (b_reference)
			result += getReference() + "\n";
		return result;
	}
	
	

}
