/*
écrit par:Roswell
email:philgauthier_@hotmail.com
 
*/

import javax.media.j3d.Behavior;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;
import java.awt.AWTEvent;
import java.util.Enumeration;
import java.awt.event.KeyEvent;

class mouvbehav extends Behavior
{
	private TransformGroup TG;
	private Transform3D rot=new Transform3D();
	private Transform3D rotation=new Transform3D();
	private Vector3f translation=new Vector3f();
	
	private WakeupOnAWTEvent keyEvent=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	mouvbehav(TransformGroup TG)
	{
		this.TG=TG;
	}
	
	public void initialize()
	{
		this.wakeupOn(keyEvent);
	}
		
	public void processStimulus(Enumeration criteria)
	{
		AWTEvent events[]=keyEvent.getAWTEvent();
		rot.setIdentity();
		TG.getTransform(rotation);
		
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD1)
		{
			translation.set(0.075f,0f,0f);
			rot.setTranslation(translation);
		}
		else
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD3)
		{
			translation.set(-0.075f,0f,0f);
			rot.setTranslation(translation);
		}
		else
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD2)
		{
			translation.set(0f,0f,0.075f);		
			rot.setTranslation(translation);
		}
		else
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD8)
		{
			translation.set(0f,0f,-0.075f);
			rot.setTranslation(translation);
		}
		else
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD4)
			rot.rotY(0.0125d);
		else
		if (((KeyEvent)events[0]).getKeyCode()==KeyEvent.VK_NUMPAD6)			
			rot.rotY(-0.0125d);
			
		rotation.mul(rot);
		TG.setTransform(rotation);
		
		this.wakeupOn(keyEvent);
	}
}
