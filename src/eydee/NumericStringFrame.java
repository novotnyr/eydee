package eydee;

public class NumericStringFrame {
	private int value;
	
	public NumericStringFrame(byte[] content) {
		try {
			TextFrame frame = new TextFrame(content);
			value = Integer.valueOf(frame.getContents());
		} catch (NumberFormatException e) {
			throw new ID3ParseException("Cannot parse content to integer", e);
		} 
	}
	
	public int getValue() {
		return value;
	}
}
