package xmlToRDF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RiotException;
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
	
	public static void writeHTML(String pathRDF, String institutionName, int numberFormations) {
		
		  
		 //initialize model, in order to get the number of formations in it
		 Model model = MyModel.initiliazeModel(); 
		 
		 for (int i = 0 ; i<numberFormations ; i++) {
			 try{
				 WriteHTML.writeFormationHTML(pathRDF, institutionName, i);
			 }
			 catch(RiotException e){System.out.println(e.getMessage() + "### Formation No: "+ i);}
		 }
	}
	
	public static void writeOneHTML(String pathXML, String pathRDF, String institutionName, String tagFormation, int numberOfFormation) {
		System.out.println(numberOfFormation);
		 try{
				 WriteHTML.writeFormationHTML(pathRDF, institutionName, numberOfFormation);
			 }
			 catch(RiotException e){System.out.println(e.getMessage() + "### Formation No: "+ numberOfFormation);}
	}
	
	public static void writeFormationHTML(String pathRDF, String institutionName, int indexFormation) {
		
		try {
			  InputStreamReader in = new InputStreamReader(new FileInputStream("docs/formationTemplate.html"), "utf-8");
			  BufferedReader br = new BufferedReader(in);
			 			  
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
				  //BufferedWriter bw = new BufferedWriter(new FileWriter("docs/"+institutionName+"/"+institutionName+"_"+indexFormation+".html"));
				  OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("docs/"+institutionName+"/"+institutionName+"_"+indexFormation+".html"),"UTF-8");
				  out.write(content);
				  out.close();
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
	
	
}
