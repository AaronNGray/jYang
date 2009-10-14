package jyang.parser;

import java.text.MessageFormat;

public abstract class StatuedBody extends YANG_Body {
	

	public StatuedBody(int id) {
		super(id);
	}
	
	public StatuedBody(yang p, int id) {
		super(p, id);
	}

	private YANG_Status status = null;
	private boolean b_status = false;

	@Override
	public void check(YangContext context) throws YangParserException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setStatus(YANG_Status s) {
		if (b_status)
			YangErrorManager.add(s.getLine(), s.getCol(), MessageFormat.format(
					YangErrorManager.messages.getString("ad2"), "status",
					getBody()));
		/*
		 * throw new YangParserException("Status already defined in  " +
		 * getBody(), s.getLine(), s.getCol());
		 */
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}


}
