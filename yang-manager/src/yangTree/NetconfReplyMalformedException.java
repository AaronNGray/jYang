package yangTree;

public class NetconfReplyMalformedException extends IllegalArgumentException{
	
	public NetconfReplyMalformedException(){
		super();
	}
	
	public NetconfReplyMalformedException(String details){
		super(details);
	}

}
