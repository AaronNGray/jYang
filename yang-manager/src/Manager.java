import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.EventListener;
import java.util.Hashtable;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicScrollBarUI;
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

public class Manager extends Applet implements MouseListener, MouseMotionListener{

	private Hashtable<String, YANG_Specification> specs = new Hashtable<String, YANG_Specification>();

	private YangController controller = new YangController();
	private YangView3D view3D = null;
	private Canvas3D canvas3D = null;
	
	private YangView2D view2D = null;

	public Manager() {

		super();
		setLayout(new BorderLayout());

		loadSpecification("netconf.yang");
	}

	private void Manager3D() {
		setView(3, "response");
		canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center", canvas3D);
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		BranchGroup scene = view3D.createSceneGraph(simpleU.getCanvas());
		scene.compile();
		simpleU.addBranchGraph(scene);
	}
	
	
	private void Manager2D() {
		
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		setView(2, "response");
		
		add(view2D.createSceneGraph());
	
		showStatus("YANG Manager");
		setVisible(true);
	}
	

	public void setView(int d, String resp) {

		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(new FileInputStream(resp));
			if (d == 3)
				view3D = controller.createView3D(doc, specs);
			else
				view2D = controller.createView2D(doc, specs);

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

	public void init() {
		System.out.println("fini");
		String dim = getParameter("dimensions");
		if (dim != null)
			if (dim.equals("2"))
				Manager2D();
			else if (dim.equals("3"))
				Manager3D();
			else
				Manager2D();
		else
			Manager2D();

	}

	public void loadSpecification(String module) {

		String[] arg = { module };

		// Set the jyang parser with the YANG specification file
		jyang parser = new jyang(arg);
		specs = parser.getYangsSpecs();

	}

	

	public void mouseEntered(MouseEvent e) {
System.out.println("e " +  getSize());
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("x");

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("p");

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("r");

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("c");
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("dragged");
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("moved");
		
	}
	
}
