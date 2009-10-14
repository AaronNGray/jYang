package jyang.parser;

import java.text.MessageFormat;
import java.util.Vector;


public abstract class YANG_DataDef extends FeaturedBody {
	
	private YANG_When when = null;

	private boolean b_when = false;
	
	public YANG_DataDef(int id) {
		super(id);
	}

	public YANG_DataDef(yang p, int id) {
		super(p, id);
	}
	public boolean isBracked() {
		return super.isBracked() || b_when;
	}

	public YANG_When getWhen() {
		return when;
	}

	public void setWhen(YANG_When w) {
		if (b_when)
			YangErrorManager.add(w.getLine(), w.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("ad2"), "when",
					getBody()));
		/*
			throw new YangParserException("When already defined in "
					+ getBody(), w.getLine(), w.getCol());
					*/
		b_when = true;
		this.when = w;
	}
	
}
