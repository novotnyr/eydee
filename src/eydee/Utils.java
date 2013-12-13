package eydee;

public class Utils {
	
	public static int parseSize(byte[] fourByteBuffer) {
		if(fourByteBuffer.length != FrameHeader.FRAME_SIZE_LENGTH) {
			throw new IllegalArgumentException("Size byte array must be of 4-element array!");
		}
		StringBuilder bits = new StringBuilder();
		for (int i = 0; i < FrameHeader.FRAME_SIZE_LENGTH; i++) {
			int element = fourByteBuffer[i];
			String elementBits = String.format("%1$#8s", Integer.toBinaryString(element));
			// we ignore first bit (7th) since 7th bit is ignored
			for(int j = 1; j < 8; j++) {
				char c = elementBits.charAt(j);
				if(c == ' ') {
					bits.append(0);
				} else {
					bits.append(elementBits.charAt(j));
				}
			}
		}
		return Integer.parseInt(bits.toString(), 2); 
	}

}
