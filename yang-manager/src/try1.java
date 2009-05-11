import java.applet.Applet;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;
import java.applet.*; //import com.sun.j3d.utils.behaviors.mouse.*;
import java.util.Enumeration;
import java.awt.event.*;
import com.sun.j3d.utils.picking.*; //import com.sun.j3d.utils.picking.behaviors.*;
//import java.awt.event.*;
//import javax.swing.*;
//import java.util.Vector;
import com.sun.j3d.utils.picking.PickTool;

public class try1 extends Applet {
	public try1() {
		Canvas3D canvas = new Canvas3D(SimpleUniverse
				.getPreferredConfiguration());
		setLayout(new BorderLayout());
		add("Center", canvas);

		SimpleUniverse universe = new SimpleUniverse(canvas);

		BranchGroup scene = CreateScene(canvas);
		scene.compile();
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
	}

	public BranchGroup CreateScene(Canvas3D canvas) {
		BranchGroup root = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);
		

		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0.2f,0f,0f));

		TransformGroup tg1 = new TransformGroup(translate1);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg1.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

		MyBeh be1 = new MyBeh(canvas, root, tg1);
		be1.setSchedulingBounds(bounds);
		root.addChild(be1);

		ColorCube cube1 = new ColorCube(0.05f);

		Node sphere = new Sphere(0.05f);
		// il faut appliquer le PickTool.setCapabilities(Node,int)
		// sur le Node concerné lorsque un
		// pickIntersection doit se faire sur ce node.sinon erreur
		PickTool.setCapabilities(cube1, PickTool.INTERSECT_FULL);
		// PickTool.setCapabilities(sphere, PickTool.INTERSECT_FULL);

		root.addChild(tg1);

		tg1.addChild(cube1);

		Vector3f lightdirection = new Vector3f(0.0f, 0.0f, -15.0f);
		Color3f lightcolor = new Color3f(0.0f, 1.0f, 6.0f);
		DirectionalLight dl = new DirectionalLight(lightcolor, lightdirection);
		BoundingSphere bound = new BoundingSphere(new Point3d(), 150.0f);
		dl.setInfluencingBounds(bound);
		root.addChild(dl);

		root.compile();
		return root;
	}

	public static void main(String[] args) {
		new MainFrame(new try1(), args, 150, 150);
	}

}
