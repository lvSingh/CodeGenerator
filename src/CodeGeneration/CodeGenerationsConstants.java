package CodeGeneration;

import java.util.HashMap;

public class CodeGenerationsConstants {
	
	public final static String ENUM_XPATH_EXPR ="messageSchema/types/enum";
	public final static String MESSAGE_XPATH_EXPR ="messageSchema/message";
	public final static String COMPOSITE_XPATH_EXPR = "messageSchema/types/compositeType";
	public final static HashMap<String,String> primitiveTypes = new HashMap<>();
	
	static{
		primitiveTypes.put("long", "getLong");
		primitiveTypes.put("int", "getInt");
		primitiveTypes.put("double", "getDouble");
		primitiveTypes.put("char", "get");
	}
	
	

}
