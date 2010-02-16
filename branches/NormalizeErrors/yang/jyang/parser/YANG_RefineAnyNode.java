/* Generated By:JJTree: Do not edit this line. YANG_RefineAnyNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=YANG_,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jyang.parser;

import java.util.Vector;

public
class YANG_RefineAnyNode extends YANG_Refine {
  private String refineAnyNodeId;

public String getRefineAnyNodeId() {
	return refineAnyNodeId;
}

public YANG_RefineAnyNode(int id) {
    super(id);
  }

  public YANG_RefineAnyNode(yang p, int id) {
    super(p, id);
  }
  
  public void setRefineAnyNodeId(
		  String r) {
	  refineAnyNodeId = r;
  }
	private YANG_Default ydefault = null;

	private boolean b_default = false;

	protected YANG_MinElement min = null;
	protected YANG_MaxElement max = null;
	private boolean b_min = false, b_max = false;

	private YANG_Mandatory mandatory = null;

	private boolean b_mandatory = false;
	

	private YANG_Presence presence = null;

	private boolean b_presence = false;

	public void setMandatory(YANG_Mandatory m) {
		if (!b_mandatory) {
			b_mandatory = true;
			mandatory = m;
		} else
			YangErrorManager.tadd(filename, m.getLine(), m.getCol(), "unex_kw",
					"mandatory");
	}

	public YANG_Mandatory getMandatory() {
		return mandatory;
	}

	public void setMinElement(YANG_MinElement m) {
		if (b_min)
			YangErrorManager.tadd(filename, m.getLine(), m.getCol(), "unex_kw",
					"min");
		else {
			b_min = true;
			min = m;
		}
	}

	public void setPresence(YANG_Presence p) {
		if (!b_presence) {
			b_presence = true;
			presence = p;
		} else
			YangErrorManager.tadd(filename, p.getLine(), p.getCol(), "unex_kw",
					"presence");
	}

	public YANG_Presence getPresence() {
		return presence;
	}

	public YANG_MinElement getMinElement() {
		return min;
	}

	public void setMaxElement(YANG_MaxElement m) {
		if (b_max)
			YangErrorManager.tadd(filename, m.getLine(), m.getCol(), "unex_kw",
					"max");
		else {
			b_max = true;
			max = m;
		}
	}

	public YANG_MaxElement getMaxElement() {
		return max;
	}
	public void setDefault(YANG_Default d) throws YangParserException {
		if (!b_default) {
			b_default = true;
			ydefault = d;
		} else
			YangErrorManager.tadd(filename, d.getLine(), d.getCol(), "unex_kw",
					"default");
	}

	public YANG_Default getDefault() {
		return ydefault;
	}

	private Vector<YANG_Must> musts = new Vector<YANG_Must>();


	public void addMust(YANG_Must m) {
		musts.add(m);
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}
  

	protected YANG_Config config = null;

	protected boolean b_config = false;

	public void setConfig(YANG_Config c) {
		if (!b_config) {
			b_config = true;
			config = c;
		} else
			YangErrorManager.tadd(filename, c.getLine(), c.getCol(), "unex_kw",
					"config");
	}

	public YANG_Config getConfig() {
		return config;
	}

	@Override
	public void check(YangContext context, YANG_Grouping g)
			throws YangParserException {
		// TODO Auto-generated method stub
		
	}

}
/* JavaCC - OriginalChecksum=86d01eb3768d2cd8ba8ae6c654d7e98b (do not edit this line) */
