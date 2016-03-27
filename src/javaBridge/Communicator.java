package javaBridge;

public interface Communicator {
	
	public void MarketUpdateRecieved(jMarketUpdateNew update) ;
	
	public void OrderResponseRecieved (jResponseMsg response) ;
	
	public void EndOfMarketUpdate ();

}
