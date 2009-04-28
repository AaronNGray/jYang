package jyang;

import java.util.Vector;

public class YANG_Deviation extends YANG_Body {

	private String deviation = null;
	private YANG_DeviateNotSupported deviatenotsupported = null;
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

	@Override
	public String getBody() {

		return getDeviation();
	}

}
