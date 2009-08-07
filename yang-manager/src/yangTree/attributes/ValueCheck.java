package yangTree.attributes;

import java.util.LinkedList;

public class ValueCheck {

	private LinkedList<UnitValueCheck> errorsList;
	private boolean isCritical = false;

	public ValueCheck() {
		errorsList = new LinkedList<UnitValueCheck>();
	}

	public void addUnitCheck(UnitValueCheck unitCheck) {
		if (!unitCheck.isOk())
			errorsList.add(unitCheck);
		if (unitCheck.isCritical())
			isCritical = true;
	}

	public void addChecks(ValueCheck check) {
		errorsList.addAll(check.getErrors());
		if (check.isCritical())
			isCritical = true;
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

	/*
	 * Return all the checks if there are only non-critical checks (i.e. a
	 * warnings) , or only the critical checks if there is at least one critical
	 * check (i.e. an error).
	 */
	public LinkedList<UnitValueCheck> getErrors() {
		if (isCritical) {
			LinkedList<UnitValueCheck> result = new LinkedList<UnitValueCheck>();
			for (UnitValueCheck check : errorsList) {
				if (check.isCritical())
					result.add(check);
			}
			return result;
		} else {
			return errorsList;
		}
	}

	public boolean isCritical() {
		return isCritical;
	}

	public String toString() {
		String result = "";
		UnitValueCheck fixed = getFixedCheck();
		if (fixed != null) {
			result = fixed.getDescription() + ". Fixed Value \""
					+ fixed.getFixedValue() + "\" assumed";
		} else {
			for (UnitValueCheck error : getErrors()) {
				result += error.getDescription() + "\n";
			}
		}
		return result;
	}

}
