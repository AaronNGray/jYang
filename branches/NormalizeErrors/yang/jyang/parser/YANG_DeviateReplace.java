package jyang.parser;

import java.text.MessageFormat;
import java.util.Enumeration;

public class YANG_DeviateReplace extends DeviateAddReplace {

	private YANG_Type type = null;
	private boolean b_type = false;

	public YANG_DeviateReplace(int id) {
		super(id);
	}

	public YANG_DeviateReplace(yang p, int id) {
		super(p, id);
	}

	public YANG_Type getType() {
		return type;
	}

	public void setType(YANG_Type t) throws YangParserException {
		if (!b_type) {
			this.type = t;
			b_type = true;
		} else
			YangErrorManager.tadd(filename, t.getLine(), t.getCol(), "unex_kw",
					"type");
	}

	public boolean isBracked() {
		return b_type || super.isBracked();
	}

	public void deviates(YangTreeNode deviated) {

		YangContext context = deviated.getNode().getContext();
		if (getType() != null) {
			if (deviated.getNode() instanceof YANG_Leaf){
				YANG_Leaf leaf = (YANG_Leaf) deviated.getNode();
				leaf.deleteType();
				leaf.setType(getType());
				try {
					leaf.check(context);
				} catch (YangParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (deviated.getNode()instanceof YANG_LeafList){
				YANG_LeafList leaflist = (YANG_LeafList) deviated.getNode();
				leaflist.deleteType();
				leaflist.setType(getType());
				try {
					leaflist.check(context);
				} catch (YangParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else 
				YangErrorManager.tadd(getFileName(), getType().getLine(),
						getType().getCol(), "bad_deviate_rep", "type",
						deviated.getNode().getBody(), deviated.getNode()
								.getFileName(), deviated.getNode().getLine());
		}
		if (getDefault() != null) {
			if (deviated.getNode() instanceof YANG_Leaf) {
				YANG_Leaf leaf = (YANG_Leaf) deviated.getNode();
				if (leaf.getDefault() != null) {
					leaf.deleteDefault();
					leaf.setDefault(getDefault());
					try {
						leaf.check(context);
					} catch (YangParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
					YangErrorManager.tadd(getFileName(),
							getDefault().getLine(), getDefault().getCol(),
							"deviate_rep", "default", leaf.getBody(), leaf
									.getFileName(), leaf.getLine());
			} else if (deviated.getNode() instanceof YANG_Choice) {
				YANG_Choice choice = (YANG_Choice) deviated.getNode();
				if (choice.getDefault() != null) {
					choice.deleteDefault();
					choice.setDefault(getDefault());
					choice.check(context);
				} else
					YangErrorManager.tadd(getFileName(),
							getDefault().getLine(), getDefault().getCol(),
							"deviate_rep", "default", choice.getBody(), choice
									.getFileName(), choice.getLine());
			} else
				YangErrorManager.tadd(getFileName(), getDefault().getLine(),
						getDefault().getCol(), "bad_deviate_rep", "default",
						deviated.getNode().getBody(), deviated.getNode()
								.getFileName(), deviated.getNode().getLine());
		}
		if (getMandatory() != null) {
			if (deviated.getNode() instanceof YANG_Leaf) {
				YANG_Leaf leaf = (YANG_Leaf) deviated.getNode();
				if (leaf.getMandatory() != null) {
					leaf.deleteMandatory();
					leaf.setMandatory(getMandatory());
				} else
					YangErrorManager.tadd(getFileName(), getMandatory()
							.getLine(), getMandatory().getCol(), "deviate_rep",
							"mandatory", leaf.getBody(), leaf.getFileName(),
							leaf.getLine());
			} else if (deviated.getNode() instanceof YANG_AnyXml) {
				YANG_AnyXml anyxml = (YANG_AnyXml) deviated.getNode();
				if (anyxml.getMandatory() != null) {
					anyxml.deleteMandatory();
					anyxml.setMandatory(getMandatory());
				} else
					YangErrorManager.tadd(getFileName(), getMandatory()
							.getLine(), getMandatory().getCol(), "deviate_rep",
							"mandatory", anyxml.getBody(),
							anyxml.getFileName(), anyxml.getLine());
			} else
				YangErrorManager.tadd(getFileName(), getMandatory().getLine(),
						getMandatory().getCol(), "bad_deviate_rep",
						"mandatory", deviated.getNode().getBody(), deviated
								.getNode().getFileName(), deviated.getNode()
								.getLine());
		}
		if (getMinElement() != null) {
			if (deviated.getNode() instanceof ListedDataDef) {
				ListedDataDef lddef = (ListedDataDef) deviated.getNode();
				if (lddef.getMinElement() != null) {
					lddef.deleteMinElement();
					lddef.setMinElement(getMinElement());
				} else
					YangErrorManager.tadd(getFileName(), getMinElement()
							.getLine(), getMinElement().getCol(),
							"deviate_rep", "mandatory", lddef.getBody(), lddef
									.getFileName(), lddef.getLine());
			} else
				YangErrorManager.tadd(getFileName(), getMinElement().getLine(),
						getMinElement().getCol(), "bad_deviate_rep",
						"min-element", deviated.getNode().getBody(), deviated
								.getNode().getFileName(), deviated.getNode()
								.getLine());
		}
		if (getMaxElement() != null) {
			if (deviated.getNode() instanceof ListedDataDef) {
				ListedDataDef lddef = (ListedDataDef) deviated.getNode();
				if (lddef.getMaxElement() != null) {
					lddef.deleteMaxElement();
					lddef.setMaxElement(getMaxElement());
				} else
					YangErrorManager.tadd(getFileName(), getMaxElement()
							.getLine(), getMaxElement().getCol(),
							"deviate_rep", "mandatory", lddef.getBody(), lddef
									.getFileName(), lddef.getLine());
			} else
				YangErrorManager.tadd(getFileName(), getMaxElement().getLine(),
						getMaxElement().getCol(), "bad_deviate_rep",
						"max-element", deviated.getNode().getBody(), deviated
								.getNode().getFileName(), deviated.getNode()
								.getLine());
		}
		if (getUnits() != null) {
			if (deviated.getNode() instanceof YANG_Leaf) {
				YANG_Leaf leaf = (YANG_Leaf) deviated.getNode();
				if (leaf.getUnits() != null) {
					leaf.deleteUnits();
					leaf.setUnits(getUnits());
				} else
					YangErrorManager.tadd(getFileName(), getUnits().getLine(),
							getUnits().getCol(), "deviate_rep", "units", leaf
									.getBody(), leaf.getFileName(), leaf
									.getLine());
			} else if (deviated.getNode() instanceof YANG_LeafList) {
				YANG_LeafList leaflist = (YANG_LeafList) deviated.getNode();
				if (leaflist.getUnits() != null) {
					leaflist.deleteUnits();
					leaflist.setUnits(getUnits());
				} else
					YangErrorManager.tadd(getFileName(), getUnits().getLine(),
							getUnits().getCol(), "deviate_rep", "units",
							leaflist.getBody(), leaflist.getFileName(),
							leaflist.getLine());

			} else
				YangErrorManager.tadd(getFileName(), getUnits().getLine(),
						getUnits().getCol(), "bad_deviate_rep", "units",
						deviated.getNode().getBody(), deviated.getNode()
								.getFileName(), deviated.getNode().getLine());
		}
		if (getConfig() != null) {
			if (deviated.getNode() instanceof ConfigDataDef) {
				ConfigDataDef cddef = (ConfigDataDef) deviated.getNode();
				if (cddef.getConfig() != null) {
					cddef.deleteConfig();
					cddef.setConfig(getConfig());
				} else
					YangErrorManager.tadd(getFileName(), getConfig().getLine(),
							getConfig().getCol(), "deviate_rep", "config",
							cddef.getBody(), cddef.getFileName(), cddef
									.getLine());
			} else
				YangErrorManager.tadd(getFileName(), getConfig().getLine(),
						getConfig().getCol(), "bad_deviate_rep", "config",
						deviated.getNode().getBody(), deviated.getNode()
								.getFileName(), deviated.getNode().getLine());
		}

	}

	public String toString() {
		String result = "deviate replace";
		if (isBracked()) {
			result += "{\n";
			if (b_type)
				result += type.toString() + "\n";
			result += super.toString() + "\n";
			result += "}";
		} else
			result += ";";
		return result;
	}

}
