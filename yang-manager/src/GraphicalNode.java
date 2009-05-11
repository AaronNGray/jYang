import datatree.*;
import java.util.*;

public class GraphicalNode {

	DataNode node  = null;
	
	public GraphicalNode(DataNode n, float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		node = n;
	}
	public DataNode getNode() {
		return node;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	float x, y, z;
	
	
}
