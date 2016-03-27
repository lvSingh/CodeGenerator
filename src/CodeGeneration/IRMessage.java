package CodeGeneration;
import java.util.*;

import coreTypes.CompositeType;
import coreTypes.EnumType;
import coreTypes.FieldType;
import coreTypes.ListType;

public class IRMessage {
	
	//Fields 
	private Map<String , FieldType> fieldType;
	
	//Arrays
	private Map<String , ListType> listTypes;


	public Map<String, FieldType> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Map<String, FieldType> fieldType) {
		this.fieldType = fieldType;
	}

	public Map<String, ListType> getListTypes() {
		return listTypes;
	}

	public void setListTypes(Map<String, ListType> listTypes) {
		this.listTypes = listTypes;
	}
	
	
	

}
