import java.util.Enumeration;

public abstract class YANG_Refinement extends YANG_Body {
	
	protected String usedgrouping = null;
	
	public YANG_Refinement(int id) {
		super(id);
	}

	public YANG_Refinement(yang p, int id) {
		super(p, id);
	}

	public abstract String getBody();

	public abstract void check(YangContext context, YANG_Grouping g)
			throws YangParserException;

	public void setUsedGrouping(String g){
		usedgrouping = g;
	}
	
	public void check(YangContext context, YANG_Container container)
			throws YangParserException {
		boolean found = false;
		for (Enumeration<YANG_DataDef> ed = container.getDataDefs().elements(); ed
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = ed.nextElement();
			found = ddef.getBody().compareTo(getBody()) == 0;
			if (found)
				try {
					if (this instanceof YANG_RefineContainer
							&& ddef instanceof YANG_Container) {
						YANG_RefineContainer rcontainer = (YANG_RefineContainer) this;
						YANG_Container gcontainer = (YANG_Container) ddef;
						rcontainer.icheck(context, gcontainer, usedgrouping);
					} else if (this instanceof YANG_RefineLeaf
							&& ddef instanceof YANG_Leaf) {
						YANG_RefineLeaf rleaf = (YANG_RefineLeaf) this;
						YANG_Leaf leaf = (YANG_Leaf) ddef;
						rleaf.check(context, leaf);
					} else if (this instanceof YANG_RefineList
							&& ddef instanceof YANG_List) {
						YANG_RefineList rlist = (YANG_RefineList) this;
						YANG_List list = (YANG_List) ddef;
						rlist.check(context, list, usedgrouping);
					} else if (this instanceof YANG_RefineLeafList
							&& ddef instanceof YANG_LeafList) {
						YANG_RefineLeafList rleaflist = (YANG_RefineLeafList) this;
						YANG_LeafList leaflist = (YANG_LeafList) ddef;
						rleaflist.check(context, leaflist);
					} else if (this instanceof YANG_RefineAnyXml
							&& ddef instanceof YANG_AnyXml) {
						YANG_RefineAnyXml rax = (YANG_RefineAnyXml) this;
						YANG_AnyXml ax = (YANG_AnyXml) ddef;
						rax.check(context, ax);
					} else if (this instanceof YANG_RefineChoice
							&& ddef instanceof YANG_Choice) {
						YANG_RefineChoice rchoice = (YANG_RefineChoice) this;
						YANG_Choice choice = (YANG_Choice) ddef;
						rchoice.check(context, choice, usedgrouping);
					}
				} catch (YangParserException ye) {
					System.err.println(context.getModuleSpecName()
							+ "@" + getLine() + "." + getCol() + " and " + ye.getMessage() + usedgrouping);
				}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + ":" + getCol()
					+ ":container " + container.getContainer() + " at line "
					+ container.getLine() + " does not defines " + getBody());
	}

	public void check(YangContext context, YANG_List list)
			throws YangParserException {
		boolean found = false;
		for (Enumeration<YANG_DataDef> ed = list.getDataDefs().elements(); ed
				.hasMoreElements()
				&& !found;) {
			YANG_DataDef ddef = ed.nextElement();
			found = ddef.getBody().compareTo(getBody()) == 0;
			if (found)
				try {
					if (this instanceof YANG_RefineContainer
							&& ddef instanceof YANG_Container) {
						YANG_RefineContainer rcontainer = (YANG_RefineContainer) this;
						YANG_Container gcontainer = (YANG_Container) ddef;
						rcontainer.icheck(context, gcontainer, usedgrouping);
					} else if (this instanceof YANG_RefineLeaf
							&& ddef instanceof YANG_Leaf) {
						YANG_RefineLeaf rleaf = (YANG_RefineLeaf) this;
						YANG_Leaf leaf = (YANG_Leaf) ddef;
						rleaf.check(context, leaf);
					} else if (this instanceof YANG_RefineList
							&& ddef instanceof YANG_List) {
						YANG_RefineList rlist = (YANG_RefineList) this;
						YANG_List glist = (YANG_List) ddef;
						rlist.check(context, glist, usedgrouping);
					} else if (this instanceof YANG_RefineLeafList
							&& ddef instanceof YANG_LeafList) {
						YANG_RefineLeafList rleaflist = (YANG_RefineLeafList) this;
						YANG_LeafList leaflist = (YANG_LeafList) ddef;
						rleaflist.check(context, leaflist);
					} else if (this instanceof YANG_RefineAnyXml
							&& ddef instanceof YANG_AnyXml) {
						YANG_RefineAnyXml rax = (YANG_RefineAnyXml) this;
						YANG_AnyXml ax = (YANG_AnyXml) ddef;
						rax.check(context, ax);
					} else if (this instanceof YANG_RefineChoice
							&& ddef instanceof YANG_Choice) {
						YANG_RefineChoice rchoice = (YANG_RefineChoice) this;
						YANG_Choice choice = (YANG_Choice) ddef;
						rchoice.check(context, choice, usedgrouping);
					}
				} catch (YangParserException ye) {
					System.err.println(context.getModuleSpecName()
							+ "@" + getLine() + "." + getCol() + " and " + ye.getMessage() + usedgrouping);
				}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + ":" + getCol()
					+ ":list " + list.getList() + " at line "
					+ list.getLine() + " does not defines " + getBody());
	}
	public void check(YangContext context, YANG_Case yc)
			throws YangParserException {
		boolean found = false;
		YANG_CaseDef cdef = null;
		for (Enumeration<YANG_CaseDef> ec = yc.getCaseDefs().elements(); ec
				.hasMoreElements()
				&& !found;) {
			cdef = ec.nextElement();
			found = cdef.getBody().compareTo(getBody()) == 0;
			if (found)
				try {
					if (this instanceof YANG_RefineContainer
							&& cdef instanceof YANG_Container) {
						YANG_RefineContainer rcontainer = (YANG_RefineContainer) this;
						YANG_Container gcontainer = (YANG_Container) cdef;
						rcontainer.icheck(context, gcontainer, usedgrouping);
					} else if (this instanceof YANG_RefineLeaf
							&& cdef instanceof YANG_Leaf) {
						YANG_RefineLeaf rleaf = (YANG_RefineLeaf) this;
						YANG_Leaf leaf = (YANG_Leaf) cdef;
						rleaf.check(context, leaf);
					} else if (this instanceof YANG_RefineList
							&& cdef instanceof YANG_List) {
						YANG_RefineList rlist = (YANG_RefineList) this;
						YANG_List list = (YANG_List) cdef;
						rlist.check(context, list, usedgrouping);
					} else if (this instanceof YANG_RefineLeafList
							&& cdef instanceof YANG_LeafList) {
						YANG_RefineLeafList rleaflist = (YANG_RefineLeafList) this;
						YANG_LeafList leaflist = (YANG_LeafList) cdef;
						rleaflist.check(context, leaflist);
					} else if (this instanceof YANG_RefineAnyXml
							&& cdef instanceof YANG_AnyXml) {
						YANG_RefineAnyXml rax = (YANG_RefineAnyXml) this;
						YANG_AnyXml ax = (YANG_AnyXml) cdef;
						rax.check(context, ax);
					} else if (this instanceof YANG_RefineChoice
							&& cdef instanceof YANG_Choice) {
						YANG_RefineChoice rchoice = (YANG_RefineChoice) this;
						YANG_Choice choice = (YANG_Choice) cdef;
						rchoice.check(context, choice, usedgrouping);
					}
				} catch (YangParserException ye) {
					System.err.println(context.getModuleSpecName()
							+ "@" + getLine() + "." + getCol() + " and " + ye.getMessage() + usedgrouping);
				}
		}
		if (!found)
			throw new YangParserException("@" + getLine() + ":" + getCol()
					+ ":case " + yc.getCase() + " at line " + yc.getLine()
					+ " does not defines " + getBody());

	}

	public void check(YangContext context, YANG_ShortCase ysc)
			throws YangParserException {
		try {
			if (this instanceof YANG_RefineContainer
					&& ysc instanceof YANG_Container) {
				YANG_RefineContainer rcontainer = (YANG_RefineContainer) this;
				YANG_Container gcontainer = (YANG_Container) ysc;
				rcontainer.icheck(context, gcontainer, usedgrouping);
			} else if (this instanceof YANG_RefineLeaf
					&& ysc instanceof YANG_Leaf) {
				YANG_RefineLeaf rleaf = (YANG_RefineLeaf) this;
				YANG_Leaf leaf = (YANG_Leaf) ysc;
				rleaf.check(context, leaf);
			} else if (this instanceof YANG_RefineList
					&& ysc instanceof YANG_List) {
				YANG_RefineList rlist = (YANG_RefineList) this;
				YANG_List list = (YANG_List) ysc;
				rlist.check(context, list, usedgrouping);
			} else if (this instanceof YANG_RefineLeafList
					&& ysc instanceof YANG_LeafList) {
				YANG_RefineLeafList rleaflist = (YANG_RefineLeafList) this;
				YANG_LeafList leaflist = (YANG_LeafList) ysc;
				rleaflist.check(context, leaflist);
			} else if (this instanceof YANG_RefineAnyXml
					&& ysc instanceof YANG_AnyXml) {
				YANG_RefineAnyXml rax = (YANG_RefineAnyXml) this;
				YANG_AnyXml ax = (YANG_AnyXml) ysc;
				rax.check(context, ax);
			} else if (this instanceof YANG_RefineChoice
					&& ysc instanceof YANG_Choice) {
				YANG_RefineChoice rchoice = (YANG_RefineChoice) this;
				YANG_Choice choice = (YANG_Choice) ysc;
				rchoice.check(context, choice, usedgrouping);
			}

		} catch (YangParserException ye) {
			System.err.println(context.getModuleSpecName() + 
					"@" + getLine() + "." + getCol() + " and " + ye.getMessage() + usedgrouping);
		}
	}

}
