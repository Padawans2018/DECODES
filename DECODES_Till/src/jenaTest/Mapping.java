package jenaTest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.rdf.model.*;

import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;

public class Mapping {
  
  public static void FrComte() {  
	  //TODO path to XML
	  Document doc = readXML("bin/FrComte.XML");
	  ArrayList<HashMap<String,String>> listKeys;
	  listKeys = new KeyReader().readKeys("bin/FrComte_key.txt");
	  
	  HashMap<String, ArrayList<String>> predicates;
	  //predicates = magicalLauraFunction(path);
	  
	  //initialize model
	  Model model = MyModel.initiliazeModel(); 
	  
	  //TODO loop: iterate over all the formations in the source.
	  //for now: only the first
	  NodeList allPrograms = doc.getElementsByTagName("formation");
	  Element formationNode = (Element) allPrograms.item(0);
	  
	  int i = 1;
	  
	  Resource formationResource = model.createResource(MyModel.uriRes+"formation_FrancheComte_"+i);
	  Resource locationResource = model.createResource(MyModel.uriRes+"location_FrancheComte_"+i);
	  Resource domainResource = model.createResource(MyModel.uriRes+"domain_FrancheComte_"+i);
	  Resource respoResource = model.createResource(MyModel.uriRes+"respo_FrancheComte_"+i);
	  Resource sectorResource = model.createResource(MyModel.uriRes+"sector_FrancheComte_"+i);
	  Resource jobResource = model.createResource(MyModel.uriRes+"job_FrancheComte_"+i);
	  Resource labelResource = model.createResource(MyModel.uriRes+"label_FrancheComte_"+i);
	  Resource contactResource = model.createResource(MyModel.uriRes+"contact_FrancheComte_"+i);
	  Resource companyResource = model.createResource(MyModel.uriRes+"company_FrancheComte_"+i);
	  Resource authorityResource = model.createResource(MyModel.uriRes+"authority_FrancheComte_"+i);

	  
	  
	  // add formation name
	  findAndAddValueToProperty(formationNode, formationResource, model, "intitule-formation", "name");
	  
	  // add audience
	  findAndAddValueToProperty(formationNode, formationResource, model, "rythme-formation", "typology");
	  /*String audience = getTextInTagDocument(doc, "targetGroup");
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
  * This function finds a Value in the formation given the name of the tag and saves it into the property given.
  * @param node the formation
  * @param formation current RDF resource
  * @param model entire RDF model
  * @param tagName The name of the tag where the text is. 
  * @param propertyName The name of the RDF predicate's resource
  * @return The string of the text.
  */
public static void findAndAddValueToProperty(Element node, MyResource formation, Model model, String tagName, String propertyName) {
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
