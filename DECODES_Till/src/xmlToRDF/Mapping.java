package xmlToRDF;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;

public class Mapping {
	
	public static void mapping(String pathXML, String pathKeyDoc, String tagFormation, String institutionName){
		  Document doc = readXML(pathXML);
		  
		  HashMap<String,HashMap<String,String>> keysHM;
		  keysHM = KeyReader.readKeys(pathKeyDoc);
		  
		  HashMap<String, List<String>> linkTypeProp = Mapping.linkTypeAndProperties(); 
		  
		  HashMap<String, ArrayList<Resource>> allResources = new HashMap<String, ArrayList<Resource>>() ; 
		  allResources.put("formation", new ArrayList<Resource>());
		  allResources.put("location", new ArrayList<Resource>());
		  allResources.put("domain", new ArrayList<Resource>());
		  allResources.put("respo", new ArrayList<Resource>());
		  allResources.put("sector", new ArrayList<Resource>());
		  allResources.put("job", new ArrayList<Resource>());
		  allResources.put("label", new ArrayList<Resource>());
		  allResources.put("contact", new ArrayList<Resource>());
		  allResources.put("company", new ArrayList<Resource>());
		  allResources.put("authority", new ArrayList<Resource>());
		  
		  //initialize model
		  Model model = MyModel.initiliazeModel(); 
		  
		  NodeList allPrograms = doc.getElementsByTagName(tagFormation);
		  
		 	  
		  for (int i = 0 ; i<allPrograms.getLength() ; i++) {
			  Element formationNode = (Element) allPrograms.item(i);
			  
			  HashMap<String, Resource> typeResInit = new HashMap<String, Resource>();
			  HashMap<String, Resource> typeResFinal = new HashMap<String, Resource>();
			  
			  
			  Resource formationResource = model.createResource(MyModel.uriRes+"formation" + "_" + institutionName + "_" +i);
			  Resource locationResource = model.createResource(MyModel.uriRes+"location" + "_" + institutionName + "_" +i);
			  Resource domainResource = model.createResource(MyModel.uriRes+"domain" + "_" + institutionName + "_" +i);
			  Resource respoResource = model.createResource(MyModel.uriRes+"respo" + "_" + institutionName + "_" +i);
			  Resource sectorResource = model.createResource(MyModel.uriRes+"sector" + "_" + institutionName + "_" +i);
			  Resource jobResource = model.createResource(MyModel.uriRes+"job" + "_" + institutionName + "_" +i);
			  Resource labelResource = model.createResource(MyModel.uriRes+"label" + "_" + institutionName + "_" +i);
			  Resource contactResource = model.createResource(MyModel.uriRes+"contact" + "_" + institutionName + "_" +i);
			  Resource companyResource = model.createResource(MyModel.uriRes+"company" + "_" + institutionName + "_" +i);
			  Resource authorityResource = model.createResource(MyModel.uriRes+"authority" + "_" + institutionName + "_" +i);
			  		  
			  typeResInit.put("formation", formationResource);	  
			  typeResInit.put("location", locationResource);
			  typeResInit.put("domain", domainResource);
			  typeResInit.put("respo", respoResource);
			  typeResInit.put("sector", sectorResource);
			  typeResInit.put("job", jobResource);
			  typeResInit.put("label", labelResource);
			  typeResInit.put("contact", contactResource);
			  typeResInit.put("company", companyResource);
			  typeResInit.put("authority", authorityResource);
			    
			 			  
			  for (String type : linkTypeProp.keySet()) {
				  
				  Resource res = typeResInit.get(type.toLowerCase());
				  
				  for (String prop : linkTypeProp.get(type)) {
					  
					  String tag = keysHM.get(type.toLowerCase()).get(prop);
				
					  //System.out.println("Type: "+type+"; Tag: "+ tag+"; Prop: "+prop);
					  if (tag!=null){
						  findAndAddValueToProperty(formationNode, res , model, prop, tag);
					  }
					  
				  }
				  
				  Resource resIdentical = Mapping.findIdenticalResourceIfExists(model, res, allResources.get(type), type);
				  if (resIdentical==null) {
					  allResources.get(type).add(res) ;
					  typeResFinal.put(type, res) ;
				  }
				  else {
					  typeResFinal.put(type, resIdentical) ; 
				  }
			  }			  
			  
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"takesPlaceIn"), typeResFinal.get("location"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isPartOf"), typeResFinal.get("domain"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isSubmittedBy"), typeResFinal.get("respo"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"givesOpportunitiesIn"), typeResFinal.get("sector"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"givesOpportunitiesIn"), typeResFinal.get("job"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"has"), typeResFinal.get("label"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isManagedBy"), typeResFinal.get("contact"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isWelcomedBy"), typeResFinal.get("company"));
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isCertifiedBy"), typeResFinal.get("authority"));
			  
		  }	  
		  
		  
		  //model.write(System.out);
		  printModel(model, institutionName + "Output");
	}
 
/**
 * This function allows us to export a model to an .rdf file
 * @param model The model we want to export
 * @param fileName The name of the .rdf that will be created
 */
public static void printModel(Model model, String fileName)
{
	File file = new File("fichiers/output/" + fileName + ".rdf");
	  try {
		file.createNewFile();
		OutputStream stream = new DataOutputStream(new FileOutputStream(file));
		RDFDataMgr.write(stream, model, Lang.RDFXML);

		stream.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
 
 /**
  * This function allows us to get the text in a tag in an xml doc. Calls the function getElementsByTagList, therefore returns null element if the String tags is null or empty. 
  * @param node The node in which we search for tags
  * @param formation The resource we want to modify with what we find in the xml document
  * @param model The model to which the resource is linked
  * @param tags The tree structure of tags, separated by "/", allowing us to get to the tag containing useful information.
  * @param propertyName The property matching the tag. 
  */
public static void findAndAddValueToProperty(Element node, Resource formation, Model model, String propertyName, String tags) {
	 ArrayList<String> tagsList = new ArrayList<String>(Arrays.asList(tags.split("/"))); 
	 Node mainNode = Mapping.getElementsByTagList(node, tagsList);
	 if (mainNode!=null) {
		 String text = mainNode.getTextContent();
		 text = text.replace("\t", ""); //Suppress uselesstabs
		 formation.addProperty(model.getProperty(MyModel.uriProp,propertyName), text);
	 }
}

/**
 * This function allows us to get the Node in a model corresponding to the child of a list of tags.
 * @param node The node in which we want to find the tag
 * @param tagsList A list of tag, each the parent of the following one
 * @return
 */
public static Node getElementsByTagList(Element node, ArrayList<String> tagsList) {
	 Element mainNode = null;
	 NodeList nodeL ;
	 
	 //System.out.println(tagsList);
	 
	 if (tagsList.isEmpty()) { 
		 System.out.println("Tags list was empty, we returned null element.");
		 return null ;
	 }
	 else {
		 nodeL =node.getElementsByTagName(tagsList.get(0)) ;
		 mainNode = (Element) nodeL.item(0);
		 
		 if (nodeL.getLength()!=1) {
		/*	 if (nodeL.getLength()==0) {System.out.println("There was no \"" + tagsList.get(0) + "\" tag.");}
			 else {System.out.println("There was more than one \"" + tagsList.get(0) + "\" tag.");}*/
		 }
	 
		 for (int i = 1; i < tagsList.size() ; i++) {
			 nodeL = mainNode.getElementsByTagName(tagsList.get(i)); 
			 mainNode = (Element) nodeL.item(0);
			 if (nodeL.getLength()!=1) {
				 /*if (nodeL.getLength()==0) {System.out.println("There was no \"" + tagsList.get(i) + "\" tag.");}
				 else {System.out.println("There was more than one \"" + tagsList.get(i) + "\" tag.");}*/
				 break; 
		 	 }
		 }
	 }
	
	 return mainNode ; 
	 
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
   * This function reads a document explaining which properties match with which resources type
   * @return A HashMap linking type with corresponding properties. 
   */
  public static HashMap<String, List<String>> linkTypeAndProperties() { 
	  FileReader file = null;
	  BufferedReader reader = null;
	  HashMap<String, List<String>> typeProperty = new HashMap<String, List<String>>(); 
		
		try{
			String fileName = "bin/xmlToRDF/linkTypeProperty.txt";
			file = new FileReader(fileName);
			
			reader = new BufferedReader(file);
			String line = "";
			String type ;
			List<String> properties ;
			
			
			while ((line = reader.readLine()) != null){
				String[] all = line.split(" ");
				ArrayList<String> allnew = new ArrayList<String>(Arrays.asList(all));
				type = all[0];
				properties = allnew.subList(1, all.length ); 
				typeProperty.put(type.toLowerCase(), properties);
			} 
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{}
		return typeProperty ; 
	}
  
  
  public static Resource findIdenticalResourceIfExists(Model model, Resource resInitial, ArrayList<Resource> resourceList, String resourceType){
	
	  for (Resource res : resourceList) {
		  if (Mapping.resourcesAreIdentical(resInitial, res, resourceType, model)) {
			  return res;
		  }
	  }
	  
	  return null ; 
	  
  }
  
  public static boolean resourcesAreIdentical (Resource resA, Resource resB, String resourceType, Model model) {
	  boolean isIdentical = true ;
	  HashMap<String, List<String>> typeProperty = Mapping.linkTypeAndProperties();
	  
	  for (String propS : typeProperty.get(resourceType)) {
		  Property prop = model.getProperty(MyModel.uriProp + propS);
			 
		  String objA = "";
		  String objB = "" ;
			 
		  if (resA.hasProperty(prop)) {
			  objA = resA.getProperty(prop).getString(); 
		  }
		  if (resB.hasProperty(prop)) {
			  objB = resB.getProperty(prop).getString(); 
		  }
			 
		  if (!objA.equalsIgnoreCase(objB)) {
			  isIdentical = false ; 
		  }	 	
	  }
	  
	  return isIdentical ; 
	  
	  
  }
  


  
  
  
  /*public static void main(String[] args) {
	Mapping.mapping("bin/FrComte.xml", "bin/FrComte_key.txt", "formation", "FrancheComte"); 
}*/
}
