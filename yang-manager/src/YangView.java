import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import datatree.*;

public class YangView {

	private DataNode model = null;

	public YangView(DataNode r) {
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

	public BranchGroup createSceneGraph(Canvas3D canvas3d) {
		high = getHigh(model);
		objRoot = new BranchGroup();
		draw(model, 0, 0, 0);

		Background background = new Background((float) 0.5, (float) 0.5,
				(float) 0.5);
		background.setApplicationBounds(new BoundingBox());

		objRoot.addChild(background);
		return objRoot;
	}

	private void draw(DataNode n, float x, float y, float z) {

		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(x, y, z));
		TransformGroup TG1 = new TransformGroup(translate1);

		if (n instanceof LeafNode) {
			Appearance app_orang = new Appearance();
			ColoringAttributes orang = new ColoringAttributes();
			orang.setColor(0.8f, 0.4f, 0.2f);
			orang.setShadeModel(ColoringAttributes.NICEST);
			app_orang.setColoringAttributes(orang);
			TG1.addChild(new Sphere((float) 0.05, app_orang));
		} else {
			Appearance app_blue = new Appearance();
			ColoringAttributes blue = new ColoringAttributes();
			blue.setColor(0.0f, 0.4f, 0.8f);
			blue.setShadeModel(ColoringAttributes.NICEST);
			app_blue.setColoringAttributes(blue);
			ColorCube cc = new ColorCube(0.05);
			cc.setAppearance(app_blue);

			Text3D t3d = new Text3D(new Font3D(new Font("Helvetica",
					Font.PLAIN, 1), new FontExtrusion()), n.getName());

			t3d.setAlignment(Text3D.ALIGN_CENTER);
			Shape3D s3d = new Shape3D(t3d);
			Appearance blk = new Appearance();
			ColoringAttributes black = new ColoringAttributes();
			black.setColor(0, 0, 0);
			blk.setColoringAttributes(black);
			s3d.setAppearance(blk);

			Transform3D translate2 = new Transform3D();
			translate2.set(new Vector3f(x, y, z));

			Transform3D scale = new Transform3D();
			scale.setScale(0.2);
			translate2.mul(scale);

			TransformGroup TG2 = new TransformGroup(translate2);
			TG2.addChild(s3d);

			TG1.addChild(cc);
			TG1.addChild(TG2);
		}

		objRoot.addChild(TG1);

		if (n instanceof DataTree) {
			DataTree dt = (DataTree) n;
			float list = 0;
			if (n instanceof ListNode)
				list = (float) -2;
			int width = dt.getNodes().size();
			float xc = x;
			for (Enumeration<DataNode> edn = dt.getNodes().elements(); edn
					.hasMoreElements();) {
				DataNode dn = edn.nextElement();
				draw(dn, xc, y - (1 / (float) (high)) * (float) 2, z + list);
				xc = xc + 1 / (float) (width);
			}
		}

	}

}
