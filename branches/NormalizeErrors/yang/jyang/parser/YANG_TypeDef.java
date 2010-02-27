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
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public YANG_TypeDef(int id) {
		super(id);
	}

	public YANG_TypeDef(yang p, int id) {
		super(p, id);
	}

	public void setTypedef(String t) {
		typedef = unquote(t);
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
			YangErrorManager.tadd(filename, t.getLine(), t.getCol(), "unex_kw",
					"type");
	}

	public YANG_Type getType() {
		return ytype;
	}

	public void setUnits(YANG_Units u) {
		if (!b_units) {
			b_units = true;
			units = u;
		} else
			YangErrorManager.tadd(filename, u.getLine(), u.getCol(), "units");
	}

	public YANG_Units getUnits() {
		return units;
	}

	public void setDefault(YANG_Default d) {
		if (!b_default) {
			b_default = true;
			defaultstr = d;
		} else
			YangErrorManager.tadd(filename, d.getLine(), d.getCol(), "default");
	}

	public YANG_Default getDefault() {
		return defaultstr;
	}

	public void check(YangContext context) throws YangParserException {

		if (!isCorrect())
			return;

		if (!b_type) {
			YangErrorManager.tadd(context.getSpec().getName(), getLine(),
					getCol(), "type_expec");
			return;
		}

		getType().check(context);

		Vector<YANG_TypeDef> me = new Vector<YANG_TypeDef>();
		chainUnions(this, me, context);

		if (b_default)
			try {
				getType().checkDefaultValue(context, this, getDefault());
			} catch (YangParserException ye) {
			}

		else {
			YANG_TypeDef defining = context.getBaseTypeDef(this);

			while (defining != null) {
				if (defining.getDefault() != null) {
					try {
						getType().checkDefaultValue(context, this,
								defining.getDefault());
						defining = context.getBaseTypeDef(defining);
					} catch (YangParserException ye) {
					}
				} else
					defining = context.getBaseTypeDef(defining);
			}
		}

		checked = true;

	}

	private void chainUnions(YANG_TypeDef zis, Vector<YANG_TypeDef> tds,
			YangContext context) throws YangParserException {
		if (zis == null)
			return;
		if (tds.contains(zis)) {
			setCorrect(false);
			for (YANG_TypeDef ytd : tds)
				ytd.setCorrect(false);
			YangErrorManager.tadd(filename, getLine(), getCol(), "circ_dep",
					zis.getBody());
			return;
		}
		String builtin = context.getBuiltInType(zis.getType());
		if (builtin != null)
			if (YangBuiltInTypes.union.compareTo(builtin) == 0) {
				YANG_Type ut = zis.getType();
				if (ut.getUnionSpec() != null) {
					for (YANG_Type utt : ut.getUnionSpec().getTypes()) {
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
			ctd.setFileName(filename);
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
