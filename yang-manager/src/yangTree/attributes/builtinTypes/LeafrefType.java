package yangTree.attributes.builtinTypes;

import java.util.LinkedList;

import jyang.parser.YANG_Type;
import yangTree.attributes.ReferenceLeafNotRetrievedException;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.attributes.restrictions.RequireInstanceRestriction;
import yangTree.nodes.LeafNode;

@SuppressWarnings("serial")
public class LeafrefType extends BuiltinType implements PathSensitiveType {

	private String relativePath;
	private YangTreePath path;
	private boolean requireInstance = true;

	public LeafrefType(YANG_Type type) {
		relativePath = type.getLeafRef().getPath().getPath();
		if (type.getLeafRef().getRequireInstance() != null && type.getLeafRef().getRequireInstance().equals("false")) {
			requireInstance = false;
		}
	}

	public void setPath(YangTreePath path) {
		this.path = path;
	}

	@Override
	public ValueCheck check(String value) {

		try {
			YangTreePath solvedPath = path.buildRelativePath(relativePath);

			if (requireInstance) {
				restrictionsList.add(new RequireInstanceRestriction(solvedPath));
			}

			ValueCheck result = super.check(value);
			if (!result.isOk())
				return result;
			LinkedList<LeafNode> leaves = solvedPath.getLeavesAtThisPath();
			if (leaves.size() == 0) {
				result.addUnitCheck(new UnitValueCheck("The reference leaf value is not present", false));
			} else {
				boolean match = false;
				for (LeafNode leaf : leaves) {
					match = match || leaf.getValue().equals(value);
				}
				if (!match)
					result.addUnitCheck(new UnitValueCheck("This value does not match a reference leaf value."));
			}
			return result;

		} catch (ReferenceLeafNotRetrievedException e) {
			ValueCheck result = new ValueCheck();
			result.addUnitCheck(new UnitValueCheck("Impossible to check this value : value of the reference leaf have not been retrieved.", false));
			return result;
		}

	}

	public String getName() {
		return "Leafref";
	}

	@Override
	public String getContent() {
		return "Leafref : " + relativePath;
	}

}
