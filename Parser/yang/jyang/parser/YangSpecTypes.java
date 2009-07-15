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
import java.util.*;

public class YangSpecTypes {

	private Hashtable<String, String> deriveds = new Hashtable<String, String>();
	private Hashtable<String, YANG_TypeDef> typedefs = new Hashtable<String, YANG_TypeDef>();

	public YangSpecTypes clone() {
		YangSpecTypes cst = new YangSpecTypes();

		for (Enumeration<String> es = deriveds.keys(); es.hasMoreElements();) {
			String k = es.nextElement();
			String t = deriveds.get(k);
			cst.add(k, t, typedefs.get(k));
		}
		return cst;
	}

	public void add(String derived, String basetype, YANG_TypeDef td) {
		deriveds.put(derived, basetype);
		typedefs.put(derived, td);
	}

	public Enumeration<String> keys() {
		return deriveds.keys();
	}

	public String get(String k) {
		if (deriveds.containsKey(k))
			return deriveds.get(k);
		return null;
	}

	public int getTypesNumber() {
		return deriveds.size();
	}

	/**
	 * Merge external typedef definition
	 * 
	 * @param yst
	 *            external typedefs
	 */
	public void merge(YangSpecTypes yst) {
		for (Enumeration<String> ek = yst.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			if (!deriveds.containsKey(k))
				add(k, yst.get(k), yst.getTypeDef(k));
		}
	}

	/**
	 * Get the typedef of the given type
	 * 
	 * @param k
	 *            the canonical name of the type
	 * @return the typedef (or null if not present)
	 */
	protected YANG_TypeDef getTypeDef(String k) {
		return typedefs.get(k);
	}

	public boolean contains(YangSpecTypes yst) {

		for (Enumeration<String> ek = yst.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			if (!deriveds.containsKey(k))
				return false;
		}
		return true;

	}

	/*
	 * public void add(String prefix, YangSpecTypes yst) throws
	 * YangParserException { for (Enumeration<String> ek = yst.keys();
	 * ek.hasMoreElements();) { String k = ek.nextElement(); add(prefix + ":" +
	 * k, yst.get(k)); } }
	 */
	public boolean isEmpty() {
		return deriveds.size() == 0;
	}

	public boolean isDefined(String t) {
		if (!YangBuiltInTypes.isBuiltIn(t))
			return deriveds.containsKey(t);
		return true;
	}

	public void check(String module) throws YangParserException {
		for (Enumeration<String> et = deriveds.elements(); et.hasMoreElements();) {
			String basetype = et.nextElement();
			if (!YangBuiltInTypes.isBuiltIn(basetype)) {
				String s = null;
				// if (basetype.substring(0, basetype.indexOf(':')).compareTo(
				// module) == 0) {
				if (!deriveds.containsKey(basetype)) {
					YANG_TypeDef td = null;
					boolean found = false;
					for (Enumeration<String> ek = deriveds.keys(); ek
							.hasMoreElements()
							&& !found;) {
						s = ek.nextElement();
						if (deriveds.get(s).compareTo(basetype) == 0) {
							found = true;
							td = typedefs.get(s);
						}
					}
					if (td != null) {
						throw new YangParserException("@:type " + basetype
								+ " used by the typedef " + s + " at line "
								+ td.getLine() + " is not defined");
					}
					// }
				}
			}
		}
		for (Enumeration<String> et = deriveds.elements(); et.hasMoreElements();) {
			String basetype = et.nextElement();
			if (!YangBuiltInTypes.isBuiltIn(basetype)) {
				Vector<String> chain = new Vector<String>();
				chain.add(basetype);
				checkChain(module, chain, deriveds.get(basetype));
			}
		}
	}

	protected void checkChain(String module, Vector<String> b, String d)
			throws YangParserException {
		if (b.contains(d)) {
			YANG_TypeDef bt = typedefs.get(d);
			throw new YangParserException("@" + bt.getLine() + "."
					+ bt.getCol() + ":circular dependency for type \"" + d
					+ "\"");
		}
		if (!YangBuiltInTypes.isBuiltIn(d)) {
			b.add(d);
			checkChain(module, b, deriveds.get(d));
		}
	}

	public String getBuiltInType(String t) {
		if (t == null)
			return t;
		else if (YangBuiltInTypes.isBuiltIn(t))
			return t;
		else 
			return getBuiltInType(deriveds.get(t));
	}

	/**
	 * Get the typedef that defines the base type of the given type
	 * 
	 * @param t
	 *            the canonical name of the type
	 * @return the typedef or null if the type is a built-in type
	 */
	public YANG_TypeDef getBaseType(String t) {
		if (YangBuiltInTypes.isBuiltIn(t))
			return null;
		String type = deriveds.get(t);
		if (type == null)
			return null;
		return typedefs.get(type);
	}

	/**
	 * Get the typedef that defines the given type
	 * 
	 * @param t
	 *            the canonical name of the type
	 * @return the typedef of null if the type is a built-in type
	 */
	public YANG_TypeDef getDefiningTypeDef(String t) {
		if (YangBuiltInTypes.isBuiltIn(t))
			return null;
		for (Enumeration<String> ek = typedefs.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			if (deriveds.get(k).compareTo(t) == 0)
				return typedefs.get(k);
		}
		return null;

	}

	/**
	 * Get the typedef that define the base type of the given typedef
	 * 
	 * @param td
	 *            a typedef
	 * @return the typedef or null if the base type is a built-in type.
	 */
	public YANG_TypeDef getBaseType(YANG_TypeDef td) {
		if (YangBuiltInTypes.isBuiltIn(td.getType().getType()))
			return null;
		for (Enumeration<String> ek = typedefs.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			if (typedefs.get(k) == td) {
				String b = deriveds.get(k);
				return typedefs.get(b);
			}
		}
		System.err.println("panic in getting base type " + td.getTypeDef());
		System.exit(-1);
		return null;

	}

	public String toString() {
		String result = new String();
		for (Enumeration<String> es = deriveds.keys(); es.hasMoreElements();) {
			String k = es.nextElement();
			result += k + "\t:  " + deriveds.get(k) + "\n";
		}
		return result;
	}
}
