package jenaTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.parser.*;

import org.jsoup.select.* ; 

/* Steps : 
 *  Initialize writes <!DOCTYPE html>, <html> and head
 *  Open tags, write in them, close them
 */

public class WriteHTML {
	//Singleton pattern
	private WriteHTML() {}
	private static final WriteHTML INSTANCE = new WriteHTML();
	public static WriteHTML getInstance() {
		return INSTANCE;
	}
	
	/*
	public String openTag(String tagType, String className, String idName, String sourceLink, String alt) {
		String tag = "<"
				+ tagType;
		if (sourceLink!="") { tag += "src=" + sourceLink + " ";}
		if (alt!="") { tag += "alt=" + alt + " ";}
		if (idName!="") { tag += "id=" + idName + " ";}
		if (className!="") { tag += "class=" + className + " ";}
		
		tag += ">\n"; 
		return(tag);
	}
	
	public String closeTag(String tagType) {
		String tag = "<\\"
				+ tagType
				+ ">\n";
		return (tag);
	}
	
	public String initializeHTML(String pageName, String stylesheetLink) {
	String start =  "<!DOCTYPE html>\n" 
			+ "<html>\n" 
			+ "<head>\n" 
			+ "<meta charset=\"utf-8\" />\n"
			+ "<link rel=\"stylesheet\" href="
    		+ stylesheetLink 
    		+ "/>\n" 
			+ "<title>"
			+ pageName
			+ "<\\title>\n" 
			+"</head>\n";
	return(start);
	
	}
	*/
	
	public void initializeHTML(String pageName, String stylesheetLink) {
		File f = new File("docs/pageName.html");
		
		String start =  "<!DOCTYPE html>\n" 
				+ "<html>\n" 
				+ "<head>\n" 
				+ "<meta charset=\"utf-8\" />\n"
				+ "<link rel=\"stylesheet\" href=\""
	    		+ stylesheetLink 
	    		+ "\"/>\n" 
				+ "<title>"
				+ pageName
				+ "</title>\n" 
				+"</head>\n"
				+"<body>"
				+"</body>\n"
				+"</html>\n";
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(start);
			bw.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();		
		}	
		
	}
	
	public void replaceTagContent(String initial, String replacement) {
		try {
			//TODO
			//The idea is having an html template with initial content and replacing it
		}
		
		catch (Exception e) {
			e.printStackTrace();		
		}
		
	}
	
	public void add() {
		try {
			File f=new File("docs/pageName.html");
			Document doc = Jsoup.parse(f, "UTF-8");
			Element body = doc.select("body").first();
			body.html("<p>lorem ipsum</p>");
			
		}
		
		catch (Exception e) {
			e.printStackTrace();		
		}	
	}
	
	public static void main(String[] args) {
		INSTANCE.initializeHTML("Title", "formationStyle.css");
		INSTANCE.add(); 
	}
	
}
