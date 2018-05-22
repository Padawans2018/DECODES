package francheComte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;

import xmlToRDF.Mapping;

public class Reader {
	public static void main(String[] args) {
		String nameDirectory = "fichiers/francheComte";
		Reader.normalizeXML(nameDirectory+"/frcomte.xml");
		Mapping.mapping(nameDirectory+"/frcomte_normalized.xml", nameDirectory+"/frcomte_keys.txt", "formation", "FrancheComte");
	}
	
	public static void normalizeXML(String pathToXML)
	{
		File xmlFile = new File(pathToXML);
		String oldContent = "";
		
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(xmlFile));
			System.out.println("Start reading.");
			String newContent = "";
			oldContent = FileUtils.readFileToString(xmlFile, "UTF-8");
			System.out.println("Done reading.");
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
