package jenaTest;


/* Steps : 
 *  Initialize writes <!DOCTYPE html>, <html> and head
 *  Open tags, write in them, close them
 *  
 */

public class WriteHTML {
	//Singleton pattern
	private WriteHTML() {}
	private static final WriteHTML INSTANCE = new WriteHTML();
	public static WriteHTML getInstance() {
		return INSTANCE;
	}

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
	
	public static void main(String[] args) {
		INSTANCE.initializeHTML("Title", "CSS");
	}
	
}
