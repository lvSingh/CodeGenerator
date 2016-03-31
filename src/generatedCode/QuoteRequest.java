package generatedCode;

import java.nio.ByteBuffer;

public class QuoteRequest {
	public long instrumentId;
	public long timeExchange;
	public TypeUtils.BuySellIndicator buySellIndicator;
	public char[] symbol = new char[20];

	public QuoteRequest convertToObject(ByteBuffer buffer) {
		QuoteRequest genObject = new QuoteRequest();
		genObject.instrumentId = (long) buffer.getLong();
		genObject.timeExchange = (long) buffer.getLong();
		genObject.buySellIndicator = (TypeUtils.BuySellIndicator) TypeUtils.getEnumBuySellIndicator(buffer.getInt());
		;
		for (int i = 0; i < 20; i++) {
			genObject.symbol[i] = (char) buffer.get();
		}
		return genObject;
	}

	public ByteBuffer convertFromObject(QuoteRequest object) {
		ByteBuffer buffer = ByteBuffer.allocate(100);
		buffer.putLong(object.instrumentId);
		buffer.putLong(object.timeExchange);
		buffer.putInt(object.buySellIndicator.getValue());
		for (int i = 0; i < 20; i++) {
			buffer.putChar(object.symbol[i]);
		}
		return buffer;
	}

}
