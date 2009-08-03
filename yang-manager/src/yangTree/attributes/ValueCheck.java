package yangTree.attributes;

import java.util.LinkedList;

public class ValueCheck {

	private LinkedList<UnitValueCheck> errorsList;

	public ValueCheck() {
		errorsList = new LinkedList<UnitValueCheck>();
	}

	public void addUnitCheck(UnitValueCheck unitCheck) {
		if (!unitCheck.isOk())
			errorsList.add(unitCheck);
	}
	
	public void addChecks(ValueCheck check) {
		errorsList.addAll(check.getAllErrors());
	}

	public boolean isOk() {
		return errorsList.size() == 0;
	}

	public UnitValueCheck getFixedCheck() {
		if (errorsList.size() == 1
				&& errorsList.getFirst().getFixedValue() != null)
			return errorsList.getFirst();
		return null;
	}

	public LinkedList<UnitValueCheck> getAllErrors() {
		return errorsList;
	}

	public String toString() {
		String result = "";
		UnitValueCheck fixed = getFixedCheck();
		if (fixed != null) {
			result = fixed.getDescription()+". Fixed Value \""+fixed.getFixedValue()+"\" assumed";
		} else {
			for (UnitValueCheck error : errorsList) {
				result += error.getDescription() + "\n";
			}
		}
		return result;
	}

}
