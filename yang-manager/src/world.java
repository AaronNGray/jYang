

// classes Java standard

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;

// classes Java 3D
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Appearance;

import javax.vecmath.Vector3f;
import javax.vecmath.Point3d;
import com.sun.j3d.utils.geometry.Box;

// classe cr��e pour l'interaction
import javax.media.j3d.BoundingSphere;


public class world extends Frame implements WindowListener
{	    
	public world()
	{
        super("- navigation : contr�le de la cam�ra -");
        this.addWindowListener(this);
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        
        // on a modifi� le constructeur pour inclure l'univers et pouvoir ainsi acc�der
		// � la cam�ra et interagir avec elle
        BranchGroup scene = createSceneGraph(simpleU);
        scene.compile();
        simpleU.addBranchGraph(scene);        
    }	
	
	public BranchGroup createSceneGraph(SimpleUniverse simpleU)
	{
		//on cr�e le Bg principal
		BranchGroup objRoot=new BranchGroup();
		
		//------------ debut creation des apparences ---------------
			// on cr�e une apparence de couleur orange
			Appearance app_orang = new Appearance();
			ColoringAttributes orang=new ColoringAttributes();
			orang.setColor(0.8f,0.4f,0.2f);
			orang.setShadeModel(ColoringAttributes.NICEST);
			app_orang.setColoringAttributes(orang);		
			
		//------------ fin creation des apparences -----------------

		//------------ d�but de creation d'une box -----------------			
        	TransformGroup TG=new TransformGroup();
        		
			Transform3D rayon3D=new Transform3D();
			rayon3D.setTranslation(new Vector3f(0.0f,0.0f,-10.0f));
			TransformGroup rayon = new TransformGroup(rayon3D);
			TG.addChild(rayon);
			
			rayon.addChild(new Box(3.2f, 2.4f,5.05f,app_orang));
			
			objRoot.addChild(TG);
		//------------ fin de creation d'une box -------------------
			
		//------- d�but de ajout de l'interaction ------------------
			// On r�cupere le TG de la cam�ra
			TransformGroup view=simpleU.getViewingPlatform().getViewPlatformTransform();

			// l'interaction aura lieu avec le TG de la cam�ra (lien par r�f�rence) 
			mouvbehav behav=new mouvbehav(view);

			// On d�finit une zone d'influence tr�s grande pour que l'interaction soit active sur une tr�s grande zone
			BoundingSphere influPartout=new BoundingSphere(new Point3d(),1000.0);
			behav.setSchedulingBounds(influPartout);

			// lien d'h�ritage
			objRoot.addChild(behav);
		//------- fin de ajout de l'interaction --------------------
		
		return objRoot;
	}
    	
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	
	public void windowClosing(WindowEvent e)
	{
		System.exit(1);
	}
    	
	public static void main(String args[])
	{
		world myApp=new world();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
	
}