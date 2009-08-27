package yangTree;

/**
 * Exception raised when trying to apply a tree when choices nodes are still present.
 * 
 */
@SuppressWarnings("serial")
public class ChoiceStillPresentException extends Exception {
	
	public ChoiceStillPresentException(){
		super();
	}

}
