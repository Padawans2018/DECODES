package jenaTest;

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
		  
		  //initialize model
		  Model model = MyModel.initiliazeModel(); 
		  
		  NodeList allPrograms = doc.getElementsByTagName(tagFormation);
		  
		  for (int i = 0 ; i<allPrograms.getLength() ; i++) {
			  Element formationNode = (Element) allPrograms.item(i);
			  
			  HashMap<String, Resource> linkTypeRes = new HashMap<String, Resource>();
			  
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
			  
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"takesPlaceIn"), locationResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isPartOf"), domainResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isSubmittedBy"), respoResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"givesOpportunitiesIn"), sectorResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"givesOpportunitiesIn"), jobResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"has"), labelResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isManagedBy"), contactResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isWelcomedBy"), companyResource);
			  formationResource.addProperty(model.getProperty(MyModel.uriProp,"isCertifiedBy"), authorityResource);
			  
			  linkTypeRes.put("formation", formationResource);	  
			  linkTypeRes.put("location", locationResource);
			  linkTypeRes.put("domain", domainResource);
			  linkTypeRes.put("respo", respoResource);
			  linkTypeRes.put("sector", sectorResource);
			  linkTypeRes.put("job", jobResource);
			  linkTypeRes.put("label", labelResource);
			  linkTypeRes.put("contact", contactResource);
			  linkTypeRes.put("company", companyResource);
			  linkTypeRes.put("authority", authorityResource);

			  
			  for (String type : linkTypeProp.keySet()) {
				  for (String prop : linkTypeProp.get(type)) {
					  
					  Resource res = linkTypeRes.get(type.toLowerCase()); 
					  String tag = keysHM.get(type.toLowerCase()).get(prop);
				
					  //System.out.println("Type: "+type+"; Tag: "+ tag+"; Prop: "+prop);
					  if (tag!=null){
						  findAndAddValueToProperty(formationNode, res , model, prop, tag);
					  }
					  
				  }
			  }
		  }	  
		  
		  
		  model.write(System.out);
		  printModel(model, institutionName + "Output");
	}
 
/**
 * This function allows us to export a model to an .rdf file
 * @param model The model we want to export
 * @param fileName The name of the .rdf that will be created
 */
public static void printModel(Model model, String fileName)
{
	File file = new File("bin/" + fileName + ".rdf");
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
			 if (nodeL.getLength()==0) {System.out.println("There was no \"" + tagsList.get(0) + "\" tag.");}
			 else {System.out.println("There was more than one \"" + tagsList.get(0) + "\" tag.");}
		 }
	 
		 for (int i = 1; i < tagsList.size() ; i++) {
			 nodeL = mainNode.getElementsByTagName(tagsList.get(i)); 
			 mainNode = (Element) nodeL.item(0);
			 if (nodeL.getLength()!=1) {
				 if (nodeL.getLength()==0) {System.out.println("There was no \"" + tagsList.get(i) + "\" tag.");}
				 else {System.out.println("There was more than one \"" + tagsList.get(i) + "\" tag.");}
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
			String fileName = "bin/linkTypeProperty.txt";
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

  
  
  
  public static void main(String[] args) {
	Mapping.mapping("bin/FrComte.xml", "bin/FrComte_key.txt", "formation", "FrancheComte"); 
}
}
