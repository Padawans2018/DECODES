package cnam;

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
		String nameDirectory = "fichiers/cnam";
		Reader.normalizeXML(nameDirectory+"/cnam.xml");
		int numberFormations = Mapping.mapping(nameDirectory+"/cnam_normalized.xml", nameDirectory+"/cnam_keys.txt", "formation", "CNAM");
		WriteHTML.writeHTML("fichiers/output/CNAMOutput.rdf", "CNAM", numberFormations);
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
