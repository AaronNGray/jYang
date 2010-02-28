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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

public class YANG_Type extends SimpleYangNode {

	private String type = null;
	private Vector<YANG_Enum> enums = new Vector<YANG_Enum>();
	private YANG_LeafRefSpecification keyref = null;
	private YANG_BitSpecification bitspec = null;
	private YANG_UnionSpecification yunion = null;
	private YANG_NumericalRestriction numrest = null;
	private YANG_StringRestriction strrest = null;
	private YANG_Base base = null;
	private YANG_Decimal64Spec dec64Spec = null;
	private String instanceIdentifierSpec = null;

	private YANG_TypeDef typedef = null;

	public YANG_TypeDef getTypedef() {
		return typedef;
	}

	public String getInstanceIdentifierSpec() {
		return instanceIdentifierSpec;
	}

	public void setInstanceIdentifierSpec(String instanceIdentifierSpec) {
		this.instanceIdentifierSpec = instanceIdentifierSpec;
	}

	public YANG_Decimal64Spec getDec64Spec() {
		return dec64Spec;
	}

	public void setDec64Spec(YANG_Decimal64Spec dec64Spec) {
		this.dec64Spec = dec64Spec;
		bracked = true;
	}

	public YANG_Base getIdentityRefSpec() {
		return base;
	}

	public void setIdentityRefSpec(YANG_Base base) {
		this.base = base;
		bracked = true;
	}

	private boolean bracked = false;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public YANG_Type(int id) {
		super(id);
	}

	public YANG_Type(yang p, int id) {
		super(p, id);
	}

	public void setType(String t) {
		type = unquote(t);
	}

	public String getType() {
		return type;
	}

	public void addEnum(YANG_Enum e) {
		enums.add(e);
		bracked = true;
	}

	public Vector<YANG_Enum> getEnums() {
		return enums;
	}

	public void setLeafRef(YANG_LeafRefSpecification k) {
		keyref = k;
		bracked = true;
	}

	public YANG_LeafRefSpecification getLeafRef() {
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

	public void checkTypeSyntax(YangContext context) throws YangParserException {

		if (YangBuiltInTypes.union.compareTo(getType()) == 0)
			if (getUnionSpec() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":union type must have at least one type");

		if (YangBuiltInTypes.leafref.compareTo(getType()) == 0)
			if (getLeafRef() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":keyref type must have at one path");

		if (YangBuiltInTypes.enumeration.compareTo(getType()) == 0) {
			if (getEnums().size() == 0)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":enumeration must have at least one enum");

			checkEnum(context);
		}

		if (YangBuiltInTypes.bits.compareTo(getType()) == 0) {
			if (getBitSpec() == null)
				throw new YangParserException("@" + getLine() + "." + getCol()
						+ ":bits  must have at least one bit");
			checkBits();
		}

	}

	public void checkTypeSyntax2(YangContext c) {

		if (YangBuiltInTypes.union.compareTo(getType()) == 0)
			if (getUnionSpec() == null)
				System.err.println("@" + getLine() + "." + getCol()
						+ ":union type must have at least one type");

		if (YangBuiltInTypes.leafref.compareTo(getType()) == 0)
			if (getLeafRef() == null)
				System.err.println("@" + getLine() + "." + getCol()
						+ ":keyref type must have at one path");

		if (YangBuiltInTypes.enumeration.compareTo(getType()) == 0) {
			if (getEnums().size() == 0)
				System.err.println("@" + getLine() + "." + getCol()
						+ ":enumeration must have at least one enum");
			try {
				checkEnum(c);
			} catch (YangParserException e) {
				System.err.println(e.getMessage());
			}
		}

		if (YangBuiltInTypes.bits.compareTo(getType()) == 0) {
			if (getBitSpec() == null)
				System.err.println("@" + getLine() + "." + getCol()
						+ ":bits  must have at least one bit");
			try {
				checkBits();
			} catch (YangParserException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	public void check(YangContext context) throws YangParserException {

		checkTypeSyntax(context);

		if (context.getBuiltInType(this) == null) {
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":type " + getType() + " is not defined");
		}
		typedef = context.getTypeDef(this);

		if (YangBuiltInTypes.isNumber(context.getBuiltInType(this))) {
			if (getBitSpec() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"bit specification", getType());

			if (getEnums().size() != 0)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"enum specification", getType());
			if (getLeafRef() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"key reference specification", getType());
			if (getStringRest() != null) {
				YANG_StringRestriction ysr = getStringRest();
				if (ysr.getLength() != null)
					YangErrorManager.tadd(filename, getStringRest().getLine(),
							getStringRest().getCol(), "not_alw",
							"restriction length", getType());
				if (ysr.getPatterns().size() != 0)
					YangErrorManager.tadd(filename, getStringRest().getLine(),
							getStringRest().getCol(), "not_alw",
							"restriction pattern", getType());
			}

			if (getUnionSpec() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"union specification", getType());

			checkRange(context);
		} else if (YangBuiltInTypes.string.compareTo(context
				.getBuiltInType(this)) == 0) {
			if (getBitSpec() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"bit specification", getType());
			if (getEnums().size() != 0)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"enum specification", getType());
			if (getLeafRef() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"key reference specification", getType());
			if (getUnionSpec() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"union specification", getType());
			if (getNumRest() != null)
				YangErrorManager.tadd(filename, getLine(), getCol(), "not_alw",
						"numerical restriction", getType());

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
			if (getLeafRef() != null)
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
			if (getLeafRef() != null)
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
			if (getLeafRef() != null)
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
			if (getLeafRef() != null)
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
		} else if (YangBuiltInTypes.leafref.compareTo(context
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
			if (getLeafRef() != null)
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
			if (getLeafRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":union type can not have key reference specification");

			checkEmptyUnion(context, new Vector<YANG_Type>(), getUnionSpec()
					.getTypes());

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
			if (getLeafRef() != null)
				throw new YangParserException(
						"@"
								+ getLine()
								+ "."
								+ getCol()
								+ ":instance-identifier type can not have key reference specification");
		}
		checked = true;
	}

	private void checkBits() throws YangParserException {
		if (getBitSpec() == null)
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":bits must have at least one bit");
		YANG_Bit bit = null;
		YANG_BitSpecification bs = getBitSpec();
		YANG_Bit[] bits = new YANG_Bit[bs.getBits().size()];
		BigInteger highest = new BigInteger("0");
		Vector<BigInteger> bitspos = new Vector<BigInteger>();
		String[] bitnames = new String[bs.getBits().size()];

		int i = 0;
		for (Enumeration<YANG_Bit> eb = bs.getBits().elements(); eb
				.hasMoreElements();) {
			bit = eb.nextElement();
			bits[i] = bit;
			bitnames[i++] = bit.getBit();
			if (bit.getPosition() == null) {
				if (highest.compareTo(YangBuiltInTypes.uint32ub
						.add(new BigInteger("1"))) == 0)
					throw new YangParserException("@" + bit.getLine() + "."
							+ bit.getCol()
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
					throw new YangParserException("@" + bit.getLine() + "."
							+ bit.getCol() + ":bit position is not an integer");
				}
				if (biginteger.compareTo(YangBuiltInTypes.uint32ub) == 1)
					throw new YangParserException("@" + bit.getLine() + "."
							+ bit.getCol() + ":" + biginteger
							+ " is a too much higher position");
				if (biginteger.compareTo(highest) > 0) {
					highest = biginteger;
					bitspos.add(highest);
					highest = highest.add(new BigInteger("1"));
				} else if (biginteger.add(new BigInteger("1")).compareTo(
						highest) == 0) {
					System.out.println(bits.length);
					YangErrorManager.tadd(filename,
							bit.getPosition().getLine(), bit.getPosition()
									.getCol(), "dup_value", "position",
							biginteger, "bit", bits[biginteger.subtract(
									new BigInteger("2")).intValue()]
									.getFileName()
									+ ":"
									+ bits[biginteger.subtract(
											new BigInteger("2")).intValue()]
											.getLine());
					return;
				} else if (biginteger.compareTo(highest) == -1) {
					bitspos.add(biginteger);
				}
			}
		}

		/*
		 * boolean duplicate = false; int position = 0; for
		 * (Enumeration<BigInteger> eb = bitspos.elements(); eb
		 * .hasMoreElements() && !duplicate;) { position = 0; BigInteger bi =
		 * eb.nextElement(); for (Enumeration<BigInteger> eb2 =
		 * bitspos.elements(); eb2 .hasMoreElements() && !duplicate;) {
		 * BigInteger bi2 = eb2.nextElement(); duplicate = (bi.compareTo(bi2) ==
		 * 0 && bi != bi2); position++; } } if (duplicate)
		 * YangErrorManager.add(filename, getLine(), getCol(), MessageFormat
		 * .format(YangErrorManager.messages.getString("dup_value"), "position",
		 * position, "bit", bits[position - 1] .getPosition().getFileName() +
		 * ":" + bits[position - 1].getPosition() .getLine()));
		 * 
		 * for (int j = 0; j < bitnames.length && !duplicate; j++) for (int k =
		 * j + 1; k < bitnames.length && !duplicate; k++) { duplicate =
		 * bitnames[j].compareTo(bitnames[k]) == 0; } if (duplicate)
		 * YangErrorManager.add(filename, getLine(), getCol(), MessageFormat
		 * .format(YangErrorManager.messages.getString("dup_value"), "position",
		 * "trailing whitespace")); throw new YangParserException("@" +
		 * getLine() + "." + getCol() + ":duplicate bit name");
		 */
	}

	private void checkEnum(YangContext context) throws YangParserException {
		int highest = 0;
		int[] enumvalues = new int[getEnums().size()];
		YANG_Enum[] tenums = new YANG_Enum[getEnums().size()];
		String[] enumnames = new String[getEnums().size()];
		int i = 0;
		for (Enumeration<YANG_Enum> ee = getEnums().elements(); ee
				.hasMoreElements();) {
			YANG_Enum yenum = ee.nextElement();
			enumnames[i] = yenum.getEnum();
			tenums[i] = yenum;
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
					YangErrorManager.tadd(filename, yenum.getLine(), yenum
							.getCol(), "enum_expr", strenum);
					return;
				}
				if (integer.compareTo(new Integer(highest)) >= 0) {
					highest = integer.intValue();
					enumvalues[i++] = highest++;
				} else if (integer.compareTo(new Integer(highest)) == -1) {
					enumvalues[i++] = integer.intValue();
				} else if (integer.compareTo(new Integer(highest)) == 0) {
					throw new YangParserException("@" + yenum.getLine() + "."
							+ yenum.getCol() + highest
							+ ":ambigous; a value must be specified");
				}
			}
		}
		boolean duplicate = false;
		int dupvalue = 0;
		String dupname = "";
		YANG_Enum dupenum = null;
		YANG_Enum firstenum = null;
		for (int j = 0; j < enumvalues.length && !duplicate; j++)
			for (int k = j + 1; k < enumvalues.length && !duplicate; k++) {
				if (enumvalues[j] == enumvalues[k]) {
					duplicate = true;
					dupvalue = enumvalues[j];
					dupenum = tenums[j].getLine() > tenums[k].getLine() ? tenums[j]
							: tenums[k];
					firstenum = tenums[j].getLine() <= tenums[k].getLine() ? tenums[j]
							: tenums[k];
				}
			}
		if (duplicate) {
			YangErrorManager.tadd(filename, dupenum.getValue().getLine(),
					dupenum.getValue().getCol(), "dupp_enum_val", dupvalue,
					getFileName() + ":" + firstenum.getLine());
			// return;
		}
		duplicate = false;
		for (int j = 0; j < enumnames.length && !duplicate; j++)
			for (int k = j + 1; k < enumnames.length && !duplicate; k++) {
				if (YangBuiltInTypes.removeQuotesAndTrim(enumnames[j])
						.compareTo(
								YangBuiltInTypes
										.removeQuotesAndTrim(enumnames[k])) == 0) {
					duplicate = true;
					dupname = enumnames[j];
					dupvalue = enumvalues[j];
					dupenum = tenums[j].getLine() > tenums[k].getLine() ? tenums[j]
							: tenums[k];
					firstenum = tenums[j].getLine() <= tenums[k].getLine() ? tenums[j]
							: tenums[k];
				}

			}
		if (duplicate) {
			YangErrorManager.tadd(filename, dupenum.getLine(),
					dupenum.getCol(), "dupp_enum_name", dupname, getFileName()
							+ ":" + firstenum.getLine());
			return;
		}

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

	/**
	 * Get the first type with a pattern restriction from the type used by the
	 * given typedef and to the bases typedef until a built-in type
	 * 
	 * @param context
	 * @param td
	 *            the typedef
	 * @return the first type with a pattern restriction or null either if the
	 *         typedef has a length restriction or a built-in type is reached.
	 * @throws YangParserException
	 */
	private YANG_Type getFirstPatternDefined(YangContext context,
			YANG_TypeDef td) throws YangParserException {

		YANG_Type basetype = td.getType();
		YANG_TypeDef typedef = null;
		YANG_TypeDef oldtd = null;
		boolean end = false;

		if (basetype.getStringRest() != null) {
			if (basetype.getStringRest().getPatterns().size() != 0)
				return basetype;
		}
		oldtd = td;
		while (!end) {
			typedef = context.getBaseTypeDef(oldtd);
			if (typedef != null) {
				if (typedef.getType().getStringRest() != null)
					if (typedef.getType().getStringRest().getPatterns().size() != 0)
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
			// else if (YangBuiltInTypes.float32.compareTo(context
			// .getBuiltInType(this)) == 0)
			// return ("-INF");
			else if (YangBuiltInTypes.decimal64.compareTo(context
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
			// else if (YangBuiltInTypes.float32.compareTo(context
			// .getBuiltInType(this)) == 0)
			// return ("INF");
			else if (YangBuiltInTypes.decimal64.compareTo(context
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
			// } else if (YangBuiltInTypes.float32.compareTo(getType()) == 0) {
			// birange[0][0] = "-INF";
			// birange[0][1] = "INF";
		} else if (YangBuiltInTypes.decimal64.compareTo(getType()) == 0) {
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

	public void checkDefaultValue(YangContext context, YangNode usernode,
			YANG_Default ydefault) throws YangParserException {
		String value = YangBuiltInTypes.removeQuotesAndTrim(ydefault
				.getDefault());

		if (YangBuiltInTypes.isNumber(context.getBuiltInType(this))) {
			String[][] ranges = null;
			YANG_NumericalRestriction numrest = null;

			if (getNumRest() != null) {
				ranges = getRanges(context);
				numrest = getNumRest();
			} else {
				YANG_TypeDef td = context.getTypeDef(this);
				if (td != null) {
					YANG_Type bt = getFirstRangeDefined(context, td);
					numrest = bt.getNumRest();
					if (bt != null)
						ranges = bt.getRanges(context);
					else
						ranges = getRanges(context);
				} else {
					ranges = getRanges(context);
				}
			}

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
						if (!inside) {
							YANG_Type t = this;
							if (getFirstRangeDefined(context, context
									.getBaseType(this)) != null)
								t = getFirstRangeDefined(context, context
										.getBaseType(this));
							String message = "";
							if (t == this) {
								message = "direct_default_match_fail";
								YangErrorManager.tadd(filename, numrest
										.getLine(), numrest.getCol(), message,
										YangBuiltInTypes.removeQuotes(value),
										"range error", "range", context
												.getTypeDef(this).getFileName()
												+ ":"
												+ context.getTypeDef(this)
														.getLine());
							} else {
								message = "default_match_fail";
								YANG_TypeDef td = this.getTypedef();
								YangErrorManager.tadd(filename, numrest
										.getLine(), numrest.getCol(), message,
										YangBuiltInTypes.removeQuotes(value),
										td.getFileName() + ":" + td.getLine(),
										"range error", "range", getFileName()
												+ ":"
												+ context.getTypeDef(this)
														.getLine());
							}

						}

					} catch (NumberFormatException ne) {// ne.printStackTrace();
						// throw new
						// YangParserException(" illegal integer value : " +
						// this);
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
						if (!inside) {
							YANG_Type t = this;
							if (getFirstRangeDefined(context, context
									.getTypeDef(this)) != this)
								t = getFirstRangeDefined(context, context
										.getTypeDef(this));
							String message = "";
							if (t == this) {
								YangErrorManager.tadd(filename, numrest
										.getLine(), numrest.getCol(),
										"direct_default_match_fail",
										YangBuiltInTypes.removeQuotes(value),
										"range error", "range", this
												.getFileName()
												+ ":" + getLine());
							} else {
								YANG_TypeDef td = t.getTypedef();
								YangErrorManager.tadd(filename, numrest
										.getLine(), numrest.getCol(),
										"default_match_fail", YangBuiltInTypes
												.removeQuotes(value), td
												.getFileName()
												+ ":" + td.getLine(),
										"range error", "range", this
												.getFileName()
												+ ":" + getLine());
							}
						}
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
			value = YangBuiltInTypes.removeQuotes(ydefault.getDefault());

			String[][] ranges = null;
			boolean isStringRestricted = false;
			boolean isPatternRestricted = false;

			Enumeration<YANG_Pattern> patterns = null;

			if (getStringRest() != null) {
				if (getStringRest().getLength() != null)
					isStringRestricted = true;
				else
					isStringRestricted = false;

				patterns = getStringRest().getPatterns().elements();

			} else
				isStringRestricted = false;

			if (isStringRestricted) {
				ranges = getLength(context);
			} else {
				YANG_TypeDef td = context.getTypeDef(this);
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
				YangErrorManager.tadd(filename, ydefault.getLine(), ydefault
						.getCol(), "direct_default_match_fail",
						YangBuiltInTypes.removeQuotes(value), "length error",
						"length", this.getFileName() + ":"
								+ getStringRest().getLine());

			boolean direct = true;
			YANG_TypeDef indirectTd = null;
			if (patterns != null) {
				if (getStringRest().getPatterns().size() == 0) {
					YANG_TypeDef td = context.getTypeDef(this);
					if (td != null) {
						YANG_Type bt = getFirstPatternDefined(context, td);
						if (bt != null)
							patterns = bt.getStringRest().getPatterns()
									.elements();
					}

				}
			} else {
				direct = false;
				indirectTd = context.getTypeDef(this);
				if (indirectTd != null) {
					YANG_Type bt = getFirstPatternDefined(context, indirectTd);
					if (bt != null)
						patterns = bt.getStringRest().getPatterns().elements();
				}

			}

			if (patterns != null)
				while (patterns.hasMoreElements()) {
					YANG_Pattern pattern = patterns.nextElement();
					if (!pattern.checkExp(value)) {
						if (direct) {
							YangErrorManager.tadd(filename, ydefault.getLine(),
									ydefault.getCol(),
									"direct_default_match_fail",
									YangBuiltInTypes.removeQuotes(value),
									"pattern mismatch", "pattern", this
											.getFileName()
											+ ":" + pattern.getLine());
						} else {
							YangErrorManager.tadd(filename, ydefault.getLine(),
									ydefault.getCol(), "default_match_fail",
									YangBuiltInTypes.removeQuotes(value),
									indirectTd.getFileName() + ":"
											+ indirectTd.getLine(),
									"pattern mismatch", "pattern", this
											.getFileName()
											+ ":" + pattern.getLine());
						}

					}
				}

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
					type.checkDefaultValue(context, ut, ydefault);
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

	private void checkEmptyUnion(YangContext context, Vector<YANG_Type> chain,
			Vector<YANG_Type> unions) throws YangParserException {
		for (Enumeration<YANG_Type> et = unions.elements(); et
				.hasMoreElements();) {
			boolean empty = false;
			YANG_Type utype = et.nextElement();
			empty = checkRecEmptyUnion(context, chain, utype);
			if (empty) {
				if (context.getTypeDef(utype) != null) {
					YANG_TypeDef td = context.getTypeDef(utype);
					YangErrorManager.tadd(utype.getFileName(), utype.getLine(), utype
							.getCol(), "empty_union", td.getFileName(), td
							.getLine());
				} else {
					YangErrorManager.tadd(utype.getFileName(), utype.getLine(),
							utype.getCol(), "empty_union", utype.getFileName(),
							utype.getLine());
				}
			}
			
		}
	}

	private boolean checkRecEmptyUnion(YangContext context,
			Vector<YANG_Type> chain, YANG_Type utype)
			throws YangParserException {

		boolean empty = false;
		if (context.getBuiltInType(utype).compareTo(YangBuiltInTypes.empty) == 0) {
			return true;
		} else if (context.getBuiltInType(utype).compareTo(
				YangBuiltInTypes.union) == 0) {
			if (utype.getUnionSpec() != null) {
				chain.add(utype);
				for (YANG_Type t : utype.getUnionSpec().getTypes())
					empty = empty || checkRecEmptyUnion(context, chain, t);
			} else {
				while (utype.getUnionSpec() == null) {
					YANG_TypeDef suptype = context.getTypeDef(utype);
					// if (!suptype.isCorrect())
					// return;
					utype = suptype.getType();
				}
				if (!chain.contains(utype)) {
					chain.add(utype);
					for (YANG_Type t : utype.getUnionSpec().getTypes())
						empty = empty || checkRecEmptyUnion(context, chain, t);
				}
			}

		}
		return empty;

	}

	public String toString() {
		String result = "";
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
