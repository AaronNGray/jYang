package jyang.parser;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Vector;

public class YANG_TypeDef extends StatuedBody {

	private String typedef = null;
	private YANG_Type ytype = null;
	private YANG_Units units = null;
	private YANG_Default defaultstr = null;

	private boolean b_type = false, b_units = false, b_default = false;

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
		if (!b_type) {
			b_type = true;
			ytype = t;
		} else
			YangErrorManager.add(filename, t.getLine(), t.getCol(),
					MessageFormat.format(YangErrorManager.messages
							.getString("unex_kw"), "type"));
	}

	public YANG_Type getType() {
		return ytype;
	}

	public void setUnits(YANG_Units u) {
		if (b_units) {
			b_units = true;
			units = u;
		} else
			YangErrorManager.add(filename, u.getLine(), u.getCol(),
					YangErrorManager.messages.getString("units"));
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setDefault(YANG_Default d) {
		if (!b_default) {
			b_default = true;
			defaultstr = d;
		} else
			YangErrorManager.add(filename, d.getLine(), d.getCol(),
					YangErrorManager.messages.getString("default"));
	}

	public YANG_Default getDefault() {
		return defaultstr;
	}

	public void check(YangContext context) throws YangParserException {

		if (!isCorrect())
			return;

		if (!b_type) {
			YangErrorManager
					.add(context.getSpec().getName(), getLine(), getCol(),
							YangErrorManager.messages.getString("type_expec"));
			return;
		}

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
						YangErrorManager.add(filename, getLine(), getCol(),
								MessageFormat.format(YangErrorManager.messages
										.getString("match_fail"), defining
										.getDefault().getDefault(), defining.getBody(), ye
										.getMessage()));
						defining = null;
						//						
						// throw new YangParserException("@" + getLine() + "."
						// + getCol() + ":default value "
						// + defining.getDefault().getDefault()
						// + " does no more match with current typedef "
						// + getTypeDef());
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
		if (tds.contains(zis)) {
			setCorrect(false);
			YangErrorManager.add(filename, getLine(), getCol(), MessageFormat
					.format(YangErrorManager.messages.getString("circ_dep"),
							zis.getBody()));
			return;
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
		result += super.toString();
		result += "}";
		return result;
	}

	public YANG_TypeDef clone() {
		YANG_TypeDef ctd = new YANG_TypeDef(parser, id);
		try {
			ctd.setType(getType());
		} catch (YangParserException e) {
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
