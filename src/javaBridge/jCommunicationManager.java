package javaBridge;

class MdProcessor implements Runnable
{
	jCommunicationManager _comMgr ;
	
	MdProcessor(jCommunicationManager o)
	{
		_comMgr = o ;
	}
	public void run()
	{
		_comMgr.MdProcess();
		
	}
}
class ResProcessor implements Runnable
{
	jCommunicationManager _comMgr ;
	
	ResProcessor(jCommunicationManager o)
	{
		_comMgr = o ;
	}
	public void run()
	{
		_comMgr.ResProcess();
		
	}
}
public class jCommunicationManager  {
	Communicator _communicator;
	jShmREQ _req;
	jShmMD  _md;
	jShmRES _res;
	boolean _signal;
	Thread t1 ;
	Thread t2 ;
	public jCommunicationManager()
	{
		_req = new jShmREQ();
		_md	 = new jShmMD();
		_res = new jShmRES();
		_signal = true;
		
	}
	public void RegisterCommunicator(Communicator com)
	{
		_communicator = com ;
	}
	public int SendOrder(jRequestMsg obj)
	{
		return _req.process(obj);
	}
	public void MdProcess()
	{
		
		while(_signal)
		{
			jMarketUpdateNew obj = _md.process();
			if(obj!= null)
			{
				_communicator.MarketUpdateRecieved(obj);
			}
		}
		
	}
	public void ResProcess()
	{
		while(_signal)
		{
			jResponseMsg obj = _res.process();
			if(obj!= null)
			{
				_communicator.OrderResponseRecieved(obj);
			}
		}
		
	}
	public  void start()
	{
		t1 = new Thread(new MdProcessor(this));
		t2 = new Thread(new ResProcessor(this));
		
		t1.start();
		t2.start();
		
		
	}
	public void stop()
	{
		_signal = false;
	}
	
}
