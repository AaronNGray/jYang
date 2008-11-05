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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class YANG_Type extends SimpleNode {

	private String type = null;
	private Vector<YANG_Enum> enums = new Vector<YANG_Enum>();
	private YANG_KeyRefSpecification keyref = null;
	private YANG_BitSpecification bitspec = null;
	private YANG_UnionSpecification yunion = null;
	private YANG_NumericalRestriction numrest = null;
	private YANG_StringRestriction strrest = null;

	private boolean bracked = false;

	public YANG_Type(int id) {
		super(id);
	}

	public YANG_Type(yang p, int id) {
		super(p, id);
	}

	public void setType(String t) {
		type = t;
	}

	public String getType() {
		return unquote(type);
	}

	public void addEnum(YANG_Enum e) {
		enums.add(e);
		bracked = true;
	}

	public Vector<YANG_Enum> getEnums() {
		return enums;
	}

	public void setKeyRef(YANG_KeyRefSpecification k) {
		keyref = k;
		bracked = true;
	}

	public YANG_KeyRefSpecification getKeyRef() {
		return keyref;
	}

	public void setBitSpec(YANG_BitSpecification b) {
		bitspec = b;
		bracked = true;
	}

	public YANG_BitSpecification getBitSpec() {
		return bitspec;
	}

	public void setUnionSpec(YANG_UnionSpecification u) {
		yunion = u;
		bracked = true;
	}

	public YANG_UnionSpecification getUnionSpec() {
		return yunion;
	}

	public void setNumRest(YANG_NumericalRestriction n) {
		numrest = n;
		bracked = true;
	}

	public YANG_NumericalRestriction getNumRest() {
		return numrest;
	}

	public void setStringRest(YANG_StringRestriction r) {
		strrest = r;
		bracked = true;
	}

	public YANG_StringRestriction getStringRest() {
		return strrest;
	}

	public boolean isBracked() {
		return bracked;
	}

	public boolean isPrefixed() {
		return type.indexOf(':') != -1;
	}

	public String getPrefix() {
		if (isPrefixed())
			return type.substring(0, type.indexOf(':'));
		else
			return null;
	}

	public String getSuffix() {
		if (isPrefixed())
			return type.substring(type.indexOf(':') + 1, type.length());
		else
			return type;
	}

	public String[][] getRanges(YangContext context) throws YangParserException {
		String[][] ranges = null;
		if (numrest != null) {
			if (numrest instanceof YANG_Range) {
				YANG_Range range = (YANG_Range) numrest;
				ranges = range.getRangeIntervals();
			}
		} else {

			ranges = new String[1][2];
			if (YangBuiltInTypes.isInteger(context.getBuiltInType(this))
					|| YangBuiltInTypes.string.compareTo(context
							.getBuiltInType(this)) == 0
					|| YangBuiltInTypes.binary.compareTo(context
							.getBuiltInType(this)) == 0) {
				ranges[0][0] = "min";
				ranges[0][1] = "max";
			} else if (YangBuiltInTypes.isFloat(context.getBuiltInType(this))) {
				ranges[0][0] = "-INF";
				ranges[0][1] = "INF";
			}
		}
		return ranges;
	}

	private String[][] getLength(YangContext context)
			throws YangParserException {
		String[][] ranges = null;
		if (getStringRest() != null)
			if (getStringRest().getLength() != null) {
				ranges = getStringRest().getLength().getLengthIntervals();
				return ranges;
			}

		ranges = new String[1][2];
		ranges[0][0] = "min";
		ranges[0][1] = "max";

		return ranges;

	}

	public void checkTypeSyntax() throws YangParserException {

		if (YangBuiltInTypes.union.compareTo(getType()) == 0)
			if (getUnionSpec() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":union type must have at least one type");

		if (YangBuiltInTypes.keyref.compareTo(getType()) == 0)
			if (getKeyRef() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type must have at one path");

		if (YangBuiltInTypes.enumeration.compareTo(getType()) == 0) {
			if (getEnums().size() == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":enumeration must have at least one enum");
			checkEnum();
		}

		if (YangBuiltInTypes.bits.compareTo(getType()) == 0) {
			if (getBitSpec() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bits  must have at least one bit");
			checkBits();
		}

	}

	public void check(YangContext context) throws YangParserException {

		if (context.getBuiltInType(this) == null) {
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":type " + getType() + " is not defined");
		}

		if (YangBuiltInTypes.isNumber(context.getBuiltInType(this))) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":numeric type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":numeric type can not have enum specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":numeric type can not have key reference specification");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":numeric type can not have length or pattern specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":numeric type can not have type specification");

			checkRange(context);
		} else if (YangBuiltInTypes.string.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":string type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":string type can not have enum specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":string type can not have key reference specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":string type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":string type can not have numeric restriction");

			checkStringLength(context);
			checkPattern(context);
		} else if (YangBuiltInTypes.yboolean.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":boolean type can not have length or pattern specification");
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":boolean type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":boolean type can not have enum specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":boolean type can not have key reference specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":boolean type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":boolean type can not have numeric restriction");
		} else if (YangBuiltInTypes.enumeration.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":enumeration type can not have bit specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":enumeration type can not have key reference specification");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":enumeration type can not have length or pattern specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":enumeration type can not have type specification");
		} else if (YangBuiltInTypes.bits
				.compareTo(context.getBuiltInType(this)) == 0) {
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":bits type can not have length or pattern specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bits type can not have enum specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":boolean type can not have key reference specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bits type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bits type can not have numeric restriction");
		} else if (YangBuiltInTypes.binary.compareTo(context
				.getBuiltInType(this)) == 0) {

			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":binary type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":binary type can not have enum specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":binary type can not have key reference specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":binary type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":binary type can not have numeric restriction");
			checkStringLength(context);
		} else if (YangBuiltInTypes.keyref.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type can not have enum specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type can not have numeric restriction");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":keyref type can not have length or pattern specification");
		} else if (YangBuiltInTypes.empty.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":empty type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":empty type can not have enum specification");
			if (getUnionSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":empty type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":empty type can not have numeric restriction");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":empty type can not have length or pattern specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":empty type can not have key reference specification");
		} else if (YangBuiltInTypes.union.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":union type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":union type can not have enum specification");
			if (getNumRest() != null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":union type can not have numeric restriction");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":union type can not have length or pattern specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":union type can not have key reference specification");
			if (YangBuiltInTypes.union.compareTo(getType()) == 0)
				for (Enumeration<YANG_Type> et = getUnionSpec().getTypes()
						.elements(); et.hasMoreElements();) {
					YANG_Type utype = et.nextElement();
					utype.check(context);
				}
		} else if (YangBuiltInTypes.instanceidentifier.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have bit specification");
			if (getEnums().size() != 0)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have enum specification");
			if (getUnionSpec() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have type specification");
			if (getNumRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have numeric restriction");
			if (getStringRest() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have length or pattern specification");
			if (getKeyRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have key reference specification");
		}
	}

	private void checkBits() throws YangParserException {
		if (getBitSpec() == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":bits must have at least one bit");
		YANG_BitSpecification bs = getBitSpec();
		BigInteger highest = new BigInteger("0");
		Vector<BigInteger> bitspos = new Vector<BigInteger>();
		String[] bitnames = new String[bs.getBits().size()];
		int i = 0;
		for (Enumeration<YANG_Bit> eb = bs.getBits().elements(); eb
				.hasMoreElements();) {
			YANG_Bit bit = eb.nextElement();
			bitnames[i++] = bit.getBit();
			if (bit.getPosition() == null) {
				if (highest.compareTo(YangBuiltInTypes.uint32ub
						.add(new BigInteger("1"))) == 0)
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":position must be given because max is given");
				bitspos.add(highest);
				highest = highest.add(new BigInteger("1"));
			} else {
				String strpos = YangBuiltInTypes.removeQuotesAndTrim(bit
						.getPosition().getPosition());
				BigInteger biginteger = null;
				try {
					biginteger = new BigInteger(strpos);
				} catch (NumberFormatException e) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":bit position is not an integer");
				}
				if (biginteger.compareTo(YangBuiltInTypes.uint32ub) == 1)
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":" + biginteger
							+ " is a too much higher position");
				if (biginteger.compareTo(highest) >= 0) {
					highest = biginteger;
					bitspos.add(highest);
					highest = highest.add(new BigInteger("1"));
				} else if (biginteger.compareTo(highest) == -1) {
					bitspos.add(biginteger);
				} else if (biginteger.compareTo(highest) == 0) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol()
							+ ":ambigous; a position must be specified");
				}
			}
		}
		boolean duplicate = false;
		for (Enumeration<BigInteger> eb = bitspos.elements(); eb
				.hasMoreElements()
				&& !duplicate;) {
			BigInteger bi = eb.nextElement();
			for (Enumeration<BigInteger> eb2 = bitspos.elements(); eb2
					.hasMoreElements()
					&& !duplicate;) {
				BigInteger bi2 = eb2.nextElement();
				duplicate = (bi.compareTo(bi2) == 0 && bi != bi2);
			}
		}
		if (duplicate)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":position must be unique");

		for (int j = 0; j < bitnames.length && !duplicate; j++)
			for (int k = j + 1; k < bitnames.length && !duplicate; k++) {
				duplicate = bitnames[j].compareTo(bitnames[k]) == 0;
			}
		if (duplicate)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":duplicate bit name");
	}

	private void checkEnum() throws YangParserException {
		int highest = 0;
		int[] enumvalues = new int[getEnums().size()];
		String[] enumnames = new String[getEnums().size()];
		int i = 0;
		for (Enumeration<YANG_Enum> ee = getEnums().elements(); ee
				.hasMoreElements();) {
			YANG_Enum yenum = ee.nextElement();
			enumnames[i] = yenum.getEnum();
			if (yenum.getValue() == null) {
				enumvalues[i++] = highest;
				highest++;
			} else {
				String strenum = YangBuiltInTypes.removeQuotesAndTrim(yenum
						.getValue().getValue());
				Integer integer = null;
				try {
					integer = Integer.parseInt(strenum);
				} catch (NumberFormatException n) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + ":enum value is not an integer");
				}
				if (integer.compareTo(new Integer(highest)) >= 0) {
					highest = integer.intValue();
					enumvalues[i++] = highest++;
				} else if (integer.compareTo(new Integer(highest)) == -1) {
					enumvalues[i++] = integer.intValue();
				} else if (integer.compareTo(new Integer(highest)) == 0) {
					throw new YangParserException("@" + getLine() + "."
							+ getCol() + highest
							+ ":ambigous; a value must be specified");
				}
			}
		}
		boolean duplicate = false;
		for (int j = 0; j < enumvalues.length && !duplicate; j++)
			for (int k = j + 1; k < enumvalues.length && !duplicate; k++) {
				duplicate = enumvalues[j] == enumvalues[k];
			}
		if (duplicate)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":duplicate enum value");

		for (int j = 0; j < enumnames.length && !duplicate; j++)
			for (int k = j + 1; k < enumnames.length && !duplicate; k++) {
				duplicate = enumnames[j].compareTo(enumnames[k]) == 0;
			}
		if (duplicate)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":duplicate enum name");

	}

	protected void checkPattern(YangContext context) throws YangParserException {
		if (getStringRest() == null)
			return;
		for (Enumeration<YANG_Pattern> ep = getStringRest().getPatterns()
				.elements(); ep.hasMoreElements();) {

			String pattern = ep.nextElement().getPattern();
			String canopattern = pattern.replaceAll("IsBasicLatin",
					"InBasicLatin");
			try {
				Pattern.compile(canopattern);
			} catch (PatternSyntaxException p) {
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":pattern definition " + p.getMessage());
			}
		}
	}

	/**
	 * check if the range is a restriction
	 * 
	 * @param context
	 * @throws YangParserException
	 */
	protected void checkRange(YangContext context) throws YangParserException {

		YANG_Type basetype = null;
		YANG_TypeDef typedef = null;

		// if the type is a number built-in type
		// check its range and the built-in bounds
		if (YangBuiltInTypes.isNumber(getType()))
			checkNumberRange(context, getBuiltInBounds(context));
		else {
			// the type is not a buit-in type
			// check only if there is a range specification
			if (getNumRest() != null) {
				// get the typedef that defines this type
				// must not be null because it is not a built-in type
				typedef = context.getTypeDef(this);
				// get the first range type
				basetype = getFirstRangeDefined(context, typedef);
				// if not null the first range type has ranges
				if (basetype != null)
					checkNumberRange(context, basetype.getRanges(context));
				// if null the first range is a built-in type
				else
					checkNumberRange(context, getRanges(context));

			}
		}
	}

	/**
	 * check if the string length is a restriction. If there is no string
	 * restriction and no length restriction, the method return. Else it looks
	 * for the first typedef that defines this type with a string length
	 * restriction and check length range with. If there is no restriction,
	 * check with itself. If there is no defining typedef, check with itself
	 * 
	 * @param context
	 * @throws YangParserException
	 */
	protected void checkStringLength(YangContext context)
			throws YangParserException {

		YANG_Type basetype = null;
		YANG_TypeDef typedef = null;

		// if the type is a string or binary built-in type
		// check its range and the built-in bounds
		if (YangBuiltInTypes.string.compareTo(getType()) == 0
				|| YangBuiltInTypes.binary.compareTo(getType()) == 0) {
			checkStringRange(context, getBuiltInBounds(context));
		} else {
			// the type is not a buit-in type
			// check only if there is a range specification
			if (getStringRest() != null) {
				if (getStringRest().getLength() != null) {
					// get the typedef that defines this type
					// must not be null because it is not a built-in type
					typedef = context.getTypeDef(this);
					// get the first range type
					basetype = getFirstLengthDefined(context, typedef);
					// if not null the first range type has length
					if (basetype != null)
						checkStringRange(context, basetype.getLength(context));
					// if null the first range is a built-in type
					else
						checkStringRange(context, getLength(context));
				}
			}
		}
	}

	/**
	 * Get the first type with a length restriction from the type used by the
	 * given typedef and to the bases typedef until a built-in type
	 * 
	 * @param context
	 * @param td
	 *            the typedef
	 * @return the first type with a length restriction or null either if the
	 *         typedef has a length restriction or a built-in type is reached.
	 * @throws YangParserException
	 */
	private YANG_Type getFirstLengthDefined(YangContext context, YANG_TypeDef td)
			throws YangParserException {

		YANG_Type basetype = td.getType();
		YANG_TypeDef typedef = null;
		YANG_TypeDef oldtd = null;
		boolean end = false;

		if (basetype.getStringRest() != null) {
			if (basetype.getStringRest().getLength() != null)
				return basetype;
		}
		oldtd = td;
		while (!end) {
			typedef = context.getBaseTypeDef(oldtd);
			if (typedef != null) {
				if (typedef.getType().getStringRest() != null)
					if (typedef.getType().getStringRest().getLength() != null)
						return typedef.getType();

				oldtd = typedef;
			} else
				end = true;
		}
		return null;

	}

	private YANG_Type getFirstRangeDefined(YangContext context, YANG_TypeDef td)
			throws YangParserException {
		YANG_Type basetype = td.getType();
		YANG_TypeDef typedef = null;
		YANG_TypeDef oldtd = null;
		boolean end = false;

		if (basetype.getNumRest() != null) {
			return basetype;
		}
		oldtd = td;
		while (!end) {
			typedef = context.getBaseTypeDef(oldtd);
			if (typedef != null) {
				if (typedef.getType().getNumRest() != null)
					return typedef.getType();
				oldtd = typedef;
			} else
				end = true;
		}
		return null;
	}

	/**
	 * check if the range is a restriction of the given range
	 * 
	 * @param context
	 * @param supranges
	 *            the range of the typedef defining this type
	 * @throws YangParserException
	 */
	private void checkNumberRange(YangContext context, String[][] supranges)
			throws YangParserException {
		if (numrest == null)
			return;
		String[][] subranges = getRanges(context);
		checkSubTyping(context, subranges, supranges);

	}

	/**
	 * check if the string range is a restriction of the given range
	 * 
	 * @param context
	 * @param supranges
	 *            the range of the typedef defining this type
	 * @throws YangParserException
	 */
	private void checkStringRange(YangContext context, String[][] supranges)
			throws YangParserException {
		String[][] subranges = getLength(context);
		checkSubTyping(context, subranges, supranges);
	}

	public void checkSubTyping(YangContext context, String[][] subranges,
			String[][] supranges) throws YangParserException {

		checkRangeValues(context, subranges);
		checkRangeValues(context, supranges);
		checkRangeOrder(context, subranges);
		checkRangeOrder(context, supranges);
		subranges = normalizeRange(context, subranges);
		supranges = normalizeRange(context, supranges);

		for (int i = 0; i < subranges.length; i++) {
			boolean inside = false;
			for (int j = 0; j < supranges.length && !inside; j++) {
				if (compareTo(context, subranges[i][0], supranges[j][0]) >= 0
						&& compareTo(context, subranges[i][1], supranges[j][1]) <= 0)
					inside = true;
			}
			if (!inside)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":subtyping error " + subranges[i][0] + ".."
						+ subranges[i][1]);
		}

	}

	private String[][] normalizeRange(YangContext context, String[][] r)
			throws YangParserException {
		for (int i = 0; i < r.length; i++)
			for (int j = 0; j < 2; j++)
				if (r[i][j].compareTo("min") == 0)
					r[i][j] = normalizeValue(context, "min");
				else if (r[i][j].compareTo("max") == 0)
					r[i][j] = normalizeValue(context, "max");
		return r;
	}

	private String normalizeValue(YangContext context, String v)
			throws YangParserException {
		if (v.compareTo("min") == 0) {
			if (YangBuiltInTypes.int8.compareTo(context.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int8lb)).toString();
			else if (YangBuiltInTypes.int16.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int16lb)).toString();
			else if (YangBuiltInTypes.int32.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int32lb)).toString();
			else if (YangBuiltInTypes.int64.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.int64lb.toString();
			else if (YangBuiltInTypes.uint8.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.uint8lb)).toString();
			else if (YangBuiltInTypes.uint16.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.uint16lb)).toString();
			else if (YangBuiltInTypes.uint32.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.uint32lb)).toString();
			else if (YangBuiltInTypes.uint64.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.uint64lb.toString();
			else if (YangBuiltInTypes.string.compareTo(context
					.getBuiltInType(this)) == 0)
				return "0";
			else if (YangBuiltInTypes.binary.compareTo(context
					.getBuiltInType(this)) == 0)
				return "0";
			else if (YangBuiltInTypes.float32.compareTo(context
					.getBuiltInType(this)) == 0)
				return ("-INF");
			else if (YangBuiltInTypes.float64.compareTo(context
					.getBuiltInType(this)) == 0)
				return ("-INF");
			else
				throw new YangParserException("panic in normalize min value "
						+ v + " " + getType());
		} else if (v.compareTo("max") == 0) {
			if (YangBuiltInTypes.int8.compareTo(context.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int8ub)).toString();
			else if (YangBuiltInTypes.int16.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int16ub)).toString();
			else if (YangBuiltInTypes.int32.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.int32ub)).toString();
			else if (YangBuiltInTypes.int64.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.int64ub.toString();
			else if (YangBuiltInTypes.uint8.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.uint8ub)).toString();
			else if (YangBuiltInTypes.uint16.compareTo(context
					.getBuiltInType(this)) == 0)
				return (new Integer(YangBuiltInTypes.uint16ub)).toString();
			else if (YangBuiltInTypes.uint32.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.uint32ub.toString();
			else if (YangBuiltInTypes.uint64.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.uint64ub.toString();
			else if (YangBuiltInTypes.string.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.uint64ub.toString();
			else if (YangBuiltInTypes.binary.compareTo(context
					.getBuiltInType(this)) == 0)
				return YangBuiltInTypes.uint64ub.toString();
			else if (YangBuiltInTypes.float32.compareTo(context
					.getBuiltInType(this)) == 0)
				return ("INF");
			else if (YangBuiltInTypes.float64.compareTo(context
					.getBuiltInType(this)) == 0)
				return ("INF");
			else
				throw new YangParserException("panic in normalize max value");
		} else
			throw new YangParserException("panic in normalize value");
	}

	public void checkRangeValues(YangContext context, String[][] r)
			throws YangParserException {
		if (YangBuiltInTypes.isInteger(context.getBuiltInType(this))
				|| YangBuiltInTypes.string.compareTo(context
						.getBuiltInType(this)) == 0
				|| YangBuiltInTypes.binary.compareTo(context
						.getBuiltInType(this)) == 0) {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < 2; j++) {
					String bound = r[i][j];
					if (bound.compareTo("min") != 0
							&& bound.compareTo("max") != 0) {
						int radix;
						if (bound.startsWith("0x")) {
							radix = 16;
							bound = bound.substring(2, bound.length());
						} else if (bound.startsWith("-0x")) {
							radix = 16;
							bound = "-" + bound.substring(3, bound.length());
						} else if (bound.startsWith("+0x")) {
							radix = 16;
							bound = bound.substring(3, bound.length());
						} else if (bound.startsWith("0") && bound.length() > 1) {
							radix = 8;
							bound = bound.substring(1, bound.length());
						} else if (bound.startsWith("-0")) {
							radix = 8;
							bound = "-" + bound.substring(2, bound.length());
						} else if (bound.startsWith("+0")) {
							radix = 8;
							bound = bound.substring(2, bound.length());
						} else {
							radix = 10;
							if (bound.startsWith("+"))
								bound = bound.substring(1, bound.length());
						}
						try {
							BigInteger Bi = new BigInteger(bound, radix);
							r[i][j] = Bi.toString();
						} catch (NumberFormatException e) {
							throw new YangParserException("@" + getLine() + "."
									+ getCol() + ":" + bound
									+ " is not an integer");
						}
					}
				}
			}

		} else if (YangBuiltInTypes.isFloat(context.getBuiltInType(this))) {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < 2; j++) {
					String bound = r[i][j];
					if (bound.compareTo("-INF") != 0
							&& bound.compareTo("INF") != 0
							&& bound.compareTo("NaN") != 0
							&& bound.compareTo("min") != 0
							&& bound.compareTo("max") != 0) {
						try {
							new BigDecimal(bound);
						} catch (NumberFormatException e) {
							throw new YangParserException("@" + getLine() + "."
									+ getCol() + ":" + bound
									+ " is not a float");
						}

					}
				}
			}

		}
	}

	public void checkRangeOrder(YangContext context, String[][] r)
			throws YangParserException {

		String bi = r[0][0];
		String bs = r[0][1];
		for (int i = 1; i < r.length; i++) {
			String oldbs = bs;
			if (compareTo(context, bi, bs) == 1)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":not ascending order in range because " + bi + " > "
						+ bs);
			bi = r[i][0];
			bs = r[i][1];
			if (compareTo(context, oldbs, bi) != -1)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":not ascending order in range because " + oldbs
						+ " >= " + bi);
		}
		if (compareTo(context, bi, bs) == 1)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":not ascending order in range because " + bi + " > "
					+ bs);
	}

	/**
	 * 
	 * @param context
	 * @param bi
	 * @param bs
	 * @return -1 if bi < bs, 0 if bi == bs and 1 if bi > bs
	 * @throws YangParserException
	 */
	private int compareTo(YangContext context, String bi, String bs)
			throws YangParserException {

		if (YangBuiltInTypes.isInteger(context.getBuiltInType(this))
				|| YangBuiltInTypes.string.compareTo(context
						.getBuiltInType(this)) == 0
				|| YangBuiltInTypes.binary.compareTo(context
						.getBuiltInType(this)) == 0) {
			if (bi.compareTo("min") == 0) {
				if (bs.compareTo("min") == 0)
					return 0;
				else
					return -1;
			}
			if (bs.compareTo("max") == 0) {
				if (bi.compareTo("max") == 0)
					return 0;
				else
					return -1;
			}
			if (bi.compareTo("max") == 0)
				return 1;
			if (bs.compareTo("min") == 0)
				return 1;
			try {
				BigInteger bbi = new BigInteger(bi);
				BigInteger bbs = new BigInteger(bs);
				return bbi.compareTo(bbs);
			} catch (NumberFormatException e) {
				throw new YangParserException("Panic in in range comparaison");
			}

		} else if (YangBuiltInTypes.isFloat(context.getBuiltInType(this))) {
			if (bi.compareTo("min") == 0 || bi.compareTo("-INF") == 0) {
				if (bs.compareTo("min") == 0 || bs.compareTo("-INF") == 0)
					return 0;
				else
					return -1;
			}
			if (bs.compareTo("max") == 0 || bs.compareTo("INF") == 0) {
				if (bi.compareTo("max") == 0 || bi.compareTo("INF") == 0)
					return 0;
				else
					return -1;
			}
			if (bi.compareTo("max") == 0 || bi.compareTo("INF") == 0)
				return 1;
			if (bs.compareTo("min") == 0 || bs.compareTo("-INF") == 0)
				return 1;
			try {
				BigDecimal bbi = new BigDecimal(bi);
				BigDecimal bbs = new BigDecimal(bs);
				return bbi.compareTo(bbs);

			} catch (NumberFormatException e) {
				throw new YangParserException("Panic in in range comparaison");
			}
		} else
			throw new YangParserException("Panic in range comparaison");

	}

	public String[][] getBuiltInBounds(YangContext context)
			throws YangParserException {
		String[][] birange = new String[1][2];
		if (YangBuiltInTypes.int8.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.int8lb)).toString();
			birange[0][1] = (new Integer(YangBuiltInTypes.int8ub)).toString();
		} else if (YangBuiltInTypes.int16.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.int16lb)).toString();
			birange[0][1] = (new Integer(YangBuiltInTypes.int16ub)).toString();
		} else if (YangBuiltInTypes.int32.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.int32lb)).toString();
			birange[0][1] = (new Integer(YangBuiltInTypes.int32ub)).toString();
		} else if (YangBuiltInTypes.int64.compareTo(getType()) == 0) {
			birange[0][0] = YangBuiltInTypes.int64lb.toString();
			birange[0][1] = YangBuiltInTypes.int64ub.toString();
		} else if (YangBuiltInTypes.uint8.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.uint8lb)).toString();
			birange[0][1] = (new Integer(YangBuiltInTypes.uint8ub)).toString();
		} else if (YangBuiltInTypes.uint16.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.uint16lb)).toString();
			birange[0][1] = (new Integer(YangBuiltInTypes.uint16ub)).toString();
		} else if (YangBuiltInTypes.uint32.compareTo(getType()) == 0) {
			birange[0][0] = (new Integer(YangBuiltInTypes.uint32lb)).toString();
			birange[0][1] = YangBuiltInTypes.uint32ub.toString();
		} else if (YangBuiltInTypes.uint64.compareTo(getType()) == 0) {
			birange[0][0] = YangBuiltInTypes.uint64lb.toString();
			birange[0][1] = YangBuiltInTypes.uint64ub.toString();
		} else if (YangBuiltInTypes.float32.compareTo(getType()) == 0) {
			birange[0][0] = "-INF";
			birange[0][1] = "INF";
		} else if (YangBuiltInTypes.float64.compareTo(getType()) == 0) {
			birange[0][0] = "-INF";
			birange[0][1] = "INF";
		} else if (YangBuiltInTypes.string.compareTo(getType()) == 0) {
			birange[0][0] = "0";
			birange[0][1] = YangBuiltInTypes.uint64ub.toString();
		} else if (YangBuiltInTypes.binary.compareTo(getType()) == 0) {
			birange[0][0] = "0";
			birange[0][1] = YangBuiltInTypes.uint64ub.toString();
		}
		return birange;
	}

	public void checkValue(YangContext context, String value)
			throws YangParserException {
		if (YangBuiltInTypes.isNumber(context.getBuiltInType(this))) {
			value = YangBuiltInTypes.removeQuotesAndTrim(value);
			String[][] ranges = null;
			if (getNumRest() != null) {
				ranges = getRanges(context);
			} else {
				YANG_TypeDef td = context.getBaseType(this);
				if (td != null) {
					YANG_Type bt = getFirstRangeDefined(context, td);
					if (bt != null)
						ranges = bt.getRanges(context);
					else
						ranges = getRanges(context);
				} else {
					ranges = getRanges(context);
				}
			}
			// }

			if (YangBuiltInTypes.isInteger(context.getBuiltInType(this))) {
				if (value.compareTo("min") == 0) {
					if (ranges[0][0].compareTo("min") != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":min value is not in the "
								+ getType() + " range values");
				} else if (value.compareTo("max") == 0) {
					if (ranges[ranges.length - 1][1].compareTo("max") != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":max value is not in the "
								+ getType() + " range values");

				} else {
					BigInteger bilb = null;
					BigInteger biub = null;
					try {
						BigInteger bi = new BigInteger(value);
						boolean inside = false;
						for (int i = 0; i < ranges.length && !inside; i++) {
							if (ranges[i][0].compareTo("min") != 0
									&& ranges[i][1].compareTo("max") != 0) {
								bilb = new BigInteger(ranges[i][0]);
								biub = new BigInteger(ranges[i][1]);
							} else if (ranges[i][0].compareTo("min") == 0
									&& ranges[i][1].compareTo("max") != 0) {
								if (YangBuiltInTypes.int8.compareTo(context
										.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int8lb).toString());
								else if (YangBuiltInTypes.int16
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int16lb)
											.toString());
								else if (YangBuiltInTypes.int32
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int32lb)
											.toString());
								else if (YangBuiltInTypes.int64
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = YangBuiltInTypes.int64lb;
								else if (YangBuiltInTypes.uint8
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint8lb)
											.toString());
								else if (YangBuiltInTypes.uint16
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint16lb)
											.toString());
								else if (YangBuiltInTypes.uint32
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint32lb)
											.toString());
								else if (YangBuiltInTypes.uint64
										.compareTo(context.getBuiltInType(this)) == 0)
									bilb = YangBuiltInTypes.uint64lb;
								biub = new BigInteger(ranges[i][1]);
							} else if (ranges[i][1].compareTo("max") == 0
									&& ranges[i][0].compareTo("min") != 0) {
								if (YangBuiltInTypes.int8.compareTo(context
										.getBuiltInType(this)) == 0)
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int8ub).toString());
								else if (YangBuiltInTypes.int16
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int16ub)
											.toString());
								else if (YangBuiltInTypes.int32
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int32ub)
											.toString());
								else if (YangBuiltInTypes.int64
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = YangBuiltInTypes.int64ub;
								else if (YangBuiltInTypes.uint8
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.uint8ub)
											.toString());
								else if (YangBuiltInTypes.uint16
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.uint16ub)
											.toString());
								else if (YangBuiltInTypes.uint32
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = YangBuiltInTypes.uint32ub;
								else if (YangBuiltInTypes.uint64
										.compareTo(context.getBuiltInType(this)) == 0)
									biub = YangBuiltInTypes.uint64ub;
								bilb = new BigInteger(ranges[i][0]);
							} else {
								if (YangBuiltInTypes.int8.compareTo(context
										.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int8lb).toString());
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int8ub).toString());
								} else if (YangBuiltInTypes.int16
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int16lb)
											.toString());
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int16ub)
											.toString());
								} else if (YangBuiltInTypes.int32
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.int32lb)
											.toString());
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.int32ub)
											.toString());
								} else if (YangBuiltInTypes.int64
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = YangBuiltInTypes.int64lb;
									biub = YangBuiltInTypes.int64ub;
								} else if (YangBuiltInTypes.uint8
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint8lb)
											.toString());
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.uint8ub)
											.toString());
								} else if (YangBuiltInTypes.uint16
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint16lb)
											.toString());
									biub = new BigInteger(new Integer(
											YangBuiltInTypes.uint16ub)
											.toString());
								} else if (YangBuiltInTypes.uint32
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = new BigInteger(new Integer(
											YangBuiltInTypes.uint32lb)
											.toString());
									biub = YangBuiltInTypes.uint32ub;
								} else if (YangBuiltInTypes.uint64
										.compareTo(context.getBuiltInType(this)) == 0) {
									bilb = YangBuiltInTypes.uint64lb;
									biub = YangBuiltInTypes.uint64ub;
								}
							}

							inside = (bilb.compareTo(bi) <= 0 && biub
									.compareTo(bi) >= 0);
						}
						if (!inside)
							throw new YangParserException("@" + getLine() + "."
									+ getCol() + ":" + value
									+ " is not inside " + getType() + " values");
					} catch (NumberFormatException ne) {
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":illegal integer value " + value);
					}
				}
			} else if (YangBuiltInTypes.isFloat(context.getBuiltInType(this))) {
				if (value.compareTo("min") == 0 || value.compareTo("-INF") == 0) {
					if (ranges[0][0].compareTo("min") != 0
							&& ranges[0][0].compareTo("-INF") != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol()
								+ ":negative infinite value is not in the "
								+ getType() + " range values");
				} else if (value.compareTo("max") == 0
						|| value.compareTo("INF") == 0) {
					if (ranges[ranges.length - 1][1].compareTo("max") != 0
							&& ranges[ranges.length - 1][1].compareTo("INF") != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol()
								+ ":positive infinite value is not in the "
								+ getType() + " range values");
				} else {
					BigDecimal bdlb = null;
					BigDecimal bdub = null;
					try {
						BigDecimal bd = new BigDecimal(value);
						boolean inside = false;
						for (int i = 0; i < ranges.length && !inside; i++) {
							if ((ranges[i][0].compareTo("min") != 0 && ranges[i][0]
									.compareTo("-INF") != 0)
									&& (ranges[i][1].compareTo("max") != 0 && ranges[i][1]
											.compareTo("INF") != 0)) {
								bdlb = new BigDecimal(ranges[i][0]);
								bdub = new BigDecimal(ranges[i][1]);
								inside = bdlb.compareTo(bd) <= 0
										&& bdub.compareTo(bd) >= 0;
							} else if ((ranges[i][0].compareTo("min") == 0 || ranges[i][0]
									.compareTo("-INF") == 0)
									&& (ranges[i][1].compareTo("max") != 0 && ranges[i][1]
											.compareTo("INF") != 0)) {
								bdub = new BigDecimal(ranges[i][1]);
								inside = bdub.compareTo(bd) >= 0;
							} else if ((ranges[i][0].compareTo("min") != 0 && ranges[i][0]
									.compareTo("-INF") != 0)
									&& (ranges[i][1].compareTo("max") == 0 || ranges[i][1]
											.compareTo("INF") == 0)) {
								bdlb = new BigDecimal(ranges[i][0]);
								inside = bdlb.compareTo(bd) <= 0;
							} else
								inside = true;
						}
						if (!inside)
							throw new YangParserException("@" + getLine() + "."
									+ getCol() + ": " + value
									+ " is not in the " + getType() + " range "
									+ bdlb + "  " + bdub);
					} catch (NumberFormatException ne) {
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ": " + value + " is not a float");
					}
				}
			}
		} else if (YangBuiltInTypes.string.compareTo(context
				.getBuiltInType(this)) == 0
				|| YangBuiltInTypes.binary.compareTo(context
						.getBuiltInType(this)) == 0) {
			value = YangBuiltInTypes.removeQuotes(value);
			String[][] ranges = null;
			boolean isStringRestricted = false;

			if (getStringRest() != null) {
				if (getStringRest().getLength() != null)
					isStringRestricted = true;
				else
					isStringRestricted = false;
			} else
				isStringRestricted = false;

			if (isStringRestricted) {
				ranges = getLength(context);
			} else {
				YANG_TypeDef td = context.getBaseType(this);
				if (td != null) {
					YANG_Type bt = getFirstLengthDefined(context, td);
					if (bt != null)
						ranges = bt.getLength(context);
					else
						ranges = getLength(context);
				} else {
					ranges = getLength(context);
				}
			}

			BigInteger bi = new BigInteger(new Integer(value.length())
					.toString());
			BigInteger bilb = null;
			BigInteger biub = null;
			boolean inside = false;
			for (int i = 0; i < ranges.length && !inside; i++) {
				if (ranges[i][0].compareTo("min") != 0
						&& ranges[i][1].compareTo("max") != 0) {
					bilb = new BigInteger(ranges[i][0]);
					biub = new BigInteger(ranges[i][1]);
					inside = (bilb.compareTo(bi) <= 0 && biub.compareTo(bi) >= 0);
				} else if (ranges[i][0].compareTo("min") == 0
						&& ranges[i][1].compareTo("max") != 0) {
					biub = new BigInteger(ranges[i][1]);
					inside = biub.compareTo(bi) >= 0;
				} else if (ranges[i][0].compareTo("min") != 0
						&& ranges[i][1].compareTo("max") == 0) {
					bilb = new BigInteger(ranges[i][0]);
					inside = bilb.compareTo(bi) <= 0;
				} else if (ranges[i][0].compareTo("min") == 0
						&& ranges[i][1].compareTo("max") == 0) {
					inside = true;
				}
			}
			if (!inside)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ": " + value + " has not correct length");
		} else if (YangBuiltInTypes.enumeration.compareTo(context
				.getBuiltInType(this)) == 0) {
			value = YangBuiltInTypes.removeQuotesAndTrim(value);
			String[] enums = getFirstEnumDefined(context, this);
			boolean match = false;
			int i = 0;
			while (!match && i < enums.length)
				match = enums[i++].compareTo(value) == 0;
			if (!match)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":enum value " + value + " is not in the type "
						+ getType());
		} else if (YangBuiltInTypes.bits
				.compareTo(context.getBuiltInType(this)) == 0) {
			value = YangBuiltInTypes.removeQuotesAndTrim(value);
			byte[] bv = value.getBytes();
			boolean binary = true;
			for (int i = 0; i < bv.length && binary; i++)
				binary = bv[i] == '1' || bv[i] == '0';
			if (!binary)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bit value " + value + " is not binary");
			if (value.length() != getFirstBitDefined(context, this))
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bit value " + value + " has not correct length");
		} else if (YangBuiltInTypes.union.compareTo(context
				.getBuiltInType(this)) == 0) {
			YANG_Type ut = getFirstUnionDefined(context, this);
			boolean found = false;
			for (Enumeration<YANG_Type> et = ut.getUnionSpec().getTypes()
					.elements(); et.hasMoreElements() && !found;) {
				YANG_Type type = et.nextElement();
				try {
					type.checkValue(context, value);
					found = true;
				} catch (YangParserException ye) {
					// nothing to do; try an other union type
				}
			}
			if (!found)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":value " + value
						+ " does not match in any union type");
		} else if (YangBuiltInTypes.empty.compareTo(context
				.getBuiltInType(this)) == 0) {
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":value " + value + " can not be of the empty type");
		} else if (YangBuiltInTypes.yboolean.compareTo(context
				.getBuiltInType(this)) == 0) {
			value = YangBuiltInTypes.removeQuotesAndTrim(value);
			if (value.compareTo("true") != 0 && value.compareTo("false") != 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":value " + value
						+ " can not be of the boolean type (true or false)");

		}

	}

	private YANG_Type getFirstUnionDefined(YangContext context, YANG_Type bt)
			throws YangParserException {

		YANG_Type basetype = bt;
		YANG_TypeDef typedef = null;
		boolean ranged = false;

		if (basetype.getUnionSpec() != null)
			return basetype;
		typedef = context.getTypeDef(basetype);
		while (!ranged) {
			if (typedef.getType().getUnionSpec() != null)
				return typedef.getType();
			else {
				typedef = context.getBaseTypeDef(typedef);
			}
		}
		return null;

	}

	private int getFirstBitDefined(YangContext context, YANG_Type bt)
			throws YangParserException {
		YANG_Type basetype = bt;
		YANG_TypeDef typedef = null;
		boolean found = false;

		if (YangBuiltInTypes.bits.compareTo(basetype.getType()) == 0) {
			found = true;
		}
		if (basetype.getBitSpec() != null) {
			found = true;
		} else {
			typedef = context.getTypeDef(basetype);
			while (!found) {
				if (typedef.getType().getBitSpec() != null) {
					basetype = typedef.getType();
					found = true;
				} else
					typedef = context.getBaseTypeDef(typedef);
			}
		}

		return basetype.getBitSpec().getBits().size();
	}

	private String[] getFirstEnumDefined(YangContext context, YANG_Type bt)
			throws YangParserException {

		YANG_Type basetype = bt;
		YANG_TypeDef typedef = null;
		boolean found = false;

		if (YangBuiltInTypes.enumeration.compareTo(basetype.getType()) == 0) {
			found = true;
		}
		if (basetype.getEnums().size() != 0) {
			found = true;
		} else {
			typedef = context.getTypeDef(basetype);
			while (!found) {
				if (typedef.getType().getEnums().size() != 0) {
					basetype = typedef.getType();
					found = true;
				} else
					typedef = context.getBaseTypeDef(typedef);
			}
		}
		String[] enumsv = new String[basetype.getEnums().size()];
		int i = 0;
		for (Enumeration<YANG_Enum> ee = basetype.getEnums().elements(); ee
				.hasMoreElements();)
			enumsv[i++] = YangBuiltInTypes.removeQuotesAndTrim(ee.nextElement()
					.getEnum());
		return enumsv;

	}

	public String toString() {
		String result = new String();
		result += "type " + type;
		if (bracked) {
			result += "{\n";
			for (Enumeration<YANG_Enum> ee = enums.elements(); ee
					.hasMoreElements();) {
				result += ee.nextElement().toString() + "\n";
			}
			if (keyref != null)
				result += keyref.toString() + "\n";
			if (bitspec != null)
				result += bitspec.toString() + "\n";
			if (yunion != null)
				result += yunion.toString() + "\n";
			if (numrest != null)
				result += numrest.toString() + "\n";
			if (strrest != null)
				result += strrest.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}
}
