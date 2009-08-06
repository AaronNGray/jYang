package yangTree.attributes;

import java.io.Serializable;

import jyang.parser.YANG_Type;
import jyang.parser.YANG_TypeDef;

public class LeafType implements Serializable{
	
	private boolean isBuiltInType ;
	
	private BuiltinType type;
	
	private String name ;
	private String units = null;
	private String defaultValue = null;
	private String description = null;
	
	public LeafType(YANG_TypeDef typeDef){
		this.isBuiltInType = false;
		this.type = BuiltinType.buildType(typeDef.getType());
		this.name = typeDef.getBody();
		if (typeDef.getUnits()!=null) this.units = typeDef.getUnits().getUnits();
		if (typeDef.getDefault()!=null) this.defaultValue = typeDef.getDefault().getDefault();
		if (typeDef.getDescription()!=null) this.description = typeDef.getDescription().getDescription();
	}
	
	public LeafType(YANG_Type ytype){
		this.isBuiltInType = true;
		this.type = BuiltinType.buildType(ytype);
		this.name = type.getName();
	}

	public boolean isBuiltInType() {
		return isBuiltInType;
	}

	public BuiltinType getBuiltinType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDescription() {
		return description;
	}
	
}
