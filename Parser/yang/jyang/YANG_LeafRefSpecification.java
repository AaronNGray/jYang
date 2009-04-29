
package jyang;

public class YANG_LeafRefSpecification extends SimpleNode {

	private YANG_Path path = null;
	public YANG_Path getPath() {
		return path;
	}

	public void setPath(YANG_Path path) {
		this.path = path;
	}

	private String requireInstance = null;
	
	public String getRequireInstance() {
		return requireInstance;
	}

	public void setRequireInstance(String requireInstance) {
		this.requireInstance = requireInstance;
	}

	public YANG_LeafRefSpecification(int id) {
		super(id);
	}
	

}
