package javaBridge;
public class jMarketUpdateNew {

	public static final int MAX_SYMBOL_SIZE = 50;
	

	/* m_exchangeName code */
	 public enum ExchangeCode
	    {
	            NSE_FO ,
	            CME ,
	            CHINA ,
	            MCX ,
	            LME ,
	            MCX_SX ,
	            BSE ,
	            BRAZIL_FU ,
	            BAD ,
	            NYSE ,
	            NSE_CD ,
	            NSE_CM ,
	            BRAZIL_EQ ,
	            MAX_VALUE
	    }
	
	
	public enum  BuySellIndicator 
    {
        NONE ,
        BUY ,
        SELL
    }
	public enum UpdateCode 
    {
        ADD ,
        MOD ,
        DEL ,
        DEL_FROM ,
        DEL_THRU ,
        TRADE_BU ,  
        TRADE_BU_GEN ,
        TRADE_NO_BU , 
        OVERLAY ,
        SNAPSHOT ,
        NONE ,
        MOD_ERROR ,
        ADD_ERROR ,
        DEL_ERROR 
    }
	public enum  UpdateFlags 
    {
         UF0_PACKET ,
         UF0_ENDPACKET ,
         UF0_INST_STATE_CHANGE ,
         UF0_MKT_STATE_CHANGE ,
         UF0_SEQUENCE_RESET ,
         UF1_INST_TRADING,
         UF1_INST_NON_TRADING ,
         UF1_INST_IN_AUCTION ,
         UF1_MKT_PREOPEN ,
         UF1_MKT_OPEN ,
         UF1_MKT_PRECLOSE ,
         UF1_MKT_CLOSE ,
         UF1_MKT_HALTED ,
         UF1_MKT_AUCTION ,
         UF1_REGULAR_BOOK_UPDATE ,
         UF1_IMPLIED_BOOK_UPDATE 
    }


	public static class BookElement {
		public double _price;
        public int _quantity;
        public int _orderCount;

		public BookElement(double price, int quantity, int orderCount) {
			this._price = price;
			this._quantity = quantity;
			this._orderCount = orderCount;
		}
		
		
		public String toString(){
			StringBuilder sb=new StringBuilder();
			sb.append(this._price);
			sb.append("|");
			sb.append(this._quantity);
			sb.append("|");
			sb.append(this._orderCount);
			return sb.toString();
		}
	}
	
	public void setBuySellIndicator(String buySellIndicator)
    {
    	this._buySellIndicator = BuySellIndicator.valueOf(buySellIndicator);
    }
	public void setUpdateFlag1(String updateFlag)
    {
    	this._updateFlag1 = UpdateFlags.valueOf(updateFlag);
    }
	public void setUpdateFlag2(String updateFlag)
    {
    	this._updateFlag2 = UpdateFlags.valueOf(updateFlag);
    }
	public void setUpdateCode(String updateCode)
	{
		this._updateType = UpdateCode.valueOf(updateCode);
	}
	public void setExchangeCode(String exchangeCode)
	{
		this._exchangeCode = ExchangeCode.valueOf(exchangeCode);
	}
	
	//************************
	public long _orderId;
	public double _currentPrice;
	public double _previousPrice;
	public double _lastTradePrice;
	public long _lastTradeTime;
	public long _totalTradedQuantity;
	public long _totalTradedValue;
	public long _currentQuantity;
	public long _previousQuantity;
	public long _lastTradeQuantity;
	public int _filledBuyLevels;
	public int _filledSellLevels;
	public int _bookUpdateIndex;
	public long _timeExchange;
	public long _timeReceived;
	public long _streamSequenceNumber;
	public long _instrumentId;
	public long _instrumentSequenceNumber;
	public BuySellIndicator _buySellIndicator; // --->> Buy sell indicator Define Enum
	public ExchangeCode _exchangeCode;
    public String _symbol;
    public BookElement _buyBook[];
	public BookElement _sellBook[];
	public UpdateCode _updateType;//-->>Update code Later t be changed to enum
	public UpdateFlags _updateFlag1;
	public UpdateFlags _updateFlag2;
	
	//**********************	
	public jMarketUpdateNew() {
		_buyBook = new BookElement[20];
		_sellBook = new BookElement[20];
	}
	
	
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		
		sb.append("|sellUpdates=");
		sb.append("[");
		for(int i=0;i<_filledSellLevels;i++){		
			sb.append(_sellBook[i]);
			sb.append(",");
		}
		sb.append("]");
		sb.append("|buyUpdates=");
		
		sb.append("[");
		for(int i=0;i<_filledBuyLevels;i++){		
			sb.append(_buyBook[i]);
			sb.append(",");
		}
		sb.append("]");
		
		sb.append("|_exchangeCode=");
		sb.append(_exchangeCode);
		sb.append("|exchTS=");
		sb.append(_timeExchange);
		sb.append("|lastTradedPrice=");
		sb.append(_lastTradePrice);
		sb.append("|lastTradedQuantity=");
		sb.append(_lastTradeQuantity);
		sb.append("|lastTradedTime=");
		sb.append(_lastTradeTime);
		sb.append("|newPrice=");
		sb.append(_currentPrice);
		sb.append("|newQuant=");
		sb.append(_currentQuantity);
		sb.append("|Previous Price=");
		sb.append(_previousPrice);
		sb.append("|Previous Quantity=");
		sb.append(_previousQuantity);
		sb.append("|seqnum=");
		sb.append(_streamSequenceNumber);
		sb.append("|side=");
		sb.append(_buySellIndicator);
		sb.append("|symbol=");
		sb.append(_symbol);
		sb.append("|timestamp=");
		sb.append(_timeReceived);
		sb.append("|Instrumnet Id=");
		sb.append(_instrumentId);
		sb.append("|totalTradedQuantity=");
		sb.append(_totalTradedQuantity);
		sb.append("|totalTradedValue=");
		sb.append(_totalTradedValue);
		sb.append("|updateLevel=");
		sb.append(_bookUpdateIndex);
		sb.append("|updateType=");
		sb.append(_updateType);
		sb.append("|validAsks=");
		sb.append(_filledSellLevels);
		sb.append("|validBids=");
		sb.append(_filledBuyLevels);
		sb.append("|Buy Sell Indicator ");
		sb.append(_buySellIndicator);
		sb.append("| end packet");
		sb.append(_updateFlag1);
		sb.append("| feed type");
		sb.append(_updateFlag2);

		return sb.toString();
	}
	public void setExchangeCode(int i) {
		switch(i)
		{
		case 1:
			this._exchangeCode =  ExchangeCode.NSE_FO ;
			break;
		case 2:
			this._exchangeCode =  ExchangeCode.CME ;
			break;
		case 3:
			this._exchangeCode =  ExchangeCode.CHINA ;
			break;
		case 4:
			this._exchangeCode =  ExchangeCode.MCX ;
			break;
		case 5:
			this._exchangeCode =  ExchangeCode.LME ;
			break;
		case 6:
			this._exchangeCode =  ExchangeCode.MCX_SX ;
			break;
		case 7:
			this._exchangeCode =  ExchangeCode.BSE ;
			break;
		case 8:
			this._exchangeCode =  ExchangeCode.BRAZIL_FU ;
			break;
		case 9:
			this._exchangeCode =  ExchangeCode.BAD ;
			break;
		case 10:
			this._exchangeCode =  ExchangeCode.NYSE ;
			break;
		case 11:
			this._exchangeCode =  ExchangeCode.NSE_CD ;
			break;
		case 12:
			this._exchangeCode =  ExchangeCode.NSE_CM ;
			break;
		case 13:
			this._exchangeCode =  ExchangeCode.BRAZIL_EQ ;
			break;
		default:
			this._exchangeCode =  ExchangeCode.MAX_VALUE ;
		}
		
	}
	public void setBuySellIndicator(byte b) {
		switch(b)
		{
		case 0:
			this._buySellIndicator = BuySellIndicator.NONE;
			break;
		case 1:
			this._buySellIndicator = BuySellIndicator.BUY;
			break;
		case 2:
			this._buySellIndicator = BuySellIndicator.SELL;
			break;
		}
	}
	           
	public void setUpdateCode(byte b) {
		switch(b)
		{
		case 0:
			this._updateType = UpdateCode.ADD;
			break;
		case 1:
			this._updateType = UpdateCode.MOD;
			break;
		case 2:
			this._updateType = UpdateCode.DEL;
			break;
		case 3:
			this._updateType = UpdateCode.DEL_FROM;
			break;
		case 4:
			this._updateType = UpdateCode.DEL_THRU;
			break;
		case 5:
			this._updateType = UpdateCode.TRADE_BU;
			break;
		case 6:
			this._updateType = UpdateCode.TRADE_BU_GEN;
			break;
		case 7:
			this._updateType = UpdateCode.TRADE_NO_BU;
			break;
		case 8:
			this._updateType = UpdateCode.OVERLAY;
			break;
		case 9:
			this._updateType = UpdateCode.SNAPSHOT;
			break;
		case 10:
			this._updateType = UpdateCode.NONE;
			break;
		case 11:
			this._updateType = UpdateCode.MOD_ERROR;
			break;
		case 12:
			this._updateType = UpdateCode.ADD_ERROR;
			break;
		case 13:
			this._updateType = UpdateCode.DEL_ERROR;
			break;
		}
	           
		
	}
	public void setUpdateFlag1(byte b) {
		switch(b)
		{
		case 0:
			this._updateFlag1 = UpdateFlags.UF0_PACKET;
			break;
		case 1:
			this._updateFlag1 = UpdateFlags.UF0_ENDPACKET;
			break;
		case 2:
			this._updateFlag1 = UpdateFlags.UF0_INST_STATE_CHANGE;
			break;
		case 3:
			this._updateFlag1 = UpdateFlags.UF0_MKT_STATE_CHANGE;
			break;
		case 4:
			this._updateFlag1 = UpdateFlags.UF0_SEQUENCE_RESET;
			break;
		}
		
	}
	public void setUpdateFlag2(byte b) {
		switch(b)
		{
		case 20:
			this._updateFlag2 = UpdateFlags.UF1_INST_TRADING ;
			break;
		case 21:
			this._updateFlag2 = UpdateFlags. UF1_INST_NON_TRADING ;
			break;
		case 22:
			this._updateFlag2 = UpdateFlags.UF1_INST_IN_AUCTION ;
			break;
		case 23:
			this._updateFlag2 = UpdateFlags.UF1_MKT_PREOPEN ;
			break;
		case 24:
			this._updateFlag2 = UpdateFlags.UF1_MKT_OPEN ;
			break;
		case 25:
			this._updateFlag2 = UpdateFlags.UF1_MKT_PRECLOSE ;
			break;
		case 26:
			this._updateFlag2 = UpdateFlags.UF1_MKT_CLOSE ;
			break;
		case 27:
			this._updateFlag2 = UpdateFlags.UF1_MKT_HALTED ;
			break;
		case 28:
			this._updateFlag2 = UpdateFlags.UF1_MKT_AUCTION ;
			break;
		case 40:
			this._updateFlag2 = UpdateFlags.UF1_REGULAR_BOOK_UPDATE ;
			break;
		case 41:
			this._updateFlag2 = UpdateFlags.UF1_IMPLIED_BOOK_UPDATE ;
			break;
		}
        
	}
}
