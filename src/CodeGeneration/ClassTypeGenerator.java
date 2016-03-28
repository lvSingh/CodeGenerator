package CodeGeneration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import coreTypes.CompositeType;
import coreTypes.FieldType;
import coreTypes.ListType;

public class ClassTypeGenerator {

	private StringBuilder generatedString;
	private String outputDir;
	private PrintWriter fileWriter;

	public ClassTypeGenerator(String outputDir) {

		this.outputDir = outputDir;

	}

	public void generateJavaClass(Map<String, CompositeType> messageMap) {

		if (messageMap.size() != 0) {
			messageMap.forEach((name, compObject) -> generateCompType(name, compObject));
		}

	}

	private void generateCompType(String name, CompositeType compObj) {
		// Define an inner class
		generatedString.append("public class " + name + " {\n");

		// Generate Body of inner class
		compObj.getFields().forEach((fieldName, fieldObject) -> generateFieldDeclerations(fieldName, fieldObject));

		// Generate parser to convert to object from ByteBuffer

		generatedString.append("public " + name + " convertToObject(ByteBuffer buffer){ \n");
		generatedString.append(name + " genObject = new " + name + "();\n");
		compObj.getFields().forEach((fieldName, fieldObject) -> generateReader(fieldName, fieldObject));
		
		//Return the generated object
		generatedString.append("return genObject;");
		// Close the curly bracket
		generatedString.append("}");

		// Close the curly bracket for class
		generatedString.append("\n}\n");
		// Write to the file

		try {
			fileWriter = new PrintWriter(outputDir + name + ".java");
			fileWriter.print(generatedString);
			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void generateReader(String fieldName, FieldType fieldObject) {

		if (fieldObject.isArray) {
			ListType arrayField = (ListType) fieldObject;
			generatedString.append("for (int i = 0 ; i < " + arrayField.getLength() + "; i++ ){\n");

			// Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypes.get(arrayField.getType());
			typeGetter = typeGetter != null ? "buffer." + typeGetter + "()" : arrayField + ".convertToObject(buffer);";

			generatedString.append("genObject." + arrayField.getName() + "[i] = " + typeGetter + ";\n");

			generatedString.append("}\n");

		} else {

			// Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypes.get(fieldObject.getType());
			typeGetter = typeGetter != null ? "buffer." + typeGetter + "()" : fieldObject + ".convertToObject(buffer);";

			generatedString.append("genObject." + fieldObject.getName() + "[i] = " + typeGetter +";\n");

		}
	}

	private void generateFieldDeclerations(String name, FieldType fieldObj) {
		if (fieldObj.isArray) {
			ListType arrayField = (ListType) fieldObj;
			generatedString.append("public " + arrayField.getType() + "[] " + arrayField.getName() + " = ");
			generatedString.append("new " + arrayField.getType() + "[" + arrayField.getLength() + "];\n");
		} else {
			String typeGetter  = CodeGenerationsConstants.primitiveTypes.get(fieldObj.getType()) ;
			typeGetter = typeGetter != null? typeGetter : "TypeUtils."+typeGetter;
				
			generatedString.append("public " + typeGetter + " " + fieldObj.getName() + "; \n");
		}

	}

}
