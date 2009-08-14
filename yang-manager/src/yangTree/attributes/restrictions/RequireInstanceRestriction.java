package yangTree.attributes.restrictions;

import java.util.LinkedList;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.nodes.LeafNode;

@SuppressWarnings("serial")
public class RequireInstanceRestriction extends Restriction {
	
	private YangTreePath path;
	
	public RequireInstanceRestriction(YangTreePath path) {
		this.path = path;
	}

	@Override
	public UnitValueCheck check(String value) {
		LinkedList<LeafNode> leaves = path.getLeavesAtThisPath();
		if (leaves.size()==0) {
			return new UnitValueCheck("No reference leaf present.");
		} else if (leaves.size()==1 && leaves.peekFirst().getValue()==null) {
			return new UnitValueCheck("No reference leaf present.");
		}
		return UnitValueCheck.checkOK();
	}

	@Override
	public String getDescription() {
		return "A reference leaf at : "+path.toString()+" must be present.";
	}

}
