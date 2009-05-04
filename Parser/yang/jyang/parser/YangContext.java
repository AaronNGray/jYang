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


public class YangContext {

	private YangSpecTypes spectypes = null;
	private YangSpecNode specnodes = null;
	private Hashtable<String, YANG_Type> pendinguniontypes = new Hashtable<String, YANG_Type>();
	private Vector<YANG_Import> imports = null;
	private YANG_Specification spec = null;

	private YangContext(YangSpecTypes st, YangSpecNode sn,
			Vector<YANG_Import> i, YANG_Specification s) {
		spectypes = st;
		specnodes = sn;
		imports = i;
		spec = s;
	}

	/**
	 * Create a context for a naming scope
	 * 
	 * @param i
	 *            imported modules
	 * @param s
	 *            current yang specification
	 */
	public YangContext(Vector<YANG_Import> i, YANG_Specification s) {
		spectypes = new YangSpecTypes();
		specnodes = new YangSpecNode();
		spec = s;
		imports = i;
	}

	/**
	 * Get imported modules
	 * 
	 * @return a vector of YANG_Import for this context
	 */
	public Vector<YANG_Import> getImports() {
		return imports;
	}

	/**
	 * Add all nodes of the given context to this context.
	 * 
	 * @param sc
	 * @throws YangParserException
	 */
	public void addSubContext(YangContext sc) throws YangParserException {
		Hashtable<String, YANG_Body> subnodes = sc.getSpecNodes().getNodes();
		for (Enumeration<String> ek = subnodes.keys(); ek.hasMoreElements();) {
			String k = ek.nextElement();
			YANG_Body body = subnodes.get(k);
			if (body instanceof YANG_TypeDef)
				addTypeDef((YANG_TypeDef) body);
			else if (body instanceof YANG_Grouping)
				addGrouping((YANG_Grouping) body);
			else
				specnodes.addSubNode(k, body);
		}
		// checkTypes();
		pendingUnions();
	}

	/**
	 * Add a node to this context
	 * 
	 * @param b
	 *            a yang node
	 * @throws YangParserException
	 *             if a typedef or a grouping is redefined or if the node
	 *             already exists in this context or if a typedef or a grouping
	 *             uses a built-in type.
	 */
	public void addNode(YANG_Body b) throws YangParserException {
		
			if (b instanceof YANG_TypeDef)
				addTypeDef((YANG_TypeDef) b);
			else if (b instanceof YANG_Grouping)
				addGrouping((YANG_Grouping) b);
			else if (b instanceof YANG_Extension)
				addExtension((YANG_Extension) b);
			else
				specnodes.put(getModuleSpecName() + ":" + b.getBody(), b);
	}

	/**
	 * Ask if a node is defined
	 * 
	 * @param n
	 *            the name of the node
	 * @return true if defined in this context;
	 */
	public boolean isNodeDefined(String n) {
		return specnodes.isDefined(getModuleSpecName() + ":" + n);
	}

	/**
	 * Ask if a type is defined in this context.
	 * 
	 * @param t
	 * @return true if the type is in this context, false else.
	 * @throws YangParserException
	 *             if there is an unknown prefix or if a built-in type is
	 *             prefixed
	 */
	public boolean isTypeDefined(YANG_Type t) throws YangParserException {
		String cn = canonicalTypeName(t, new Hashtable<String, YANG_Type>());
		return specnodes.isDefinedAsTypeDef(cn);
	}

	public boolean isTypeDefined(String t) {
		return specnodes.isDefinedAsTypeDef(getModuleSpecName() + ":" + t);
	}

	/**
	 * Ask if an used extension exist in this context.
	 * 
	 * @param u
	 *            an unknown statement
	 * @return true if the extension is defined in this context.
	 * @throws YangParserException
	 *             if there is an unknown prefix or if a built-in type is
	 *             prefixed
	 */
	public boolean isExtensionDefined(YANG_Unknown u)
			throws YangParserException {
		String prefix = u.getPrefix();
		String suffix = u.getExtension();
		YANG_Type faketype = new YANG_Type(0);
		faketype.setType(prefix + ":" + suffix);
		String fakenametype = canonicalTypeName(faketype,
				new Hashtable<String, YANG_Type>());
		return specnodes.isDefinedAsExtension(fakenametype);
	}
	
	public YANG_Extension getExtension(YANG_Unknown u)
	throws YangParserException {

		String prefix = u.getPrefix();
		String suffix = u.getExtension();
		YANG_Type faketype = new YANG_Type(0);
		faketype.setType(prefix + ":" + suffix);
		String fakenametype = canonicalTypeName(faketype,
				new Hashtable<String, YANG_Type>());
		return specnodes.getExtension(fakenametype);
	}

	/**
	 * Ask if an used grouping element exist.
	 * 
	 * @param u
	 *            a YANG_Uses element
	 * @return true if the grouping used exist in this context.
	 * @throws YangParserException
	 *             when the grouping does not exist in this context.
	 */
	public boolean isGroupingDefined(YANG_Uses u) throws YangParserException {
		String uses = u.getUses();
		if (uses.indexOf(':') != -1) {

			String prefix = uses.substring(0, uses.indexOf(':'));
			String suffix = uses
					.substring(uses.indexOf(':') + 1, uses.length());
			String cn = null;
			try {
				cn = canonicalTypeName(prefix, suffix,
						new Hashtable<String, YANG_Type>());
			} catch (YangParserException e) {
				throw new YangParserException("@" + u.getLine() + "."
						+ u.getCol() + ":" + e.getMessage());
			}
			return specnodes.isDefinedAsGrouping(cn);
		} else
			return specnodes.isDefinedAsGrouping(getModuleSpecName() + ":"
					+ uses);
	}

	public boolean isGroupingDefined(String g) {
		return specnodes.isDefinedAsGrouping(getModuleSpecName() + ":" + g);
	}

	public YANG_Grouping getUsedGrouping(YANG_Uses u) {
		String uses = u.getUses();
		if (uses.indexOf(':') != -1) {

			String prefix = uses.substring(0, uses.indexOf(':'));
			String suffix = uses
					.substring(uses.indexOf(':') + 1, uses.length());
			String cn = null;
			try {
				cn = canonicalTypeName(prefix, suffix,
						new Hashtable<String, YANG_Type>());
			} catch (YangParserException e) {
				// must not occurs
				System.err.println("Panic in used grouping");
			}
			return specnodes.getUsedGrouping(cn);
		} else{
			return specnodes.getUsedGrouping(getModuleSpecName() + ":" + uses);}

	}

	/**
	 * Clone the context. It must be used before going into an substatement
	 */
	public YangContext clone() {
		YangSpecTypes cspectypes = spectypes.clone();
		YangSpecNode cspecnodes = specnodes.clone();
		return new YangContext(cspectypes, cspecnodes, imports, spec);
	}

	/**
	 * 
	 * @param c
	 *            an external context
	 * @return true if all externals types are inside this context
	 */
	public boolean contains(YangContext c) {
		// return spectypes.contains(c.getSpecTypes());
		return specnodes.contains(c.getSpecNodes());
	}

	/**
	 * Get the context specification
	 * 
	 * @return a YANG_Module or a YANG_subModule
	 */
	public YANG_Specification getSpec() {
		return spec;
	}

	/**
	 * Get the module name or the belongs-to submodule name, not the submodule
	 * name.
	 * 
	 * @return a string in the module name space.
	 */
	public String getModuleSpecName() {
		if (spec instanceof YANG_Module)
			return spec.getName();
		else
			return ((YANG_SubModule) spec).getBelong().getBelong();
	}

	private YangSpecTypes getSpecTypes() {
		return spectypes;
	}

	private YangSpecNode getSpecNodes() {
		return specnodes;
	}

	/**
	 * Merge an external context to the current context.
	 * 
	 * @param c
	 *            externals context
	 */
	public void merge(YangContext c) {
		spectypes.merge(c.getSpecTypes());
		specnodes.merge(c.getSpecNodes());
	}

	/**
	 * Check if all typedefs have a resolved type and there is no circular
	 * references
	 * 
	 * @throws YangParserException
	 *             if a type is not defined or if there is a circular reference
	 */
	public void checkTypes() throws YangParserException {
		spectypes.check(getModuleSpecName());
	}

	/**
	 * Get the typedef statement that defines the given typedef
	 * 
	 * @param typedef
	 * @return the YANG_TypeDef
	 */
	public YANG_TypeDef getTypeDef(YANG_TypeDef typedef) {
		for (Enumeration<YANG_Body> eb = getSpecNodes().getNodes().elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			if (body instanceof YANG_TypeDef)
				if (((YANG_TypeDef) body).getTypeDef().compareTo(
						typedef.getType().getType()) == 0)
					return (YANG_TypeDef) body;
		}
		return null;

	}

	private void addGrouping(YANG_Grouping g) throws YangParserException {

		// Are we trying to redefine a built-in type ?

		if (YangBuiltInTypes.isBuiltIn(g.getGrouping()))
			throw new YangParserException(spec.getName() + " : built-in type "
					+ g.getGrouping() + " cannot be redefined", g.getLine(), g
					.getCol());

		specnodes.put(getModuleSpecName() + ":" + g.getGrouping(), g);
	}

	private void addExtension(YANG_Extension e) throws YangParserException {

		specnodes.put(getModuleSpecName() + ":" + e.getExtension(), e);
	}

	private void addTypeDef(YANG_TypeDef td) throws YangParserException {

		// Are we trying to redefine a built-in type ?

		if (YangBuiltInTypes.isBuiltIn(td.getTypeDef()))
			throw new YangParserException(spec.getName() + " : built-in type "
					+ td.getTypeDef() + " cannot be redefined", td.getLine(),
					td.getCol());

		YANG_Type type = td.getType();

		String typestr = canonicalTypeName(type, pendinguniontypes);

		specnodes.put(getModuleSpecName() + ":" + td.getTypeDef(), td);
		spectypes.add(getModuleSpecName() + ":" + td.getTypeDef(), typestr, td);

	}

	private String canonicalTypeName(YANG_Type type,
			Hashtable<String, YANG_Type> pendinguniontype)
			throws YangParserException {
		String prefix = null;
		String suffix = null;

		if (type.isPrefixed())
			prefix = type.getPrefix();
		suffix = type.getSuffix();
		String cn = null;
		try {
			cn = canonicalTypeName(prefix, suffix, pendinguniontype);
		} catch (YangParserException e) {
			throw new YangParserException("@" + type.getLine() + "."
					+ type.getCol() + ":" + e.getMessage());
		}

		if (YangBuiltInTypes.union.compareTo(suffix) == 0) {
			YANG_UnionSpecification unionspec = type.getUnionSpec();
			pendingUnionTypes(unionspec, pendinguniontype, imports, spec);
		}
		return cn;

	}

	private String canonicalTypeName(String prefix, String suffix,
			Hashtable<String, YANG_Type> pendinguniontype)
			throws YangParserException {
		String result = null;
		if (prefix == null) {
			if (YangBuiltInTypes.isBuiltIn(suffix))
				return suffix;
			else
				return getModuleSpecName() + ":" + suffix;
		} else {
			if (YangBuiltInTypes.isBuiltIn(suffix))
				throw new YangParserException("built in type " + suffix
						+ " cannot be prefixed");
			boolean found = false;
			for (Enumeration<YANG_Import> ei = imports.elements(); ei
					.hasMoreElements()
					&& !found;) {
				YANG_Import impo = ei.nextElement();

				if (impo.getPrefix().getPrefix().equals(prefix)) {
					result = impo.getImportedModule() + ":" + suffix;
					found = true;
				}
			}
			if (!found) {
				if (spec instanceof YANG_Module) {
					if (spec.getPrefix().getPrefix().equals(prefix)) {
						found = true;
						result = spec.getName() + ":" + suffix;
					}
				}
				if (!found){
					throw new YangParserException("the prefix " + prefix
							+ " is not a prefix of an imported module");
				}
			}
		}
		return result;
	}

	private void pendingUnionTypes(YANG_UnionSpecification us,
			Hashtable<String, YANG_Type> pendinguniontype,
			Vector<YANG_Import> imports, YANG_Specification spec)
			throws YangParserException {
		for (Enumeration<YANG_Type> et = us.getTypes().elements(); et
				.hasMoreElements();) {
			YANG_Type utype = et.nextElement();
			if (!YangBuiltInTypes.isBuiltIn(utype.getType()))
				pendinguniontype.put(
						canonicalTypeName(utype, pendinguniontypes), utype);
			else if (YangBuiltInTypes.union.compareTo(utype.getType()) == 0) {
				YANG_UnionSpecification uspec = utype.getUnionSpec();
				pendingUnionTypes(uspec, pendinguniontype, imports, spec);
			}
		}

	}

	/**
	 * Resolve types used inside an union type. Must be called after all
	 * typedefs of a naming scope are added
	 * 
	 * @throws YangParserException
	 *             if a type is not defined
	 */
	public void pendingUnions() throws YangParserException {
		for (Enumeration<String> ek = pendinguniontypes.keys(); ek
				.hasMoreElements();) {
			String k = ek.nextElement();
			YANG_Type ptype = pendinguniontypes.get(k);
			if (spectypes.get(k) == null) {
				throw new YangParserException("@" + ptype.getLine() + "."
						+ ptype.getCol() + ":Type " + k
						+ " in union is not defined");
			}
		}

	}

	/**
	 * Get the built-in base type of the given type name
	 * 
	 * @param t
	 * @return
	 */
	public String getBuiltInType(YANG_Type type) throws YangParserException {
		String t = canonicalTypeName(type, new Hashtable<String, YANG_Type>());
		return getSpecTypes().getBuiltInType(t);
	}

	/**
	 * Get the typedef used to defined the given type
	 * 
	 * @param type
	 * @return typedef
	 * @throws YangParserException
	 */
	public YANG_TypeDef getBaseType(YANG_Type type) throws YangParserException {
		String t = canonicalTypeName(type, new Hashtable<String, YANG_Type>());
		return getSpecTypes().getBaseType(t);
	}

	/**
	 * Get the typedef that define the base type of the given typedef
	 * 
	 * @param td
	 *            a typedef
	 * @return the typedef or null if the base type is a built-in type.
	 */
	public YANG_TypeDef getBaseTypeDef(YANG_TypeDef typedef)
			throws YangParserException {
		return getSpecTypes().getBaseType(typedef);
	}

	/**
	 * Get the typedef that defines the given type
	 * 
	 * @param t
	 *            the canonical name of the type
	 * @return the typedef of null if the type is a built-in type
	 */
	public YANG_TypeDef getTypeDef(YANG_Type type) throws YangParserException {
		String t = canonicalTypeName(type, new Hashtable<String, YANG_Type>());
		return getSpecTypes().getTypeDef(t);
	}

	/**
	 * Print types resolution and nodes name
	 */
	public String toString() {
		return spectypes.toString() + "\n" + specnodes.toString();
	}

}