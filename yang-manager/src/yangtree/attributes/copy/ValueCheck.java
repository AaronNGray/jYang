package yangtree.attributes.copy;

import java.util.LinkedList;

/**
 * Represents all the errors (critical UnitValueChecks) and warnings (non-critical UnitValueChecks) that have been detected on a CheckedNode.
 * @see UnitValueCheck
 */
public class ValueCheck {

	private LinkedList<UnitValueCheck> errorsList;
	private boolean isCritical = false;

	public ValueCheck() {
		errorsList = new LinkedList<UnitValueCheck>();
	}

	/**
	 * Adds a single check to this ValueCheck
	 */
	public void addUnitCheck(UnitValueCheck unitCheck) {
		if (!unitCheck.isOk())
			errorsList.add(unitCheck);
		if (unitCheck.isCritical())
			isCritical = true;
	}

	/**
	 * Merges two ValueChecks
	 */
	public void addChecks(ValueCheck check) {
		errorsList.addAll(check.getErrors());
		if (check.isCritical())
			isCritical = true;
	}

	/**
	 * Returns <code>true</code> if there is no errors and no warnings.
	 */
	public boolean isOk() {
		return errorsList.size() == 0;
	}

	/**
	 * Returns a <code>UnitValueCheck</code> containing a fixed value if one and only one such value have been retrieved.
	 * Returns <code>null</code> otherwise.
	 */
	public UnitValueCheck getFixedCheck() {
		if (errorsList.size() == 1
				&& errorsList.getFirst().getFixedValue() != null)
			return errorsList.getFirst();
		return null;
	}

	/**
	 * Returns all the checks if there are only warnings , or only the errors if
	 * there is at least one error.
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

	/**
	 * Returns <code>true</code> if there is at least one error.
	 */
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
