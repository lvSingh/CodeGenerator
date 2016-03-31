package CodeGeneration;

import java.util.HashMap;

public class CodeGenerationsConstants {
	
	public final static String ENUM_XPATH_EXPR ="messageSchema/types/enum";
	public final static String MESSAGE_XPATH_EXPR ="messageSchema/message";
	public final static String COMPOSITE_XPATH_EXPR = "messageSchema/types/compositeType";
	public final static HashMap<String,String> primitiveTypesGetter = new HashMap<>();
	public final static HashMap<String,String> primitiveTypesSetter = new HashMap<>();
	
	static{
		primitiveTypesGetter.put("long", "getLong");
		primitiveTypesGetter.put("int", "getInt");
		primitiveTypesGetter.put("double", "getDouble");
		primitiveTypesGetter.put("char", "get");
		
		primitiveTypesSetter.put("long", "putLong");
		primitiveTypesSetter.put("int", "putInt");
		primitiveTypesSetter.put("double", "putDouble");
		primitiveTypesSetter.put("char", "putChar");
	}
	
	

}
