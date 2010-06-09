package yangtree.attributes.restrictions;

import java.util.LinkedList;

import yangtree.attributes.UnitValueCheck;
import yangtree.attributes.YangTreePath;
import yangtree.nodes.LeafNode;

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
		} else if (leaves.size()==1 && leaves.getFirst().getValue()==null) {
			return new UnitValueCheck("No reference leaf present.");
		}
		return UnitValueCheck.checkOK();
	}

	@Override
	public String getDescription() {
		return "A reference leaf must be present.";
	}

}