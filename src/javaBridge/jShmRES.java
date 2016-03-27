package javaBridge;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class jShmRES {
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
	public jShmRES()
	{
		_size = 810;
		_byteObj =  new byte[120]; 
		_dataFile = new File("/dev/shm/jResBuffer.bin");
		_dataFile.delete();
		
		_syncFile = new File("/dev/shm/jResSync.bin");
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
		
		_readCount = 0;
		
	}
	public jResponseMsg process()
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
	private jResponseMsg parseToObj(byte[] arr) {
		ByteBuffer buf = ByteBuffer.wrap(arr);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		jResponseMsg obj = new jResponseMsg();
		
		obj.setBuySellIndicator(buf.get());
		obj.setResponseType(buf.get());
		obj.setErrorCode(buf.getShort());
		obj.setExchangeCode(buf.getInt());
		obj._quantity = buf.getInt();
		obj._internalOrderID = buf.getInt();
		obj._instrumentId = buf.getLong();
		obj._timeStamp = buf.getLong();
		obj._externalOrderID = buf.getLong();
		obj._price = buf.getDouble();
		obj._accId = buf.getInt();
		
		return obj;
	}
	

}
