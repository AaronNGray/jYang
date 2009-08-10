package yangTree.attributes.builtinTypes;

import jyang.parser.YANG_Type;
import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;
import yangTree.attributes.restrictions.RequireInstanceRestriction;
import yangTree.nodes.LeafNode;
import yangTree.nodes.RootNode;

public class LeafrefType extends BuiltinType {

	private String relativePath;
	private YangTreePath path;
	private boolean requireInstance = true;

	public LeafrefType(YANG_Type type) {
		relativePath = type.getLeafRef().getPath().getPath();
		if (type.getLeafRef().getRequireInstance() != null
				&& type.getLeafRef().getRequireInstance().equals("false")) {
			requireInstance = false;
		}
	}

	public void setPath(YangTreePath path) {
		this.path = path.buildRootPath(relativePath);
		if (requireInstance) {
			restrictionsList.add(new RequireInstanceRestriction(this.path));
		}
	}

	@Override
	public ValueCheck check(String value) {
		ValueCheck result = super.check(value);
		if (!result.isOk())
			return result;
		LeafNode leaf = path.solvePath();
		if (leaf!=null && leaf.getValue()!=null ){
				if (!leaf.getValue().equals(value))
			result.addUnitCheck(new UnitValueCheck(
					"This value does not match the reference leaf value : \""
							+ leaf.getValue() + "\""));
		} else {
			result.addUnitCheck(new UnitValueCheck("The reference leaf value is not present",false));
		}
		return result;
	}

	public String getName() {
		return "Leafref";
	}

	@Override
	public String getContent() {
		return "Leafref : " + path.toString();
	}

}
