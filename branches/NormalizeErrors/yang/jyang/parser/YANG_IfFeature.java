package jyang.parser;

public class YANG_IfFeature extends SimpleYangNode {

	private String iffeature = null;

	public YANG_IfFeature(int id) {
		super(id);
	}

	public YANG_IfFeature(yang p, int id) {
		super(p, id);
	}

	public String getIfFeature() {
		return iffeature;
	}

	public void setIfFeature(String iffeature) {
		this.iffeature = unquote(iffeature);
	}
	
	public String toString() {
		return "if-feature " + iffeature;
	}

}
