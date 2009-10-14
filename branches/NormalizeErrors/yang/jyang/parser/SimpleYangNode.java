package jyang.parser;

import java.util.Vector;

public abstract class SimpleYangNode extends SimpleNode {

	protected yang parser;
	private Vector<YANG_Unknown> unknowns = new Vector<YANG_Unknown>();

	private boolean isRootNode = false;

	private int line, col;

	public SimpleYangNode(int i) {
		super(i);
	}

	public SimpleYangNode(yang p, int i) {
		this(i);
		parser = p;
	}

	public void addUnknown(YANG_Unknown u) {
		unknowns.add(u);
	}

	public Vector<YANG_Unknown> getUnknowns() {
		return unknowns;
	}

	public void setLine(int l) {
		line = l;
	}

	public void setCol(int c) {
		col = c;
	}

	public int getLine() {
		return line;
	}

	public int getCol() {
		return col;
	}
	private SimpleYangNode parent = null;

	public void setParent(SimpleYangNode b) {
		parent = b;
	}

	public SimpleYangNode getParent() {
		return parent;
	}
	public void setRootNode(boolean b) {
		isRootNode = b;
	}

	public boolean isRootNode() {
		return isRootNode;
	}


	public YANG_Config getParentConfig() {
		if (this instanceof YANG_Grouping)
			return null;
		if (isRootNode) {
			if (getConfig() == null) {
				YANG_Config c = new YANG_Config(-1);
				c.setConfig(YangBuiltInTypes.config);
				return c;
			} else
				return getConfig();
		} else {
			SimpleYangNode parent = getParent();
			if (parent != null) {
				if (parent.getConfig() != null)
					return parent.getConfig();
				else
					return getParent().getParentConfig();
			} 
		}
		return null;
	}


	public String unquote(String s) {
		if (s.charAt(0) == '"')
			s = s.substring(1);
		if (s.charAt(s.length() - 1) == '"')
			s = s.substring(0, s.length() - 1);
		return s;
	}
	
	public YANG_Config getConfig() {
		return null;
	}

}
