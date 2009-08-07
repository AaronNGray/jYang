package yangTree.attributes;

public class UnitValueCheck {
	
	private boolean isOk;
	private boolean isCritical;
	private String description = null;
	private String fixedValue = null;
		
	private UnitValueCheck(){
		this.isOk = true;
	}
	
	public UnitValueCheck(String description){
		this.isOk = false;
		this.description = description;
		this.isCritical = true;
	}
		
	public UnitValueCheck(String description, boolean isCritical){
		this.isOk = false;
		this.description = description;
		this.isCritical = isCritical;
	}
	
	public UnitValueCheck(String description, String fixedValue){
		this.isOk = false;
		this.description = description;
		this.fixedValue = fixedValue;
		this.isCritical = false;
	}
	
	public static UnitValueCheck checkOK(){
		return new UnitValueCheck();
	}
	
	public boolean isOk(){
		return isOk;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getFixedValue(){
		return fixedValue;
	}
	
	public boolean isCritical(){
		return isCritical;
	}
}
