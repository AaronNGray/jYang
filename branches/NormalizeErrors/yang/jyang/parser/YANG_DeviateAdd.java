package jyang.parser;

import java.util.Enumeration;
import java.util.Vector;



public class YANG_DeviateAdd extends DeviateAddReplace {

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
	
	public boolean isBracked() {
		return musts.size() != 0 || uniques.size() != 0 || super.isBracked();
	}

	public String toString(){
		String result = "deviate add";
		if (isBracked()){
			result += "{\n";
			result += super.toString() + "\n";
		for (Enumeration<YANG_Must> em = musts.elements(); em.hasMoreElements();)
			result += em.nextElement() + "\n";
		for (Enumeration<YANG_Unique> eu = uniques.elements(); eu.hasMoreElements();)
			result += eu.nextElement() + "\n";
		result += "}";
		}
		else
			result += ";";
		return result;
	}

	public void deviates(YangTreeNode deviated) {
		if (getDefault() != null){
			YangContext context = deviated.getNode().getContext();
			if (deviated.getNode() instanceof YANG_Leaf){
				YANG_Leaf leaf = (YANG_Leaf) deviated.getNode();
				if (leaf.getDefault() == null){
					leaf.setDefault(getDefault());
					try {
						leaf.check(context);
					} catch (YangParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	

}
