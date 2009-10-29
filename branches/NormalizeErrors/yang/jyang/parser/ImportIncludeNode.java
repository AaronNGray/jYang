package jyang.parser;

import java.text.MessageFormat;

public abstract class ImportIncludeNode extends SimpleYangNode {

	private YANG_Revision revision = null;
	private boolean b_revision = false;

	public ImportIncludeNode(int i) {
		super(i);
	}

	public ImportIncludeNode(yang p, int i) {
		super(p, i);
	}

	public YANG_Revision getRevision() {
		return revision;
	}

	void setRevision(YANG_Revision r) {
		if (!b_revision) {
			this.revision = r;
			b_revision = true;
		}
		else
			YangErrorManager
			.add(filename, r.getLine(), r.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("unex_kw"),
					"revision"));
	}
	
	public boolean isBracked() {
		return b_revision;
	}

	public String toString() {
		String result = "";
		if (b_revision)
			result += "revision-date " + revision.toString();
		return result;
	}

}
