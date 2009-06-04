import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import sun.font.Font2D;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

import datatree.*;

public class YangView3D {

	private DataNode model = null;

	private Vector<GraphicalNode> nodes = new Vector<GraphicalNode>();
	
	private final float NODE_SIZE = 0.02f;
	private final float EDGE_SECT = 0.005f;
	

	public YangView3D(DataNode r) {
		model = r;
	}

	private int getHigh(DataNode n) {
		if (n instanceof DataTree) {
			DataTree dt = (DataTree) n;
			int max = 0;
			for (Enumeration<DataNode> edn = dt.getNodes().elements(); edn
					.hasMoreElements();) {
				DataNode dn = edn.nextElement();
				int h = getHigh(dn);
				if (h > max)
					max = h;
			}
			return 1 + max;
		} else
			return 1;
	}

	private int high;
	BranchGroup objRoot = null;
	private TransformGroup mouseTransform = null;
	BoundingSphere bounds = null;
	private Canvas3D canvas = null;

	public BranchGroup createSceneGraph(Canvas3D canvas3d) {
		canvas = canvas3d;
		high = getHigh(model);
		objRoot = new BranchGroup();

		mouseTransform = new TransformGroup();
		mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		mouseTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Creation comportement rotation a la souris
		MouseRotate rotate = new MouseRotate(mouseTransform);
		rotate.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(rotate);
		// Creation comportement deplacement a la souris
		MouseTranslate translate = new MouseTranslate(mouseTransform);
		translate.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(translate);

		// Creation comportement zoom a la souris
		MouseZoom zoom = new MouseZoom(mouseTransform);
		zoom.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(zoom);

		draw(model, 0, 0, 0);

		Background background = new Background(0.5f, 0.5f, 0.5f);
		background.setApplicationBounds(new BoundingBox());

		objRoot.addChild(background);
		objRoot.addChild(mouseTransform);
		return objRoot;
	}

	private void draw(DataNode n, float x, float y, float z) {
		draw(n, 0, 0, 0, x, y, z);
	}

	private void draw(DataNode n, float x0, float y0, float z0, float x,
			float y, float z) {

		nodes.add(new GraphicalNode(n, x, y, z));

		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(x, y, z));

		TransformGroup TG1 = new TransformGroup(translate1);

		Text3D t3d = new Text3D(new Font3D(
				new Font("Helvetica", Font.PLAIN, 1), new FontExtrusion()), n
				.getName());

		t3d.setAlignment(Text3D.ALIGN_CENTER);

		Shape3D s3d = new Shape3D(t3d);
		Appearance blk = new Appearance();
		ColoringAttributes black = new ColoringAttributes();
		black.setColor(0, 0, 0);
		blk.setColoringAttributes(black);
		s3d.setAppearance(blk);

		
		Transform3D translate2 = new Transform3D();
		translate2.set(new Vector3f(0,0.025f,0));
		Transform3D scale = new Transform3D();
		scale.setScale(0.05);
		translate2.mul(scale);

		TransformGroup TG2 = new TransformGroup(translate2);

		TG2.addChild(s3d);
		TG1.addChild(TG2);

		if (n instanceof LeafNode) {
			Appearance app_orang = new Appearance();
			ColoringAttributes orang = new ColoringAttributes();
			orang.setColor(0.8f, 0.4f, 0.2f);
			orang.setShadeModel(ColoringAttributes.NICEST);
			app_orang.setColoringAttributes(orang);
			Sphere sphere = new Sphere(NODE_SIZE, app_orang);
			TG1.addChild(sphere);
		} else {
			Appearance app_blue = new Appearance();
			ColoringAttributes blue = new ColoringAttributes();
			blue.setColor(0.0f, 0.4f, 0.8f);
			blue.setShadeModel(ColoringAttributes.NICEST);
			app_blue.setColoringAttributes(blue);
			ColorCube cc = new ColorCube(NODE_SIZE);
			cc.setAppearance(app_blue);
			TG1.addChild(cc);
		}
		float dX = x0 - x;
		dX = dX * dX;
		float dY = y0 - y;
		dY = dY * dY;
		float dZ = z0 - z;
		dZ = dZ * dZ;

		double r = Math.sqrt((double) dX + (double) dY + (double) dZ);

		double teta = Math.acos((double) (y - y0) / r);
		double phy = Math.acos((double) (x - x0) / (r * Math.sin(teta)));

		Cylinder cy = new Cylinder(EDGE_SECT, (float) r);

		Transform3D transedge = new Transform3D();
		
		transedge.set(new Vector3f(
		-(float) (Math.sqrt(dX) / 2), (float) Math.sqrt(dY) / 2, (float) Math
				.sqrt(dZ) / 2));
		
		
		Transform3D rotate = new Transform3D();
		rotate.rotZ(-teta);
		Transform3D rotateY = new Transform3D();
		rotateY.rotY(phy);
		transedge.mul(rotateY);
		transedge.mul(rotate);

		TransformGroup placeEdge = new TransformGroup(transedge);
		
		placeEdge.addChild(cy);

		TG1.addChild(placeEdge);

		mouseTransform.addChild(TG1);
		
		if (n instanceof DataTree) {
			DataTree dt = (DataTree) n;
			float list = 0;
			if (n instanceof ListNode)
				list = -0.3f;
			else
				list = -0.001f;
			int width = dt.getNodes().size();
			float xc = x;
			float zc = z;
			float coef = 1.07f;
			for (Enumeration<DataNode> edn = dt.getNodes().elements(); edn
					.hasMoreElements();) {
				DataNode dn = edn.nextElement();
				if (dn instanceof ListNode)
					draw(dn, x, y, z, x, (y - (1 / (float) (high)) * (float) 2),
							zc);
				else
					draw(dn, x, y, z, xc * coef, (y - (1 / (float) (high)) * (float) 2)*coef,
							(z + list)*coef);
				xc = xc + (float) 1 / (float) (width);
				zc = zc - (float) 1 / (float) (width);
				//coef *= coef;
			}
		}

	}

	public void clickEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		float xf = 1f / (float) x;
		float yf = 1f / (float) y;
		boolean found = false;
		GraphicalNode n = null;
		for (Enumeration<GraphicalNode> egn = nodes.elements(); egn
				.hasMoreElements()
				&& !found;) {
			n = egn.nextElement();
			found = xf == n.getX() && yf == n.getY();
			// System.out.println(n.getNode().getName() + " at " + n.getX() +
			// ", " + n.getY());
		}
		if (found)
			System.out.println(n.getNode().getName());

	}

}