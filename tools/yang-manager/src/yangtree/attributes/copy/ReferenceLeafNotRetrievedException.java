package yangtree.attributes.copy;

import yangtree.attributes.builtinTypes.LeafrefType;

/**
 * Exception raised when trying to check a leafref type which reference leaf is out of the retrieved data tree.
 * @see LeafrefType
 *
 */
@SuppressWarnings("serial")
public class ReferenceLeafNotRetrievedException extends Exception {
	
	public ReferenceLeafNotRetrievedException(){
		super();
	}

}
