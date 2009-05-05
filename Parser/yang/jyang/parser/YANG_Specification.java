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

	public void check(String[] p, Vector<String> checked)
			throws YangParserException {
		check(p, checked, null);
		checkTreeNode(p);

	}

	@SuppressWarnings("unchecked")
	public void check(String[] p, Vector<String> checkeds, YangContext c)
			throws YangParserException {

		if (checkeds.contains(getName())) {
			return;
		}
		checkeds.add(getName());
		//try {
			checkHeader(p);
			checkLinkage(p);

			YangContext context = buildSpecContext(p, null,
					(Vector<String>) checkeds.clone());

			context.pendingUnions();

			context.checkTypes();

			checkBodies(p, checkeds, context);

			if (c != null) {
				c.merge(context);
				context = c;
			}

			for (Enumeration<YANG_Module> es = importeds.elements(); es
					.hasMoreElements();) {
				YANG_Module module = es.nextElement();
				String importedmodulename = module.getName();
				if (!checkeds.contains(importedmodulename)) {
					Vector<String> cks = (Vector<String>) checkeds.clone();
					module.check(p, cks);
				}
				else 
					throw new YangParserException(importedmodulename + " and " + getName() + " have circular import chain");
			}
			for (Enumeration<YANG_SubModule> es = includeds.elements(); es
					.hasMoreElements();) {
				YANG_SubModule submodule = es.nextElement();
				String includedsubmodulename = submodule.getName();
				if (!checkeds.contains(includedsubmodulename)) {
					Vector<String> cks = (Vector<String>) checkeds.clone();
					submodule.check(p, cks);
				}
				else 
					throw new YangParserException(includedsubmodulename + " and " + getName() + " have circular include chain");
			}
//		} catch (YangParserException e) {
//			throw new YangParserException("In " + getName() + " : " + e.getMessage());
//		}
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
				System.err.println(ye.getMessage());
			}
		}

	}

	public YangContext buildSpecContext(String[] paths, YangContext c,
			Vector<String> builded) throws YangParserException {

		YangContext specontext = getThisSpecContext();
		builded.add(getName());

		if (c != null) {
			if (c.contains(specontext))
				return c;
			else
				c.merge(specontext);

		} else {
			c = specontext;
		}
		if (importeds.size() == 0)
			checkImport(paths);
		for (Enumeration<YANG_Module> em = importeds.elements(); em
				.hasMoreElements();) {
			YANG_Module mod = em.nextElement();
			// if (!builded.contains(mod.getName())) {
			YangContext clc = c.clone();
			try {
				YangContext importedcontexts = mod.buildSpecContext(paths, clc,
						builded);
				c.merge(importedcontexts);
			} catch (YangParserException ye) {
				System.err.println(ye.getMessage());
			}
			// }
		}
		if (includeds.size() == 0)
			checkInclude(paths);
		for (Enumeration<YANG_SubModule> es = includeds.elements(); es
				.hasMoreElements();) {
			YANG_SubModule smod = es.nextElement();
			// if (!builded.contains(smod.getName())) {
			YangContext clc = c.clone();
			try {
				YangContext includedcontexts = smod.buildSpecContext(paths,
						clc, builded);
				c.merge(includedcontexts);
			} catch (YangParserException ye) {
				System.err.println(ye.getMessage());
			}
			// }
		}

		return c;

	}

	public YangContext getThisSpecContext() throws YangParserException {

		YangContext context = new YangContext(getImports(), this);

		for (Enumeration<YANG_Body> eb = getBodies().elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			try {
				context.addNode(body);
			} catch (YangParserException ye) {
				System.err.println(getName() + ye.getMessage());
			}
		}
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
			try {
				File externalfile = new File(yangspecfilename);
				yang.ReInit(new FileInputStream(externalfile));
				found = true;
				try {
					externalspec = yang.Start();
				} catch (ParseException p) {
					throw new YangParserException(" -> " + externalmodulename
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
