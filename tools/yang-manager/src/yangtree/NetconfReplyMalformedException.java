package yangtree;

/**
 * Exception raised when the netconf reply is not formed as expected.
 *
 */
@SuppressWarnings("serial")
public class NetconfReplyMalformedException extends IllegalArgumentException{
	
	public NetconfReplyMalformedException(){
		super();
	}
	
	public NetconfReplyMalformedException(String details){
		super(details);
	}

}
