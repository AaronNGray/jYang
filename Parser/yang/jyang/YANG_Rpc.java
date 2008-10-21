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

public class YANG_Rpc extends YANG_Body {

	private String rpc = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
	private Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();
	private YANG_Input input = null;
	private YANG_Output output = null;

	private boolean bracked = true;

	private boolean b_status = false, b_description = false,
			b_reference = false, b_input = false, b_output = false;

	public YANG_Rpc(int id) {
		super(id);
	}

	public YANG_Rpc(yang p, int id) {
		super(p, id);
	}

	public void setRpc(String r) {
		rpc = r;
	}

	public String getBody() {
		return getRpc();
	}

	public String getRpc() {
		return rpc;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in rpc "
					+ rpc, s.getLine(), s.getCol());
		b_status = true;
		status = s;
		bracked = true;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException("Description already defined in rpc "
					+ rpc, d.getLine(), d.getCol());
		b_description = true;
		description = d;
		bracked = true;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException("Reference already defined in rpc "
					+ rpc, r.getLine(), r.getCol());
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

	public void setInput(YANG_Input i) throws YangParserException {
		if (b_input)
			throw new YangParserException(
					"Input already defined in rpc " + rpc, i.getLine(), i
							.getCol());
		b_input = true;
		input = i;
		bracked = true;
	}

	public YANG_Input getInput() {
		return input;
	}

	public void setOutput(YANG_Output o) throws YangParserException {
		if (b_output)
			throw new YangParserException("Output already defined in rpc "
					+ rpc, o.getLine(), o.getCol());
		b_output = true;
		output = o;
		bracked = true;
	}

	public YANG_Output getOutput() {
		return output;
	}

	public boolean isBracked() {
		return bracked;
	}

	public void check(YangContext context) throws YangParserException {
		if (input != null)
			input.check(context);
		if (output != null)
			output.check(context);
	}

	public String toString() {
		String result = new String();
		result += "rpc " + rpc;
		if (bracked) {
			result += "{\n";
			if (status != null)
				result += status.toString() + "\n";
			if (description != null)
				result += description.toString() + "\n";
			for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
					.hasMoreElements();)
				result += et.nextElement().toString() + "\n";
			for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
					.hasMoreElements();)
				result += eg.nextElement().toString() + "\n";
			if (input != null)
				result += input.toString() + "\n";
			if (output != null)
				result += output.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}
}
