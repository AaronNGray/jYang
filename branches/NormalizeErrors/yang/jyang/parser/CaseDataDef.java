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

	public YANG_Case getCase() {
		return ycase;
	}
	
	public void addDataDef(YANG_DataDef c) {
		ycase.getDataDefs().add(c);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return ycase.getDataDefs();
	}


	@Override
	public void check(YangContext context) throws YangParserException {
	}

	@Override
	public String getBody() {
		return ycase.getBody();
	}
	
	public String toString(){
		return ycase.toString();
	}

}
