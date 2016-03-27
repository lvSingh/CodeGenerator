package CodeGeneration;

import java.util.Map;

import coreTypes.CompositeType;
import coreTypes.EnumType;

public class IRTypes {

	// Inner Classes to common utility class
	private Map<String, CompositeType> compositeType;

	// Enum types
	private Map<String, EnumType> enumTypes;

	public Map<String, CompositeType> getCompositeType() {
		return compositeType;
	}

	public void setCompositeType(Map<String, CompositeType> compositeType) {
		this.compositeType = compositeType;
	}

	public Map<String, EnumType> getEnumTypes() {
		return enumTypes;
	}

	public void setEnumTypes(Map<String, EnumType> enumTypes) {
		this.enumTypes = enumTypes;
	}
	
	

}
