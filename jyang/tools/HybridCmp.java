package jyang.tools;

import java.io.*;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class HybridCmp {

	private String inc = "  ";

	public HybridCmp(String fn1, String fn2) throws SAXException, IOException,
			ParserConfigurationException {
		File f1 = new File(fn1);
		File f2 = new File(fn2);
		Document doc1 = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(f1);
		Document doc2 = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(f2);

		System.out.println(cmp(doc1, doc2, ""));

	}

	private boolean cmp(Node n1, Node n2, String I) {

		if (!n1.hasChildNodes() && !n2.hasChildNodes()) {
			if (n1.getNodeName().compareTo(n2.getNodeName()) == 0)
				System.out.println(I + "cmp " + n1.getNodeName() + " "
						+ n2.getNodeName() + " : ok");
			else
				System.out.println(" : exit false");

			return n1.getNodeName().compareTo(n2.getNodeName()) == 0;
		}

		boolean allfound = false;

		if (n1.getNodeName().compareTo(n2.getNodeName()) == 0) {
			System.out.println(I + "cmp " + n1.getNodeName() + " "
					+ n2.getNodeName());

			Vector<Node> nodes = new Vector<Node>();

			for (int i = 0; i < n1.getChildNodes().getLength(); i++) {

				boolean found = false;
				Node C1 = n1.getChildNodes().item(i);
				Node C2 = null;
				for (int j = 0; j < n2.getChildNodes().getLength() && !found; j++) {
					C2 = n2.getChildNodes().item(j);
					found = cmp(C1, C2, I + inc);
					if (found) {
						nodes.add(C2);
					}
				}
				if (!found) {
					return false;
				}
			}
			if (nodes.size() == n1.getChildNodes().getLength()) {
				System.out.println(I + "cmp " + n1.getNodeName() + " "
						+ n2.getNodeName() + " OK");
				return true;
			} else
				return false;

		} else {
			return false;
		}
	}

	static public void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException {
		new HybridCmp(args[0], args[1]);
	}

}
