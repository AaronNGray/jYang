package jyang;

public abstract class YANG_DeviateConfig extends YANG_DeviateUnitsDefault {

	private YANG_Config config = null;
	
	private YANG_Mandatory mandatory = null;
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	
	public YANG_DeviateConfig(int i) {
		super(i);
	}
	public YANG_DeviateConfig(yang p, int i) {
		super(p, i);
	}
	public YANG_Config getConfig() {
		return config;
	}
	public void setConfig(YANG_Config config) {
		this.config = config;
	}
	public YANG_Mandatory getMandatory() {
		return mandatory;
	}
	public void setMandatory(YANG_Mandatory mandatory) {
		this.mandatory = mandatory;
	}
	public YANG_MinElement getMinElement() {
		return min;
	}
	public void setMinElement(YANG_MinElement min) {
		this.min = min;
	}
	public YANG_MaxElement getMaxElement() {
		return max;
	}
	public void setMaxElement(YANG_MaxElement max) {
		this.max = max;
	}

}
