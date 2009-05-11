import java.applet.Applet;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import java.applet.*;
//import com.sun.j3d.utils.behaviors.mouse.*;
import java.util.Enumeration;
import java.awt.event.*;
import com.sun.j3d.utils.picking.*;
//import com.sun.j3d.utils.picking.behaviors.*;
//import java.awt.event.*;
//import javax.swing.*;
//import java.util.Vector;
import com.sun.j3d.utils.picking.PickTool;

public class try1 extends Applet {
	 public try1() 
	 {
	  Canvas3D canvas=new Canvas3D(SimpleUniverse.
	  getPreferredConfiguration());
	  setLayout(new BorderLayout());
	  add("Center",canvas);
	  
	  SimpleUniverse universe=new SimpleUniverse(canvas);
	  
	  BranchGroup scene=CreateScene(canvas);
	  
	  universe.getViewingPlatform().setNominalViewingTransform();
	  universe.addBranchGraph(scene);
	 }
	 
	 public BranchGroup CreateScene(Canvas3D canvas)
	 {
	  BranchGroup root=new BranchGroup();
	        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	  TransformGroup tg=new TransformGroup();
	  TransformGroup tg1=new TransformGroup();
	  TransformGroup tg2=new TransformGroup();
	  TransformGroup tg3=new TransformGroup();
	  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	//  tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
	  tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	  tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	//  tg2.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
	  tg3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	  tg3.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	//  tg3.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
	  tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	  tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	  tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
	  
	  Transform3D t3d=new Transform3D();
	  
	  t3d.set(new Vector3d(0,0.3,0));
	  tg2.setTransform(t3d);
	  
	  t3d.set(new Vector3d(0,-0.3,0));
	  tg3.setTransform(t3d);

	  MyBeh be=new MyBeh(canvas,root,tg);
	  be.setSchedulingBounds (bounds);    
	  root.addChild(be);

	  MyBeh be1=new MyBeh(canvas,root,tg1);
	  be1.setSchedulingBounds (bounds);    
	  root.addChild(be1);

	  MyBeh be2=new MyBeh(canvas,root,tg2);
	  be2.setSchedulingBounds (bounds);    
	  root.addChild(be2);

	  MyBeh be3=new MyBeh(canvas,root,tg3);
	  be3.setSchedulingBounds (bounds);    
	  root.addChild(be3);

	//Points du CARRE1
	  Point3f point1=new Point3f(-0.3f,0.1f,0.0f);
	  Point3f point2=new Point3f(-0.3f,-0.1f,0.0f);
	  Point3f point3=new Point3f(-0.2f,-0.1f,0.0f);
	  Point3f point4=new Point3f(-0.2f,0.1f,0.0f);
	//Points du CARRE2 
	  Point3f point5=new Point3f(-0.1f,0.1f,0f);
	  Point3f point6=new Point3f(-0.1f,-0.1f,0f);
	  Point3f point7=new Point3f(0.1f,-0.1f,0f);
	  Point3f point8=new Point3f(0.1f,0.1f,0f);

	  GeometryArray pa1=new QuadArray(4,PointArray.COORDINATES| PointArray.BY_REFERENCE);
	  GeometryArray pa2=new QuadArray(4,PointArray.COORDINATES| PointArray.BY_REFERENCE);
	  Shape3D sh1;
	  Shape3D sh2;
	  
	  pa1.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
	  pa1.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
	  pa1.setCapability(GeometryArray.ALLOW_COUNT_READ); 
	  pa1.setCoordRef3f(new Point3f[]{point1,point2,point3,point4});

	  pa2.setCapability(GeometryArray.ALLOW_REF_DATA_WRITE);
	  pa2.setCapability(GeometryArray.ALLOW_REF_DATA_READ);
	  pa2.setCapability(GeometryArray.ALLOW_COUNT_READ); 
	  pa2.setCoordRef3f(new Point3f[]{point5,point6,point7,point8});
	  
	//CARRE1 a gauche fils de tg1 
	  sh1=new Shape3D(pa1);
	//CARRE2 au centre fils de tg   
	  sh2=new Shape3D(pa2);
	//CUBE1 en HAUT, fils de tg2   
	  ColorCube cube1=new ColorCube(0.05f);
	//CUBE2 EN BAS fils de tg3
	  ColorCube cube2=new ColorCube(0.05f);
	  
	//  Node sphere=new Sphere(0.05f);
	  //il faut appliquer le PickTool.setCapabilities(Node,int)
	  // sur le Node concerné lorsque un
	  //pickIntersection doit se faire sur ce node.sinon erreur
	  PickTool.setCapabilities(cube1, PickTool.INTERSECT_FULL);
	  PickTool.setCapabilities(cube2, PickTool.INTERSECT_FULL);
	  PickTool.setCapabilities(sh1, PickTool.INTERSECT_FULL);
	  PickTool.setCapabilities(sh2, PickTool.INTERSECT_FULL);
	//  PickTool.setCapabilities(sphere, PickTool.INTERSECT_FULL);

	   root.addChild(tg1);
	  root.addChild(tg2);
	  root.addChild(tg3);
	  root.addChild(tg);
	  
	  tg.addChild(sh2);  
	  tg1.addChild(sh1);
	  tg2.addChild(cube1);
	  tg3.addChild(cube2);
	  
	  
	  Vector3f lightdirection=new Vector3f(0.0f,0.0f,-15.0f);
	  Color3f lightcolor=new Color3f(0.0f,1.0f,6.0f);
	  DirectionalLight dl=new DirectionalLight(lightcolor,lightdirection);
	  BoundingSphere bound=new BoundingSphere(new Point3d(),150.0f);
	  dl.setInfluencingBounds(bound);
	  root.addChild(dl);

	  root.compile();
	  return root;
	 }
	 
	 public static void main(String[] args)
	 {
	  new MainFrame(new try1(),args,150,150);
	 }
	 
}
