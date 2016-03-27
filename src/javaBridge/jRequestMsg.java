package javaBridge;

public class jRequestMsg {
	public enum OrderType {
		LIMIT, MARKET, STOP, CONDITIONAL_LIMIT_PRICE, BEST_PRICE
	}

	public enum RequestType {
		NEWORDER, MODIFYORDER, CANCELORDER, ORDERSTATUS, HEARTBEAT
	}

	public enum OrderDuration {
		DAY, GTC, GTD, FAK, FOK

	}

	public enum ExchangeCode {
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

	public enum BuySellIndicator {
		NONE, BUY, SELL
	}

	public byte convertEnumToInt(BuySellIndicator b) {
		byte ret_value = 0;
		switch (b) {
		case NONE:
			ret_value = 0;
			break;
		case BUY:
			ret_value = 1;
			break;
		case SELL:
			ret_value = 2;
			break;
		}
		return ret_value;

	}

	public int convertEnumToInt(ExchangeCode e) {
		int ret_value = 0;
		switch (e) {
		case NSE_FO:
			ret_value = 1;
			break;
		case CME:
			ret_value = 2;
			break;
		case CHINA:
			ret_value = 3;
			break;
		case MCX:
			ret_value = 4;
			break;
		case LME:
			ret_value = 5;
			break;
		case MCX_SX:
			ret_value = 6;
			break;
		case BSE:
			ret_value = 7;
			break;
		case BRAZIL_FU:
			ret_value = 8;
			break;
		case BAD:
			ret_value = 9;
			break;
		case NYSE:
			ret_value = 10;
			break;
		case NSE_CD:
			ret_value = 11;
			break;
		case NSE_CM:
			ret_value = 12;
			break;
		case BRAZIL_EQ:
			ret_value = 13;
			break;
		case MAX_VALUE:
			ret_value = 0;
			break;
		}
		return ret_value;

	}
	public byte convertEnumToInt(RequestType r) {
		byte ret_value = 0;
		switch (r) {
		case NEWORDER:
			ret_value = 0;
			break;
		case MODIFYORDER:
			ret_value = 1;
			break;
		case  CANCELORDER:
			ret_value = 2;
			break;
		case ORDERSTATUS:
			ret_value = 3;
			break;
		case HEARTBEAT:
			ret_value = 4;
			break;
		}
		return ret_value;

	}
	public byte convertEnumToInt(OrderType o) {
		byte ret_value = 0;
		switch (o) {
		case MARKET:
			ret_value = 1;
			break;
		case LIMIT:
			ret_value = 2;
			break;
		case  STOP:
			ret_value = 3;
			break;
		case CONDITIONAL_LIMIT_PRICE:
			ret_value = 5;
			break;
		case BEST_PRICE:
			ret_value = 6;
			break;
		}
		return ret_value;

	}
	public byte convertEnumToInt(OrderDuration o) {
		byte ret_value = 0;
		switch (o) {
		case DAY:
			ret_value = 0;
			break;
		case GTC:
			ret_value = 1;
			break;
		case GTD:
			ret_value = 6;
			break;
		case FAK:
			ret_value = 3;
			break;
		case FOK:
			ret_value = 4;
			break;
		}
		return ret_value;

	}	


	public RequestType Request_Type;
	public OrderType OrdType;
	public OrderDuration Duration;
	public int externalOrderID;
	public int Quantity;
	public int QuantityFilled;
	public int DisclosedQnty;
	public double Price;
	public long TimeStamp;
	public int AccountID;
	public BuySellIndicator Transaction_Type;
	public ExchangeCode Exchange_Type;
	public long instrumentId ;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("|Request_Type=");
		sb.append(Request_Type);
		sb.append("|OrdType=");
		sb.append(OrdType);
		sb.append("|Duration=");
		sb.append(Duration);
		sb.append("|OrderID=");
		sb.append(externalOrderID);
		sb.append("|Quantity=");
		sb.append(Quantity);
		sb.append("|QuantityFilled=");
		sb.append(QuantityFilled);
		sb.append("|DisclosedQnty=");
		sb.append(DisclosedQnty);
		sb.append("|Price=");
		sb.append(Price);
		sb.append("|TimeStamp=");
		sb.append(TimeStamp);
		sb.append("|AccountID=");
		sb.append(AccountID);
		sb.append("|Transaction_Type=");
		sb.append(Transaction_Type);
		sb.append("|Exchange_Type=");
		sb.append(Exchange_Type);

		return sb.toString();
	}
}
