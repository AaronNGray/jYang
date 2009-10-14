package jyang.parser;

import java.util.Vector;

public abstract class MustDataDef extends ConfigDataDef {
	
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();

	public MustDataDef(int id) {
		super(id);
	}

	public MustDataDef(yang p, int id) {
		super(p, id);
	}
	
	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}
	

}
