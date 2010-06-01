

import java.io.*;

import jyang.parser.YANG_Specification;
import jyang.parser.YangErrorManager;
import jyang.parser.yang;


public class JyangTest {

	/**
	 * Simple jyang test, parses and checks one YANG specification.
	 * Imported or included modules or submodules are looked in the current 
	 * directory.
	 * Error messages are on the standard output
	 * 
	 * @param args YANG file name
	 */
	public static void main(String[] args) throws Exception {

		   FileInputStream yangfile = new FileInputStream("test.yang");
		   
		   // Set the jyang parser with the YANG specification file
		   new yang(yangfile);
		   yang.setFileName("test.yang");
		   YangErrorManager.setCurrentModule("test.yang");
		   // do the lexical and syntactic check
		   YANG_Specification spec = yang.Start();
		   // do the semantical check
		   spec.check();
		   // Now one can access YANG statements through spec
		   // for example spec.getHeaders() or spec.getImports()
		   
		   
		   
	}
}
