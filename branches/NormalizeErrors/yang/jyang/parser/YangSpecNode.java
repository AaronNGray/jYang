package jyang.parser;

/*
 * Copyright 2008 Emmanuel Nataf, Olivier Festor
 * 
 * This file is part of jyang.

 jyang is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 jyang is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with jyang.  If not, see <http://www.gnu.org/licenses/>.

 */
import java.text.MessageFormat;
import java.util.*;

public class YangSpecNode {

	protected Hashtable<String, YANG_Body> bodies = new Hashtable<String, YANG_Body>();

	public YangSpecNode clone() {
		YangSpecNode csn = new YangSpecNode();
		for (Enumeration<String> ek = bodies.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			csn.bodies.put(k, bodies.get(k));
		}
		return csn;

	}

	private final static String lprefix = "leaf:";
	private final static String rlprefix = "rleaf:";
	private final static String cprefix = "container:";
	private final static String rcprefix = "rcontainer:";
	private final static String liprefix = "list:";
	private final static String rliprefix = "rlist:";
	private final static String leprefix = "leaflist:";
	private final static String rleprefix = "rleaflist:";
	private final static String chprefix = "choice:";
	private final static String rchprefix = "rchoice:";
	private final static String aprefix = "anyxml:";
	private final static String raprefix = "ranyxml:";
	private final static String rprefix = "rpc:";
	private final static String notprefix = "notification:";
	private final static String gprefix = "grouping:";
	private final static String tprefix = "typedef:";
	private final static String eprefix = "extension:";

	public boolean isDefined(String k) {
		return isDefinedAsNode(k) || isDefinedAsGrouping(k)
				|| isDefinedAsTypeDef(k);
	}

	public boolean isDefinedAsNode(String k) {
		if (bodies.containsKey(lprefix + k) || bodies.containsKey(cprefix + k)
				|| bodies.containsKey(liprefix + k)
				|| bodies.containsKey(leprefix + k)
				|| bodies.containsKey(chprefix + k)
				|| bodies.containsKey(rprefix + k)
				|| bodies.containsKey(aprefix + k)
				|| bodies.containsKey(notprefix + k)
				|| bodies.containsKey(rcprefix + k)
				|| bodies.containsKey(rlprefix + k)
				|| bodies.containsKey(rliprefix + k)
				|| bodies.containsKey(rleprefix + k)
				|| bodies.containsKey(rchprefix + k)
				|| bodies.containsKey(raprefix + k)
				|| bodies.containsKey(eprefix + k))
			return true;
		else
			return false;

	}

	public boolean isDefinedAsGrouping(String k) {
		if (bodies.containsKey(gprefix + k))
			return true;
		else
			return false;
	}

	public YANG_Grouping getUsedGrouping(String k) {
		return (YANG_Grouping) bodies.get(gprefix + k);
	}

	public boolean isDefinedAsExtension(String k) {
		if (bodies.containsKey(eprefix + k))
			return true;
		else
			return false;

	}

	public YANG_Extension getExtension(String k) {
		return (YANG_Extension) bodies.get(eprefix + k);
	}

	public boolean isDefinedAsTypeDef(String k) {
		if (bodies.containsKey(tprefix + k))
			return true;
		else
			return false;

	}

	/**
	 * Put the body only if not already present
	 * 
	 * @param module
	 *            : the yang module name
	 * @param name
	 *            the node name
	 * @param b
	 *            the body node
	 */
	public void put(String name, YANG_Body b) {
		String modulefilename = b.getFileName();
		if (b instanceof YANG_TypeDef) {
			if (isDefinedAsTypeDef(name)) {
				YangErrorManager.tadd(modulefilename, b.getLine(), b.getCol(),
						"typedef", b.getBody(), modulefilename, get(name)
								.getLine());
				return;
			}
		} else if (b instanceof YANG_Grouping) {
			if (isDefinedAsGrouping(name)) {
				YangErrorManager.tadd(modulefilename, b.getLine(), b.getCol(),
						"grouping", b.getBody(), modulefilename, get(name)
								.getLine());
				return;
			}
		} else if (b instanceof YANG_Extension) {
			if (isDefinedAsExtension(name)) {
				YangErrorManager.tadd(modulefilename, b.getLine(), b.getCol(),
						"extension", b.getBody(), modulefilename, get(name)
								.getLine());
				return;
			}
		} else {
			boolean found = false;
			if (isDefinedAsNode(name)) {
				if (b instanceof YANG_Leaf)
					found = true;
				else if (b instanceof YANG_List)
					found = true;
				else if (b instanceof YANG_LeafList)
					found = true;
				else if (b instanceof YANG_Choice)
					found = true;
				else if (b instanceof YANG_AnyXml)
					found = true;
				else if (b instanceof YANG_Container)
					found = true;
			}
			if (found) {
				YangErrorManager.tadd(modulefilename, b.getLine(), b.getCol(),
						"dup_child", b.getBody(), get(name).getFileName(), get(
								name).getLine());
				return;
			}
		}

		if (b instanceof YANG_Leaf)
			bodies.put(lprefix + name, b);
		else if (b instanceof YANG_Container)
			bodies.put(cprefix + name, b);
		else if (b instanceof YANG_List)
			bodies.put(liprefix + name, b);
		else if (b instanceof YANG_LeafList)
			bodies.put(leprefix + name, b);
		else if (b instanceof YANG_Choice)
			bodies.put(chprefix + name, b);
		else if (b instanceof YANG_AnyXml)
			bodies.put(aprefix + name, b);
		else if (b instanceof YANG_Rpc)
			bodies.put(rprefix + name, b);
		else if (b instanceof YANG_Notification)
			bodies.put(notprefix + name, b);
		else if (b instanceof YANG_Grouping)
			bodies.put(gprefix + name, b);
		else if (b instanceof YANG_TypeDef)
			bodies.put(tprefix + name, b);
		else if (b instanceof YANG_Extension)
			bodies.put(eprefix + name, b);
	}

	public YANG_Body get(String k) {
		if (isDefined(k))
			if (bodies.containsKey(lprefix + k))
				return bodies.get(lprefix + k);
			else if (bodies.containsKey(rlprefix + k))
				return bodies.get(rlprefix + k);
			else if (bodies.containsKey(cprefix + k))
				return bodies.get(cprefix + k);
			else if (bodies.containsKey(rcprefix + k))
				return bodies.get(rcprefix + k);
			else if (bodies.containsKey(liprefix + k))
				return bodies.get(liprefix + k);
			else if (bodies.containsKey(rliprefix + k))
				return bodies.get(rliprefix + k);
			else if (bodies.containsKey(leprefix + k))
				return bodies.get(leprefix + k);
			else if (bodies.containsKey(rleprefix + k))
				return bodies.get(rleprefix + k);
			else if (bodies.containsKey(chprefix + k))
				return bodies.get(chprefix + k);
			else if (bodies.containsKey(rchprefix + k))
				return bodies.get(rchprefix + k);
			else if (bodies.containsKey(aprefix + k))
				return bodies.get(aprefix + k);
			else if (bodies.containsKey(raprefix + k))
				return bodies.get(raprefix + k);
			else if (bodies.containsKey(rprefix + k))
				return bodies.get(rprefix + k);
			else if (bodies.containsKey(notprefix + k))
				return bodies.get(notprefix + k);
			else if (bodies.containsKey(gprefix + k))
				return bodies.get(gprefix + k);
			else if (bodies.containsKey(tprefix + k))
				return bodies.get(tprefix + k);
			else if (bodies.containsKey(eprefix + k))
				return bodies.get(eprefix + k);
			else
				return null;
		return null;

	}

	/**
	 * Merge all nodes in the YangSpecNode. Replace existing nodes
	 * 
	 * @param p
	 *            external specification nodes
	 */
	public void merge(YangSpecNode p) {
		for (Enumeration<String> ek = p.getNodes().keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			if (!bodies.containsKey(k)) {
				bodies.put(k, p.bodies.get(k));
			}

		}
	}

	public boolean contains(YangSpecNode n) {
		boolean contain = true;
		Hashtable<String, YANG_Body> nodes = n.getNodes();
		for (Enumeration<String> eb = nodes.keys(); eb.hasMoreElements()
				&& contain;) {
			String k = eb.nextElement();
			contain = bodies.containsKey(k);
		}
		return contain;

	}

	public void addSubNode(String k, YANG_Body b) {
		bodies.put(k, b);
	}

	public Hashtable<String, YANG_Body> getNodes() {
		return bodies;
	}

	public String toString() {
		String result = new String();
		for (Enumeration<String> ek = bodies.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			result += k + "\n";
		}
		return result;
	}

}