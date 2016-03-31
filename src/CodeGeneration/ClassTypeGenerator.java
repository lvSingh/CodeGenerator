package CodeGeneration;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Map;

import coreTypes.CompositeType;
import coreTypes.EnumType;
import coreTypes.FieldType;
import coreTypes.ListType;

public class ClassTypeGenerator {

	private String outputDir;
	private PrintWriter fileWriter;
	private Map<String, EnumType> enumMap;

	public ClassTypeGenerator(String outputDir, Map<String, EnumType> enumMap) {

		this.outputDir = outputDir;
		this.enumMap = enumMap;

	}

	public void generateJavaClass(Map<String, CompositeType> messageMap) {

		if (messageMap.size() != 0) {
			messageMap.forEach((name, compObject) -> generateCompType(name, compObject));
		}

	}

	private void generateCompType(String name, CompositeType compObj) {

		StringBuilder generatedString = new StringBuilder();

		// Import statements
		generatedString.append("import java.nio.ByteBuffer;\n");

		// Define an inner class
		generatedString.append("public class " + name + " {\n");

		// Generate Body of inner class
		compObj.getFields().forEach(
				(fieldName, fieldObject) -> generateFieldDeclerations(fieldName, fieldObject, generatedString));

		// Generate parser to convert to object from ByteBuffer

		generatedString.append("public " + name + " convertToObject(ByteBuffer buffer){ \n");
		generatedString.append(name + " genObject = new " + name + "();\n");
		compObj.getFields()
				.forEach((fieldName, fieldObject) -> generateReader(fieldName, fieldObject, generatedString));

		// Return the generated object
		generatedString.append("return genObject;\n");
		// Close the curly bracket
		generatedString.append("}\n");

		// Generate writer for object
		generatedString.append("public ByteBuffer" + " convertFromObject( " + name + " object ){ \n");
		generatedString.append("ByteBuffer buffer = ByteBuffer.allocate(100);\n");
		compObj.getFields()
				.forEach((fieldName, fieldObject) -> generateWriter(fieldName, fieldObject, generatedString));

		// Return the generated object
		generatedString.append("return buffer;\n");
		// Close the curly bracket
		generatedString.append("}\n");

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

	private void generateWriter(String fieldName, FieldType fieldObject, StringBuilder generatedString) {

		String typeSetter = CodeGenerationsConstants.primitiveTypesSetter.get(fieldObject.getType());

		if (fieldObject.isArray && !enumMap.containsKey(fieldObject.getType())) {
			ListType arrayField = (ListType) fieldObject;
			generatedString.append("for (int i = 0 ; i < " + arrayField.getLength() + "; i++ ){\n");

			// Check if it's a primitive type of composite type

			typeSetter = typeSetter != null ? "buffer." + typeSetter + "(object." + fieldName + "[i]);\n"
					: "buffer.put(object." + fieldName + "[i].convertFromObject(object." + fieldName + "[i]));\n";
			generatedString.append(typeSetter);

			generatedString.append("}\n");
		} else {
			if (enumMap.containsKey(fieldObject.getType()) ) {
				// Field is of enum type
				typeSetter = "buffer.putInt(object." + fieldName + ".getValue());\n";
			}else{
				typeSetter = typeSetter != null ? "buffer." + typeSetter + "(object." + fieldName + ");\n"
						: "buffer.put(object." + fieldName + ".convertFromObject(object." + fieldName + "));\n";
			}
			generatedString.append(typeSetter);
		}
	}

	private void generateReader(String fieldName, FieldType fieldObject, StringBuilder generatedString) {

		if (fieldObject.isArray) {
			ListType arrayField = (ListType) fieldObject;
			generatedString.append("for (int i = 0 ; i < " + arrayField.getLength() + "; i++ ){\n");

			// Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypesGetter.get(arrayField.getType());
			typeGetter = typeGetter != null ? "(" + arrayField.getType() + ") buffer." + typeGetter + "()"
					: "TypeUtils." + arrayField.getType() + ".convertToObject(buffer)";

			generatedString.append("genObject." + arrayField.getName() + "[i] = " + typeGetter + ";\n");

			generatedString.append("}\n");

		} else {

			// Check of Type is primitive or Reference
			String typeGetter = CodeGenerationsConstants.primitiveTypesGetter.get(fieldObject.getType());
			String type = CodeGenerationsConstants.primitiveTypesGetter.containsKey(fieldObject.getType())
					? fieldObject.getType() : null;

			if (typeGetter == null) {
				if (enumMap.containsKey(fieldObject.getType())) {
					// field is of enum type
					typeGetter = "TypeUtils.getEnum" + fieldObject.getType() + "(buffer.getInt());\n";

				} else {
					typeGetter = "TypeUtils." + fieldObject.getType() + ".convertToObject(buffer)";
				}
				type = "TypeUtils." + fieldObject.getType();
			} else {
				typeGetter = "buffer." + typeGetter + "()";
			}

			generatedString.append("genObject." + fieldObject.getName() + " = (" + type + ") " + typeGetter + ";\n");

		}
	}

	private void generateFieldDeclerations(String name, FieldType fieldObj, StringBuilder generatedString) {
		if (fieldObj.isArray) {
			ListType arrayField = (ListType) fieldObj;

			String type = CodeGenerationsConstants.primitiveTypesGetter.containsKey(arrayField.getType())
					? arrayField.getType() : null;
			// Check if field is of composite type
			if (type == null) {
				type = "TypeUtils." + arrayField.getType();
			}
			// lhs
			generatedString.append("public " + type + "[] " + arrayField.getName() + " = ");
			// rhs
			generatedString.append("new " + type + "[" + arrayField.getLength() + "];\n");
		} else {
			String type = CodeGenerationsConstants.primitiveTypesGetter.containsKey(fieldObj.getType())
					? fieldObj.getType() : null;
			;
			// Check if field is of type enum or composite
			if (type == null) {
				if (enumMap.containsKey(fieldObj.getType())) {
					// field is of enum type
					type = "TypeUtils." + fieldObj.getType();
				} else {
					type = "TypeUtils." + type;
				}
			}

			generatedString.append("public " + type + " " + fieldObj.getName() + "; \n");
		}

	}

}
