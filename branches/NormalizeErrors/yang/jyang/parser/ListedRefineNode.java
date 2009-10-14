package jyang.parser;

public abstract class ListedRefineNode extends MustRefineNode {

	protected YANG_MinElement min = null;
	protected YANG_MaxElement max = null;
	private boolean b_min = false, b_max = false;

	public ListedRefineNode(int id) {
		super(id);
	}
	
	public ListedRefineNode(yang p, int id) {
		super(p, id);
	}
	
	public void setMinElement(YANG_MinElement m) {
		if (b_min)
			YangErrorManager.add(m.getLine(), m.getCol(),
					YangErrorManager.messages.getString("min"));
		b_min = true;
		min = m;
	}

	public YANG_MinElement getMinElement() {
		return min;
	}

	public void setMaxElement(YANG_MaxElement m) throws YangParserException {
		if (b_max)
			YangErrorManager.add(m.getLine(), m.getCol(),
					YangErrorManager.messages.getString("max"));
		b_max = true;
		max = m;
	}

	public YANG_MaxElement getMaxElement() {
		return max;
	}


}
