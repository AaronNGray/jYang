package jyang.parser;

import java.util.Vector;



public abstract class YANG_DeviateMustUnique extends Deviate {

	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	
	private Vector<YANG_Unique> uniques = new Vector<YANG_Unique>();
	
	public YANG_DeviateMustUnique(int i) {
		super(i);
	}
	public YANG_DeviateMustUnique(yang p, int i) {
		super(p, i);
	}
	public Vector<YANG_Must> getMusts() {
		return musts;
	}
	public void addMust(YANG_Must m) {
		this.musts.add(m);
	}
	public Vector<YANG_Unique> getUniques() {
		return uniques;
	}
	public void addUnique(YANG_Unique u) {
		this.uniques.add(u);
	}

}
