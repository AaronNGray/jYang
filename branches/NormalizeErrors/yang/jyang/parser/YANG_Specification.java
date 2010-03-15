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
import java.text.MessageFormat;
import java.util.*;

public abstract class YANG_Specification extends SimpleYangNode {

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

	private boolean organization = false, contact = false, description = false,
			reference = false;

	private YangTreeNode schemaTree = null;

	public YangTreeNode getSchemaTree() {
		return schemaTree;
	}

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

	public void addMeta(YANG_Meta m) {

		if (m instanceof YANG_Organization) {
			if (organization)
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "organization");
			else
				organization = true;
		}
		if (m instanceof YANG_Contact) {
			if (contact)
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "contact");
			else
				contact = true;
		}
		if (m instanceof YANG_Description) {
			if (description)
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "description");
			else
				description = true;
		}
		if (m instanceof YANG_Reference) {
			if (reference)
				YangErrorManager.tadd(filename, m.getLine(), m.getCol(),
						"unex_kw", "reference");
			else
				reference = true;
		}
		metas.add(m);
	}

	public void check() throws YangParserException {
		String[] path = new String[1];
		path[0] = ".";
		Vector<String> cked = new Vector<String>();
		check(path, cked);
	}

	public static boolean isCheckOk() {
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
		checkTreeNode(p);

	}

	protected YangContext checkAsExternal(String[] p, Vector<String> checked)
			throws YangParserException {
		YangContext c = check(p, checked, null);
		// if (isCheckOk)
		// checkTreeNode(p);
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

		YangContext localcontext = buildSpecContext(p, null,
				(Vector<String>) checkeds.clone());

		localcontext.pendingUnions();

		localcontext.checkTypes();

		if (c != null)
			 c.merge(localcontext);
			//c.mergeChecked(localcontext);
		else
			c = localcontext;
		checkBodies(p, checkeds, c);
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
				YangContext clc = c.clone();
				try {
					YangContext importedcontexts = module.buildSpecContext(
							paths, null, cks);
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
					YangContext includedcontexts = submodule.checkAsExternal(
							paths, cks);
					if (this instanceof YANG_Module)
						c.mergeChecked(includedcontexts);
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

		for (Enumeration<YANG_Body> eb = getBodies().elements(); eb
				.hasMoreElements();) {
			YANG_Body body = eb.nextElement();
			if (!(body instanceof YANG_Uses || body instanceof YANG_Augment))
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
				YANG_Revision revision = included.getRevision();
				if (revision != null)
					includedspecname += "." + revision.getDate();
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
	protected void checkImport(String[] paths) {

		for (YANG_Linkage link : getLinkages()) {
			if (link instanceof YANG_Import) {
				YANG_Import imported = (YANG_Import) link;
				String importedspecname = imported.getImportedModule();
				YANG_Revision revision = imported.getRevision();
				if (revision != null)
					importedspecname += "." + revision.getDate();
				YANG_Specification importedspec = getExternal(paths,
						importedspecname);
				if (!(importedspec instanceof YANG_Module))
					YangErrorManager.tadd(filename, imported.getLine(),
							imported.getCol(), "not_module", importedspecname);
				else
					importeds.add((YANG_Module) importedspec);
			}
		}
	}

	protected abstract void checkInclude(String[] paths)
			throws YangParserException;

	protected YANG_Specification getExternal(String[] paths,
			String externalmodulename) {
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
					yang.setFileName(yangspecfilename);
					externalspec = yang.Start();
					// externalspec.check();
					Vector<String> checkeds = new Vector<String>();
					externalspec.check(paths, checkeds);
				} catch (ParseException p) {
					System.err.println(externalmodulename + ":"
							+ p.getMessage());
				}
			} catch (NullPointerException np) {
			} catch (FileNotFoundException fnf) {
				// nothing to do
				// pass to the next path
			}
		}
		if (!found)
			System.err.println("@external yang specification "
					+ externalmodulename + " not found");
		if (externalmodulename != null && externalspec != null)
			checkedSpecs.put(externalmodulename, externalspec);
		return externalspec;
	}

	protected YANG_Specification getExternal(String[] paths,
			String externalmodulename, boolean b) throws YangParserException {
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
					yang.setFileName(yangspecfilename);
					externalspec = yang.Start();
				} catch (ParseException p) {
					throw new YangParserException(externalmodulename + ":"
							+ p.getMessage());
				}
			} catch (NullPointerException np) {
			} catch (FileNotFoundException fnf) {
				// nothing to do
				// pass to the next path
			}
		}
		if (!found)
			YangErrorManager.tadd(getName(), getLine(), getCol(),
					"file_not_found", externalmodulename);
		else
			checkedSpecs.put(externalmodulename, externalspec);
		return externalspec;
	}

	private void checkTreeNode(String[] p) throws YangParserException {
		Vector<String> builded = new Vector<String>();
		Hashtable<String, YangTreeNode> importedtreenodes = new Hashtable<String, YangTreeNode>();
		builded.add(getName());
		schemaTree = buildTreeNode(p, builded, importedtreenodes);
		schemaTree.check(this, schemaTree, schemaTree, importedtreenodes);
	}

	public YangTreeNode buildTreeNode(String[] p, Vector<String> builded,
			Hashtable<String, YangTreeNode> importedtreenodes) {

		YangTreeNode root = new YangTreeNode();

		for (YANG_Body body : bodies)
			if (body instanceof YANG_DataDef) {
				YANG_DataDef ddef = (YANG_DataDef) body;
				Vector<YangTreeNode> sons = ddef.groupTreeNode(root);
				for (YangTreeNode son : sons)
					root.addChild(son);
			} else if (body instanceof YANG_Rpc) {
				YANG_Rpc rpc = (YANG_Rpc) body;
				YangTreeNode ytnrpc = new YangTreeNode();
				ytnrpc.setNode(rpc);
				ytnrpc.setParent(root);
				root.addChild(ytnrpc);
				if (rpc.getInput() != null) {
					YangTreeNode ytnrpcin = new YangTreeNode();
					IoDataDef ioddef = new IoDataDef(rpc.getInput());
					ytnrpcin.setNode(ioddef);
					ytnrpcin.setParent(ytnrpc);
					ytnrpc.addChild(ytnrpcin);
					for (YANG_DataDef ddef : rpc.getInput().getDataDefs())
						for (YangTreeNode ichild : ddef.groupTreeNode(ytnrpcin))
							ytnrpcin.addChild(ichild);
				} else {
					YANG_Input in = new YANG_Input(0);
					in.setLine(rpc.getLine());
					in.setCol(rpc.getCol());
					in.setFileName(rpc.getFileName());
					IoDataDef ioddef = new IoDataDef(in);
					YangTreeNode ytnin = new YangTreeNode();
					ytnin.setNode(ioddef);
					ytnin.setParent(ytnrpc);
					ytnrpc.addChild(ytnin);
				}
				if (rpc.getOutput() != null) {
					YangTreeNode ytnrpcout = new YangTreeNode();
					IoDataDef ioddef = new IoDataDef(rpc.getOutput());
					ytnrpcout.setNode(ioddef);
					ytnrpcout.setParent(ytnrpc);
					ytnrpc.addChild(ytnrpcout);
					for (YANG_DataDef ddef : rpc.getOutput().getDataDefs())
						for (YangTreeNode ochild : ddef
								.groupTreeNode(ytnrpcout))
							ytnrpcout.addChild(ochild);
				} else {
					YANG_Output out = new YANG_Output(0);
					out.setLine(rpc.getLine());
					out.setCol(rpc.getCol());
					out.setFileName(rpc.getFileName());
					IoDataDef ioddef = new IoDataDef(out);
					YangTreeNode ytnout = new YangTreeNode();
					ytnout.setNode(ioddef);
					ytnout.setParent(ytnrpc);
					ytnrpc.addChild(ytnout);

				}
			} else if (body instanceof YANG_Notification) {
				YANG_Notification notif = (YANG_Notification) body;
				YangTreeNode ytnn = new YangTreeNode();
				ytnn.setNode(notif);
				ytnn.setParent(root);
				root.addChild(ytnn);
				for (YANG_DataDef ddef : notif.getDataDefs())
					for (YangTreeNode child : ddef.groupTreeNode(ytnn))
						ytnn.addChild(child);
			}
		try {
			for (YANG_Specification spec : getIncludedSubModules(p)) {
				YangTreeNode includedtreenode = spec.buildTreeNode(p, builded,
						importedtreenodes);
				for (YangTreeNode includednode : includedtreenode.getChilds())
					root.includeNode(includednode);
			}
		/*	
			for (YANG_Specification spec : getImportedModules(p)) {
				YangTreeNode includedtreenode = spec.buildTreeNode(p, builded,
						importedtreenodes);
				for (YangTreeNode includednode : includedtreenode.getChilds())
					root.includeNode(includednode);	
			}
			*/
		} catch (YangParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Hashtable<String, YANG_Augment> augs = new Hashtable<String, YANG_Augment>();
		for (YANG_Body body : bodies)
			if (body instanceof YANG_Augment) {
				YANG_Augment aug = (YANG_Augment) body;
				augs.put(aug.getAugment(), aug);
			}
		String[] taugs = new String[augs.size()];
		int ik = 0;
		for (String k : augs.keySet())
			taugs[ik++] = k;

		for (int i = 0; i < taugs.length; i++)
			for (int j = i + 1; j < taugs.length; j++) {
				String[] ti = taugs[i].split("/");
				String[] tj = taugs[j].split("/");
				if (ti.length > tj.length) {
					String ls = taugs[i];
					taugs[i] = taugs[j];
					taugs[j] = ls;
				}
			}

		for (int i = 0; i < taugs.length; i++) {
			YANG_Body augmentedbody = root.getBodyInTree(this, root,
					importedtreenodes, taugs[i]);
			if (augmentedbody == null) {
				YangErrorManager.tadd(filename, augs.get(taugs[i]).getLine(),
						augs.get(taugs[i]).getCol(), "augmented_not_found",
						taugs[i]);
			} else {
				YANG_Augment aug = augs.get(taugs[i]);
				try {
					aug.checkAugment(augmentedbody);
				} catch (YangParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				YangTreeNode augmentednode = root.getNodeInTree(this, root,
						importedtreenodes, taugs[i]);
				augmentednode.augments(aug);
			}
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
		return root;
	}
}
