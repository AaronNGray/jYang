package jyang.tools;
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
import java.io.*;

import jyang.parser.ParseException;
import jyang.parser.YANG_AnyXml;
import jyang.parser.YANG_Argument;
import jyang.parser.YANG_Augment;
import jyang.parser.YANG_Bit;
import jyang.parser.YANG_Body;
import jyang.parser.YANG_Case;
import jyang.parser.YANG_CaseDataDef;
import jyang.parser.YANG_Choice;
import jyang.parser.YANG_Config;
import jyang.parser.YANG_Contact;
import jyang.parser.YANG_Container;
import jyang.parser.YANG_DataDef;
import jyang.parser.YANG_Default;
import jyang.parser.YANG_Description;
import jyang.parser.YANG_Enum;
import jyang.parser.YANG_ErrorMessage;
import jyang.parser.YANG_Extension;
import jyang.parser.YANG_Grouping;
import jyang.parser.YANG_Import;
import jyang.parser.YANG_Include;
import jyang.parser.YANG_Input;
import jyang.parser.YANG_Key;
import jyang.parser.YANG_Leaf;
import jyang.parser.YANG_LeafList;
import jyang.parser.YANG_Length;
import jyang.parser.YANG_Linkage;
import jyang.parser.YANG_List;
import jyang.parser.YANG_Mandatory;
import jyang.parser.YANG_MaxElement;
import jyang.parser.YANG_Meta;
import jyang.parser.YANG_MinElement;
import jyang.parser.YANG_Module;
import jyang.parser.YANG_Must;
import jyang.parser.YANG_Notification;
import jyang.parser.YANG_NumericalRestriction;
import jyang.parser.YANG_OrderedBy;
import jyang.parser.YANG_Organization;
import jyang.parser.YANG_Output;
import jyang.parser.YANG_Pattern;
import jyang.parser.YANG_Presence;
import jyang.parser.YANG_Range;
import jyang.parser.YANG_Reference;
import jyang.parser.YANG_RefineAnyXml;
import jyang.parser.YANG_RefineCase;
import jyang.parser.YANG_RefineChoice;
import jyang.parser.YANG_RefineContainer;
import jyang.parser.YANG_RefineLeaf;
import jyang.parser.YANG_RefineLeafList;
import jyang.parser.YANG_RefineList;
import jyang.parser.YANG_Refine;
import jyang.parser.YANG_Revision;
import jyang.parser.YANG_Rpc;
import jyang.parser.YANG_ShortCase;
import jyang.parser.YANG_Specification;
import jyang.parser.YANG_Status;
import jyang.parser.YANG_StringRestriction;
import jyang.parser.YANG_Type;
import jyang.parser.YANG_TypeDef;
import jyang.parser.YANG_Unique;
import jyang.parser.YANG_Units;
import jyang.parser.YANG_Uses;
import jyang.parser.YANG_Value;
import jyang.parser.YANG_When;
import jyang.parser.yang;


public class Yang2Yin {

	public Yang2Yin(YANG_Specification n, String[] paths, PrintStream out) {

		// try {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		if (n instanceof YANG_Module) {
			out.println("<module xmlns=\"urn:ietf:params:xml:ns:yin:1\"");
		} else {
			out.println("<submodule xmlns=\"urn:ietf:params:xml:ns:yin:1\"");
		}
		YANG_Specification yangspec = n;
		// Reading linkages statement for xml name spaces generation
		Vector<YANG_Linkage> linkages = yangspec.getLinkages();
		for (Enumeration<YANG_Linkage> el = linkages.elements(); el
				.hasMoreElements();) {
			YANG_Linkage linkage = el.nextElement();

			if (linkage instanceof YANG_Import) {
				YANG_Import yimport = (YANG_Import) linkage;
				int i = 0;
				boolean found = false;
				YANG_Specification externalspec = null;
				while (i < paths.length && !found) {
					String directory = paths[i++];
					String yangspecfilename = directory + File.separator
							+ yimport.getImportedModule() + ".yang";
					try {
						File externalfile = new File(yangspecfilename);
						yang.ReInit(new FileInputStream(externalfile));
						found = true;
						try {
							externalspec = yang.Start();

							out.println("        xmlns:"
									+ unquote(yimport.getPrefix().getPrefix())
									+ "=\""
									+ unquote(externalspec.getNameSpace()
											.getNameSpace()) + "\"");
						} catch (ParseException p) {
							// must not occurs because already done

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
			}

			else if (linkage instanceof YANG_Include) {
			}
		}

		out.println("        name=\"" + yangspec.getName() + "\">");

		// Header generation

		if (yangspec instanceof YANG_Module) {
			out.println("  <namespace uri=\""
					+ unquote(yangspec.getNameSpace().getNameSpace()) + "\"/>");

			out.println("  <prefix value=\""
					+ unquote(yangspec.getPrefix().getPrefix()) + "\"/>");
		}
		if (yangspec.getYangVersion() != null)
			out.println("  <yang-version=\""
					+ unquote(yangspec.getYangVersion().getYangVersion())
					+ "\"/>");

		// Linkages generation

		for (Enumeration<YANG_Linkage> el2 = linkages.elements(); el2
				.hasMoreElements();) {
			YANG_Linkage linkage = el2.nextElement();
			if (linkage instanceof YANG_Import) {
				YANG_Import yimport = (YANG_Import) linkage;
				out.println("  <import module=\""
						+ unquote(yimport.getImportedModule()) + "\">");
				out.println("    <prefix value=\""
						+ unquote(yimport.getPrefix().getPrefix()) + "\"/>");
				out.println("  </import>");
			} else if (linkage instanceof YANG_Include) {
				YANG_Include yinclude = (YANG_Include) linkage;
				out.println("  <include module=\""
						+ yinclude.getIncludedModule() + "\"/>");
			}
		}

		// Meta statement generation

		for (Enumeration<YANG_Meta> emeta = yangspec.getMetas().elements(); emeta
				.hasMoreElements();) {
			YANG_Meta ymeta = emeta.nextElement();
			if (ymeta instanceof YANG_Organization) {
				YANG_Organization org = (YANG_Organization) ymeta;
				out.println("  <organization>\n  <info>\n    "
						+ unquote(org.getOrganization())
						+ "\n    </info>\n  </organization>");
			} else if (ymeta instanceof YANG_Contact) {
				YANG_Contact cont = (YANG_Contact) ymeta;
				out.println("  <contact>\n  <info>\n  "
						+ unquote(cont.getContact())
						+ "\n    </info>\n  </contact>");
			} else if (ymeta instanceof YANG_Description) {
				YANG_Description desc = (YANG_Description) ymeta;
				out.println(gDescription(desc, "  "));
			} else if (ymeta instanceof YANG_Reference) {
				YANG_Reference ref = (YANG_Reference) ymeta;
				out.println("  <reference info=\""
						+ unquote(ref.getReference()) + "\"/>");
			}
		}

		// Revisions statements generation

		for (Enumeration<YANG_Revision> erev = yangspec.getRevisions()
				.elements(); erev.hasMoreElements();) {
			YANG_Revision revision = erev.nextElement();
			out.println("  <revision date=\"" + unquote(revision.getDate())
					+ "\">");
			if (revision.getDescription() != null)
				out.println(gDescription(revision.getDescription(), "    "));
			out.println("  </revision>");
		}

		// Bodies statements generation

		for (Enumeration<YANG_Body> eb = yangspec.getBodies().elements(); eb
				.hasMoreElements();)
			out.println(gBody(eb.nextElement(), "  "));
		if (yangspec instanceof YANG_Module)
			out.println("</module>");
		else
			out.println("</submodule>");

	}

	private String gBody(YANG_Body body, String prefix) {
		String result = new String();
		if (body instanceof YANG_Extension)
			result = gExtension((YANG_Extension) body, prefix) + "\n";
		else if (body instanceof YANG_TypeDef)
			result = gTypeDef((YANG_TypeDef) body, prefix) + "\n";
		else if (body instanceof YANG_Grouping)
			result = gGrouping((YANG_Grouping) body, prefix) + "\n";
		else if (body instanceof YANG_DataDef)
			result = gDataDef((YANG_DataDef) body, prefix) + "\n";
		else if (body instanceof YANG_Rpc)
			result = gRpc((YANG_Rpc) body, prefix) + "\n";
		else if (body instanceof YANG_Notification)
			result = gNotification((YANG_Notification) body, prefix) + "\n";
		return result;
	}

	private String gDescription(YANG_Description desc, String prefix) {
		return prefix + "<description>\n  " + prefix + "<text>\n    " + prefix
				+ unquote(desc.getDescription()) + "\n  " + prefix
				+ "</text>\n" + prefix + "</description>";
	}

	

	private String gReference(YANG_Reference ref, String prefix) {
		return prefix + "<reference info=\"" + unquote(ref.getReference())
				+ "\"/>";
	}

	private String gTypeDef(YANG_TypeDef typedef, String prefix) {
		String result = new String();
		result += prefix + "<typedef name=\"" + unquote(typedef.getTypeDef())
				+ "\">\n";
		if (typedef.getType() != null)
			result += gType(typedef.getType(), prefix + "  ") + "\n";
		if (typedef.getUnits() != null)
			result += gUnits(typedef.getUnits(), prefix + "  ") + "\n";
		if (typedef.getStatus() != null)
			result += gStatus(typedef.getStatus(), prefix + "  ") + "\n";
		if (typedef.getDescription() != null)
			result += gDescription(typedef.getDescription(), prefix + "  ")
					+ "\n";
		result += prefix + "</typedef>";
		return result;
	}

	private String gType(YANG_Type ytype, String prefix) {
		String result = new String();
		if (ytype.isBracked()) {
			result += prefix + "<type name=\"" + unquote(ytype.getType())
					+ "\">\n";
			for (Enumeration<YANG_Enum> ee = ytype.getEnums().elements(); ee
					.hasMoreElements();)
				result += gEnum(ee.nextElement(), prefix + "  ") + "\n";
			if (ytype.getNumRest() != null)
				result += gNumRest(ytype.getNumRest(), prefix + "  ") + "\n";
			if (ytype.getStringRest() != null)
				result += gStringRest(ytype.getStringRest(), prefix + "  "); // No
			// "\n"
			// because
			// gStringRest
			// gives
			// one
			if (ytype.getUnionSpec() != null)
				for (Enumeration<YANG_Type> et = ytype.getUnionSpec()
						.getTypes().elements(); et.hasMoreElements();)
					result += gType(et.nextElement(), prefix + "  ") + "\n";
			if (ytype.getBitSpec() != null)
				for (Enumeration<YANG_Bit> eb = ytype.getBitSpec().getBits()
						.elements(); eb.hasMoreElements();)
					result += gBit(eb.nextElement(), prefix + "  ") + "\n";
			if (ytype.getLeafRef() != null)
				result += prefix + "  <path value=\""
						+ unquote(ytype.getLeafRef().getPath().getPath())
						+ "\"/>\n";
			result += prefix + "</type>";
		} else
			result += prefix + "<type name=\"" + unquote(ytype.getType())
					+ "\"/>";

		return result;
	}

	private String gEnum(YANG_Enum yenum, String prefix) {
		String result = new String();
		if (yenum.isBracked()) {
			result += prefix + "<enum name=\"" + unquote(yenum.getEnum())
					+ "\">\n";
			if (yenum.getValue() != null)
				result += gValue(yenum.getValue(), prefix + "  ") + "\n";
			if (yenum.getStatus() != null)
				result += gStatus(yenum.getStatus(), prefix + "  ") + "\n";
			if (yenum.getDescription() != null)
				result += gDescription(yenum.getDescription(), prefix + "  ")
						+ "\n";
			if (yenum.getReference() != null)
				result += gReference(yenum.getReference(), prefix + "  ")
						+ "\n";
			result += prefix + "</enum>";
		} else
			result += prefix + "  <enum name=\"" + unquote(yenum.getEnum())
					+ "\"/>";
		return result;
	}

	private String gValue(YANG_Value value, String prefix) {
		return prefix + "<value value=\"" + unquote(value.getValue()) + "\"/>";
	}

	private String gBit(YANG_Bit bit, String prefix) {
		String result = new String();
		result += prefix + "<bit name=\"" + unquote(bit.getBit()) + "\">\n";
		if (bit.getPosition() != null)
			result += prefix + "  <position value=\""
					+ unquote(bit.getPosition().getPosition()) + "\"/>\n";
		if (bit.getStatus() != null)
			result += gStatus(bit.getStatus(), prefix + "  ") + "\n";
		if (bit.getDescription() != null)
			result += gDescription(bit.getDescription(), prefix + "  ") + "\n";
		if (bit.getReference() != null)
			result += gReference(bit.getReference(), prefix + "  ") + "\n";
		result += prefix + "</bit>";
		return result;
	}

	private String gStatus(YANG_Status status, String prefix) {
		return prefix + "<status value=\"" + unquote(status.getStatus())
				+ "\"/>";
	}

	private String gUnits(YANG_Units units, String prefix) {
		String result = new String();
		result += prefix + "<units name =\"" + unquote(units.getUnits())
				+ "\"/>";
		return result;
	}

	private String gNumRest(YANG_NumericalRestriction numrest, String prefix) {
		String result = new String();
		if (numrest instanceof YANG_Range) {
			YANG_Range range = (YANG_Range) numrest;
			if (range.isBracked()) {
				result += prefix + "<range value=\""
						+ unquote(range.getRange()) + "\">\n";
				if (range.getErrMess() != null)
					result += gErrMess(range.getErrMess(), prefix + "  ")
							+ "\n";
				if (range.getErrAppTag() != null)
					result += prefix + "  <error-app-tag value=\""
							+ unquote(range.getErrAppTag().getErrorAppt())
							+ "\"/>\n";
				if (range.getDescription() != null)
					result += gDescription(range.getDescription(), prefix
							+ "  ")
							+ "\n";
				if (range.getReference() != null)
					result += gReference(range.getReference(), prefix + "  ")
							+ "\n";
				result += prefix + "</range>";
			} else
				result += prefix + "<range value=\""
						+ unquote(range.getRange()) + "\"/>";
		}
		return result;
	}

	private String gStringRest(YANG_StringRestriction strrest, String prefix) {
		String result = new String();
		if (strrest.getLength() != null)
			result += gLength(strrest.getLength(), prefix) + "\n";
		for (Enumeration<YANG_Pattern> ep = strrest.getPatterns().elements(); ep
				.hasMoreElements();)
			result += gPattern(ep.nextElement(), prefix) + "\n";
		return result;
	}

	private String gLength(YANG_Length length, String prefix) {
		String result = new String();
		if (length.isBracked()) {
			result += prefix + "<length value=\"" + unquote(length.getLength())
					+ "\">\n";
			if (length.getErrMess() != null) {
				result += prefix + "<error-message>\n";
				result += prefix + "  <value>\n";
				result += prefix + "    "
						+ unquote(length.getErrMess().getErrorMessage()) + "\n";
				result += prefix + "  </value>\n";
				result += prefix + "</error-message>\n";
			}
			if (length.getErrAppTag() != null) {
				result += prefix + "<error-app-tag value=\""
						+ unquote(length.getErrAppTag().getErrorAppt())
						+ "\"/>\n";
			}
			if (length.getDescription() != null) {
				result += gDescription(length.getDescription(), prefix + "  ")
						+ "\n";
			}
			if (length.getReference() != null) {
				result += gReference(length.getReference(), prefix + "  ")
						+ "\n";
			}
			result += prefix + "</length>";

		} else
			result += prefix + "<length value=\"" + unquote(length.getLength())
					+ "\"/>";

		return result;
	}

	private String gPattern(YANG_Pattern pattern, String prefix) {
		String result = new String();
		if (pattern.isBracked()) {
			result += prefix + "<pattern value=\""
					+ unquote(pattern.getPattern()) + "\">\n";
			if (pattern.getErrMess() != null) {
				result += prefix + "<error-message>\n";
				result += prefix + "  <value>\n";
				result += prefix + "    "
						+ unquote(pattern.getErrMess().getErrorMessage())
						+ "\n";
				result += prefix + "  </value>\n";
				result += prefix + "</error-message>\n";
			}
			if (pattern.getErrAppTag() != null) {
				result += prefix + "<error-app-tag value=\""
						+ unquote(pattern.getErrAppTag().getErrorAppt())
						+ "\"/>\n";
			}
			if (pattern.getDescription() != null) {
				result += gDescription(pattern.getDescription(), prefix + "  ")
						+ "\n";
			}
			if (pattern.getReference() != null) {
				result += gReference(pattern.getReference(), prefix + "  ")
						+ "\n";
			}
			result += prefix + "</pattern>";

		} else
			result += prefix + "<pattern value=\""
					+ unquote(pattern.getPattern()) + "\"/>";

		return result;
	}

	private String gExtension(YANG_Extension ext, String prefix) {
		String result = new String();
		if (ext.isBracked()) {
			result += prefix + "<extension name=\""
					+ unquote(ext.getExtension()) + "\">\n";
			if (ext.getArgument() != null)
				result += gArgument(ext.getArgument(), prefix + "  ") + "\n";
			if (ext.getStatus() != null)
				result += gStatus(ext.getStatus(), prefix + "  ") + "\n";
			if (ext.getDescription() != null)
				result += gDescription(ext.getDescription(), prefix + "  ")
						+ "\n";
			if (ext.getReference() != null)
				result += gReference(ext.getReference(), prefix + "  ") + "\n";
			result += prefix + "</extension>";
		} else
			result += prefix + "<extension name=\""
					+ unquote(ext.getExtension()) + "\"/>";
		return result;
	}

	private String gArgument(YANG_Argument arg, String prefix) {
		String result = new String();
		if (arg.isBracked()) {
			result += prefix + "<argument name=\"" + unquote(arg.getArgument())
					+ "\">\n";
			if (arg.getYin() != null)
				result += prefix + "  <yin-element value=\""
						+ unquote(arg.getYin().getYin()) + "\"/>\n";
			result += prefix + "</argument>";
		} else
			result += prefix + "<argument name=\"" + unquote(arg.getArgument())
					+ "\"/>";
		return result;
	}

	private String gGrouping(YANG_Grouping grouping, String prefix) {
		String result = new String();
		if (grouping.isBracked()) {
			result += prefix + "<grouping name=\""
					+ unquote(grouping.getGrouping()) + "\">\n";
			if (grouping.getStatus() != null)
				result += gStatus(grouping.getStatus(), prefix + "  ") + "\n";
			if (grouping.getDescription() != null)
				result += gDescription(grouping.getDescription(), prefix + "  ")
						+ "\n";
			if (grouping.getReference() != null)
				result += gReference(grouping.getReference(), prefix + "  ")
						+ "\n";
			for (Enumeration<YANG_TypeDef> et = grouping.getTypeDefs()
					.elements(); et.hasMoreElements();)
				result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_Grouping> eg = grouping.getGroupings()
					.elements(); eg.hasMoreElements();)
				result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_DataDef> ed = grouping.getDataDefs()
					.elements(); ed.hasMoreElements();)
				result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
			result += prefix + "</grouping>";
		} else
			result += prefix + "<grouping name=\""
					+ unquote(grouping.getGrouping()) + "\"/>";
		return result;

	}

	private String gDataDef(YANG_DataDef datadef, String prefix) {
		String result = new String();
		if (datadef instanceof YANG_Container)
			result += gContainer((YANG_Container) datadef, prefix);
		else if (datadef instanceof YANG_Leaf)
			result += gLeaf((YANG_Leaf) datadef, prefix);
		else if (datadef instanceof YANG_LeafList)
			result += gLeafList((YANG_LeafList) datadef, prefix);
		else if (datadef instanceof YANG_List)
			result += gList((YANG_List) datadef, prefix);
		else if (datadef instanceof YANG_Choice)
			result += gChoice((YANG_Choice) datadef, prefix);
		else if (datadef instanceof YANG_AnyXml)
			result += gAnyXml((YANG_AnyXml) datadef, prefix);
		else if (datadef instanceof YANG_Uses)
			result += gUses((YANG_Uses) datadef, prefix);
	//	else if (datadef instanceof YANG_Augment)
	//		result += gAugment((YANG_Augment) datadef, prefix);
		return result;
	}

	private String gContainer(YANG_Container container, String prefix) {
		String result = new String();
		if (container.isBracked()) {
			result += prefix + "<container name=\""
					+ unquote(container.getContainer()) + "\">\n";
			for (Enumeration<YANG_Must> em = container.getMusts().elements(); em
					.hasMoreElements();)
				result += gMust(em.nextElement(), prefix + "  ") + "\n";
			if (container.getPresence() != null)
				result += gPresence(container.getPresence(), prefix + "  ")
						+ "\n";
			if (container.getConfig() != null)
				result += gConfig(container.getConfig(), prefix + "  ") + "\n";
			if (container.getStatus() != null)
				result += gStatus(container.getStatus(), prefix + "  ") + "\n";
			if (container.getDescription() != null)
				result += gDescription(container.getDescription(), prefix
						+ "  ")
						+ "\n";
			if (container.getReference() != null)
				result += gReference(container.getReference(), prefix + "  ")
						+ "\n";
			for (Enumeration<YANG_TypeDef> et = container.getTypeDefs()
					.elements(); et.hasMoreElements();)
				result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_Grouping> eg = container.getGroupings()
					.elements(); eg.hasMoreElements();)
				result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_DataDef> ed = container.getDataDefs()
					.elements(); ed.hasMoreElements();)
				result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
			result += prefix + "</container>";
		} else
			result += prefix + "<container name=\""
					+ unquote(container.getContainer()) + "\"/>";
		return result;
	}

	private String gLeaf(YANG_Leaf leaf, String prefix) {
		String result = new String();
		result += prefix + "<leaf name=\"" + unquote(leaf.getLeaf()) + "\">\n";
		result += gType(leaf.getType(), prefix + "  ") + "\n";
		if (leaf.getUnits() != null)
			result += gUnits(leaf.getUnits(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Must> em = leaf.getMusts().elements(); em
				.hasMoreElements();)
			result += gMust(em.nextElement(), prefix + "  ") + "\n";
		if (leaf.getDefault() != null)
			result += gDefault(leaf.getDefault(), prefix + "  ") + "\n";
		if (leaf.getConfig() != null)
			result += gConfig(leaf.getConfig(), prefix + "  ") + "\n";
		if (leaf.getMandatory() != null)
			result += gMandatory(leaf.getMandatory(), prefix + "  ") + "\n";
		if (leaf.getStatus() != null)
			result += gStatus(leaf.getStatus(), prefix + "  ") + "\n";
		if (leaf.getDescription() != null)
			result += gDescription(leaf.getDescription(), prefix + "  ") + "\n";
		if (leaf.getReference() != null)
			result += gReference(leaf.getReference(), prefix + "  ") + "\n";
		result += prefix + "</leaf>";
		return result;
	}

	private String gLeafList(YANG_LeafList leaflist, String prefix) {
		String result = new String();
		result += prefix + "<leaf-list name=\""
				+ unquote(leaflist.getLeafList()) + "\"/>\n";
		result += gType(leaflist.getType(), prefix + "  ") + "\n";
		if (leaflist.getUnits() != null)
			result += gUnits(leaflist.getUnits(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Must> em = leaflist.getMusts().elements(); em
				.hasMoreElements();)
			result += gMust(em.nextElement(), prefix + "  ") + "\n";
		if (leaflist.getConfig() != null)
			result += gConfig(leaflist.getConfig(), prefix + "  ") + "\n";
		if (leaflist.getMinElement() != null)
			result += gMinElement(leaflist.getMinElement(), prefix + "  ")
					+ "\n";
		if (leaflist.getMaxElement() != null)
			result += gMaxElement(leaflist.getMaxElement(), prefix + "  ")
					+ "\n";
		if (leaflist.getOrderedBy() != null)
			result += gOrderedBy(leaflist.getOrderedBy(), prefix + "  ") + "\n";
		if (leaflist.getStatus() != null)
			result += gStatus(leaflist.getStatus(), prefix + "  ") + "\n";
		if (leaflist.getDescription() != null)
			result += gDescription(leaflist.getDescription(), prefix + "  ")
					+ "\n";
		if (leaflist.getReference() != null)
			result += gReference(leaflist.getReference(), prefix + "  ") + "\n";
		result += prefix + "</leaflist>";
		return result;
	}

	private String gList(YANG_List list, String prefix) {
		String result = new String();
		result += prefix + "<list name=\"" + unquote(list.getList()) + "\">\n";
		for (Enumeration<YANG_Must> em = list.getMusts().elements(); em
				.hasMoreElements();)
			result += gMust(em.nextElement(), prefix + "  ") + "\n";
		if (list.getKey() != null)
			result += gKey(list.getKey(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Unique> eu = list.getUniques().elements(); eu
				.hasMoreElements();)
			result += gUnique(eu.nextElement(), prefix + "  ") + "\n";
		if (list.getConfig() != null)
			result += gConfig(list.getConfig(), prefix + "  ") + "\n";
		if (list.getMinElement() != null)
			result += gMinElement(list.getMinElement(), prefix + "  ") + "\n";
		if (list.getMaxElement() != null)
			result += gMaxElement(list.getMaxElement(), prefix + "  ") + "\n";
		if (list.getOrderedBy() != null)
			result += gOrderedBy(list.getOrderedBy(), prefix + "  ") + "\n";
		if (list.getStatus() != null)
			result += gStatus(list.getStatus(), prefix + "  ") + "\n";
		if (list.getDescription() != null)
			result += gDescription(list.getDescription(), prefix + "  ") + "\n";
		if (list.getReference() != null)
			result += gReference(list.getReference(), prefix + "  ") + "\n";
		for (Enumeration<YANG_TypeDef> et = list.getTypeDefs().elements(); et
				.hasMoreElements();)
			result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Grouping> eg = list.getGroupings().elements(); eg
				.hasMoreElements();)
			result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_DataDef> ed = list.getDataDefs().elements(); ed
				.hasMoreElements();)
			result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
		result += prefix + "</list>";
		return result;
	}

	private String gChoice(YANG_Choice choice, String prefix) {
		String result = new String();
		result += prefix + "<choice name=\"" + unquote(choice.getChoice())
				+ "\">\n";
		if (choice.getDefault() != null)
			result += gDefault(choice.getDefault(), prefix + "  ") + "\n";
		if (choice.getMandatory() != null)
			result += gMandatory(choice.getMandatory(), prefix + "  ") + "\n";
		if (choice.getStatus() != null)
			result += gStatus(choice.getStatus(), prefix + "  ") + "\n";
		if (choice.getDescription() != null)
			result += gDescription(choice.getDescription(), prefix + "  ")
					+ "\n";
		if (choice.getReference() != null)
			result += gReference(choice.getReference(), prefix + "  ") + "\n";
		for (Enumeration<YANG_ShortCase> es = choice.getShortCases().elements(); es
				.hasMoreElements();)
			result += gShortCase(es.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Case> ec = choice.getCases().elements(); ec
				.hasMoreElements();)
			result += gCase(ec.nextElement(), prefix + "  ") + "\n";
		result += prefix + "</choice>";
		return result;
	}

	private String gAnyXml(YANG_AnyXml anyxml, String prefix) {
		String result = new String();
		if (anyxml.isBracked()) {
			result += prefix + "<any-xml name=\"" + unquote(anyxml.getAnyXml())
					+ "\">\n";
			if (anyxml.getConfig() != null)
				result += gConfig(anyxml.getConfig(), prefix + "  ") + "\n";
			if (anyxml.getMandatory() != null)
				result += gMandatory(anyxml.getMandatory(), prefix + "  ")
						+ "\n";
			if (anyxml.getStatus() != null)
				result += gStatus(anyxml.getStatus(), prefix + "  ") + "\n";
			if (anyxml.getDescription() != null)
				result += gDescription(anyxml.getDescription(), prefix + "  ")
						+ "\n";
			if (anyxml.getReference() != null)
				result += gReference(anyxml.getReference(), prefix + "  ")
						+ "\n";
			result += prefix + "</any-xml>";
		} else
			result += prefix + "<any-xml name=\"" + unquote(anyxml.getAnyXml())
					+ "\"/>";
		return result;
	}

	private String gShortCase(YANG_ShortCase s, String prefix) {
		if (s instanceof YANG_Container)
			return gContainer((YANG_Container) s, prefix);
		if (s instanceof YANG_Leaf)
			return gLeaf((YANG_Leaf) s, prefix);
		if (s instanceof YANG_LeafList)
			return gLeafList((YANG_LeafList) s, prefix);
		if (s instanceof YANG_List)
			return gList((YANG_List) s, prefix);
		if (s instanceof YANG_AnyXml)
			return gAnyXml((YANG_AnyXml) s, prefix);
		return "";
	}

	private String gCase(YANG_Case c, String prefix) {
		String result = new String();
		if (c.isBracked()) {
			result += prefix + "<case name=\"" + unquote(c.getCase()) + "\">\n";
			if (c.getStatus() != null)
				result += gStatus(c.getStatus(), prefix + "  ") + "\n";
			if (c.getDescription() != null)
				result += gDescription(c.getDescription(), prefix + "  ")
						+ "\n";
			if (c.getReference() != null)
				result += gReference(c.getReference(), prefix + "  ") + "\n";
			for (Enumeration<YANG_CaseDataDef> ec = c.getCaseDefs().elements(); ec
					.hasMoreElements();)
				result += gCaseDef(ec.nextElement(), prefix + "  ") + "\n";
			result += prefix + "</case>";
		} else
			result += prefix + "<case name=\"" + unquote(c.getCase()) + "\"/>";
		return result;
	}

	private String gCaseDef(YANG_CaseDataDef c, String prefix) {
		if (c instanceof YANG_Container)
			return gContainer((YANG_Container) c, prefix);
		if (c instanceof YANG_Leaf)
			return gLeaf((YANG_Leaf) c, prefix);
		if (c instanceof YANG_LeafList)
			return gLeafList((YANG_LeafList) c, prefix);
		if (c instanceof YANG_List)
			return gList((YANG_List) c, prefix);
		if (c instanceof YANG_AnyXml)
			return gAnyXml((YANG_AnyXml) c, prefix);
		if (c instanceof YANG_Uses)
			return gUses((YANG_Uses) c, prefix);
		if (c instanceof YANG_Augment)
			return gAugment((YANG_Augment) c, prefix);
		return "";
	}

	private String gUses(YANG_Uses uses, String prefix) {
		String result = new String();
		if (uses.isBracked()) {
			result += prefix + "<uses name=\"" + unquote(uses.getUses())
					+ "\"/>\n";
			if (uses.getStatus() != null)
				result += gStatus(uses.getStatus(), prefix + "  ") + "\n";
			if (uses.getDescription() != null)
				result += gDescription(uses.getDescription(), prefix + "  ")
						+ "\n";
			if (uses.getReference() != null)
				result += gReference(uses.getReference(), prefix + "  ") + "\n";
			for (Enumeration<YANG_Refine> er = uses.getRefinements()
					.elements(); er.hasMoreElements();)
				result += gRefinement(er.nextElement(), prefix + "  ") + "\n";
			result += prefix + "</uses>";
		} else
			result += prefix + "<uses name=\"" + unquote(uses.getUses())
					+ "\"/>";
		return result;
	}

	private String gAugment(YANG_Augment augment, String prefix) {
		String result = new String();
		result += prefix + "<augment target-node=\""
				+ unquote(augment.getAugment()) + "\">\n";
		if (augment.getWhen() != null)
			result += gWhen(augment.getWhen(), prefix + "  ") + "\n";
		if (augment.getStatus() != null)
			result += gStatus(augment.getStatus(), prefix + "  ") + "\n";
		if (augment.getDescription() != null)
			result += gDescription(augment.getDescription(), prefix + "  ")
					+ "\n";
		if (augment.getReference() != null)
			result += gReference(augment.getReference(), prefix + "  ") + "\n";
		// if (augment.getInput() != null)
		// result += gInput(augment.getInput(), prefix + " ") + "\n";
		// if (augment.getOutput() != null)
		// result += gOutput(augment.getOutput(), prefix + " ") + "\n";
		for (Enumeration<YANG_DataDef> ed = augment.getDataDefs().elements(); ed
				.hasMoreElements();)
			result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Case> ec = augment.getCases().elements(); ec
				.hasMoreElements();)
			result += gCase(ec.nextElement(), prefix + "  ") + "\n";
		result += prefix + "</augment>";
		return result;
	}

	private String gRpc(YANG_Rpc r, String prefix) {
		String result = new String();
		if (r.isBracked()) {
			result += prefix + "<rpc name=\"" + unquote(r.getRpc()) + "\">\n";
			if (r.getStatus() != null)
				result += gStatus(r.getStatus(), prefix + "  ") + "\n";
			if (r.getDescription() != null)
				result += gDescription(r.getDescription(), prefix + "  ")
						+ "\n";
			if (r.getReference() != null)
				result += gReference(r.getReference(), prefix + "  ") + "\n";
			for (Enumeration<YANG_TypeDef> et = r.getTypeDefs().elements(); et
					.hasMoreElements();)
				result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_Grouping> eg = r.getGroupings().elements(); eg
					.hasMoreElements();)
				result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
			if (r.getInput() != null)
				result += gInput(r.getInput(), prefix + "  ") + "\n";
			if (r.getOutput() != null)
				result += gOutput(r.getOutput(), prefix + "  ") + "\n";
			result += prefix + "</rpc>";
		} else
			result += prefix + "<rpc name=\"" + unquote(r.getRpc()) + "\"/>";
		return result;
	}

	private String gNotification(YANG_Notification n, String prefix) {
		String result = new String();
		if (n.isBracked()) {
			result += prefix + "<notification name=\""
					+ unquote(n.getNotification()) + "\">\n";
			if (n.getStatus() != null)
				result += gStatus(n.getStatus(), prefix + "  ") + "\n";
			if (n.getDescription() != null)
				result += gDescription(n.getDescription(), prefix + "  ")
						+ "\n";
			if (n.getReference() != null)
				result += gReference(n.getReference(), prefix + "  ") + "\n";
			for (Enumeration<YANG_TypeDef> et = n.getTypeDefs().elements(); et
					.hasMoreElements();)
				result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_Grouping> eg = n.getGroupings().elements(); eg
					.hasMoreElements();)
				result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
			for (Enumeration<YANG_DataDef> ed = n.getDataDefs().elements(); ed
					.hasMoreElements();)
				result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
			result += prefix + "</notification>";
		} else
			result += prefix + "<notification name=\""
					+ unquote(n.getNotification()) + "\"/>";
		return result;
	}

	private String gInput(YANG_Input i, String prefix) {
		String result = new String();
		result += prefix + "<input>\n";
		for (Enumeration<YANG_TypeDef> et = i.getTypeDefs().elements(); et
				.hasMoreElements();)
			result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Grouping> eg = i.getGroupings().elements(); eg
				.hasMoreElements();)
			result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_DataDef> ed = i.getDataDefs().elements(); ed
				.hasMoreElements();)
			result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
		result += prefix + "</input>";
		return result;
	}

	private String gOutput(YANG_Output o, String prefix) {
		String result = new String();
		result += prefix + "<output>\n";
		for (Enumeration<YANG_TypeDef> et = o.getTypeDefs().elements(); et
				.hasMoreElements();)
			result += gTypeDef(et.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_Grouping> eg = o.getGroupings().elements(); eg
				.hasMoreElements();)
			result += gGrouping(eg.nextElement(), prefix + "  ") + "\n";
		for (Enumeration<YANG_DataDef> ed = o.getDataDefs().elements(); ed
				.hasMoreElements();)
			result += gDataDef(ed.nextElement(), prefix + "  ") + "\n";
		result += prefix + "</output>";
		return result;
	}

	private String gWhen(YANG_When w, String prefix) {
		return prefix + "<when condition=\"" + unquote(w.getWhen()) + "\"/>";
	}

	private String gRefinement(YANG_Refine r, String prefix) {
		if (r instanceof YANG_RefineContainer)
			return gRefineContainer((YANG_RefineContainer) r, prefix);
		if (r instanceof YANG_RefineLeaf)
			return gRefineLeaf((YANG_RefineLeaf) r, prefix);
		if (r instanceof YANG_RefineLeafList)
			return gRefineLeafList((YANG_RefineLeafList) r, prefix);
		if (r instanceof YANG_RefineList)
			return gRefineList((YANG_RefineList) r, prefix);
		if (r instanceof YANG_RefineChoice)
			return gRefineChoice((YANG_RefineChoice) r, prefix);
		if (r instanceof YANG_RefineAnyXml)
			return gRefineAnyXml((YANG_RefineAnyXml) r, prefix);
		return "";
	}

	private String gRefineList(YANG_RefineList l, String prefix) {
		String result = new String();
		if (l.isBracked()) {
			//result += prefix + "<list name=\"" + unquote(l.getRefineList())
			//		+ "\">\n";
			for (Enumeration<YANG_Must> em = l.getMusts().elements(); em
					.hasMoreElements();)
				result += gMust(em.nextElement(), prefix + "  ") + "\n";
			if (l.getConfig() != null)
				result += gConfig(l.getConfig(), prefix + "  ") + "\n";
			if (l.getMinElement() != null)
				result += gMinElement(l.getMinElement(), prefix + "  ") + "\n";
			if (l.getMaxElement() != null)
				result += gMaxElement(l.getMaxElement(), prefix + "  ") + "\n";
			if (l.getDescription() != null)
				result += gDescription(l.getDescription(), prefix + "  ")
						+ "\n";
			if (l.getReference() != null)
				result += gReference(l.getReference(), prefix + "  ") + "\n";
		//	for (Enumeration<YANG_Refine> er = l.getRefinements()
		//			.elements(); er.hasMoreElements();)
		//		result += gRefinement(er.nextElement(), prefix + "  ") + "\n";
		//	result += prefix + "</list>";
	//	} else
	//		result += prefix + "<list name=\"" + unquote(l.getRefineList())
	//				+ "\"/>";
		}
			return result;
	}

	private String gRefineChoice(YANG_RefineChoice c, String prefix) {
		String result = new String();
		if (c.isBracked()) {
	//		result += prefix + "<choice name=\"" + unquote(c.getRefineChoice())
	//				+ "\">\n";
			if (c.getDefault() != null)
				result += gDefault(c.getDefault(), prefix + "  ") + "\n";
			if (c.getMandatory() != null)
				result += gMandatory(c.getMandatory(), prefix + "  ") + "\n";
			if (c.getDescription() != null)
				result += gDescription(c.getDescription(), prefix + "  ")
						+ "\n";
			if (c.getReference() != null)
				result += gReference(c.getReference(), prefix + "  ") + "\n";
	//		for (Enumeration<YANG_RefineCase> ec = c.getRefineCases()
	//				.elements(); ec.hasMoreElements();)
	//			result += gRefineCase(ec.nextElement(), prefix + "  ") + "\n";
	//		result += prefix + "</choice>";
	//	} else
	//		result += prefix + "<choice name=\"" + unquote(c.getRefineChoice())
	//				+ "\"/>";
		}
		return result;
	}

	private String gRefineCase(YANG_RefineCase c, String prefix) {
		String result = new String();
		if (c.isBracked()) {
	//		result += prefix + "<case name=\"" + unquote(c.getRefineCase())
	//				+ "\">\n";
			if (c.getDescription() != null)
				result += gDescription(c.getDescription(), prefix + "  ")
						+ "\n";
			if (c.getReference() != null)
				result += gReference(c.getReference(), prefix + "  ") + "\n";
	//		for (Enumeration<YANG_Refine> er = c.getRefinements()
	//				.elements(); er.hasMoreElements();)
	//			result += gRefinement(er.nextElement(), prefix + "  ") + "\n";
	//		result += prefix + "</case>";
	//	} else
	//		result += prefix + "<case name=\"" + unquote(c.getRefineCase())
	//				+ "\"/>";
		}
		return result;
	}

	private String gRefineAnyXml(YANG_RefineAnyXml a, String prefix) {
		String result = new String();
		if (a.isBracked()) {
	//		result += prefix + "<anyxml name=\"" + unquote(a.getRefineAnyXml())
	//				+ "\">\n";
			if (a.getConfig() != null)
				result += gConfig(a.getConfig(), prefix + "  ") + "\n";
			if (a.getMandatory() != null)
				result += gMandatory(a.getMandatory(), prefix + "  ") + "\n";
			if (a.getDescription() != null)
				result += gDescription(a.getDescription(), prefix + "  ")
						+ "\n";
			if (a.getReference() != null)
				result += gReference(a.getReference(), prefix + "  ") + "\n";
			result += prefix + "</anyxml>";
	/*	} else
			result += prefix + "<anyxml name=\"" + unquote(a.getRefineAnyXml())
					+ "\"/>";
					*/
		}
		return result;
	}

	private String gRefineLeafList(YANG_RefineLeafList l, String prefix) {
		String result = new String();
		if (l.isBracked()) {
	//		result += prefix + "<leaf-list name=\""
	//				+ unquote(l.getRefineLeafList()) + "\">\n";
			for (Enumeration<YANG_Must> em = l.getMusts().elements(); em
					.hasMoreElements();)
				result += gMust(em.nextElement(), prefix + "  ") + "\n";
			if (l.getConfig() != null)
				result += gConfig(l.getConfig(), prefix + "  ") + "\n";
			if (l.getMinElement() != null)
				result += gMinElement(l.getMinElement(), prefix + "  ") + "\n";
			if (l.getMaxElement() != null)
				result += gMaxElement(l.getMaxElement(), prefix + "  ") + "\n";
			if (l.getDescription() != null)
				result += gDescription(l.getDescription(), prefix + "  ")
						+ "\n";
			if (l.getReference() != null)
				result += gReference(l.getReference(), prefix + "  ") + "\n";
			result += prefix + "</leaf-list>";
	/*	} else
			result += prefix + "<leaf-list name=\""
					+ unquote(l.getRefineLeafList()) + "\"/>";
					*/
		}
		return result;
	}

	private String gRefineContainer(YANG_RefineContainer c, String prefix) {
		String result = new String();
		if (c.isBracked()) {
	//		result += prefix + "<container name=\""
	//				+ unquote(c.getRefineContainer()) + "\">\n";
			for (Enumeration<YANG_Must> em = c.getMusts().elements(); em
					.hasMoreElements();)
				result += gMust(em.nextElement(), prefix + "  ") + "\n";
			if (c.getPresence() != null)
				result += gPresence(c.getPresence(), prefix + "  ") + "\n";
			if (c.getConfig() != null)
				result += gConfig(c.getConfig(), prefix + "  ") + "\n";
			if (c.getDescription() != null)
				result += gDescription(c.getDescription(), prefix + "  ")
						+ "\n";
			if (c.getReference() != null)
				result += gReference(c.getReference(), prefix + "  ") + "\n";
			/*
			for (Enumeration<YANG_Refine> er = c.getRefinements()
					.elements(); er.hasMoreElements();)
				result += gRefinement(er.nextElement(), prefix + "  ") + "\n";
			*/
			result += prefix + "</container>";
	/*	} else
			result += prefix + "<container name=\""
					+ unquote(c.getRefineContainer()) + "\"/>";
					*/
		}
		return result;
	}

	private String gRefineLeaf(YANG_RefineLeaf l, String prefix) {
		String result = new String();
		if (l.isBracked()) {
	//		result += prefix + "<leaf name=\"" + unquote(l.getRefineLeaf())
	//				+ "\">\n";
			for (Enumeration<YANG_Must> em = l.getMusts().elements(); em
					.hasMoreElements();)
				result += gMust(em.nextElement(), prefix + "  ") + "\n";
			if (l.getDefault() != null)
				result += gDefault(l.getDefault(), prefix + "  ") + "\n";
			if (l.getConfig() != null)
				result += gConfig(l.getConfig(), prefix + "  ") + "\n";
			if (l.getMandatory() != null)
				result += gMandatory(l.getMandatory(), prefix + "  ") + "\n";
			if (l.getDescription() != null)
				result += gDescription(l.getDescription(), prefix + "  ")
						+ "\n";
			if (l.getReference() != null)
				result += gReference(l.getReference(), prefix + "  ") + "\n";
			result += prefix + "</leaf>";
	/*	} else
			result += prefix + "<leaf name=\"" + unquote(l.getRefineLeaf())
					+ "\"/>";
					*/
		}
		return result;
	}

	private String gKey(YANG_Key k, String prefix) {
		return prefix + "<key value=\"" + unquote(k.getKey()) + "\"/>";
	}

	private String gUnique(YANG_Unique u, String prefix) {
		return prefix + "<unique tag=\"" + unquote(u.getUnique()) + "\"/>";
	}

	private String gMinElement(YANG_MinElement m, String prefix) {
		return prefix + "<min-elements value=\"" + unquote(m.getMinElement())
				+ "\"/>";
	}

	private String gMaxElement(YANG_MaxElement m, String prefix) {
		return prefix + "<max-elements value=\"" + unquote(m.getMaxElement())
				+ "\"/>";
	}

	private String gOrderedBy(YANG_OrderedBy o, String prefix) {
		return prefix + "<ordered-by value=\"" + unquote(o.getOrderedBy())
				+ "\"/>";
	}

	private String gMust(YANG_Must must, String prefix) {
		String result = new String();
		if (must.isBracked()) {
			result += prefix + "<must condition=\"" + unquote(must.getMust())
					+ "\">\n";
			if (must.getErrMess() != null)
				result += gErrMess(must.getErrMess(), prefix + "  ") + "\n";
			if (must.getErrAppTag() != null)
				result += prefix + "  <error-app-tag value=\""
						+ unquote(must.getErrAppTag().getErrorAppt())
						+ "\"/>\n";
			if (must.getDescription() != null)
				result += gDescription(must.getDescription(), prefix + "  ")
						+ "\n";
			if (must.getReference() != null)
				result += gReference(must.getReference(), prefix + "  ") + "\n";
			result += prefix + "</must>";
		} else
			result += prefix + "<must condition=\"" + unquote(must.getMust())
					+ "\"/>";
		return result;
	}

	private String gErrMess(YANG_ErrorMessage errmess, String prefix) {
		String result = new String();
		result += prefix + "  <error-message>\n";
		result += prefix + "    <text>\n";
		result += prefix + "      " + unquote(errmess.getErrorMessage()) + "\n";
		result += prefix + "    </text>\n";
		result += prefix + "  </error-message>";
		return result;
	}

	private String gPresence(YANG_Presence presence, String prefix) {
		return prefix + "<presence>\n" + prefix + "  <text>\n" + prefix
				+ "    " + presence + "\n" + prefix + "  </text>\n" + prefix
				+ "</presence>";
	}

	private String gConfig(YANG_Config config, String prefix) {
		return prefix + "<config value=\"" + unquote(config.getConfigStr())
				+ "\"/>";
	}

	private String gMandatory(YANG_Mandatory mand, String prefix) {
		return prefix + "<mandatory value=\"" + unquote(mand.getMandatory())
				+ "\"/>";
	}

	private String gDefault(YANG_Default def, String prefix) {
		return prefix + "<default value=\"" + unquote(def.getDefault())
				+ "\"/>";
	}

	private String unquote(String s) {
		if (s.charAt(0) == '"')
			s = s.substring(1);
		if (s.charAt(s.length() - 1) == '"')
			s = s.substring(0, s.length() - 1);
		return s;
	}
}
