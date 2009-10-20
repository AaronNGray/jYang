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
		if (!b_when) {
			b_when = true;
			this.when = w;
		} else
			YangErrorManager.add(w.getLine(), w.getCol(),
					YangErrorManager.messages.getString("when"));
	}

	public String toString() {
		String result = "";
		result += "when " + when + ";";
		return result;
	}

}