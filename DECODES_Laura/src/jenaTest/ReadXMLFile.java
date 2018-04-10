package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;


public class ReadXMLFile {
  
  public void reunion() {  
	  
  }
  
  public Document readXML(String xmlPath) {
	  try {
		File fXmlFile = new File("C:\\Users\\laura\\OneDrive\\Documents\\Centrale\\S8\\DECODES\\reunion\\daeuaSocial.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
			
		doc.getDocumentElement().normalize();

		return(doc);
		
	  }
	  catch (Exception e) {
		e.printStackTrace();
		return(null);
	  }	    		  
  }

}
