package yangTree.nodes;

import java.util.LinkedList;

import jyang.parser.YANG_Choice;

public class ChoiceNode extends DataTree {

	public ChoiceNode(YANG_Choice choice){
		definition = choice;
	}
	
	
	public String toString(){
		return definition.getBody();
	}
	

	@Override
	public String getNodeType() {
		return "Choice";
	}

	@Override
	public DataTree cloneBody() {
		return new ChoiceNode((YANG_Choice) definition);
	}
	
}
