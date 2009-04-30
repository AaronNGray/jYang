package jyang.parser;


public class YANG_Include extends SimpleNode implements YANG_Linkage {

	private String include = null;
	
	private YANG_Revision revision = null;
	
	public YANG_Revision getRevision() {
		return revision;
	}

	public void setRevision(YANG_Revision revision) {
		this.revision = revision;
	}

	public YANG_Include(int id) {
		super(id);
	}

	public YANG_Include(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		include = s;
	}

	public String getIncludedModule() {
		return include;
	}

	public String toString() {
		return " include " + include + ";";
	}

}
