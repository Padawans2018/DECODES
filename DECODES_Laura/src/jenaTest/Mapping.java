package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import java.io.File;

import org.apache.jena.rdf.model.*;

import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;


public class Mapping {
  
  public static void stEtienne() {  
	  Document doc = readXML("C:\\Users\\laura\\OneDrive\\Documents\\Centrale\\S8\\DECODES\\stEtienne\\example.xml");
  
	  Model model = MyModel.initiliazeModel(); 
	  
	  Resource formation = model.createResource(MyModel.uriRes+"formation_StEtienne_1");
	  
	  // add formation name
	  String formationName= getTextInTag(doc, "cdmfr:programName");
	  formation.addProperty(model.getProperty(MyModel.uriProp,"Name"), formationName);
	  
	  // add audience
	  String audience = getTextInTag(doc, "targetGroup");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "Audience"), audience);
	  
	  // add RNCP (maybe won't work in other files ?)
	  Node validation = doc.getElementsByTagName("ametys-cdm:ujm-validation").item(0); 
	  NodeList validationChildren = validation.getChildNodes();
	  String RNCP = "";
	  //TODO
	  System.out.println(RNCP);
	  
	  // add prerequisites (can not do until we have more info) 
	  String requirements = getTextInTag(doc, "formalPrerequisites");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "Requirements"), requirements);
	  
	  //add goals
	  String goals = getTextInTag(doc, "learningObjectives");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "Goals"), goals);
	  
	  //add time organisation
	  String timeOrganisation = getTextInTag(doc, "ametys-cdm:ujm-calendrier");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "TimeOrganisation"), timeOrganisation);
	  
	  //add grading methods
	  String gradingMethods = getTextInTag(doc, "ametys-cdm:ujm-validation");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "GradingMethods"), gradingMethods);
	  
	  //add price
	  String price = getTextInTag(doc, "ametys-cdm:ujm-specific-rights");
	  formation.addProperty(model.getProperty(MyModel.uriProp, "Price"), price);
	  model.write(System.out);
	
}

  /**
   * This function allows us to get the text in a tag in an xml doc. Asserts that there is only one tag by the name given, returns null if not. 
   * @param doc The xml doc.
   * @param tagName The name of the tag where the text is. Make sure it refers to a unique tag.
   * @return The string of the text.
   */
 public static String getTextInTag(Document doc, String tagName) {
	  NodeList nodeList = doc.getElementsByTagName(tagName);
	  if (nodeList.getLength()==1) {
		  String text = nodeList.item(0).getTextContent();
		  text = text.replace("\t", "");
		  return text;		  
	  }
	  else { return null ; }
 }
  
  /**
   * This function allows us to get a Document on which DOM methods can be used.
   * @param xmlPath The path to the xml file in your computer
   * @return the Document corresponding to the XML file
   */
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
  
  
  
  
  /**
   * This function allows us to get the text in a tag in an xml doc. Asserts that there is only one tag by the name given, returns null if not. 
   * @param doc The xml doc.
   * @param tagName The name of the tag where the text is. Make sure it refers to a unique tag.
   * @return The string of the text.
   */
 public static void findAndAddValueToProperty(Element node, Resource formation, Model model, String tagName, String propertyName) {
 	  NodeList nodeList = node.getElementsByTagName(tagName);
 	  if (nodeList.getLength()==1) {
 		  String text = nodeList.item(0).getTextContent();
 		  text = text.replace("\t", "");
 		  formation.addProperty(model.getProperty(MyModel.uriProp,propertyName), text);
 	  }
 	 
 }
 
 /*
 public static NodeList getElementsByTagList(Document doc, ArrayList<String> tagsList) {
	 if (tagsList.isEmpty()) { return null ;}
	 else {
	 
		 NodeList nodeL = doc. ; 
		 //TODO everything
	 
	 
	 File fXmlFile = new File("frcompte");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
			
		doc.getDocumentElement().normalize();
	 
	
	 for (String tag : tagsList) {
		 nodeL = node.get
	 }
	 }
		
	return nodeL ; 
 	}
   */
  
  
  
  
  
  public static HashMap<String, List<String>> linkTypeAndProperties() { 
	  FileReader file = null;
	  BufferedReader reader = null;
	  HashMap<String, List<String>> typeProperty = new HashMap<String, List<String>>(); 
		
		try{
			String fileName = "linkTypeProperty.txt";
			file = new FileReader(fileName);
			
			reader = new BufferedReader(file);
			String line = "";
			String type ;
			List<String> properties ;
			
			
			while ((line = reader.readLine()) != null){
				String[] all = line.split(" ");
				ArrayList<String> allnew = new ArrayList<>(Arrays.asList(all));
				type = all[0];
				properties = allnew.subList(1, all.length -1); 
				typeProperty.put(type, properties);
				System.out.println(line);
			} 
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{}
		return typeProperty ; 
	}
  

  
  
  
  public static void main(String[] args) {
	Mapping.stEtienne();
  }
  
}
