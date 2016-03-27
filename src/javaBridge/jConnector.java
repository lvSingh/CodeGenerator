package javaBridge;

public class jConnector {
	
	public native void StartSync() ;
	
	public native void Stop();
	
	static
	{
		System.loadLibrary("jconnector"); // Loading libjconnector.so 
	}
	

}
