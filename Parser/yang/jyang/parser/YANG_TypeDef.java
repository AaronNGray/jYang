package jyang.parser;

import java.util.Enumeration;
import java.util.Vector;

public class YANG_TypeDef extends YANG_Body {

	private String typedef = null;
	private YANG_Type ytype = null;
	private YANG_Units units = null;
	private YANG_Default defaultstr = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;

	private boolean b_type = false, b_units = false, b_default = false,
			b_status = false, b_description = false, b_reference = false;
	
	private boolean used = false, correct = true;

	public YANG_TypeDef(int id) {
		super(id);
	}

	public YANG_TypeDef(yang p, int id) {
		super(p, id);
	}

	public void setTypedef(String t) {
		typedef = t;
	}

	public String getBody() {
		return getTypeDef();
	}

	public String getTypeDef() {
		return typedef;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (b_type)
			throw new YangParserException("Type already defined in typedef "
					+ typedef, t.getLine(), t.getCol());
		b_type = true;
		ytype = t;
	}

	public YANG_Type getType() {
		return ytype;
	}

	public void setUnits(YANG_Units u) throws YangParserException {
		if (b_units)
			throw new YangParserException("Units already defined in typedef "
					+ typedef, u.getLine(), u.getCol());
		b_units = true;
		units = u;
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException("Status already defined in typedef "
					+ typedef, s.getLine(), s.getCol());
		b_status = true;
		status = s;
	}

	public YANG_Status getStatus() {
		return status;
	}

	public void setDescription(YANG_Description d) throws YangParserException {
		if (b_description)
			throw new YangParserException(
					"Description already defined in typedef " + typedef, d
							.getLine(), d.getCol());
		b_description = true;
		description = d;
	}

	public YANG_Description getDescription() {
		return description;
	}

	public void setDefault(YANG_Default d) throws YangParserException {
		if (b_default)
			throw new YangParserException("Default already defined in typedef "
					+ typedef, d.getLine(), d.getCol());
		b_default = true;
		defaultstr = d;
	}

	public YANG_Default getDefault() {
		return defaultstr;
	}

	public void setReference(YANG_Reference r) throws YangParserException {
		if (b_reference)
			throw new YangParserException(
					"Reference already defined in typedef " + typedef, r
							.getLine(), r.getCol());
		b_reference = true;
		reference = r;
	}

	public YANG_Reference getReference() {
		return reference;
	}

	public void check(YangContext context) throws YangParserException {
		
		if (!isCorrect())
			return;
		
		if (!b_type)
			throw new YangParserException("Type statement expected in typedef "
					+ typedef, getLine(), getCol());

		
		getType().check(context);

		Vector<YANG_TypeDef> me = new Vector<YANG_TypeDef>();
		chainUnions(this, me, context);

		if (b_default)
			getDefault().check(context, getType());

		else {
			YANG_TypeDef defining = context.getBaseTypeDef(this);

			while (defining != null) {
				if (defining.getDefault() != null) {
					try {
						getType().checkValue(context,
								defining.getDefault().getDefault());
						defining = null;
					} catch (YangParserException ye) {
						throw new YangParserException("@" + getLine() + "."
								+ getCol() + ":default value "
								+ defining.getDefault().getDefault()
								+ " does no more match with current typedef "
								+ getTypeDef());
					}
				} else
					defining = context.getBaseTypeDef(defining);
			}
		}

	}

	private void chainUnions(YANG_TypeDef zis, Vector<YANG_TypeDef> tds,
			YangContext context) throws YangParserException {
		if (zis == null)
			return;
		if (tds.contains(zis)){
			setCorrect(false);
			throw new YangParserException("@" + getLine() + "." + getCol()
					+ ":recursive union from " + zis.getTypeDef());
		}

		if (YangBuiltInTypes.union.compareTo(context.getBuiltInType(zis
				.getType())) == 0) {
			YANG_Type ut = zis.getType();
			if (ut.getUnionSpec() != null) {
				for (Enumeration<YANG_Type> eus = ut.getUnionSpec().getTypes()
						.elements(); eus.hasMoreElements();) {
					YANG_Type utt = eus.nextElement();
					tds.add(zis);
					chainUnions(context.getTypeDef(utt), tds, context);
				}
			}
		}
	}

	public String toString() {
		String result = new String();
		result += "typedef " + typedef + "{\n";
		if (ytype != null)
			result += ytype.toString() + "\n";
		if (units != null)
			result += units.toString() + "\n";
		if (defaultstr != null)
			result += defaultstr.toString() + "\n";
		if (status != null)
			result += status.toString() + "\n";
		if (description != null)
			result += description.toString() + "\n";
		if (reference != null)
			result += reference.toString() + "\n";
		result += "}";
		return result;
	}

	public YANG_TypeDef clone() {
		YANG_TypeDef ctd = new YANG_TypeDef(parser, id);
		try {
			ctd.setType(getType());
		} catch (YangParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ctd;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}

}
