/* Generated By:JJTree: Do not edit this line. YANG_Unknown.java */
import java.util.*;

public class YANG_Unknown extends YANG_Body {

	private String prefix = null;
	private String extension = null;
	private String argument = null;

	private boolean bracked = false;

	public YANG_Unknown(int id) {
		super(id);
	}

	public YANG_Unknown(yang p, int id) {
		super(p, id);
	}

	public void setExtension(String p) {
		extension = p;
	}

	public String getExtension() {
		return extension;
	}

	public String getBody() {
		return getExtension();
	}

	public void setArgument(String a) {
		argument = a;
	}

	public String getArgument() {
		return argument;
	}

	public void setPrefix(String p) {
		prefix = p;
	}

	public String getPrefix() {
		return prefix;
	}

	public void check(YangContext context) throws YangParserException {
		if (!context.isExtensionDefined((YANG_Unknown) this)) {

			System.err.println(context.getSpec().getName() + "@" + getLine()
					+ "." + getCol() + ":extension " + prefix + ":" + extension
					+ " not found");
		} else {
			YANG_Extension extension = context
					.getExtension((YANG_Unknown) this);
			if (extension.getArgument() != null){
				if (getArgument() != null) {
					/*if (extension.getArgument().getArgument().compareTo(
							getArgument()) != 0)
						throw new YangParserException("@" + getLine() + "."
								+ getCol() +":extension "
								+ extension.getBody() + " defines "
								+ extension.getArgument().getArgument()
								+ " as argument and not " + getArgument());
								*/
				} else {
					throw new YangParserException("@" + getLine() + "." + getCol()
							+ ":extension " + extension.getBody()
							+ " defines " + extension.getArgument().getArgument()
							+ " as argument");
				}
			}
			else {
				if (getArgument() != null)
					throw new YangParserException("@" + getLine() + "." + getCol()
							+ ":extension " + extension.getBody()
							+ " does not defines any argument as "
							+ getArgument());
			}
		}
	}

	public String toString() {
		String result = new String();
		result += prefix + ":" + extension;
		if (argument != null)
			result += " " + argument;
		if (bracked) {
			result += "{\n";
			for (Enumeration<YANG_Unknown> eu = getUnknowns().elements(); eu
					.hasMoreElements();)
				result += eu.nextElement().toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}
}
