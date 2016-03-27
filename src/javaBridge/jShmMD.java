package javaBridge;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import javaBridge.jMarketUpdateNew.BookElement;

public class jShmMD {
	File _dataFile ;
	RandomAccessFile _rDataFile ;
	FileChannel _fDataChannel ;
	MappedByteBuffer _readDataBuf ;
	long _size ;						// Size of the jRequest Object
	
	File _syncFile ;
	RandomAccessFile _rSyncFile ;
	FileChannel _fSyncChannel ;
	MappedByteBuffer _readSyncBuf ;
	
	byte _readCount ;
	byte [] _byteObj ;
	
	
	
	/*
	 * Constructor 
	 */
	public jShmMD()
	{
		_size = 810;
		_byteObj = new byte[(int)_size];
		_dataFile = new File("/dev/shm/jMdBuffer.bin");
		_dataFile.delete();
		
		_syncFile = new File("/dev/shm/jMdSync.bin");
		_syncFile.delete();
		try
		{
			/*
			 * Creating the Data File where the request Object will be written 
			 */
			_rDataFile = new RandomAccessFile(_dataFile, "rw");
			_fDataChannel = _rDataFile.getChannel();
			_readDataBuf = _fDataChannel.map(FileChannel.MapMode.READ_WRITE, 0,_size);		// Mapping the file 
			_readDataBuf.order(ByteOrder.LITTLE_ENDIAN); 								//Setting up the byte order
			
			/*
			 * Creating the Sync File where readCounter will be stored 
			 */
			
			_rSyncFile = new RandomAccessFile(_syncFile, "rw");
			_fSyncChannel = _rSyncFile.getChannel();
			_readSyncBuf = _fSyncChannel.map(FileChannel.MapMode.READ_WRITE, 0 ,8);		// Mapping the file 
			_readSyncBuf.order(ByteOrder.LITTLE_ENDIAN); 								//Setting up the byte order
			
		} 
		catch (IOException e) {
			System.out.println("Sometihng is wrong with the request object shared memory file ");
			e.printStackTrace();
		}
		
		_readCount = 0 ;
		
		
	}
	
	public jMarketUpdateNew process()
	{
		
			_readCount = _readSyncBuf.get(0);
			if(_readCount == 1 )
			{
				_readDataBuf.get(_byteObj);
				_readDataBuf.position(0);
				_readSyncBuf.put((byte)0);
				_readSyncBuf.position(0);
				return parseToObj(_byteObj);
				
			}
			
		return null;
	}

	private jMarketUpdateNew parseToObj(byte[] arr) {
		ByteBuffer buf = ByteBuffer.wrap(arr);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		int i = 0 ;
		byte [] temp1 = new byte[48]; //To read the Symbol 
		jMarketUpdateNew obj = new jMarketUpdateNew();
		
		obj._timeExchange = buf.getLong();
		obj._timeReceived = buf.getLong();
		obj._streamSequenceNumber = buf.getLong();
		obj._instrumentId = buf.getLong();
		obj._instrumentSequenceNumber = buf.getInt();
		obj.setExchangeCode(buf.getInt());
		/*
		 * Reading next 48 bytes Symbol Name 
		 */
		
		while(i<48)
		{
			temp1[i++] = buf.get();
		}
		obj._symbol = new String(temp1);
		obj._orderId = buf.getLong();
		obj._currentPrice = buf.getDouble();
		obj._previousPrice = buf.getDouble();
		obj._lastTradePrice = buf.getDouble();
		obj._lastTradeTime = buf.getLong();
		obj._totalTradedQuantity = buf.getLong();
		obj._totalTradedValue = buf.getLong();
		obj._currentQuantity = buf.getInt();
		obj._previousQuantity = buf.getInt();
		obj._lastTradeQuantity = buf.getInt();
		obj._filledBuyLevels = buf.get();
		obj._filledSellLevels = buf.get();
		obj._bookUpdateIndex = buf.get();
		obj.setBuySellIndicator(buf.get());
		obj.setUpdateCode(buf.get());
		obj.setUpdateFlag1(buf.get());
		obj.setUpdateFlag2(buf.get());
		
		//To Compensate the padding 
		i = buf.position() ;
		buf.position(i+5);
		
		/*
		 * reading next 24(8-8-8)[Book Element] bytes 20(Interest Level) time  
		 */
		//Buy Book
		for(i = 0 ; i < 20 ; i++)
		{
			obj._buyBook[i] = new BookElement(buf.getDouble(), buf.getInt(), buf.getInt());
			
		}
		//Sell Book
		for(i = 0 ; i < 20 ; i++)
		{
			obj._sellBook[i] = new BookElement(buf.getDouble(), buf.getInt(), buf.getInt());
		}
		
		return obj;
		
	}

}
