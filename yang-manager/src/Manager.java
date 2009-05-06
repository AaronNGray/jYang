import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import datatree.DataTree;

import jyang.parser.YANG_Specification;



public class Manager {
	
	private Hashtable<String, YANG_Specification> specs = new Hashtable<String, YANG_Specification>();
	
	public void loadSpecification(String module){
		
			String[] arg = {module};
		
		   // Set the jyang parser with the YANG specification file
		   jyang parser = new jyang(arg);
		   specs = parser.getYangsSpecs();
		
	}
	
	public YangView getView(String resp){ 
		XMLStreamReader xsr = null;
		
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(resp));
			YangController controller = new YangController();
			controller.createView(doc, specs);
			
			try {
				xsr = XMLInputFactory.newInstance()
				.createXMLStreamReader(new FileInputStream(resp));
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			YangController controllerx = new YangController();
			//return controllerx.createView(xsr);
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main (String[] args){
		Manager m = new Manager();
		m.loadSpecification(args[0]);
		m.getView(args[1]);
	}

}
