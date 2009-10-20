package jyang.parser;

import java.util.Vector;

public class CaseDataDef extends YANG_DataDef {
	
	YANG_Case ycase;

	public CaseDataDef(int id) {
		super(id);
	}

	public CaseDataDef(yang p, int id) {
		super(p, id);
	}
	
	public CaseDataDef(YANG_Case c){
		super(0);
		ycase = c;
	}

	public String getCase() {
		return ycase.getCase();
	}
	
	public void addCaseDef(YANG_CaseDataDef c) {
		ycase.getCaseDefs().add(c);
	}

	public Vector<YANG_CaseDataDef> getCaseDefs() {
		return ycase.getCaseDefs();
	}


	@Override
	public void check(YangContext context) throws YangParserException {
	}

	@Override
	public String getBody() {
		return ycase.getBody();
	}

}
