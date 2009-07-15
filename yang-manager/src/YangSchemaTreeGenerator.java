import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import jyang.parser.YANG_Specification;
import datatree.*;




public class YangSchemaTreeGenerator {
	
	
	private jyang parser = null;
	
	public YangSchemaTreeGenerator(String[] args){
		String ip = args[0];
		int nbmodules = Integer.parseInt(args[1]);
		
		String[] yarg = new String[nbmodules];
		
		for (int i = 0; i < nbmodules; i++){
			yarg[i] = args[i+2];
		}
		
		String yxpaths[] = new String[2 * nbmodules];
		for (int i = 0; i < 2 * nbmodules;i++ ){
			yxpaths[i] = args[2 + nbmodules + i];
		}
		
		parser = new jyang(yarg);
		Hashtable<String, YANG_Specification> specs = parser.getYangsSpecs();
		for (Enumeration<YANG_Specification> especs = specs.elements(); especs.hasMoreElements();){
			YANG_Specification spec = especs.nextElement();
			System.err.println(spec.getSchemaTree().toString());
		}
		
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(ip+ ".yang.byte"));
			os.writeObject(specs);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String[]argv){
		new YangSchemaTreeGenerator(argv);
	}

}
