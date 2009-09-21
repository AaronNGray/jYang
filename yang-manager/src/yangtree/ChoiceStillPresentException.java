package yangtree;

/**
 * Exception raised when trying to apply a tree where choices nodes are still present.
 * 
 */
@SuppressWarnings("serial")
public class ChoiceStillPresentException extends Exception {
	
	public ChoiceStillPresentException(){
		super();
	}

}
