package yangTree.attributes.restrictions;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.YangTreePath;

public class RequireInstanceRestriction extends Restriction {
	
	YangTreePath path;
	
	public RequireInstanceRestriction(YangTreePath path){
		this.path = path;
	}

	@Override
	public UnitValueCheck check(String value) {
		if (path.solvePath()==null){
			return new UnitValueCheck("The reference leaf is not present.");
		} else {
			if (path.solvePath().getValue()==null){
				System.out.println("node : "+path.solvePath().getName()+" ,value : "+path.solvePath().getValue());
				return new UnitValueCheck("The reference leaf is not present.");
			}
			return UnitValueCheck.checkOK();
		}
	}

	@Override
	public String getDescription() {
		return "The reference leaf at : "+path.toString()+" must be present.";
	}

}
