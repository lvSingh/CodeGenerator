package CodeGeneration;

public class DeSerializationUtility {
	
	public static byte[] doubleToArray(double d){

		long lng = Double.doubleToLongBits(d);
		
		return longToArray(lng);

	}
	
	public static byte[] longToArray(long l){
		
		byte[] output = new byte[8];
		for(int j = 7; j >= 0; j--)
		{
			output[7-j] = (byte)((l >> ((7 - j) * 8)) & 0xff);
		}
		return output;
	}
	
	public static byte[] intToByteArray(int i){
		
		byte[] output = new byte[8];
		for(int j = 3 ; j >-0 ; j--){
			output[3-j] = (byte)((i >> ((3 - j) * 8)) & 0xff);
		}
		return output;
	}

}
