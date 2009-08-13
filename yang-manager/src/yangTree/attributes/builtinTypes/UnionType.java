package yangTree.attributes.builtinTypes;

import java.util.LinkedList;

import applet.Util;

import jyang.parser.YANG_Type;

import yangTree.attributes.UnitValueCheck;
import yangTree.attributes.ValueCheck;
import yangTree.attributes.YangTreePath;

public class UnionType extends BuiltinType implements PathSensitiveType {
	
	private LinkedList<BuiltinType> typesList ;
	
	public UnionType(YANG_Type type){
		this.typesList = new LinkedList<BuiltinType>();
		for (YANG_Type ytype : type.getUnionSpec().getTypes()){
			typesList.add(BuiltinType.buildType(ytype));
		}
	}
	
	@Override
	public ValueCheck check(String value){
		ValueCheck result = new ValueCheck();
		BuiltinType matchFound = null; int i=0;
		while (matchFound==null && i<typesList.size()){
			if (typesList.get(i).check(value).isOk()) matchFound = typesList.get(i);
			i++;
		}
		if (matchFound!=null){
			result.addChecks(matchFound.check(value));
			return result;
		}
		
		i=0;
		while (matchFound==null && i<typesList.size()){
			if (typesList.get(i).check(value).getFixedCheck()!=null) matchFound = typesList.get(i);
			i++;
		}
		if (matchFound!=null){
			result.addChecks(matchFound.check(value));
			return result;
		}
		
		result.addUnitCheck(new UnitValueCheck("This value does not correctly match any of the union types."));
		return result;
	}
	
	public void setPath(YangTreePath path){
		for (BuiltinType type : typesList){
			if (type instanceof PathSensitiveType)
				((PathSensitiveType) type).setPath(path);
		}
	}
	
	public String getName(){
		String result = "Union {";
		for (BuiltinType type : typesList){
			result += " "+ type.getName()+" |";
		}
		return result.substring(0, result.length()-2)+" }";
	}

	@Override
	public String getContent() {
		String result =  "Union : <ul>";
		for (BuiltinType type : typesList){
			result += "<li>"+type.getContent()+"</li>";
		}
		return result+"</ul>";
	}

}
