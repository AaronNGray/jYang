package jyang.tools;

import java.io.PrintStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import jyang.parser.*;

public class Yang2Ensuite {

	public Yang2Ensuite(YANG_Specification n, String[] paths, PrintStream out) {

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

}
