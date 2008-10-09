/* Generated By:JJTree: Do not edit this line. YANG_Notification.java */
import java.util.*;

public class YANG_Notification extends YANG_Body {

	private String notification = null;
	private YANG_Status status = null;
	private YANG_Description description = null;
	private YANG_Reference reference = null;
	private Vector<YANG_TypeDef> typedefs = new Vector<YANG_TypeDef>();
	private Vector<YANG_Grouping> groupings = new Vector<YANG_Grouping>();
	private Vector<YANG_DataDef> datadefs = new Vector<YANG_DataDef>();

	private boolean bracked = false;

	private boolean b_status = false, b_description = false,
			b_reference = false;

	public YANG_Notification(int id) {
		super(id);
	}

	public YANG_Notification(yang p, int id) {
		super(p, id);
	}

	public void setNotification(String n) {
		notification = n;
	}

	public String getBody() {
		return getNotification();
	}

	public String getNotification() {
		return notification;
	}

	public void setStatus(YANG_Status s) throws YangParserException {
		if (b_status)
			throw new YangParserException(
					"Status already defined in notification " + notification, s
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
					"Description already defined in notification "
							+ notification, d.getLine(), d.getCol());
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
					"Reference already defined in notification " + notification,
					r.getLine(), r.getCol());
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
	}

	public String toString() {
		String result = new String();
		result += "notification " + notification;
		if (bracked) {
			result += "{\n";
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
