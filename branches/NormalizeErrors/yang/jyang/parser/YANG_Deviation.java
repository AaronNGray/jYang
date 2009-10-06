package jyang.parser;

import java.util.Vector;


public class YANG_Deviation extends YANG_Body {

	private String deviation = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private YANG_DeviateNotSupported deviateNotSupported = null;

	private Vector<YANG_DeviateAdd> deviateadds = new Vector<YANG_DeviateAdd>();
	private Vector<YANG_DeviateDelete> deviatedeletes = new Vector<YANG_DeviateDelete>();
	private Vector<YANG_DeviateReplace> deviatereplaces = new Vector<YANG_DeviateReplace>();

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public YANG_Deviation(int id) {
		super(id);
	}

	public YANG_Deviation(yang p, int id) {
		super(p, id);
	}

	
	public void check(YangContext context) throws YangParserException {

	}

	public String getBody() {
		return getDeviation();
	}
	
	public YANG_DeviateNotSupported getDeviateNotSupported() {
		return deviateNotSupported;
	}

	public void setDeviateNotSupported(YANG_DeviateNotSupported deviateNotSupported) {
		this.deviateNotSupported = deviateNotSupported;
	}
	
	public YANG_Description getDescription() {
		return description;
	}

	public void setDescription(YANG_Description description) {
		this.description = description;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void setReference(YANG_Reference reference) {
		this.reference = reference;
	}
	
	public void addDeviateAdd(YANG_DeviateAdd da){
		deviateadds.add(da);
	}
	public Vector<YANG_DeviateAdd> getDeviateAdds(){
		return deviateadds;
	}
	public void addDeviateDelete(YANG_DeviateDelete dd){
		deviatedeletes.add(dd);
	}
	public Vector<YANG_DeviateDelete> getDeviateDeletes(){
		return deviatedeletes;
	}
	public void addDeviateReplace(YANG_DeviateReplace dr){
		deviatereplaces.add(dr);
	}
	public Vector<YANG_DeviateReplace> getDeviateReplaces(){
		return deviatereplaces;
	}

}
