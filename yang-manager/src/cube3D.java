	import java.applet.Applet;


// classes Java standart
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
// classes Java 3d
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BoundingBox;
import javax.media.j3d.Bounds;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;

public class cube3D extends Applet implements MouseMotionListener, MouseListener
{
	private static final long serialVersionUID = 2687955466074691940L;


    public cube3D()
    {

        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        
        setLayout(new BorderLayout());
        // 1ere étape création du Canvas3d qui va afficher votre univers virtuel avec une config prédéfinie
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        // 2eme étape on crée notre scène (regroupement d'objets)
        BranchGroup scene = createSceneGraph();
        // on les compile pour optimiser les calculs
        scene.compile();

        // 3eme étape on crée l'univers qui va contenir notre scène 3d
        // utilise simpleUniverse qui simplifie le code (il crée un environnement minimal simple)
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        
        // on met le plan de projection en arriere par rapport à l'origine
        
        simpleU.getViewingPlatform().setNominalViewingTransform();
        
        // on place la scène dans l'univers simpleU 
        
        simpleU.addBranchGraph(scene);

    }

    //crée un regroupement d'objets contenant un objet cube
    public BranchGroup createSceneGraph()
    {

        //on crée le Bg principal
        BranchGroup objRoot=new BranchGroup();


    
        // on crée un cube
        
        Transform3D rotate = new Transform3D();
        rotate.rotX(Math.PI/3.0d);
        TransformGroup objRotate = new TransformGroup(rotate);
        
        objRotate.addChild(new ColorCube(0.5));// de rayon 50 cm
        
        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(objRotate);
        objRotate.addChild(behavior);
        behavior.setSchedulingBounds(new BoundingBox(new Point3d(0,0,0), new Point3d(200,200,200)));

        objRoot.addChild(objRotate);
        
        return objRoot;

    }

    public void windowActivated(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){System.out.println("icon");}
    public void windowOpened(WindowEvent e){System.out.println("open");}

    public void windowClosing(WindowEvent e) {  System.exit(1); }

    public static void main(String[] args)
    {

        cube3D myApp=new cube3D();
        myApp.setSize(300,300);
        myApp.setVisible(true);

    }

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("Click");
		
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println("entered");
		
	}

	public void mouseExited(MouseEvent e) {
		System.out.println("exited");
		
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("pressed");
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

