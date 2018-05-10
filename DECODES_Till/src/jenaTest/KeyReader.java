package jenaTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyReader {
	public KeyReader() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<HashMap<String,String>> readKeys(String path)
	{
		/* of the keys for the different subjects 
		 * Course - 0
		 * Location - 1
		 * Domaine - 2
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
}
