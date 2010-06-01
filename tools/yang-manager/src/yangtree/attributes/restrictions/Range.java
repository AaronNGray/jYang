package yangtree.attributes.restrictions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Range implements Serializable {

	private int min;
	private int max;

	public Range(String[] minmax) {
		this.min = new Integer(minmax[0]);
		this.max = new Integer(minmax[1]);
	}

	public boolean isInRange(Double value) {
		return value <= max && value >= min;
	}

	public String toString() {
		if (min == max) {
			return "{" + min+ "}";
		} else {
			return "[" + min + ".." + max + "]";
		}
	}

}
