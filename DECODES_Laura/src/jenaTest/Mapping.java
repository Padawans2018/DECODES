package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import org.apache.jena.rdf.model.*;

import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;

public class Mapping {
  
  public static void stEtienne() {  
	  Document doc = readXML("C:\\Users\\laura\\OneDrive\\Documents\\Centrale\\S8\\DECODES\\stEtienne\\example.xml");
  
	  Model model = MyModel.initiliazeModel(); 
	  
	  
	  Resource formation = model.createResource(MyModel.uriRes+"formation_StEtienne_1");
	  
	  NodeList test = doc.getElementsByTagName("cdmfr:programName");
	  String formationName= test.item(0).getTextContent();
	  
	  formation.addProperty(model.getProperty(MyModel.uriProp,"Name"), formationName);
	  
	  model.write(System.out);
	  
	
	  
  }
  
  
  public static Document readXML(String xmlPath) {
	  try {
		File fXmlFile = new File(xmlPath);
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

  public static void main(String[] args) {
	Mapping.stEtienne(); 
}
}
