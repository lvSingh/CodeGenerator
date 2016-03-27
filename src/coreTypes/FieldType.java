package coreTypes;

public class FieldType extends GenericType {
	
	private String name ;
	private String type ;
	public boolean isArray;
	
	public FieldType(){
		
	}
	public FieldType(String name , String type){
		this.name = name;
		this.type = type;
		this.isArray = false;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
