package javaBridge;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class jShmREQ {
	File _dataFile ;
	RandomAccessFile _rDataFile ;
	FileChannel _fDataChannel ;
	MappedByteBuffer _writeDataBuf ;
	long _size ;						// Size of the jRequest Object
	
	File _syncFile ;
	RandomAccessFile _rSyncFile ;
	FileChannel _fSyncChannel ;
	MappedByteBuffer _writeSyncBuf ;
	
	File _orderFile ;
	RandomAccessFile _rOrderFile ;
	FileChannel _fOrderChannel ;
	MappedByteBuffer _writeOrderBuf ;
	
	byte _writeCount ;
	
	/*
	 * Constructor 
	 */
	public jShmREQ()
	{
		_size = 112*8;																 
		_dataFile = new File("/dev/shm/jReqBuffer.bin");
		_dataFile.delete();
		
		_syncFile = new File("/dev/shm/jReqSync.bin");
		_syncFile.delete();
		
		_orderFile = new File("/dev/shm/jOrdBuffer.bin");
		_orderFile.delete();
		try
		{
			/*
			 * Creating the Data File where the request Object will be written 
			 */
			_rDataFile = new RandomAccessFile(_dataFile, "rw");
			_fDataChannel = _rDataFile.getChannel();
			_writeDataBuf = _fDataChannel.map(FileChannel.MapMode.READ_WRITE, 0,_size);		// Mapping the file 
			_writeDataBuf.order(ByteOrder.LITTLE_ENDIAN); 								//Setting up the byte order
			
			/*
			 * Creating the Sync File where readCounter will be stored 
			 */
			
			_rSyncFile = new RandomAccessFile(_syncFile, "rw");
			_fSyncChannel = _rSyncFile.getChannel();
			_writeSyncBuf = _fSyncChannel.map(FileChannel.MapMode.READ_WRITE, 0 ,8);		// Mapping the file 
			_writeSyncBuf.order(ByteOrder.LITTLE_ENDIAN); 								//Setting up the byte order
			
			/*
			 * Creating the Order file where internal Order Id will be written after request call
			 */
			_rOrderFile = new RandomAccessFile(_orderFile, "rw");
			_fOrderChannel = _rOrderFile.getChannel();
			_writeOrderBuf = _fOrderChannel.map(FileChannel.MapMode.READ_WRITE, 0,_size);		// Mapping the file 
			_writeOrderBuf.order(ByteOrder.LITTLE_ENDIAN); 		
			
		} 
		catch (IOException e) {
			System.out.println("Sometihng is wrong with the request object shared memory files ");
			e.printStackTrace();
		}
		
		_writeCount = 0 ;
		
	}

	public int process(jRequestMsg requestObj)
	{
		int orderId = -1 ;
			_writeCount = _writeSyncBuf.get(0);
			if(_writeCount == 0 )
			{
				
				_writeDataBuf.put(parseToByte(requestObj));
				_writeDataBuf.position(0);
				_writeSyncBuf.put((byte)1);
				_writeSyncBuf.position(0);
				orderId = _writeOrderBuf.getInt(0);
				_writeOrderBuf.position(0);
				_writeOrderBuf.putInt(0, 0);
				return orderId ;
			}
		
			return -1;
	}
	/*
	 * Convert object into ByteBuffer 
	 */
	private byte[] parseToByte(jRequestMsg Obj)
	{
		byte [] objData = new byte[54];
		
		int i = 0 ;
			if( i >= 0 && i <= 7 )
			{
				for(int j = 7; j >= 0; j--)
				{
					objData[i++] = (byte)((Obj.instrumentId >> ((7 - j) * 8)) & 0xff);
				}
			}
			// Request Type
			if( i == 8 )
			{
				objData[i++] = Obj.convertEnumToInt(Obj.Request_Type);
				
			}
			//Order Type
			if( i == 9 )
			{
				
				objData[i++] = Obj.convertEnumToInt(Obj.OrdType);
				
			}
			// Order Duration
			if( i == 10)
			{
				objData[i++] = Obj.convertEnumToInt(Obj.Duration);
				
			}
			// Buy Sell Indicator
			if( i == 11 )
			{
				objData[i++] = Obj.convertEnumToInt(Obj.Transaction_Type);
				
			}
			// Exchange Code
			if( i >= 12 && i <= 15 )
			{
				objData[i++] = (byte)Obj.convertEnumToInt(Obj.Exchange_Type) ;
				objData[i++] = (byte)(Obj.convertEnumToInt(Obj.Exchange_Type)>>8) ;
				objData[i++] = (byte)(Obj.convertEnumToInt(Obj.Exchange_Type)>>16) ;
				objData[i++] = (byte)(Obj.convertEnumToInt(Obj.Exchange_Type)>>24) ;
				
			}
			// Order Id
			if( i >= 16 && i <= 19)
			{
				
				objData[i++] = (byte)(Obj.externalOrderID);
				objData[i++] = (byte)(Obj.externalOrderID >> 8);
				objData[i++] = (byte)(Obj.externalOrderID >> 16);
				objData[i++] = (byte)(Obj.externalOrderID >> 24);
				
			}
			
			// Quantity
			if( i >= 20 && i <= 23 )
			{
				
				objData[i++] = (byte)(Obj.Quantity);
				objData[i++] = (byte)(Obj.Quantity >> 8);
				objData[i++] = (byte)(Obj.Quantity >> 16);
				objData[i++] = (byte)(Obj.Quantity >> 24);
				
			}
			// Filled Quantity
			if( i >= 24 && i <= 27)
			{
				
				objData[i++] = (byte)(Obj.QuantityFilled);
				objData[i++] = (byte)(Obj.QuantityFilled >> 8);
				objData[i++] = (byte)(Obj.QuantityFilled >> 16);
				objData[i++] = (byte)(Obj.QuantityFilled >> 24);
				
			}
			// Disclosed Quantity
			if( i >= 28 && i <= 31)
			{
				objData[i++] = (byte)(Obj.DisclosedQnty);
				objData[i++] = (byte)(Obj.DisclosedQnty >> 8);
				objData[i++] = (byte)(Obj.DisclosedQnty >> 16);
				objData[i++] = (byte)(Obj.DisclosedQnty >> 24);
				
			}
			// Price
			if( i >= 32 && i <= 39)
			{
			 for(byte x : DoubleToByteArr(Obj.Price))
			 {
				 objData[i++] = x;
			 }
				
				
			}
			// Time Stamp
			if( i >= 40 && i<= 47)
			{
				for(int j = 7; j >= 0; j--)
				{
					objData[i++] = (byte)((Obj.TimeStamp >> ((7 - j) * 8)) & 0xff);
				}
				
			}
			// Account Id 
			if( i >= 48 && i <= 53)
			{
				objData[i++] = (byte)(Obj.AccountID);
				objData[i++] = (byte)(Obj.AccountID >> 8);
				objData[i++] = (byte)(Obj.AccountID >> 16);
				objData[i++] = (byte)(Obj.AccountID >> 24);
			}
			
		return objData;
	}
	/*
	 * Utility Method to Convert Double to Byte Array  
	 */
private byte[] DoubleToByteArr(double d)
{
	byte[] output = new byte[8];
	long lng = Double.doubleToLongBits(d);
	for(int i = 7; i >= 0; i--)
		{
		
			output[7-i] = (byte)((lng >> ((7 - i) * 8)) & 0xff);
		}
	return output;
}

}
