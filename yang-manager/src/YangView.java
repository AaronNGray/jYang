import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import datatree.*;


public class YangView  {
	
	private DataNode model = null;
	
	public YangView(DataNode r){
		model = r;
	}
	
	public BranchGroup createSceneGraph(){
    	BranchGroup objRoot=new BranchGroup();
    	
    	Appearance app_orang = new Appearance();
		ColoringAttributes orang=new ColoringAttributes();
		orang.setColor(0.8f,0.4f,0.2f);
		orang.setShadeModel(ColoringAttributes.NICEST);
		app_orang.setColoringAttributes(orang);		
					
    	TransformGroup TG=new TransformGroup();
    		
		Transform3D rayon3D=new Transform3D();
		rayon3D.setTranslation(new Vector3f(0.0f,0.0f,-10.0f));
		TransformGroup rayon = new TransformGroup(rayon3D);
		TG.addChild(rayon);
		
		rayon.addChild(new Box(3.2f, 2.4f,5.05f,app_orang));
		
		objRoot.addChild(TG);
		return objRoot;
	}

}
