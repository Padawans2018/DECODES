package xmlToRDF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyReader {
	
	/**
	 * This function reads the key document written by the user, to understand which property matches with which tag in the xml doc.
	 * @param path The path to the key document 
	 * @return An array list containing hashMaps, each hashMap corresponding to one resource and linking all the properties corresponding to this resource with the right tag.
	 */
	public static HashMap<String,HashMap<String,String>> readKeys(String path)
	{
		HashMap<String,HashMap<String,String>> keysHM = new HashMap<String,HashMap<String,String>>();
		keysHM.put("formation", new HashMap<String,String>());
		keysHM.put("location", new HashMap<String,String>()); 
		keysHM.put("domain", new HashMap<String,String>()); 
		keysHM.put("respo", new HashMap<String,String>()); 
		keysHM.put("sector", new HashMap<String,String>()); 
		keysHM.put("job", new HashMap<String,String>()); 
		keysHM.put("label", new HashMap<String,String>()); 
		keysHM.put("contact", new HashMap<String,String>()); 
		keysHM.put("company", new HashMap<String,String>()); 
		keysHM.put("authority", new HashMap<String,String>()); 
		try {
			  FileReader fr = new FileReader(path);
			  String line = null;
			  String key;
			  String tag;
			  BufferedReader br = new BufferedReader(fr);
			  
			  while ((line = br.readLine()) != null)
			  {
				  //when there is no tag in the document for the given property
				  if (line.split(" ").length < 2)
					  continue;
				  key = line.split(" ")[0];
				  tag = line.split(" ")[1];
				  String subject = key.split("/")[0];
				  keysHM.get(subject.toLowerCase()).put(key.split("/")[1], tag);
			  }
		  }catch(Exception e){}
		  finally{}
		return keysHM;
	}
}
