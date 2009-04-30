package jyang.parser;


public abstract class YANG_DataDef extends YANG_Body{


	public YANG_DataDef(int id) {
		super(id);
	}

	public YANG_DataDef(yang p, int id) {
		super(p, id);
	}
}
