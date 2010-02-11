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

public class YANG_Uses extends YANG_DataDef implements YANG_CaseDataDef {

	private String uses = null;
	private Vector<YANG_Refine> refinements = new Vector<YANG_Refine>();
	private Vector<YANG_UsesAugment> usesaugments = new Vector<YANG_UsesAugment>();

	/**
	 * Grouping used reference
	 */
	private YANG_Grouping grouping = null;

	public YANG_Grouping getGrouping() {
		return grouping;
	}

	public void setGrouping(YANG_Grouping g) {
		this.grouping = g;
	}

	public YANG_Uses(int id) {
		super(id);
	}

	public YANG_Uses(yang p, int id) {
		super(p, id);
	}

	public void setUses(String u) {
		uses = u;
	}

	public String getBody() {
		return getUses();
	}

	public String getUses() {
		return uses;
	}

	public boolean isBracked() {
		return super.isBracked() || refinements.size() != 0
				|| usesaugments.size() != 0;
	}

	public void addRefinement(YANG_Refine r) throws YangParserException {

		refinements.add(r);
	}

	public Vector<YANG_Refine> getRefinements() {
		return refinements;
	}

	public void addUsesAugment(YANG_UsesAugment ua) {
		usesaugments.add(ua);
	}

	private boolean checked = false;

	public void check(YangContext context) throws YangParserException {

		if (!context.isGroupingDefined(this)) {
			System.err
					.println(context.getSpec().getName() + "@" + getLine()
							+ "." + getCol() + ":grouping " + uses
							+ " cannot be found");
			return;
		} else {

			YANG_Grouping grouping = context.getUsedGrouping(this);

			setGrouping(grouping);

			String gping = getGrouping().getGrouping();

			if (YangBuiltInTypes.isBuiltIn(gping)) {
				System.err.println(context.getSpec().getName() + "@"
						+ getLine() + "." + getCol()
						+ ":a built-in type cannot be used : " + uses);
				return;
			}

			if (!grouping.isChecked()) {

				Vector<YANG_TypeDef> typedefs = grouping.getTypeDefs();
				Vector<YANG_Grouping> groupings = grouping.getGroupings();
				Vector<YANG_DataDef> datadefs = grouping.getDataDefs();

				for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
						.hasMoreElements();) {
					YANG_TypeDef typedef = (YANG_TypeDef) et.nextElement();
					context.addNode(typedef);
				}

				for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
						.hasMoreElements();) {
					YANG_Grouping g = (YANG_Grouping) eg.nextElement();
					context.addNode(g);
				}

				for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
						.hasMoreElements();)
					context.addNode(ed.nextElement());

				for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
						.hasMoreElements();) {
					YANG_Body body = (YANG_Body) et.nextElement();
					body.setParent(this);
					YangContext clcts = context.clone();
					try {
						body.checkBody(clcts);
					} catch (YangParserException e) {
						System.err.println(context.getSpec().getName()
								+ e.getMessage());
					}
				}

				for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
						.hasMoreElements();) {
					YANG_Body body = (YANG_Body) eg.nextElement();
					body.setParent(getParent());
					YangContext clcts = context.clone();
					body.checkBody(clcts);
				}

				for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
						.hasMoreElements();) {
					YANG_Body body = (YANG_Body) ed.nextElement();

					body.setParent(getParent());
					YangContext clcts = context.clone();
					try {
						if (body instanceof YANG_Uses) {
							YANG_Uses used = (YANG_Uses) body;
							Set<YANG_Grouping> s = new HashSet<YANG_Grouping>();
							s.add(grouping);
							if (checkRecursiveUses(context, s, used))
								body.checkBody(clcts);
							else {
								YangErrorManager.tadd(used.getFileName(), used
										.getParent().getLine(), used
										.getParent().getCol(), "rec_grouping",
										used.getParent().toString());
							}
						} else
							body.checkBody(clcts);
					} catch (YangParserException e) {
						System.err.println(context.getSpec().getName()
								+ e.getMessage());
					}

					grouping.setChecked(true);
				}
			}

			for (Enumeration<YANG_Refine> er = refinements.elements(); er
					.hasMoreElements();) {
				YANG_Refine ref = er.nextElement();
				try {
					ref.setUsedGrouping("from the used grouping "
							+ grouping.getGrouping() + " at line "
							+ grouping.getLine());
					ref.setParent(this);
					ref.check(context, grouping);
				} catch (YangParserException ye) {
					System.err.println(context.getModuleSpecName()
							+ ye.getMessage());
				}
			}
			checked = true;
		}
	}

	public boolean isChecked() {
		return checked;
	}

	private boolean checkRecursiveUses(YangContext context,
			Set<YANG_Grouping> setG, YANG_Uses used) {
		if (used.checked)
			return true;
		YANG_Grouping g = context.getUsedGrouping(used);
		if (setG.contains(g)) {
			checked = false;
		} else {
			setG.add(g);
			for (YANG_DataDef ddef : g.getDataDefs()) {
				if (ddef instanceof YANG_Uses)
					return checkRecursiveUses(context, setG, (YANG_Uses) ddef);
			}
		}
		return false;
	}

	public String toString() {
		String result = new String();
		result += "uses " + uses;
		if (isBracked()) {
			result += "{\n";
			result += super.toString() + "\n";
			for (YANG_UsesAugment er : usesaugments)
				result += er.toString() + "\n";
			result += "}";
		} else
			result += ";";

		return result;
	}

}
