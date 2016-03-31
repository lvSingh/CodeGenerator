package generatedCode;

import java.nio.ByteBuffer;

public class TypeUtils {
	public static class BookElement {
		public double price;
		public int quantity;
		public int orderCount;

		public static BookElement convertToObject(ByteBuffer buffer) {
			BookElement genObject = new BookElement();
			genObject.price = buffer.getDouble();
			genObject.quantity = buffer.getInt();
			genObject.orderCount = buffer.getInt();
			return genObject;
		}

		public ByteBuffer convertFromObject(BookElement object) {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			buffer.putDouble(object.price);
			buffer.putInt(object.quantity);
			buffer.putInt(object.orderCount);
			return buffer;
		}
	}

	public enum BuySellIndicator {
		Sell(0), Buy(1), Invalid(10);
		private int value;

		BuySellIndicator(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}

	public static BuySellIndicator getEnumBuySellIndicator(int b) {
		for (BuySellIndicator e : BuySellIndicator.values()) {
			if (e.getValue() == b) {
				return e;
			}
		}
		return null;
	}

}
