package jyang.parser;

import java.text.MessageFormat;

public abstract class DocumentedNode extends SimpleYangNode {

	public DocumentedNode(int id) {
		super(id);
	}

	public DocumentedNode(yang p, int id) {
		super(p, id);
	}

	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean b_description = false, b_reference = false;

	public boolean isBracked() {
		return b_description || b_reference;
	}

	public void setDescription(YANG_Description d) {
		if (b_description)
			YangErrorManager.add(d.getLine(), d.getCol(),
					YangErrorManager.messages.getString("description"));
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) {
		if (b_reference)
			YangErrorManager.add(r.getLine(), r.getCol(),
					YangErrorManager.messages.getString("reference"));
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public String toString() {
		String result = "";
		if (b_description)
			result += getDescription() + "\n";
		if (b_reference)
			result += getReference() + "\n";
		return result;
	}

}
