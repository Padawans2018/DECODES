package jenaTest;

import org.apache.jena.rdf.model.*;

import org.apache.jena.rdf.*;
import org.apache.jena.vocabulary.*;

public class Main {
	
      public static void main(String[] args) {
    	// some definitions
    		String uriProperties = "http://decodes/properties/";
    		String uriResources = "http://decodes/resources/";
    		
    	    // create an empty model
    	    Model model = ModelFactory.createDefaultModel();
    	    
    	    
    	    //create alllllll properties
    	    Property name = model.createProperty(uriProperties,"Name");
    	    Property institutionLogo = model.createProperty(uriProperties,"InstitutionLogo");
    	    Property rncp = model.createProperty(uriProperties,"RNCP");
    	    Property cpf = model.createProperty(uriProperties,"CPF");
    	    Property typologie = model.createProperty(uriProperties,"Typologie");
    	    Property audience = model.createProperty(uriProperties,"Audience");
    	    Property requirements = model.createProperty(uriProperties,"Requirements");
    	    Property goals = model.createProperty(uriProperties,"Goals");
    	    Property skillsLearned = model.createProperty(uriProperties,"SkillsLearned");
    	    Property learningMethods = model.createProperty(uriProperties,"LearningMethods");
    	    Property timeOrganisation = model.createProperty(uriProperties,"TimeOrganisation");
    	    Property gradingMethods = model.createProperty(uriProperties,"GradingMethods");
    	    Property distanceLearning = model.createProperty(uriProperties,"DistanceLearning");
    	    Property apprenticeship = model.createProperty(uriProperties,"Apprenticeship");
    	    Property professionalisationContract = model.createProperty(uriProperties,"ProfessionalisationContract");
    	    Property eav = model.createProperty(uriProperties,"EAV");
    	    Property accesibility = model.createProperty(uriProperties,"Accessibility");
    	    Property totalDuration = model.createProperty(uriProperties,"totalDuration");
    	    Property includingCompanyDuration = model.createProperty(uriProperties,"IncludingCompanyDuration");
    	    Property schedule = model.createProperty(uriProperties,"Schedule");
    	    Property price = model.createProperty(uriProperties,"Price");
    	    Property notes = model.createProperty(uriProperties,"Notes");
    	    Property nsfCode = model.createProperty(uriProperties,"NSFCode");
    	    Property id = model.createProperty(uriProperties,"ID");
    	    Property job = model.createProperty(uriProperties,"Job");
    	    Property speciality = model.createProperty(uriProperties,"Speciality");
    	    Property naf = model.createProperty(uriProperties,"NAF");
    	    Property romeCode = model.createProperty(uriProperties,"RomeCode");
    	    Property qualityLogo = model.createProperty(uriProperties,"QualityLogo");
    	    Property phoneNumber = model.createProperty(uriProperties,"PhoneNumber");
    	    Property emailAddress = model.createProperty(uriProperties,"EmailAddress");
    	    Property address = model.createProperty(uriProperties,"Address");
    	    Property institution = model.createProperty(uriProperties,"Institution");
    	    
    	    Property isPartOf = model.createProperty(uriProperties,"IsPartOf");
    	    Property isSubmittedBy = model.createProperty(uriProperties,"IsSubmittedBy");
    	    Property givesOpportunitiesIn = model.createProperty(uriProperties,"GivesOpportunitiesIn");
    	    Property has = model.createProperty(uriProperties,"Has");
    	    Property isManagedBy = model.createProperty(uriProperties,"IsManagedBy");
    	    Property isWelcomedBy = model.createProperty(uriProperties,"IsWelcomedBy");
    	    Property isCertifiedBy = model.createProperty(uriProperties,"IsCertifiedBy");
    	    Property takesPlaceIn = model.createProperty(uriProperties,"TakesPlaceIn");
		   
        // create the resource
        //   and add the properties cascading style

    	String formationName = "formation_1 Lyon1";
        
        Resource formation1Lyon1  = model.createResource(uriResources+"formation1Lyon1")
             .addProperty(name, formationName);  
        
       model.write(System.out);
        System.out.println(formation1Lyon1);
      }
      
      public void firstMapping() {
    	  
      }
}
