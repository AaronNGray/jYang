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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class YANG_Specification extends SimpleNode {

	static protected Hashtable<String, YANG_Specification> checkedSpecs = new Hashtable<String, YANG_Specification>();
	static protected boolean isCheckOk = true;
	protected Vector<YANG_Header> headers = new Vector<YANG_Header>();
	protected YANG_YangVersion yangversion = null;
	protected YANG_NameSpace namespace = null;
	protected YANG_Prefix prefix = null;
	protected Vector<YANG_Linkage> linkages = new Vector<YANG_Linkage>();
	protected Vector<YANG_Meta> metas = new Vector<YANG_Meta>();
	protected Vector<YANG_Revision> revisions = new Vector<YANG_Revision>();
	protected Vector<YANG_Body> bodies = new Vector<YANG_Body>();

	protected Vector<YANG_Module> importeds = new Vector<YANG_Module>();
	protected Vector<YANG_SubModule> includeds = new Vector<YANG_SubModule>();

	public YANG_Specification(int i) {
		super(i);
	}

	public YANG_Specification(yang p, int i) {
		super(p, i);
	}

	public Vector<YANG_Header> getHeaders() {
		return headers;
	}

	public YANG_NameSpace getNameSpace() {
		return namespace;
	}

	public YANG_Prefix getPrefix() {
		return prefix;
	}

	public Vector<YANG_Linkage> getLinkages() {
		return linkages;
	}

	public YANG_YangVersion getYangVersion() {
		return yangversion;
	}

	public Vector<YANG_Meta> getMetas() {
		return metas;
	}

	public void addLinkage(YANG_Linkage l) {
		linkages.add(l);
	}

	public void addRevision(YANG_Revision r) {
		revisions.add(r);
	}

	public Vector<YANG_Revision> getRevisions() {
		return revisions;
	}

	public void addBody(YANG_Body b) {
		bodies.add(b);
	}

	public Vector<YANG_Body> getBodies() {
		return bodies;
	}

	public void check() throws YangParserException {
		String[] path = new String[1];
		path[0] = ".";
		Vector<String> cked = new Vector<String>();
		check(path, cked);
	}
	
	public static boolean isCheckOk(){
		return isCheckOk;
	}

	/**
	 * Initial check of this specification. It starts the checking with an empty
	 * context
	 * 
	 * @param p
	 *            path for yang files
	 * @param checked
	 *            vector of checked specification name (must be empty)
	 * @throws YangParserException
	 *             if a semantical error occurs
	 */
	public void check(String[] p, Vector<String> checked)
			throws YangParserException {
		check(p, checked, null);
		if (isCheckOk)
			checkTreeNode(p);

	}

	protected YangContext checkAsExternal(String[] p, Vector<String> checked)
			throws YangParserException {
		YangContext c = check(p, checked, null);
		if (isCheckOk)
			checkTreeNode(p);
		return c;
	}

	@SuppressWarnings("unchecked")
	public YangContext check(String[] p, Vector<String> checkeds, YangContext c)
			throws YangParserException {

		if (checkeds.contains(getName())) {
			return c;
		}
		checkeds.add(getName());
		checkHeader(p);
		checkLinkage(p);

		YangContext context = buildSpecContext(p, null,
				(Vector<String>) checkeds.clone());

		context.pendingUnions();
		context.checkTypes();

		checkBodies(p, checkeds, context);

		if (c != null)
			c.merge(context);
		else
			c = context;

		return c;

	}

	private void checkBodies(String[] p, Vector<String> ckd, YangContext context)
			throws YangParserException {

		for (Enumeration<YANG_Body> eb = getBodies().elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			YangContext bodycontext = context.clone();
			try {
				body.setRootNode(true);
				body.checkBody(bodycontext);
			} catch (YangParserException ye) {
				System.err.println(getName() + ye.getMessage());
			}
		}

	}

	public YangContext buildSpecContext(String[] paths, YangContext c,
			Vector<String> builded) throws YangParserException {

		if (c == null)
			c = new YangContext(getImports(), this);

		if (importeds.size() == 0)
			checkImport(paths);
		for (Enumeration<YANG_Module> es = importeds.elements(); es
				.hasMoreElements();) {
			YANG_Module module = es.nextElement();
			String importedmodulename = module.getName();
			if (!builded.contains(importedmodulename)) {
				Vector<String> cks = (Vector<String>) builded.clone();
				// module.check(paths, cks);
				YangContext clc = c.clone();
				try {
					YangContext importedcontexts = module.buildSpecContext(
							paths, clc, builded);
					c.merge(importedcontexts);
				} catch (YangParserException ye) {
					throw new YangParserException(getName()
							+ " has an error in imported module : "
							+ module.getName() + "\n\t" + ye.getMessage());
				}
			} else
				throw new YangParserException(importedmodulename + " and "
						+ getName() + " have circular import chain");
		}

		if (includeds.size() == 0)
			checkInclude(paths);
		for (Enumeration<YANG_SubModule> es = includeds.elements(); es
				.hasMoreElements();) {
			YANG_SubModule submodule = es.nextElement();
			String includedsubmodulename = submodule.getName();
			if (!builded.contains(includedsubmodulename)) {
				try {
					Vector<String> cks = (Vector<String>) builded.clone();
					// submodule.check(paths, cks);
					// YangContext clc = c.clone();
					// YangContext includedcontexts =
					// submodule.buildSpecContext(paths,
					// clc, builded);
					YangContext includedcontexts = submodule.checkAsExternal(
							paths, cks);
					c.merge(includedcontexts);
				} catch (YangParserException ye) {
					throw new YangParserException(getName()
							+ " has an error in included submodule : "
							+ submodule.getName() + "\n\t" + ye.getMessage());
				}
			} else
				throw new YangParserException(includedsubmodulename + " and "
						+ getName() + " have circular include chain");
		}

		YangContext specontext = getThisSpecContext(c);
		builded.add(getName());

		if (c != null) {
			if (c.contains(specontext))
				return c;
			else
				c.merge(specontext);

		} else {
			c = specontext;
		}

		return c;

	}

	public YangContext getThisSpecContext(YangContext context)
			throws YangParserException {

		// YangContext context = new YangContext(getImports(), this);

		for (Enumeration<YANG_Body> eb = getBodies().elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			if (!(body instanceof YANG_Uses))
				try {
					context.addNode(body);
				} catch (YangParserException ye) {
					System.err.println(getName() + ye.getMessage());
				}
		}

		YANG_Body body = null;

		try {
			for (Enumeration<YANG_Body> eb = getBodies().elements(); eb
					.hasMoreElements();) {
				body = eb.nextElement();
				if (body instanceof YANG_Uses) {
					YANG_Uses uses = (YANG_Uses) body;
					uses.check(context);
					YANG_Grouping g = uses.getGrouping();
					Vector<YANG_Grouping> usedgroupings = g.getGroupings();
					Vector<YANG_TypeDef> usedtypedefs = g.getTypeDefs();
					Vector<YANG_DataDef> useddatadefs = g.getDataDefs();
					for (Enumeration<YANG_TypeDef> et = usedtypedefs.elements(); et
							.hasMoreElements();) {
						YANG_TypeDef typedef = (YANG_TypeDef) et.nextElement();
						context.addNode(typedef);
					}
					for (Enumeration<YANG_Grouping> eg = usedgroupings
							.elements(); eg.hasMoreElements();) {
						YANG_Grouping ug = (YANG_Grouping) eg.nextElement();
						context.addNode(ug);
					}
					for (Enumeration<YANG_DataDef> ued = useddatadefs
							.elements(); ued.hasMoreElements();) {
						YANG_DataDef ddef = (YANG_DataDef) ued.nextElement();
						context.addNode(ddef);
					}
				}
			}
		} catch (YangParserException ye) {
			String mes = ye.getMessage();
			mes = mes.substring(mes.indexOf(":") + 1, mes.length());
			mes = mes.substring(0, mes.indexOf("defined"));
			mes = "@" + body.getLine() + "." + body.getCol() + ":grouping "
					+ body.getBody() + " used " + mes + " used elsewhere";
			// System.err.println(getName() + mes);
			throw new YangParserException(getName() + mes);
		}
		/*
		 * Vector<YANG_Body> cleanedbodies = new Vector<YANG_Body>();
		 * 
		 * for (Enumeration<YANG_Body> eb = bodies.elements();
		 * eb.hasMoreElements();){ YANG_Body b = eb.nextElement(); if (!(b
		 * instanceof YANG_Uses)) cleanedbodies.add(b); }
		 * 
		 * bodies = cleanedbodies; bodies.addAll(usedbodies);
		 */
		return context;
	}

	public abstract void checkHeader(String[] p) throws YangParserException;

	public abstract String getName();

	public Vector<YANG_Specification> getImportedModules(String[] paths)
			throws YangParserException {
		Vector<YANG_Specification> im = new Vector<YANG_Specification>();
		for (Enumeration<YANG_Linkage> el = getLinkages().elements(); el
				.hasMoreElements();) {
			YANG_Linkage linkage = el.nextElement();
			if (linkage instanceof YANG_Import) {
				YANG_Import imported = (YANG_Import) linkage;
				String importedspecname = imported.getImportedModule();
				YANG_Specification importedspec = getExternal(paths,
						importedspecname);
				im.add(importedspec);

			}
		}
		return im;
	}

	public Vector<YANG_Import> getImports() {
		Vector<YANG_Import> imports = new Vector<YANG_Import>();
		for (Enumeration<YANG_Linkage> el = getLinkages().elements(); el
				.hasMoreElements();) {
			YANG_Linkage linkage = el.nextElement();
			if (linkage instanceof YANG_Import)
				imports.add((YANG_Import) linkage);

		}
		return imports;
	}

	public Vector<YANG_Include> getIncludes() {
		Vector<YANG_Include> includes = new Vector<YANG_Include>();
		for (Enumeration<YANG_Linkage> el = getLinkages().elements(); el
				.hasMoreElements();) {
			YANG_Linkage linkage = el.nextElement();
			if (linkage instanceof YANG_Include)
				includes.add((YANG_Include) linkage);
		}
		return includes;
	}

	public Vector<YANG_Specification> getIncludedSubModules(String[] paths)
			throws YangParserException {
		Vector<YANG_Specification> is = new Vector<YANG_Specification>();
		for (Enumeration<YANG_Linkage> el = getLinkages().elements(); el
				.hasMoreElements();) {
			YANG_Linkage linkage = el.nextElement();
			if (linkage instanceof YANG_Include) {
				YANG_Include included = (YANG_Include) linkage;
				String includedspecname = included.getIncludedModule();
				YANG_Specification includedspec = getExternal(paths,
						includedspecname);
				is.add(includedspec);
			}
		}
		return is;

	}

	/**
	 * check correctness of include and import statements but do not a check of
	 * these included or imported specifications
	 * 
	 * @param p
	 * @throws YangParserException
	 */
	protected void checkLinkage(String[] paths) throws YangParserException {
		checkImport(paths);
		checkInclude(paths);
	}

	/**
	 * Check if imported modules are accessible and have a correct syntax
	 * 
	 * @param paths
	 *            directories paths where find yang modules and submodules
	 * 
	 * @throws YangParserException
	 */
	protected void checkImport(String[] paths) throws YangParserException {
		Vector<YANG_Specification> imported = getImportedModules(paths);
		for (Enumeration<YANG_Specification> es = imported.elements(); es
				.hasMoreElements();) {
			YANG_Specification importedspec = es.nextElement();
			if (!(importedspec instanceof YANG_Module))
				throw new YangParserException("Imported  "
						+ importedspec.getName() + " is not a module");
			else
				importeds.add((YANG_Module) importedspec);
		}
	}

	protected abstract void checkInclude(String[] paths)
			throws YangParserException;

	/*
	 * protected YANG_Specification parseExternal(String[] paths, String
	 * externalmodulename, Vector<String> checkeds) throws YangParserException {
	 * YANG_Specification spec = getExternal(paths, externalmodulename);
	 * spec.check(paths, checkeds); return spec; }
	 */
	protected YANG_Specification getExternal(String[] paths,
			String externalmodulename) throws YangParserException {
		int i = 0;
		boolean found = false;
		YANG_Specification externalspec = null;
		while (i < paths.length && !found) {
			String directory = paths[i++];
			String yangspecfilename = directory + File.separator
					+ externalmodulename + ".yang";
			if (checkedSpecs.containsKey(externalmodulename))
				return checkedSpecs.get(externalmodulename);
			try {
				File externalfile = new File(yangspecfilename);
				yang.ReInit(new FileInputStream(externalfile));
				found = true;
				try {
					externalspec = yang.Start();
				} catch (ParseException p) {
					throw new YangParserException(externalmodulename + " : "
							+ p.getMessage());

				}
			} catch (NullPointerException np) {
				// Must not occurs
				np.printStackTrace();
				System.err.println("Panic, abort");
				System.exit(-3);
			} catch (FileNotFoundException fnf) {
				// nothing to do
				// pass to the next path
			}
		}
		if (!found)
			throw new YangParserException("@external yang specification "
					+ externalmodulename + " not found");
		checkedSpecs.put(externalmodulename, externalspec);
		return externalspec;
	}

	private void checkTreeNode(String[] p) throws YangParserException {
		Vector<String> builded = new Vector<String>();
		Hashtable<String, YangTreeNode> importedtreenodes = new Hashtable<String, YangTreeNode>();
		builded.add(getName());
		YangTreeNode tn = buildTreeNode(p, builded, importedtreenodes);

		tn.check(this, tn, tn, importedtreenodes);
	}

	protected YangTreeNode buildTreeNode(String[] p, Vector<String> builded,
			Hashtable<String, YangTreeNode> importedtreenodes) {

		YangTreeNode ytn = new YangTreeNode();
		for (Enumeration<YANG_Body> eb = bodies.elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			body.builtTreeNode(ytn, ytn);
		}
		try {
			for (Enumeration<YANG_Specification> ei = getImportedModules(p)
					.elements(); ei.hasMoreElements();) {
				YANG_Specification spec = ei.nextElement();
				if (!builded.contains(spec.getName())) {
					builded.add(spec.getName());
					YangTreeNode itn = spec.buildTreeNode(p, builded,
							importedtreenodes);
					importedtreenodes.put(spec.getName(), itn);
				}
			}

		} catch (YangParserException e) {
			System.err
					.println("panic could not happen in buildTreeNode in YANG_Specification");
			System.exit(-1);
		}
		return ytn;
	}
}
