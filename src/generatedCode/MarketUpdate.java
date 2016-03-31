package generatedCode;

import java.nio.ByteBuffer;

public class MarketUpdate {
	public long instrumentId;
	public long timeExchange;
	public TypeUtils.BuySellIndicator buySellIndicator;
	public TypeUtils.BookElement[] buyBook = new TypeUtils.BookElement[20];
	public char[] symbol = new char[20];

	public MarketUpdate convertToObject(ByteBuffer buffer) {
		MarketUpdate genObject = new MarketUpdate();
		genObject.instrumentId = (long) buffer.getLong();
		genObject.timeExchange = (long) buffer.getLong();
		genObject.buySellIndicator = (TypeUtils.BuySellIndicator) TypeUtils.getEnumBuySellIndicator(buffer.getInt());
		;
		for (int i = 0; i < 20; i++) {
			genObject.buyBook[i] = TypeUtils.BookElement.convertToObject(buffer);
		}
		for (int i = 0; i < 20; i++) {
			genObject.symbol[i] = (char) buffer.get();
		}
		return genObject;
	}

	public ByteBuffer convertFromObject(MarketUpdate object) {
		ByteBuffer buffer = ByteBuffer.allocate(100);
		buffer.putLong(object.instrumentId);
		buffer.putLong(object.timeExchange);
		buffer.putInt(object.buySellIndicator.getValue());
		for (int i = 0; i < 20; i++) {
			buffer.put(object.buyBook[i].convertFromObject(object.buyBook[i]));
		}
		for (int i = 0; i < 20; i++) {
			buffer.putChar(object.symbol[i]);
		}
		return buffer;
	}

}
