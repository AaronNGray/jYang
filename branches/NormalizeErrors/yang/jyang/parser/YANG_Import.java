package jyang.parser;


public class YANG_Import extends SimpleNode implements YANG_Linkage {

	private String importstr = null;
	private YANG_Prefix prefix = null;
	private YANG_Revision revision = null;
	
	private boolean b_prefix = false, b_revision = false;

	public YANG_Import(int id) {
		super(id);
	}

	public YANG_Import(yang p, int id) {
		super(p, id);
	}

	public void setIdentifier(String s) {
		importstr = s;
	}

	public String getImportedModule() {
		return importstr;
	}

	public void setPrefix(YANG_Prefix p)  throws YangParserException {
		if (b_prefix)
			throw new YangParserException(
					"Prefix is already defined in import " + importstr, p
							.getLine(), p.getCol());
		prefix = p;
		b_prefix = true;
	}

	public YANG_Prefix getPrefix() {
		return prefix;
	}

	public String toString() {
		String result =  " import " + importstr + " {" + prefix.toString();
		if (b_revision)
			result += "\n" + revision.toString();
		result += "}";
		return result;
		
	}

	public YANG_Revision getRevision() {
		return revision;
	}

	public void setRevision(YANG_Revision r)  throws YangParserException  {
		if (b_revision)
			throw new YangParserException(
					"Revision is already defined in import " + importstr, r
							.getLine(), r.getCol());
		this.revision = r;
		b_revision = true;
	}

}
