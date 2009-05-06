import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
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

	public BranchGroup createSceneGraph() {
		high = getHigh(model);
		objRoot = new BranchGroup();
		draw(model, 0, (float)0.7, 0);
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
		} else
			TG1.addChild(new ColorCube(0.05));
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
				draw(dn, xc, y - (1 / (float) (high))*(float)2, z + list);
				xc = xc + 1 / (float) (width);
			}
		}

	}

}
