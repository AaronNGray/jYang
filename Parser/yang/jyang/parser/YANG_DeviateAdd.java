package jyang.parser;

import java.util.Vector;



public class YANG_DeviateAdd extends YANG_DeviateConfig {

	private Vector<YANG_Must> musts = new Vector<YANG_Must>();

	private Vector<YANG_Unique> uniques = new Vector<YANG_Unique>();

	public YANG_DeviateAdd(int id) {
		super(id);
	}

	public YANG_DeviateAdd(yang p, int id) {
		super(p, id);
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