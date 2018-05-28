package stEtienne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;

import xmlToRDF.Mapping;
import xmlToRDF.WriteHTML;

public class Reader {
	public static void main(String[] args) {
		File dir = new File("fichiers/StEtienne");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			int i = 0;
			for (File child : directoryListing) {
				//file name starts with a number (== is a formation)
				if(child.getName().charAt(0)<58){
					String nameDirectory = "fichiers/stEtienne";
					//Reader.normalizeXML(nameDirectory+"/cnam.xml");
					System.out.println(child.getName());
					Mapping.mapping(nameDirectory+"/"+child.getName(), 
							nameDirectory+"/stEtienne_keys.txt", 
							"formation", 
							"StEtienne");
					WriteHTML.writeOneHTML(nameDirectory+"/"+child.getName(), 
							"fichiers/output/StEtienne_"+i+"_Output.rdf", 
							"StEtienne", "formation",i);

				}
				i++;
			}
		} 
		else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
		/*String nameDirectory = "fichiers/stEtienne";
		//Reader.normalizeXML(nameDirectory+"/cnam.xml");
		Mapping.mapping(nameDirectory+"/stEtienne.xml", nameDirectory+"/stEtienne_keys.txt", "formation", "StEtienne");*/
	}
	
	public static void normalizeXML(String pathToXML)
	{
		File xmlFile = new File(pathToXML);
		String oldContent = "";
		
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(xmlFile));
			String newContent = "";
			oldContent = FileUtils.readFileToString(xmlFile, "UTF-8");
			newContent = oldContent.replace("&", "&amp;");
			
			FileOutputStream fileStream = new FileOutputStream(new File(pathToXML.replace(".xml","_normalized.xml")));
			OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
			
			writer.write(newContent);
			writer.close();
		}
		catch (Exception E){}
		finally {
			try {
				reader.close();
			}
			catch(Exception E){}
		}
	}
}
