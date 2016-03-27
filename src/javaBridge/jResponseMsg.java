package javaBridge;

public class jResponseMsg 
{
    public enum ResponseType 
    {
        NEW_ORDER_CONFIRM,
        NEW_ORDER_FREEZE,
        MODIFY_ORDER_CONFIRM,
        CANCEL_ORDER_CONFIRM,
        TRADE_CONFIRM,
        ORDER_ERROR,
        MODIFY_ORDER_REJECT,
        CANCEL_ORDER_REJECT,
        ORS_REJECT,
        RMS_REJECT,
        SIM_REJECT,
        MODIFY_ORDER_PENDING,
        CANCEL_ORDER_PENDING,
        ORDERS_PER_DAY_LIMIT_REJECT,
        ORDERS_PER_DAY_LIMIT_WARNING,
        ORDER_EXPIRED,
        STOP_LOSS_WARNING,
        NULL_RESPONSE 
    }
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
    public enum ErrorCode 
    {
        E_NO_ORS_ERROR,
        E_NO_ORS_RMS_ERROR,
        E_ORS_RMS_BASE,
        E_ORS_INVALID_SIDE,
        E_ORS_INVALID_ORDER_TYPE,
        E_ORS_INVALID_DURATION,
        E_ORS_INVALID_REQUEST_TYPE,
        E_ORS_UNKNOWN_TRADING_SESSION,
        E_ORS_RECONNECTING_TO_EXCHANGE,
        E_ORS_SESSION_TERMINATED,
        E_ORS_SESSION_ABORTED,
        E_ORS_SYMBOLMAPPING_NOT_FOUND,
        E_ORS_NOT_CONFIRMED,
        E_ORS_PAUSED,
        E_ORS_QUEUED_ORDER_REJECT,
        E_ORS_EXCHANGE_CANCEL,
        E_ORS_SELF_TRADE,
        E_RMS_NO_ERROR,
        E_RMS_BLOCKED_ACCOUNT,
        E_RMS_BLOCKED_SYMBOL,
        E_RMS_ORDER_QTY_LIMIT_BREACHED,
        E_RMS_ORDER_VALUE_LIMIT_BREACHED,
        E_RMS_UNEXPECTED_ORDERID,
        E_RMS_TICKER_OO_LIMIT_BREACHED,
        E_RMS_TICKER_VALUE_LIMIT_BREACHED,
        E_RMS_ORDER_FREQUENCY_LIMIT_BREACHED,
        E_RMS_MODIFY_ORDER_NOT_FOUND,
        E_RMS_CANCEL_ORDER_NOT_FOUND,
        E_RMS_TOTAL_ORDERS_PER_DAY_LIMIT_BREACHED,
        E_RMS_TOTAL_ORDERS_PER_DAY_LIMIT_WARNING,
        E_RMS_INCOMPLETE_INIT,
        E_RMS_STRAT_WITH_MORE_ACCOUNTS,
        E_RMS_TOTAL_ORDERS_PER_TRADE_LIMIT_WARNING,
        E_RMS_TOTAL_ORDERS_PER_TRADE_LIMIT_BREACHED,
        E_RMS_ACCOUNT_LEVEL_STOP_LOSS_LIMIT_WARNING,
        E_RMS_ACCOUNT_LEVEL_STOP_LOSS_LIMIT_BREACHED,
        E_RMS_FIRM_LEVEL_STOP_LOSS_LIMIT_BREACHED,
        E_RMS_TOTAL_POSITION_IN_LOTS_FIRMLEVEL,
        E_RMS_QUANTITY_PERORDER_IN_LOTS_FIRMLEVEL,
        E_RMS_NOT_ALLOWED_SYMBOL,
        E_RMS_TICKER_LEVEL_STOP_LOSS_LIMIT_WARNING,
        E_RMS_TICKER_LEVEL_STOP_LOSS_LIMIT_BREACHED,
        E_RMS_MAX_CANCEL_CONFIRMS_LIMIT_BREACHED,
        E_RMS_TICKER_LEVEL_PERCENTAGE_AWAY_FROM_X_FAILED,
        E_RMS_ORDER_LEVEL_FILL_QUANTITY_MISMATCH,
        E_RMS_NET_POSITION_IN_LOTS_FIRMLEVEL,
        E_SANITY_BASE,
        E_SANITY_NO_ERROR,
        E_SANITY_ORDERBOOK_CROSS,
        E_SANITY_LTP_MISMATCH_NEWPRICE,
        E_SANITY_LTQ_MISMATCH_NEWQTY,
        E_SANITY_LTQ_MISMATCH_PREV_BEST_BID,
        E_SANITY_LTP_MISMATCH_PREV_BEST_ASK,
        E_SANITY_LTQ_MISMATCH_PREV_BEST_ASK,
        E_SANITY_SEQUENCE_WRONG,
        E_SANITY_RPT_SEQUENCE_WRONG,
        E_SANITY_LEVEL_NEGATIVE_QTY,
        E_SANITY_BID_ORDER_INVALID,
        E_SANITY_ASK_ORDER_INVALID,
        E_SANITY_ETS_INVALID,
        E_SANITY_LTS_INVALID,
        E_SANITY_LTP_MISMATCH_PREV_BEST_BID,
        E_SANITY_VALIDBIDS_ASKS_BAD_VALUE,
        E_SANITY_TTQ_ASC_ORDER_FAILED,
        E_SANITY_ALL_MATCH_WITH_PREVIOUS_BOOK,
        E_SANITY_SIDE_CHECK,
        E_SANITY_VALID_BIDS_CHECK,
        E_SANITY_VALID_ASKS_CHECK
    }

	
    public void setExchangeCode(String exchangeCode)
	{
		this._exchangeCode = ExchangeCode.valueOf(exchangeCode);
	}
    public void setResponseType(String responseType)
    {
    	this._responseType = ResponseType.valueOf(responseType);
    }
    public void setErrorCode(String errorCode)
    {
    	this._errorCode = ErrorCode.valueOf(errorCode);
    }
    public void setBuySellIndicator(String buySellIndicator)
    {
    	this._buySellIndicator = BuySellIndicator.valueOf(buySellIndicator);
    }
    
    public ResponseType	_responseType;
    public long		_externalOrderID;
    public long		_internalOrderID;
    public ErrorCode _errorCode;
    public int		_quantity; 
    public double	_price; 
    public long		_timeStamp;
    public long		_instrumentId;
    public BuySellIndicator _buySellIndicator;
    public ExchangeCode _exchangeCode;
    int _accId ;
    
    @Override
    public String toString(){
    	StringBuilder sb=new StringBuilder();
    	
    	sb.append("extrenalOrderID=");
    	sb.append(_externalOrderID);
    	sb.append("intrenalOrderID=");
    	sb.append(_internalOrderID);
    	sb.append("|ReponseType=");
    	sb.append(_responseType);
    	sb.append("|ErrorCode=");
    	sb.append(_errorCode);
    	sb.append("|Quantity=");
    	sb.append(_quantity);
    	sb.append("|Price=");
    	sb.append(_price);
    	sb.append("|TimeStamp=");
    	sb.append(_timeStamp);
    	sb.append("|Buy Sell Indicator=");
    	sb.append(_buySellIndicator);
    	
    	
    	return sb.toString();
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
    public void setResponseType(byte b) {
		/*
		 * Trick Method which assumes that enum values in C++ have been assigned numbers from 0 onwards
		 * according to their order  .. If its not the case then please use switch case statement as in
		 * setBuySellIndicator 
		 */
    	this._responseType = ResponseType.values()[(int)b];
	}
    public void setErrorCode(short s)
    {
    	/*
		 * Trick Method which assumes that enum values in C++ have been assigned numbers from 0 onwards
		 * according to their order  .. If its not the case then please use switch case statement as in
		 * setBuySellIndicator 
		 */
    	this._errorCode = ErrorCode.values()[(int)s];
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
}
