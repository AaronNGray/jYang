package yangTree.attributes;

import yangTree.nodes.ListNode;
import yangTree.nodes.YangNode;

/**
 * Builds and records delete operations that will be performed at the "apply modification" action.
 *
 */
public class DeleteOperations {
	
	String operations = "";
	
	private static final String NETCONF_XMLNS = "xmlns:xc=\"urn:ietf:params:xml:ns:netconf:base:1.0\"";
	
	/**
	 * Creates a new empty stack of delete operations.
	 */
	public DeleteOperations(){}
	
	/**
	 * Marks the specific node to be deleted.
	 * @param nodeToDelete : the node that must be deleted.
	 */
	public void addDeleteOperation(YangNode nodeToDelete){
		String newOperation = "<" + nodeToDelete.getName() + " ";
		if (nodeToDelete.getNameSpace() != null && nodeToDelete.getNameSpace().getNameSpace() != null) {
			newOperation += nodeToDelete.getNameSpace().getXMLArg();
		}
		newOperation += NETCONF_XMLNS+" xc:operation=\"delete\">";
		if (nodeToDelete instanceof ListNode){
			ListNode list = (ListNode) nodeToDelete;
			newOperation += list.getKeyXMLRepresentation();
		}
		newOperation += "</"+nodeToDelete.getName()+">";
		
		operations += newOperation;
	}
	
	/**
	 * Returns the XML-formated filter needed by a netconf request to perform these delete operations.
	 */
	public String getOperations(){
		return operations;
	}

}
