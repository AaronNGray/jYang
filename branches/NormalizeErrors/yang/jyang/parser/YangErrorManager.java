package jyang.parser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YangErrorManager {

	static private class Error {
		String module;
		int line;
		int column;
		String messageId;

		public Error(String m, int l, int c, String mi) {
			module = m;
			line = l;
			column = c;
			messageId = mi;
		}
		public String toString(){
			return module + ":" + line + ";" + column + ":" + messageId;
		}
	}

	static private class ErrorsComparator implements Comparator {

		@Override
		public int compare(Object o0, Object o1) {
			Error e0 = (Error) o0, e1 = (Error) o1;
			if (e0.module.compareTo(e1.module) != 0)
				return e0.module.compareTo(e1.module);
			else if (e0.line != e1.line)
				return e0.line - e1.line;
			else if (e0.column != e1.column)
				return e0.column - e1.column;
			return 0;
		}

	}

	static private ErrorsComparator errorComp = new ErrorsComparator();

	static private TreeSet<Error> errors = new TreeSet<Error>(errorComp);

	static public ResourceBundle messages;

	static protected Properties properties = new Properties(
			"jyang.parser.resources");

	// @SuppressWarnings("static-access")
	static public ResourceBundle getMessages() {
		return messages;
	}

	static public Properties getProperties() {
		return properties;
	}

	 static public void init() {

		messages = null;

		try {
			messages = ResourceBundle.getBundle("jyang.parser.MessagesBundle");
		} catch (MissingResourceException mre) {
			Logger.getLogger("").logp(Level.SEVERE, "Yang Error Manager",
					"static initialization",
					"Can not read  MessagesBundle (messages properties files)",
					mre);
		}
	}
	 static private String module;
	 
	 static public void setCurrentModule(String m){
		 module = m;
	 }

	static public void add(String module, int line, int col, String mess) {
		Error error = new Error(module, line, col, mess);
		errors.add(error);
	}

	static public void add(int line, int col, String mess) {
		add(module, line, col, mess);
	}
	
	static public void print(OutputStream out) throws IOException{
		for (Iterator<Error> i = errors.iterator();i.hasNext();){
			out.write((i.next().toString() + "\n").getBytes());
		}
	}

}
