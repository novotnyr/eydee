package eydee;

import java.util.Arrays;

public class FrameHeader {
	private static final int FRAME_LENGTH = 10;

	private static final int FRAME_ID_LENGTH = 4;
	
	public static final int FRAME_SIZE_LENGTH = 4;
	
	private String id;
	
	private int frameSize;

	public FrameHeader(byte[] tenByteArray) {
		if(tenByteArray.length != FRAME_LENGTH) {
			throw new IllegalArgumentException("Frame header buffer must be 10 bytes long!");
		}
		this.id = new String(Arrays.copyOf(tenByteArray, FRAME_ID_LENGTH));
		this.frameSize = Utils.parseSize(Arrays.copyOfRange(tenByteArray, FRAME_ID_LENGTH, FRAME_ID_LENGTH + FRAME_SIZE_LENGTH));
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int size) {
		this.frameSize = size;
	}
	
	
}
