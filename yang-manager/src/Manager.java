import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Hashtable;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import datatree.DataTree;

import jyang.parser.YANG_Specification;



public class Manager extends Applet{
	
	 private  Hashtable<String, YANG_Specification> specs = new Hashtable<String, YANG_Specification>();
	
	 private  YangController controller = new YangController();
	 private  YangView view = null;
	 private Canvas3D canvas3D  = null;
	
	
	public Manager(){
		
		super();
        setLayout(new BorderLayout());
        canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        loadSpecification("netconf.yang");
        setView("response");
        BranchGroup scene = view.createSceneGraph();
        scene.compile();
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);
	}
	

	
	 public void setView(String resp){ 
		
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream(resp));
			view =  controller.createView(doc, specs);
			
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
		
	}
	
	public static void init (String[] args){
		Manager m = new Manager();
		m.setVisible(true);
		
	}
	
	
	 public void loadSpecification(String module){
		
			String[] arg = {module};
		
		   // Set the jyang parser with the YANG specification file
		   jyang parser = new jyang(arg);
		   specs = parser.getYangsSpecs();
		
	}

}
