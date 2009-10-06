package jyang.tools;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import jyang.parser.*;

public class Yang2Ensuite {

	private Hashtable<String, YANG_DataDef> datadefs = new Hashtable<String, YANG_DataDef>();

	public Yang2Ensuite(YANG_Specification n, String[] paths, PrintStream out) {

		setDataDefRoots(n, paths);

		//setModule(n, paths, out);
	}

	private void setModule(YANG_Specification n, String[] paths, PrintStream out) {

		XMLStreamWriter xmlout;
		try {
			xmlout = XMLOutputFactory.newInstance().createXMLStreamWriter(out);
			String modulename = n.getName();
			String namespace = n.getNameSpace().getNameSpace();
			String prefix = null;
			if (n.getPrefix() != null)
				prefix = n.getPrefix().getPrefix();
			xmlout.setDefaultNamespace(namespace);
			xmlout.writeStartElement(namespace, "module");
			xmlout.writeCharacters("\n  ");
			xmlout.writeStartElement("name");
			xmlout.writeCharacters(modulename);
			xmlout.writeEndElement();
			xmlout.writeCharacters("\n  ");
			xmlout.writeStartElement("xpath");
			xmlout.writeCharacters(namespace);
			xmlout.writeEndElement();
			xmlout.writeCharacters("\n  ");
			xmlout.writeStartElement("namespace");
			xmlout.writeAttribute("pref", prefix);
			xmlout.writeCharacters(namespace);
			xmlout.writeEndElement();

			xmlout.writeCharacters("\n");
			xmlout.writeEndElement();
			out.flush();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setDataDefRoots(YANG_Specification s, String[] paths) {
		try {
			for (Enumeration<YANG_Specification> es = s.getIncludedSubModules(paths).elements(); es.hasMoreElements();)
				setDataDefRoots(es.nextElement(), paths);
			for (Enumeration<YANG_Body> ed = s.getBodies().elements(); ed
					.hasMoreElements();) {
				YANG_Body body = ed.nextElement();
				if (body instanceof YANG_DataDef) {
					datadefs.put(body.getBody(), (YANG_DataDef) body);
					System.out.println(body.getBody());
				}
			}
		} catch (YangParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
