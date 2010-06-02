import java.io.*;


import jyang.*;
import jyang.parser.YANG_Specification;
import jyang.parser.yang;

public class JyangManager {

	/**
	 * Simple jyang test, parses and checks one YANG specification.
	 * Imported or included modules or submodules are looked in the current 
	 * directory.
	 * Error messages are on the standard output
	 * 
	 * @param args YANG file name
	 */
	public static void main(String[] args) throws Exception {

		 FileInputStream yangfile = new FileInputStream(args[0]);
		   // Set the jyang parser with the YANG specification file
		   new yang(yangfile);
		   // do the lexical and syntactic check
		   YANG_Specification spec = yang.Start();
		   // do the semantical check
		   spec.check();
		   // Now one can access YANG statements through spec
		   // for example spec.getHeaders() or spec.getImports()
		   
		   FileInputStream rpcResponse = new FileInputStream(args[1]);
		   
	}
}
