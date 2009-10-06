

package jyang.parser;


public class YANG_IfFeature extends SimpleNode {
	
	private String iffeature = null;
	
  public String getIfFeature() {
		return iffeature;
	}

	public void setIfFeature(String iffeature) {
		this.iffeature = iffeature;
	}

public YANG_IfFeature(int id) {
    super(id);
  }

  public YANG_IfFeature(yang p, int id) {
    super(p, id);
  }

}
