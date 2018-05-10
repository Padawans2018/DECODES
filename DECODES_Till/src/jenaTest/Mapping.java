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
  
  public static void FrComte() {  
	  //TODO path to XML
	  Document doc = readXML("bin/FrComte.XML");
	  HashMap<String,HashMap<String,String>> keysHM;
	  keysHM = KeyReader.readKeys("bin/FrComte_key.txt");
	  
	  HashMap<String, List<String>> linkTypeProp = Mapping.linkTypeAndProperties(); 
	  
	  //initialize model
	  Model model = MyModel.initiliazeModel(); 
	  
	  //TODO loop: iterate over all the formations in the source.
	  //for now: only the first
	  NodeList allPrograms = doc.getElementsByTagName("formation");
	  
	  for (int i = 0 ; i<allPrograms.getLength() ; i++) {
		  Element formationNode = (Element) allPrograms.item(i);
		  
		  HashMap<String, Resource> linkTypeRes = new HashMap<String, Resource>();
		  
		  linkTypeRes.put("formation", model.createResource(MyModel.uriRes+"formation_FrancheComte_"+i));	  
		  linkTypeRes.put("location", model.createResource(MyModel.uriRes+"location_FrancheComte_"+i));
		  linkTypeRes.put("domain", model.createResource(MyModel.uriRes+"domain_FrancheComte_"+i));
		  linkTypeRes.put("respo", model.createResource(MyModel.uriRes+"respo_FrancheComte_"+i));
		  linkTypeRes.put("sector", model.createResource(MyModel.uriRes+"sector_FrancheComte_"+i));
		  linkTypeRes.put("job", model.createResource(MyModel.uriRes+"job_FrancheComte_"+i));
		  linkTypeRes.put("label", model.createResource(MyModel.uriRes+"label_FrancheComte_"+i));
		  linkTypeRes.put("contact", model.createResource(MyModel.uriRes+"contact_FrancheComte_"+i));
		  linkTypeRes.put("company", model.createResource(MyModel.uriRes+"company_FrancheComte_"+i));
		  linkTypeRes.put("authority", model.createResource(MyModel.uriRes+"authority_FrancheComte_"+i));
		  
		  
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
	  printModel(model);
	  
	
}
  
public static void printModel(Model model)
{
	File file = new File("bin/output.txt");
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
  * This function allows us to get the text in a tag in an xml doc. Calls the function getElementsByTagList, therefore returns null element if the String tags is null or empty. 
  * @param node The node in which we search for tags
  * @param formation The resource we want to modify with what we find in the xml document
  * @param model The model to which the resource is linked
  * @param tags The tree structure of tags, separated by "/", allowing us to get to the tag containing useful information.
  * @param propertyName The property matching the tag. 
  */
public static void findAndAddValueToProperty(Element node, Resource formation, Model model, String propertyName, String tags) {
	 ArrayList<String> tagsList = new ArrayList<>(Arrays.asList(tags.split("/"))); 
	 Node mainNode = Mapping.getElementsByTagList(node, tagsList);
	 if (mainNode!=null) {
		 String text = mainNode.getTextContent();
		 text = text.replace("\t", "");
		 formation.addProperty(model.getProperty(MyModel.uriProp,propertyName), text);
	 }
}


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
			 System.out.println("First tag was not unique.");
		 }
	 
		 for (int i = 1; i < tagsList.size() ; i++) {
			 nodeL = mainNode.getElementsByTagName(tagsList.get(i)); 
			 mainNode = (Element) nodeL.item(0);
			 if (nodeL.getLength()!=1) {
				 System.out.println("One of the tag, not the first, was not unique."); 
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
				properties = allnew.subList(1, all.length -1); 
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
	Mapping.FrComte(); 
}
}
