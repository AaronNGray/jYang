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
import javax.vecmath.Vector3f;

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
        // 1ere √©tape cr√©ation du Canvas3d qui va afficher votre univers virtuel avec une config pr√©d√©finie
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        // 2eme √©tape on cr√©e notre sc√®ne (regroupement d'objets)
        BranchGroup scene = createSceneGraph();
        // on les compile pour optimiser les calculs
        scene.compile();

        // 3eme √©tape on cr√©e l'univers qui va contenir notre sc√®ne 3d
        // utilise simpleUniverse qui simplifie le code (il cr√©e un environnement minimal simple)
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        
        // on met le plan de projection en arriere par rapport √† l'origine
        
        simpleU.getViewingPlatform().setNominalViewingTransform();
        
        // on place la sc√®ne dans l'univers simpleU 
        
        simpleU.addBranchGraph(scene);

    }

    //cr√©e un regroupement d'objets contenant un objet cube
    public BranchGroup createSceneGraph()
    {

    	//on crée le Bg principal
    	BranchGroup objRoot=new BranchGroup();

    	//------------ début de creation du premier cube ------------

    	// on crée un vecteur de translation 30 cm suivant les Y
    	Transform3D translate1 = new Transform3D(); 
    	translate1.set(new Vector3f(0.5f, 0.3f, 0.0f));

    	// on crée une matrice de tranformation pour faire tourner notre cube
    	Transform3D rotate = new Transform3D(); 
    	//(X represente la vericale orientée vers le bas,Y represente l' horizontale orientée vers la gauche,Z) 
    	rotate.rotX(Math.PI/3.0d);//rotation d'angle Pi/3

    	// on combine les deux transformations: translation puis rotation 
    	translate1.mul(rotate);
    	// on crée un groupe de transformation rotate suivant la matrice de transformation translate1 
    	TransformGroup TG1 = new TransformGroup(translate1);

    	// on crée un cube qui herite de cette rotation
    	TG1.addChild(new ColorCube(0.3));// de rayon 30 cm 
    	objRoot.addChild(TG1);

    	//------------ fin de creation du premier cube ------------
    	//------------ début de creation du deuxieme cube ------------

    	// on crée un vecteur de translation de 30 cm suivant les Y (dans l'autre sens) 
    	Transform3D translate2 = new Transform3D();
    	translate2.set(new Vector3f(-0.4f, 0.4f, 0.0f));

    	// on crée une matrice de tranformation pour faire tourner notre cube 
    	Transform3D rotate2 = new Transform3D();
    	rotate2.rotZ(Math.PI/3.0d);//rotation d'angle Pi/3

    	// on combine les deux transformations: translation puis rotation
    	translate2.mul(rotate2);

    	// on réduit la taille du cube par 2 (on la multiplie par 0.5)
    	translate2.setScale(0.5f);

    	// on crée un groupe de transformation rotate suivant la matrice de transformation translate1
    	TransformGroup TG2 = new TransformGroup(translate2);

    	// on crée un cube qui herite de cette rotation 
    	TG2.addChild(new ColorCube(0.3));// de rayon 20 cm

    	objRoot.addChild(TG2);

    	//------------ fin de creation du deuxieme cube ------------

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

