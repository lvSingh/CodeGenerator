package coreTypes;

import java.util.LinkedHashMap;

public class EnumType extends GenericType{
	private String name;
	private LinkedHashMap<String, String> values ;

	public EnumType(String name) {
		
		this.values = new LinkedHashMap<String, String>();
		this.name = name;
	}

	public LinkedHashMap<String, String> getValues() {
		return values;
	}

	public void setValues(LinkedHashMap<String, String> values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
