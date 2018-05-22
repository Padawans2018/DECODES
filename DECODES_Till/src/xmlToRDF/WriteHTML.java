package xmlToRDF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



public class WriteHTML {
	//Singleton pattern
	private WriteHTML() {}
	private static final WriteHTML INSTANCE = new WriteHTML();
	public static WriteHTML getInstance() {
		return INSTANCE;
	}
	
	public static void writeHTML(String pathXML, String pathRDF, String institutionName, String tagFormation) {
		Document doc = Mapping.readXML(pathXML);
		  
		 //initialize model, in order to get the number of formations in it
		 Model model = MyModel.initiliazeModel(); 
		 NodeList allPrograms = doc.getElementsByTagName(tagFormation);
		 int numberFormations = allPrograms.getLength();
		 
		 for (int i = 0 ; i<numberFormations ; i++) {
			 WriteHTML.writeFormationHTML(pathRDF, institutionName, i);
		 }
	}
	
	public static void writeFormationHTML(String pathRDF, String institutionName, int indexFormation) {
		
		try {
			  FileReader f = new FileReader("docs/formationTemplate.html");
			  BufferedReader br = new BufferedReader(f);
			  
			  HashMap<String, String> propsAndObj = WriteHTML.readFormationRDF(pathRDF, institutionName, indexFormation);
			  String content = "" ; 
			  String line = "" ;
			  while ((line = br.readLine()) != null)
			  {
				  content += "\n" + line ; 
			  }
			  
			  for (String prop : propsAndObj.keySet()) {
				  String obj = propsAndObj.get(prop);
				  if (obj!=null) {
					  content = content.replace("$" + prop, obj);
					  //System.out.println(prop + " : " + obj);
				  }
				  else {
					  content = content.replace("$" + prop, "MISSING INFORMATION");
				  }
			  }
			  
			  //System.out.println(content);

			  try {
				  BufferedWriter bw = new BufferedWriter(new FileWriter("docs/"+institutionName+"/"+institutionName+"_"+indexFormation+".html"));
				  bw.write(content);
				  bw.close();
			  }
		
			  catch (Exception e) {
				  e.printStackTrace();		
			  }
			  
			  br.close();
			  
		}
		catch (Exception e) {
			  e.printStackTrace();		
		}
		
		
	}
	
	public static HashMap<String, String> readFormationRDF(String pathRDF, String institutionName, int indexFormation) {
		 Model model = ModelFactory.createDefaultModel();
		 ArrayList<String> allProps = WriteHTML.getAllProperties();
		 HashMap<String, String> propsAndObj = new HashMap<String, String>();
		 

		 try {
			 // use the FileManager to find the input file
			 InputStream in = FileManager.get().open(pathRDF);
			 
			 // read the RDF/XML file
			 model.read(in, null);
			 
			 //model.write(System.out);
			 
			 
			 for (String propS : allProps) {
				 String type = propS.split("/")[0];
				 String p = propS.split("/")[1];
				 Resource res = model.getResource(MyModel.uriRes+type+"_" + institutionName+ "_" + indexFormation);
				 Property prop = model.getProperty(MyModel.uriProp + p);
				 
				 //System.out.println(formation);
				 //System.out.println(prop);
				 
				 if (res.hasProperty(prop)) {
					 String obj = res.getProperty(prop).getString(); 
					 propsAndObj.put(propS, obj); 
				 }
				 else 
				 {
					 propsAndObj.put(propS, null);
				 }
			 }
			 
			 //System.out.println(propsAndObj);
			 
		}
		catch (Exception e) {
			e.printStackTrace();		
		}
		 
		return propsAndObj ; 
	}
	
	public static ArrayList<String> getAllProperties(){
		HashMap<String, List<String>> linkTypeProp = Mapping.linkTypeAndProperties();
		
		ArrayList<String> allProps = new ArrayList<String>(); 
		
		String correctProp ;
		for (String type : linkTypeProp.keySet()) {
			for (String prop : linkTypeProp.get(type)) {
				correctProp = type.toLowerCase() + "/" + prop ; 
				allProps.add(correctProp);
				//System.out.println("**" + correctProp);
			}
		}
		
		
		return allProps ;
			  
	}
	
	public static void main(String[] args) {
		//WriteHTML.writeHTML("fichiers/FrComte.xml", "fichiers/output/FrancheComteOutput.rdf", "FrancheComte", "formation");
		//WriteHTML.writeFormationHTML("fichiers/output/FrComteOutput.rdf", "FrancheComte", 3);
	}
	
}
