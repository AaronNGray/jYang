package jyang.parser;

import java.util.Vector;

public abstract class DataDefBody extends FeaturedBody {
	
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

	public DataDefBody(int id) {
		super(id);
	}

	public DataDefBody(yang p, int id) {
		super(p, id);
	}
	
	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}


}
