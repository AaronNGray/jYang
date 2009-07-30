package yangTree.attributes;

public class UnitValueCheck {
	
	private boolean isOk;
	private String description = null;
	private String fixedValue = null;
		
	private UnitValueCheck(){
		this.isOk = true;
	}
		
	public UnitValueCheck(String description){
		this.isOk = false;
		this.description = description;
	}
	
	public UnitValueCheck(String description, String fixedValue){
		this.isOk = false;
		this.description = description;
		this.fixedValue = fixedValue;
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

}
