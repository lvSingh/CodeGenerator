package CodeGeneration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import coreTypes.CompositeType;
import coreTypes.EnumType;
import coreTypes.FieldType;
import coreTypes.ListType;

public class StaticUtilGenerator {
	
	private StringBuilder className ;
	private StringBuilder generatedString ;
	private String outputDir;
	private PrintWriter fileWriter ;
	public StaticUtilGenerator(String outputDir){
		this.outputDir = outputDir;
		className = new StringBuilder("TypeUtils");
		generatedString = new StringBuilder("public class "+className + "{ \n");
		
	}
	public boolean utilGenerator(Map<String,CompositeType> compositeTypeMap,Map<String,EnumType> enumMap){
		
		if(compositeTypeMap.size() != 0 ){
			compositeTypeMap.forEach((name , compObject)->generateCompType(name, compObject));
		}
		if(enumMap.size() != 0){
			enumMap.forEach((name , enumObject)->generateEnum(name, enumObject));
		}
		
		//Close the curly bracket for the util class
		generatedString.append("\n}\n");
		//Write to the file 
		
		try {
			fileWriter = new PrintWriter(outputDir+className+".java");
			fileWriter.print(generatedString);
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	private void generateCompType(String name ,CompositeType compObj){
		
		//Define an inner class
		generatedString.append("public static class "+name+" {\n");
		
		//Generate Body of inner class
		compObj.getFields().forEach((fieldName , fieldObject)->generateFieldDeclerations(fieldName, fieldObject));
		
		//Generate parser to convert to object from ByteBuffer  
		
		generatedString.append("public static"+name+" convertToObject(ByteBuffer buffer){ \n");
		generatedString.append(name+" genObject = new "+name+"();\n");
		compObj.getFields().forEach((fieldName , fieldObject)->generateBufferToObjConverter(fieldName,fieldObject));
		
		//Close the curly bracket
		generatedString.append("}");
	} 
	
	private void  generateBufferToObjConverter(String fieldName, FieldType fieldObject) {
		
		if(fieldObject.isArray){
			ListType arrayField = (ListType)fieldObject;
			generatedString.append("for (int i = 0 ; i < "+arrayField.getLength()+"; i++ ){\n");
			
			//Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypes.get(arrayField.getType());
			typeGetter = typeGetter != null?"buffer."+typeGetter+"()":arrayField+".convertToObject(buffer);";
			
			generatedString.append("genObject."+arrayField.getName()+"[i] = "+typeGetter+";\n");
			
			generatedString.append("}");
			
		}else{
			
			//Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypes.get(fieldObject.getType());
			typeGetter = typeGetter != null?"buffer."+typeGetter+"()":fieldObject+".convertToObject(buffer);";
			
			generatedString.append("genObject."+fieldObject.getName()+"[i] = "+typeGetter);
			
		}
	}
	private void generateFieldDeclerations(String name , FieldType fieldObj){
		if(fieldObj.isArray){
			ListType arrayField = (ListType)fieldObj;
			generatedString.append("public "+arrayField.getType()+"[] "+arrayField.getName()+" = ");
			generatedString.append("new "+arrayField.getType()+"["+arrayField.getLength()+"];\n");
		}else{
			generatedString.append("public "+fieldObj.getType()+" "+fieldObj.getName()+"; \n");
		}
		
	}
	
	private void generateEnum(String name , EnumType enumTypeObj){
		
		generatedString.append("public enum "+name+" {\n");
		
		//Generate Enum values
		enumTypeObj.getValues().forEach((entryName,entryValue)->generateEnumEntries(entryName,entryValue));
		
		//Remove ',' after last enum value and add ';' instead 
		generatedString.replace(generatedString.length()-3,generatedString.length(), ";\n");
		
		//Generate variable and set it up in Constructor
		generatedString.append("private int value ;\n");
		generatedString.append(name+"( int val ){\n");
		generatedString.append("this.value = val ;\n } \n");
		
		//Generate getter for the value 
		generatedString.append("public int getValue(){\n");
		generatedString.append("return this.value; \n } \n");
		
		//Close the curly bracket
		generatedString.append("} \n");
		
		//Generate getEnum(byte b ) to get the Enum 
		generateGetEnum(name);
		
	}
	
	private void generateGetEnum(String name) {
		
		generatedString.append("public "+ name +" getEnum"+name+"( byte b ){\n");
		generatedString.append("for( "+name+" e :"+name+".values() ){\n");
		generatedString.append("if( e.getValue() == b ){\n");
		generatedString.append("return e ; \n } \n } \n return null; \n }\n");
		
	}
	private void generateEnumEntries(String name , String value){
		
		generatedString.append(name+"("+value+") , \n");
	}
}
