package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;


public class ReadXMLFile {

  public static void main(String[] args) {

    try {

	File fXmlFile = new File("C:\\Users\\laura\\OneDrive\\Documents\\Centrale\\S8\\DECODES\\stEtienne\\example.xml");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
		
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}
