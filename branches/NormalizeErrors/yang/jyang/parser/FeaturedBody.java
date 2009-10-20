package jyang.parser;

import java.util.Vector;

public abstract class FeaturedBody extends StatuedBody {

	private Vector<YANG_IfFeature> ifFeatures = new Vector<YANG_IfFeature>();

	public FeaturedBody(int id) {
		super(id);
	}

	public FeaturedBody(yang p, int id) {
		super(p, id);
	}

	public Vector<YANG_IfFeature> getIfFeatures() {
		return ifFeatures;
	}

	public void addIfFeature(YANG_IfFeature i) {
		ifFeatures.add(i);
	}

	public boolean isBracked() {
		return ifFeatures.size() != 0;
	}

	public String toString() {
		String result = "";
		for (YANG_IfFeature ifs : ifFeatures)
			result += ifs.toString() + "\n";
		return result;
	}

}