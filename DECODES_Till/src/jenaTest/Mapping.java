package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileReader;

import org.apache.jena.rdf.model.*;

import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;

public class Mapping {
  
  public static void FrComte() {  
	  //TODO path to XML
	  Document doc = readXML("bin/FrComte.XML");
	  
	  //TODO read input file: name of tag and name of RDF property
	  try {
		  FileReader fr = new FileReader("bin/FrComte_key.txt");
		  String line = null;
		  BufferedReader br
	  }
	  //initialize model
	  Model model = MyModel.initiliazeModel(); 
	  
	  //TODO loop: iterate over all the formations in the source.
	  //for now: only the first
	  NodeList allPrograms = doc.getElementsByTagName("formation");
	  Element formationNode = (Element) allPrograms.item(1);
	  
	  Resource formationResource = model.createResource(MyModel.uriRes+"formation_FrancheComte_1");
	  
	  // add formation name
	  findAndAddValueToProperty(formationNode, formationResource, model, "intitule-formation", "name");
	  
	  /*// add audience
	  String audience = getTextInTagDocument(doc, "targetGroup");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "Audience"), audience);
	  
	  // add RNCP (maybe won't work in other files ?)
	  Node validation = doc.getElementsByTagName("ametys-cdm:ujm-validation").item(0); 
	  NodeList validationChildren = validation.getChildNodes();
	  String RNCP = "";
	  //TODO
	  System.out.println(RNCP);
	  
	  // add prerequisites (can not do until we have more info) 
	  String requirements = getTextInTagDocument(doc, "formalPrerequisites");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "Requirements"), requirements);
	  
	  //add goals
	  String goals = getTextInTagDocument(doc, "learningObjectives");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "Goals"), goals);
	  
	  //add time organisation
	  String timeOrganisation = getTextInTagDocument(doc, "ametys-cdm:ujm-calendrier");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "TimeOrganisation"), timeOrganisation);
	  
	  //add grading methods
	  String gradingMethods = getTextInTagDocument(doc, "ametys-cdm:ujm-validation");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "GradingMethods"), gradingMethods);
	  
	  //add price
	  String price = getTextInTagDocument(doc, "ametys-cdm:ujm-specific-rights");
	  formationResource.addProperty(model.getProperty(MyModel.uriProp, "Price"), price);*/
	  
	  model.write(System.out);
	
}

  /**
   * This function allows us to get the text in a tag in an xml doc. Asserts that there is only one tag by the name given, returns null if not. 
   * @param doc The xml doc.
   * @param tagName The name of the tag where the text is. Make sure it refers to a unique tag.
   * @return The string of the text.
   */
 public static String getTextInTagDocument(Document doc, String tagName) {
	  NodeList nodeList = doc.getElementsByTagName(tagName);
	  if (nodeList.getLength()==1) {
		  String text = nodeList.item(0).getTextContent();
		  text = text.replace("\t", "");
		  return text;		  
	  }
	  else { return null ; }
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

  
  
  
  public static void main(String[] args) {
	Mapping.FrComte(); 
}
}
