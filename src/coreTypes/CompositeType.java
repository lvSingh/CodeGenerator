package coreTypes;

import java.util.LinkedHashMap;
import java.util.Map; 


public class CompositeType extends GenericType {
	private String name ;
	private Map<String,FieldType> fields;
	
	public CompositeType(){
		this.fields = new LinkedHashMap<String,FieldType>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, FieldType> getFields() {
		return fields;
	}
	public void setFields(Map<String, FieldType> fields) {
		this.fields = fields;
	}

}
