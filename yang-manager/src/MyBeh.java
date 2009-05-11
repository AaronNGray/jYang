import java.awt.event.MouseEvent;

import javax.media.j3d.Behavior;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
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


public class MyBeh extends Behavior {
	WakeupCriterion clic=new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
	 WakeupCriterion drag=new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED);
	 WakeupCondition wakeupOr=new WakeupOr(new WakeupCriterion[]{clic,drag});
	 Canvas3D canvas;
	 Transform3D t =new Transform3D();
	 TransformGroup tg=new TransformGroup();
	 Vector3d v=new Vector3d(0,0,0);
	 PickCanvas pickCanvas;
	 PickResult pickResult;
	 Point3d intPt=new Point3d(0,0,0);
	 Point3d floatPt=new Point3d(0,0,0);
	 Transform3D t3=new Transform3D();
	 
	 public MyBeh(Canvas3D canvas,BranchGroup scene,TransformGroup tg)
	 {
	     pickCanvas = new PickCanvas(canvas, scene);
	     pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
	     pickCanvas.setTolerance(4.0f);
	  this.canvas=canvas;
	  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	  tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
	  this.tg=tg;
	 }
	  
	 public void initialize()
	 {
	  wakeupOn(wakeupOr);
	 }
	 
	 public void processStimulus (Enumeration criteria)
	 {
	  WakeupCriterion wakeup;
	  AWTEvent[] events;
	  
	  while(criteria.hasMoreElements())
	  {
	   wakeup=(WakeupCriterion)criteria.nextElement();
	   if(wakeup instanceof WakeupOnAWTEvent)
	   {
	    events=((WakeupOnAWTEvent)wakeup).getAWTEvent();
	    for(int i=0;i<events.length;i++)
	    {
	//TRANSLATE     
	     if((events[i].getID()==MouseEvent.MOUSE_PRESSED || events[i].getID()==MouseEvent.MOUSE_DRAGGED)&& 
	     !((MouseEvent)events[i]).isControlDown() && 
	     !((MouseEvent)events[i]).isMetaDown() && 
	     !((MouseEvent)events[i]).isAltDown())
	     {
	      int x=((MouseEvent)events[i]).getX();
	      int y=((MouseEvent)events[i]).getY();
	      System.out.println("Coordinates of Mouse: "+new Point2d(x,y));
	      //marque le point de clic
	      pickCanvas.setShapeLocation(x,y);
	      //Point a partir duquel le rayon commencera a detectŽ le point le plus proche
	      //ce point est la distance ou est placŽ l'oeil de l'observateur
	      //ainsi le rayon detecteur est lancŽ a parit de ce point donc on est sur q'aucun pointt
	      //n'est epargnŽ
	      Point3d eyePos=pickCanvas.getStartPosition();
	      pickResult=pickCanvas.pickClosest();
	      
	      if(pickResult!=null && pickResult.getObject()==tg.getChild((tg.numChildren())-1))
	      {
	       PickIntersection pi=
	       pickResult.getClosestIntersection(eyePos);
	       
	       intPt=pi.getPointCoordinatesVW();
	       intPt.z=0;
	       floatPt.x=intPt.x;
	       floatPt.y=intPt.y;
	       v.set(intPt);
	       t3.set(v);
	       tg.setTransform(t3);

	      System.out.println("Coordinates of pick intersection\n: "+intPt);
	      System.out.println("Coordinates of eyePos: "+eyePos);
	      }
	     }
	/*//ZOOM      
	     if((events[i].getID()==MouseEvent.MOUSE_PRESSED || events[i].getID()==MouseEvent.MOUSE_DRAGGED)&& 
	     ((MouseEvent)events[i]).isControlDown() && 
	     !((MouseEvent)events[i]).isMetaDown() && 
	     !((MouseEvent)events[i]).isAltDown())
	     {
	      int x=((MouseEvent)events[i]).getX();
	      int y=((MouseEvent)events[i]).getY();
	      System.out.println("Coordinates of Mouse: "+new Point2d(x,y));
	      //marque le point de clic
	      pickCanvas.setShapeLocation(x,y);
	      //Point a partir duquel le rayon commencera a detectŽ le point le plus proche
	      //ce point est la distance ou est placŽ l'oeil de l'observateur
	      //ainsi le rayon detecteur est lancŽ a parit de ce point donc on est sur q'aucun pointt
	      //n'est epargnŽ
	      Point3d eyePos=pickCanvas.getStartPosition();
	      pickResult=pickCanvas.pickClosest();
	      
	      if(pickResult!=null)
	      {
	       PickIntersection pi=
	       pickResult.getClosestIntersection(eyePos);
	       
	       intPt=pi.getPointCoordinatesVW();
	       intPt.x=0;
	       intPt.y=0;
	       v.set(intPt);
	       t3.set(v);
	       tg.setTransform(t3);

	      System.out.println("Coordinates of pick intersection\n: "+intPt);
	      System.out.println("Coordinates of eyePos: "+eyePos);
	      }

	      System.out.println("\n\n********************************************************");
	      System.out.println("********************************************************");
	      System.out.println("********************************************************");
	     }*/
	    }
	   }
	  }
	  
	  wakeupOn(wakeupOr);
	 } 
}
