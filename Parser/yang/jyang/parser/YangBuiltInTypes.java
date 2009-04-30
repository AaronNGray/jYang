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
import java.math.*;

public class YangBuiltInTypes {

	public static final String int8 = "int8";
	public static final String int16 = "int16";
	public static final String int32 = "int32";
	public static final String int64 = "int64";
	public static final String uint8 = "uint8";
	public static final String uint16 = "uint16";
	public static final String uint32 = "uint32";
	public static final String uint64 = "uint64";
	public static final String float32 = "float32";
	public static final String float64 = "float64";
	public static final String string = "string";
	public static final String yboolean = "boolean";
	public static final String enumeration = "enumeration";
	public static final String bits = "bits";
	public static final String binary = "binary";
	public static final String keyref = "keyref";
	public static final String empty = "empty";
	public static final String union = "union";
	public static final String instanceidentifier = "instance-identifier";

	public static final int int8lb = -128;
	public static final int int8ub = 127;
	public static final int int16lb = -32768;
	public static final int int16ub = 32767;
	public static final int int32lb = -2147483648;
	public static final int int32ub = 2147483647;
	public static final BigInteger int64lb = new BigInteger(
			"-9223372036854775808");
	public static final BigInteger int64ub = new BigInteger(
			"9223372036854775807");
	public static final int uint8lb = 0;
	public static final int uint16lb = 0;
	public static final int uint32lb = 0;
	public static final BigInteger uint64lb = new BigInteger("0");
	public static final int uint8ub = 255;
	public static final int uint16ub = 65535;
	public static final BigInteger uint32ub = new BigInteger("4294967295");
	public static final BigInteger uint64ub = new BigInteger(
			"18446744073709551615");
	
	public static final int idlength = 63;
	
	public static final String config = "true";
	public static final String mandatory = "false";

	public String canonizeString(String s) {
		String result = s;
		result = result.trim();
		return result;
	}
	
	public static boolean isNumber(String s){
		return isInteger(s) || isFloat(s);
	}

	public static boolean isInteger(String t) {
		if (t == null)
			return false;
		if (t.compareTo(int8) == 0 || t.compareTo(int16) == 0
				|| t.compareTo(int32) == 0 || t.compareTo(int64) == 0
				|| t.compareTo(uint8) == 0 || t.compareTo(uint16) == 0
				|| t.compareTo(uint32) == 0 || t.compareTo(uint64) == 0)
			return true;
		else
			return false;
	}

	public static boolean isFloat(String t) {
		if (t == null)
			return false;
		if (t.compareTo(float32) == 0 || t.compareTo(float64) == 0)
			return true;
		else
			return false;
	}

	public static boolean isBuiltIn(String t) {
		if (t == null)
			return false;
		if (t.compareTo(int8) == 0 || t.compareTo(int16) == 0
				|| t.compareTo(int32) == 0 || t.compareTo(int64) == 0
				|| t.compareTo(uint8) == 0 || t.compareTo(uint16) == 0
				|| t.compareTo(uint32) == 0 || t.compareTo(uint64) == 0
				|| t.compareTo(float32) == 0 || t.compareTo(float64) == 0
				|| t.compareTo(string) == 0 || t.compareTo(yboolean) == 0
				|| t.compareTo(enumeration) == 0 || t.compareTo(bits) == 0
				|| t.compareTo(binary) == 0 || t.compareTo(keyref) == 0
				|| t.compareTo(empty) == 0 || t.compareTo(union) == 0
				|| t.compareTo(instanceidentifier) == 0)
			return true;
		else
			return false;
	}

	public static String removeQuotesAndTrim(String qs) {
		String s = new String(qs);
		s = s.trim();
		if (s.charAt(0) == '\"')
			s = s.substring(1, s.length());
		if (s.charAt(s.length() - 1) == '\"')
			s = s.substring(0, s.length() - 1);
		s = s.trim();
		return s;
	}
	public static String removeQuotes(String qs) {
		String s = new String(qs);
		s = s.trim();
		if (s.charAt(0) == '\"')
			s = s.substring(1, s.length());
		if (s.charAt(s.length() - 1) == '\"')
			s = s.substring(0, s.length() - 1);
		return s;
	}
}
