package jyang.parser;

import java.util.Enumeration;
import java.util.Vector;


public abstract class YANG_DataDefConfigMust extends YANG_DataDefConfig {

	private Vector<YANG_Must> musts = new Vector<YANG_Must>();

	public YANG_DataDefConfigMust(int id) {
		super(id);
	}
	public YANG_DataDefConfigMust(yang p, int id) {
		super(p, id);
	}
	
	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}
	
	public boolean isBracked(){
		return super.isBracked() || musts.size() != 0;
	}
	
	public String toString(){
		String result = "";
		for (Enumeration<YANG_Must> em = musts.elements(); em
				.hasMoreElements();)
			result += em.nextElement().toString() + "\n";
		result += super.toString();
		return result;
	}
	

}
