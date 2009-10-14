package jyang.parser;

public abstract class ListedDataDef extends MustDataDef 
implements YANG_CaseDataDef, YANG_ShortCase {
	
	private YANG_MinElement min = null;
	private YANG_MaxElement max = null;
	private YANG_OrderedBy ordered = null;
	private boolean b_min = false, b_max = false, b_ordered = false;
	
	public ListedDataDef(int id) {
		super(id);
	}

	public ListedDataDef(yang p, int id) {
		super(p, id);
	}
	public void setMinElement(YANG_MinElement m){
		if (b_min)
			YangErrorManager.add(m.getLine(), m.getCol(),
					YangErrorManager.messages.getString("min"));
		b_min = true;
		min = m;
	}

	public YANG_MinElement getMinElement() {
		return min;
	}

	public void setMaxElement(YANG_MaxElement m) {
		if (b_max)
			YangErrorManager.add(m.getLine(), m.getCol(),
					YangErrorManager.messages.getString("max"));
		b_max = true;
		max = m;
	}

	public YANG_MaxElement getMaxElement() {
		return max;
	}

	public void setOrderedBy(YANG_OrderedBy o) throws YangParserException {
		if (b_ordered)
			YangErrorManager.add(o.getLine(), o.getCol(),
					YangErrorManager.messages.getString("ordered"));
		b_ordered = true;
		ordered = o;
	}

	public YANG_OrderedBy getOrderedBy() {
		return ordered;
	}

	public boolean isBracked(){
		return super.isBracked() || b_ordered || b_min || b_max;
	}

}
