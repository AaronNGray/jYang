package jyang.parser;

import java.text.MessageFormat;
import java.util.Vector;

public abstract class StatuedNode extends DocumentedNode {

	private YANG_Status status = null;
	private YANG_When when = null;
	private Vector<YANG_IfFeature> ifFeatures = new Vector<YANG_IfFeature>();
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

	public StatuedNode(int i) {
		super(i);
	}

	public StatuedNode(yang p, int id) {
		super(p, id);
	}

	private boolean b_status = false;
	private boolean b_when = false;

	public void setStatus(YANG_Status s) {
		if (b_status)
			YangErrorManager.add(s.getLine(), s.getCol(),
					YangErrorManager.messages.getString("status"));
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public YANG_When getWhen() {
		return when;
	}

	public void setWhen(YANG_When w) {
		if (b_when)
			YangErrorManager.add(w.getLine(), w.getCol(),
					YangErrorManager.messages.getString("when"));

		b_when = true;
		this.when = w;
	}

	public Vector<YANG_IfFeature> getIfFeatures() {
		return ifFeatures;
	}

	public void addIfFeature(YANG_IfFeature i) {
		ifFeatures.add(i);
	}

	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}

	public void check(YangContext context) throws YangParserException {
	}

}
