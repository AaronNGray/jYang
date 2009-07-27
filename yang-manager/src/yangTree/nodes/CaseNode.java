package yangTree.nodes;

import jyang.parser.YANG_Case;


public class CaseNode extends DataTree {


	public CaseNode(YANG_Case c) {
		definition = c;
	}

	public void addContent(DataNode d) {
		nodes.add(d);
	}

	public String toString() {
		return definition.getBody();
	}
	
	public CaseNode cloneBody(){
		return new CaseNode((YANG_Case) definition);
	}

	@Override
	public String getNodeType() {
		return "Case";
	}
	
}
