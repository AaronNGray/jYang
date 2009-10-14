package jyang.parser;

import java.util.Vector;

public abstract class MustRefineNode extends ConfigRefineNode {
	
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();


	public MustRefineNode(int id) {
		super(id);
	}
	
	public MustRefineNode(yang p, int id) {
		super(p, id);
	}

	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}
	

}
