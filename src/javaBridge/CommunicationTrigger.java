/*
 * This is the main communication trigger class which will start all the action 
 * It uses another class Communicator to provide call backs after receipt of Market Data and Order Responses
 * You can put your relevant code in these methods 
 * 
 * PLEASE DO NOT CHANGE ANY THING ELSE
 * 
 *  
 */
package javaBridge;
/*
 * This utility class facilitates the communication with the Java Bridge 
 */
class CommunicatorImpl implements Communicator
{
	jCommunicationManager comMgr ;
	public CommunicatorImpl()
	{
		comMgr = new jCommunicationManager();

	}
	
	public void init()
	{
		/*
		 * Registering the Call back Methods
		 */
		
		comMgr.RegisterCommunicator((Communicator)this);
		
		/*
		 * Start processing from Java Side
		 */
		
		comMgr.start();
		
	}
	
	public void MarketUpdateRecieved(jMarketUpdateNew update) 
	{
		System.out.println(update.toString());
		
		jRequestMsg msg = new jRequestMsg() ;
		msg.Exchange_Type = jRequestMsg.ExchangeCode.MCX;
		msg.Price = 102.10;
		msg.Quantity = 1 ;
		msg.QuantityFilled = 0;
		msg.DisclosedQnty = msg.Quantity;
		msg.AccountID = 1826;
		msg.Transaction_Type = jRequestMsg.BuySellIndicator.BUY;
		msg.Duration = jRequestMsg.OrderDuration.DAY;
		msg.OrdType = jRequestMsg.OrderType.LIMIT;
		msg.Request_Type = jRequestMsg.RequestType.NEWORDER;
		msg.instrumentId = update._instrumentId;
		System.out.println(msg.instrumentId);
		int order = comMgr.SendOrder(msg);
		if(order != -1)
		{
			System.out.println(order + " placed successfully" );
		}
		else
		{
			System.out.println("Order failed");
		}
		
	}
	public void OrderResponseRecieved (jResponseMsg response)
	{
		System.out.println("Response Received");
		System.out.println(response.toString());
	}
	public void EndOfMarketUpdate() {
		
		/*
		 * Mandatory step to stop processing of the communicator
		 */
		comMgr.stop();
		
		//Market update has just ended 
		//Do your thing
	}
}
public class CommunicationTrigger 
{
	CommunicatorImpl _communicator ;
	jConnector _connector;
	
	public CommunicationTrigger()
	{
		_communicator = new CommunicatorImpl();
		//_connector = new jConnector();
		
	}
	public void StartProcessing()
	{
		//_connector.StartSync();
		_communicator.init();
	}
	
	public static void main(String args[])
	{
		CommunicationTrigger comT = new CommunicationTrigger();
		
		comT.StartProcessing();
	}
}
