package coreTypes;

public class ListType extends FieldType {
	
	private int length;
	
	public ListType (String name , String type , int length){
		super(name, type);
		this.length = length;
		this.isArray = true;
	}
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}
