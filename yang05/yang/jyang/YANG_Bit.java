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

public class YANG_Bit extends SimpleNode {

	private String bit = null;
	private YANG_Position position = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	public YANG_Bit(int id) {
		super(id);
	}

	public YANG_Bit(yang p, int id) {
		super(p, id);
	}

	public void setBit(String b) {
		bit = b;
	}

	public String getBit() {
		return bit;
	}

	public void setPosition(YANG_Position p) {
		position = p;
	}

	public YANG_Position getPosition() {
		return position;
	}

	public void setStatus(YANG_Status s) {
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) {
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setReference(YANG_Reference r) {
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public String toString() {
		String result = new String();
		result += "bit " + bit + "{\n";
		if (position != null)
			result += position.toString() + "\n";
		if (status != null)
			result += status.toString() + "\n";
		if (description != null)
			result += description.toString() + "\n";
		if (reference != null)
			result += reference.toString() + "\n";
		result += "}";
		return result;
	}
}
