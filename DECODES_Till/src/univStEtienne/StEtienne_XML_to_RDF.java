package univStEtienne;

import fonctionsUtiles.XMLfunctions;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class StEtienne_XML_to_RDF {
	
	
	public static void main(String[] args) {
		Document xmlStEtienne = XMLfunctions.readXML("bin/StEtienne.xml");
		
		NodeList nodes = xmlStEtienne.getElementsByTagName("cdmfr:exists");
		
		XMLfunctions.printNote(nodes);
		
	}
}
