package yangTree.attributes;

/**
 * Represents a unit check that have been applied on a value given a type and some restrictions.
 * This can be an error (critical check), a warning (non-critical check), or a OK (empty check).
 * @see ValueCheck
 */
public class UnitValueCheck {
	
	private boolean isOk;
	private boolean isCritical;
	private String description = null;
	private String fixedValue = null;
		
	private UnitValueCheck(){
		this.isOk = true;
	}
	
	/**
	 * Creates a new critical unit check.
	 */
	public UnitValueCheck(String description){
		this.isOk = false;
		this.description = description;
		this.isCritical = true;
	}
	
	/**
	 * Creates a new unit check that can be critical or not.
	 */
	public UnitValueCheck(String description, boolean isCritical){
		this.isOk = false;
		this.description = description;
		this.isCritical = isCritical;
	}
	
	/**
	 * Creates a new non-critical unit check that suggest a fixed value.
	 */
	public UnitValueCheck(String description, String fixedValue){
		this.isOk = false;
		this.description = description;
		this.fixedValue = fixedValue;
		this.isCritical = false;
	}
	
	/**
	 * Creates a OK unit check.
	 */
	public static UnitValueCheck checkOK(){
		return new UnitValueCheck();
	}
	
	public boolean isOk(){
		return isOk;
	}
	
	public String getDescription(){
		return description;
	}
	
	/**
	 * Returns a fixed value if such value has been suggested. Returns <code>null</code> otherwise.
	 */
	public String getFixedValue(){
		return fixedValue;
	}
	
	public boolean isCritical(){
		return isCritical;
	}
}
