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

public class YANG_Container extends YANG_DataDef implements YANG_CaseDef,
		YANG_ShortCase {

	private String container = null;
	private Vector<YANG_Must> musts = new Vector<YANG_Must>();
	private YANG_Presence presence = null;
	private YANG_Config config = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
	private Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

	private boolean bracked = false;

	private boolean b_presence = false, b_config = false, b_status = false,
			b_description = false, b_reference = false;

	public YANG_Container(int id) {
		super(id);
	}

	public YANG_Container(yang p, int id) {
		super(p, id);
	}

	public void setContainer(String c) {
		container = c;
	}

	public String getBody() {
		return getContainer();
	}

	public String getContainer() {
		return container;
	}

	public void addMust(YANG_Must m) {
		musts.add(m);
		bracked = true;
	}

	public Vector<YANG_Must> getMusts() {
		return musts;
	}

	public void setPresence(YANG_Presence p) throws YangParserException {
		if (b_presence)
			throw new YangParserException(
					"Presence already defined in container " + container, p
							.getLine(), p.getCol());
		b_presence = true;
		presence = p;
		bracked = true;
	}

	public YANG_Presence getPresence() {
		return presence;
	}

	public void setConfig(YANG_Config c) throws YangParserException {
		if (b_config)
			throw new YangParserException(
					"Config already defined in container " + container, c
							.getLine(), c.getCol());
		b_config = true;
		config = c;
		bracked = true;
	}

	public YANG_Config getConfig() {
		return config;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException(
					"Status already defined in container " + container, s
							.getLine(), s.getCol());
		b_status = true;
		status = s;
		bracked = true;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in container " + container, d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in container " + container, r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
		bracked = true;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void addTypeDef(YANG_TypeDef t) {
		typedefs.add(t);
		bracked = true;
	}

	public Vector<YANG_TypeDef> getTypeDefs() {
		return typedefs;
	}

	public void addGrouping(YANG_Grouping g) {
		groupings.add(g);
		bracked = true;
	}

	public Vector<YANG_Grouping> getGroupings() {
		return groupings;
	}

	public void addDataDef(YANG_DataDef d) {
		datadefs.add(d);
		bracked = true;
	}

	public Vector<YANG_DataDef> getDataDefs() {
		return datadefs;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context) throws YangParserException {
		if (b_config){
			YANG_Config parentConfig = getParentConfig();
			if (parentConfig.getConfig().compareTo("false") == 0 &&
					config.getConfig().compareTo("true") == 0)
				throw new YangParserException("@" + getLine() + "." + getCol() +
						":config to true and parent config to false");
		}
		
	}

	public String toString() {
		String result = new String();
		result += "container " + container;
		if (bracked) {
			result += " {\n";
			for (Enumeration<YANG_Must> em = musts.elements(); em
					.hasMoreElements();)
				result += em.nextElement().toString() + "\n";
			if (presence != null)
				result += presence.toString() + "\n";
			if (config != null)
				result += config.toString() + "\n";
			if (status != null)
				result += status.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			if (reference != null)
				result += reference.toString() + "\n";
			for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
					.hasMoreElements();)
				result += et.nextElement().toString() + "\n";
			for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
					.hasMoreElements();)
				result += eg.nextElement().toString() + "\n";
			for (Enumeration<YANG_DataDef> ed = datadefs.elements(); ed
					.hasMoreElements();)
				result += ed.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";

		return result;
	}

}
