package jenaTest;

import org.apache.jena.*;
import org.apache.jena.rdf.model.Resource;

public class MyResource {
	private String type;
	private Resource resource;
	
	public MyResource(String pType, Resource pResource) {
		type = pType;
		resource = pResource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
	
}
