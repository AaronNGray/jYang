package jyang;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YANG_Augment extends YANG_DataDef implements YANG_CaseDef {

	private String augment = null;
	private YANG_When when = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();
	private Vector<YANG_Case> cases = new Vector<YANG_Case>();
	//private YANG_Input input = null;
	//private YANG_Output output = null;

	private Pattern nid = null;

	private boolean b_when = false, b_status = false, b_description = false,
			b_reference = false, b_input = false, b_output = false;

	public YANG_Augment(int id) {
		super(id);
		try {
			nid = Pattern
					.compile("((/([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*)+)|(([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*((/([_A-Za-z][._\\-A-Za-z0-9]*:)?[_A-Za-z][._\\-A-Za-z0-9]*)+)?)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public YANG_Augment(yang p, int id) {
		super(p, id);
	}

	public void setAugment(String a) throws YangParserException {
		String aa = YangBuiltInTypes.removeQuotesAndTrim(a);
		Matcher m = nid.matcher(aa);
		if (m.matches())
			augment = aa;
		else
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":incorrect node identifier expression :" + a);
	}

	public String getBody() {
		return getAugment();
	}

	public String getAugment() {
		return augment;
	}

	public boolean isAbsoluteSchemaNodeId() {
		return augment.charAt(0) == '/';
	}

	public void setWhen(YANG_When w) throws YangParserException {
		if (b_when)
			throw new YangParserException("When already defined in augment "
					+ augment, w.getLine(), w.getCol());
		b_when = true;
		when = w;
	}

	public YANG_When getWhen() {
		return when;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in augment "
					+ augment, s.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in augment " + augment, d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in augment " + augment, r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}

	public void addCase(YANG_Case c) {
		cases.add(c);
	}

	public Vector<YANG_Case> getCases() {
		return cases;
	}
/*
	public void setInput(YANG_Input i) throws YangParserException {
		if (b_input)
			throw new YangParserException("Input already defined in augment "
					+ augment, i.getLine(), i.getCol());
		b_input = true;
		input = i;
	}

	public YANG_Input getInput() {
		return input;
	}

	public void setOutput(YANG_Output o) throws YangParserException {
		if (b_output)
			throw new YangParserException("Output already defined in augment "
					+ augment, o.getLine(), o.getCol());
		b_output = true;
		output = o;
	}

	public YANG_Output getOutput() {
		return output;
	}
	*/

	public void check(YangContext context) throws YangParserException {
		if ((b_input || b_output)
				&& (datadefs.size() != 0 || cases.size() != 0))
			throw new YangParserException(
					"Input or output statement and datadef or case in augment "
							+ augment, getLine(), getCol());
		if (!(b_input || b_output) && datadefs.size() == 0 && cases.size() == 0)
			throw new YangParserException(
					"Either input / output or datadef / case statements in augment "
							+ augment, getLine(), getCol());

		String nids[] = getAugment().split("/");
		int start = 0;
		if (nids[0].length() == 0)
			start = 1;
		if (nids[start].indexOf(':') != -1) {
			String prefix = nids[start].substring(0, nids[start].indexOf(':'));
			boolean found = false, localprefix = false;

			localprefix = prefix.compareTo(context.getSpec().getPrefix()
					.getPrefix()) == 0;
			found = localprefix;
			for (Enumeration<YANG_Import> ei = context.getImports().elements(); ei
					.hasMoreElements()
					&& !found;) {
				YANG_Import yimport = ei.nextElement();
				found = yimport.getPrefix().getPrefix().compareTo(prefix) == 0;
			}
			if (!found)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":imported module " + prefix + " not found");
			for (int i = start; i < nids.length; i++) {
				if (nids[i].indexOf(':') != -1) {
					if (prefix.compareTo(nids[i].substring(0, nids[i]
							.indexOf(':'))) != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol()
								+ ":change prefix in a node reference "
								+ getAugment());
				} else {
					if (!localprefix)
						throw new YangParserException("@" + getLine() + "."
								+ getCol()
								+ ":change prefix in a node reference "
								+ getAugment());
				}
			}
		}

		Vector<String> caseids = new Vector<String>();
		for (Enumeration<YANG_Case> ec = getCases().elements(); ec
				.hasMoreElements();) {
			YANG_Case ycase = ec.nextElement();
			if (caseids.contains(ycase.getCase()))
				throw new YangParserException("case " + ycase.getCase()
						+ " already defined", ycase.getLine(), ycase.getCol());
			else
				caseids.add(ycase.getCase());
		}

	}

	public void checkAugment(YANG_Body body) throws YangParserException {

		if (getCases().size() != 0 && !(body instanceof YANG_Choice)) {
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":only a choice can be augmented by a case :"
					+ body.getBody() + " at line " + body.getLine()
					+ " is not a choice");
		}

		if (body instanceof YANG_Container) {
			YANG_Container container = (YANG_Container) body;
			checkDouble(container.getDataDefs());
		} else if (body instanceof YANG_List) {
			YANG_List list = (YANG_List) body;
			checkDouble(list.getDataDefs());
		} else if (body instanceof YANG_Choice) {
			YANG_Choice choice = (YANG_Choice) body;
			Vector<YANG_DataDef> vdef = new Vector<YANG_DataDef>();
			Vector<YANG_Case> vc = choice.getCases();
			for (Enumeration<YANG_Case> ecd = vc.elements(); ecd
					.hasMoreElements();) {
				YANG_Case ycase = ecd.nextElement();
				vdef.add(ycase);
			}
			checkDoubleCase(vdef);
		} else if (body instanceof YANG_Case) {
			YANG_Case ycase = (YANG_Case) body;
			Vector<YANG_DataDef> vdef = new Vector<YANG_DataDef>();
			for (Enumeration<YANG_CaseDef> ecdef = ycase.getCaseDefs()
					.elements(); ecdef.hasMoreElements();) {
				YANG_CaseDef cdef = ecdef.nextElement();
				YANG_DataDef ddef = null;
				if (cdef instanceof YANG_AnyXml)
					ddef = (YANG_AnyXml) cdef;
				else if (cdef instanceof YANG_Container)
					ddef = (YANG_Container) cdef;
				else if (cdef instanceof YANG_List)
					ddef = (YANG_List) cdef;
				else if (cdef instanceof YANG_Leaf)
					ddef = (YANG_Leaf) cdef;
				else if (cdef instanceof YANG_LeafList)
					ddef = (YANG_LeafList) cdef;
				else if (cdef instanceof YANG_List)
					ddef = (YANG_List) cdef;
				vdef.add(ddef);
			}
			checkDouble(vdef);

		} else if (body instanceof YANG_Input) {
			YANG_Input input = (YANG_Input) body;
			checkDouble(input.getDataDefs());
		} else if (body instanceof YANG_Output) {
			YANG_Output output = (YANG_Output) body;
			checkDouble(output.getDataDefs());
		} else if (body instanceof YANG_Rpc) {
			YANG_Rpc rpc = (YANG_Rpc) body;
			Vector<YANG_DataDef> vdef = new Vector<YANG_DataDef>();
			if (rpc.getInput() != null)
				vdef.add(rpc.getInput());
			if (rpc.getOutput() != null)
				vdef.add(rpc.getOutput());
			checkDouble(vdef);
		} else if (body instanceof YANG_Notification) {
			YANG_Notification notif = (YANG_Notification) body;
			checkDouble(notif.getDataDefs());
		} else {
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":illegal data node :" + body.getBody() + " at line "
					+ body.getLine() + " can not be augmented");
		}
	}

	private void checkDouble(Vector<YANG_DataDef> vddef)
			throws YangParserException {
		boolean found = false;
		YANG_DataDef augddef = null;
		YANG_DataDef targddef = null;
		for (Enumeration<YANG_DataDef> eda = getDataDefs().elements(); eda
				.hasMoreElements()
				&& !found;) {
			augddef = eda.nextElement();
			for (Enumeration<YANG_DataDef> eddef = vddef.elements(); eddef
					.hasMoreElements()
					&& !found;) {
				targddef = eddef.nextElement();
				found = augddef.getBody().compareTo(targddef.getBody()) == 0;
			}
		}
		if (found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":augmented data node already defined : "
					+ augddef.getBody() + " at line " + augddef.getLine()
					+ " and " + targddef.getBody() + " at line "
					+ targddef.getLine());

	}

	private void checkDoubleCase(Vector<YANG_DataDef> vddef)
			throws YangParserException {
		boolean found = false;
		YANG_DataDef augddef = null;
		YANG_DataDef targddef = null;
		for (Enumeration<YANG_Case> ec = getCases().elements(); ec
				.hasMoreElements()
				&& !found;) {
			augddef = ec.nextElement();
			for (Enumeration<YANG_DataDef> eddef = vddef.elements(); eddef
					.hasMoreElements()
					&& !found;) {
				targddef = eddef.nextElement();
				found = augddef.getBody().compareTo(targddef.getBody()) == 0;
			}
		}
		if (found)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":augmented data node already defined : case "
					+ augddef.getBody() + " at line " + augddef.getLine()
					+ " and case " + targddef.getBody() + " at line "
					+ targddef.getLine());

	}

	public String toString() {
		String result = new String();
		result += "augment " + augment + "{\n";
		if (when != null)
			result += when.toString() + "\n";
		if (status != null)
			result += status.toString() + "\n";
		if (description != null)
			result += description.toString() + "\n";
		if (reference != null)
			result += reference.toString() + "\n";
		for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
				.hasMoreElements();)
			result += ed.nextElement().toString() + "\n";
		for (Enumeration<YANG_Case> ec = cases.elements(); ec.hasMoreElements();)
			result += ec.nextElement().toString() + "\n";
//		if (input != null)
//			result += input.toString() + "\n";
//		if (output != null)
//			result += output.toString() + "\n";
		result += "}";
		return result;
	}
}
