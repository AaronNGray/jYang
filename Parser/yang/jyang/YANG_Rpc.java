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

public class YANG_Rpc extends YANG_DataDefInfo {

	private String rpc = null;
	private Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
	private Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();
	private YANG_Input input = null;
	private YANG_Output output = null;


	private boolean b_input = false, b_output = false;

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

	
	public void addTypeDef(YANG_TypeDef t) {
		typedefs.add(t);
	}

	public Vector<YANG_TypeDef> getTypeDefs() {
		return typedefs;
	}

	public void addGrouping(YANG_Grouping g) {
		groupings.add(g);
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
	}

	public YANG_Output getOutput() {
		return output;
	}

	public boolean isBracked(){
		return super.isBracked() || b_input || b_output || typedefs.size() != 0 || groupings.size() != 0;
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
		if (isBracked()) {
			result += "{\n";
			result += super.toString() + "\n";
			for (Enumeration<YANG_TypeDef> et = typedefs.elements(); et
					.hasMoreElements();)
				result += et.nextElement().toString() + "\n";
			for (Enumeration<YANG_Grouping> eg = groupings.elements(); eg
					.hasMoreElements();)
				result += eg.nextElement().toString() + "\n";
			if (b_input)
				result += input.toString() + "\n";
			if (b_output)
				result += output.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}
}
