import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Hashtable;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GraphicsContext3D;
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

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import datatree.DataTree;

import jyang.parser.YANG_Specification;



public class Manager extends Applet implements MouseListener{
	
	 private  Hashtable<String, YANG_Specification> specs = new Hashtable<String, YANG_Specification>();
	
	 private  YangController controller = new YangController();
	 private  YangView view = null;
	 private Canvas3D canvas3D  = null;
	
	
	public Manager(){
		
		super();
        setLayout(new BorderLayout());
        canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        canvas3D.addMouseListener(this);
        loadSpecification("netconf.yang");
        setView("response");
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        BranchGroup scene = view.createSceneGraph(simpleU.getCanvas());
        scene.compile();
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
		
	}
	
	
	 public void loadSpecification(String module){
		
			String[] arg = {module};
		
		   // Set the jyang parser with the YANG specification file
		   jyang parser = new jyang(arg);
		   specs = parser.getYangsSpecs();
		
	}



	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int h = getHeight();
		int w = getWidth();
		
		float x0 = (float)(x - (w/2));
		System.out.println(x0/w);
	view.clickEvent(e);
	}



	public void mouseEntered(MouseEvent e) {
		
	}



	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
