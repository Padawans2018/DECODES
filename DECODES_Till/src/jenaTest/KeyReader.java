package jenaTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyReader {
	
	/**
	 * This function reads the key document written by the user, to understand which property matches with which tag in the xml doc.
	 * @param path The path to the key document 
	 * @return An array list containing hashMaps, each hashMap corresponding to one resource and linking all the properties corresponding to this resource with the rigth tag.
	 */
	public static ArrayList<HashMap<String,String>> readKeysTill(String path)
	{
		/* of the keys for the different subjects 
		 * Course - 0
		 * Location - 1
		 * Domain - 2
		 * Responsible - 3
		 * Sector of activity - 4
		 * Job - 5
		 * Quality label - 6
		 * Contact - 7
		 * Cooperating company - 8 
		 * Certifying authority - 9
		 */
		ArrayList<HashMap<String,String>> listKeys = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i<10;i++){
			listKeys.add(new HashMap<String,String>());}
		try {
			  FileReader fr = new FileReader(path);
			  String line = null;
			  String key;
			  String tag;
			  BufferedReader br = new BufferedReader(fr);
			  while ((line = br.readLine()) != null)
			  {
				  key = line.split(" ")[0];
				  tag = line.split(" ")[1];
				  String subject = key.split("/")[0];
				  if(subject.compareTo(key)==0)
					  listKeys.get(0).put(key, tag);
				  else if (subject.compareTo("Location")==0)
					  listKeys.get(1).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Domain")==0)
					  listKeys.get(2).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Responsible")==0)
					  listKeys.get(3).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Sector")==0)
					  listKeys.get(4).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Job")==0)
					  listKeys.get(5).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Label")==0)
					  listKeys.get(6).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Contact")==0)
					  listKeys.get(7).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Company")==0)
					  listKeys.get(8).put(key.split("/")[1], tag);
				  else if (subject.compareTo("Authority")==0)
					  listKeys.get(9).put(key.split("/")[1], tag);
				  else System.out.println("subject not found");
			  }
		  }catch(Exception e){}
		  finally{}
		return listKeys;
	}
	
	/**
	 * This function reads the key document written by the user, to understand which property matches with which tag in the xml doc.
	 * @param path The path to the key document 
	 * @return An array list containing hashMaps, each hashMap corresponding to one resource and linking all the properties corresponding to this resource with the rigth tag.
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
