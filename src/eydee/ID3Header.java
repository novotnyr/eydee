package eydee;

import java.util.Arrays;

public class ID3Header {
	public static final int HEADER_SIZE = 10;

	public static final String HEADER_IDENTIFIER = "ID3";
	
	public static final int HEADER_MAJOR_VERSION_INDEX = 3;
	
	public static final int HEADER_REVISION_INDEX = 4;
	
	public static final int HEADER_FLAGS_INDEX = 5;
	
	private boolean usingUnsynchronization;
	
	private int majorVersion;
	
	private int revisionVersion;
	
	private boolean isFollowedByExtendedHeader;
	
	private boolean isExperimental;
	
	private int tagSize;

	public static ID3Header parse(byte[] headerBytes) {
		if(headerBytes.length < HEADER_SIZE) {
			throw new IllegalArgumentException("Header is too short: 10 bytes must be read!");				
		}
		
		byte[] identifierBytes = Arrays.copyOfRange(headerBytes, 0, 3);
		String identifier = new String(identifierBytes);
		if(!identifier.equals(HEADER_IDENTIFIER)) {
			throw new ID3ParseException("Wrong ID3 tag identifier: " + identifier);
		}
		
		ID3Header header = new ID3Header();
		header.setMajorVersion(headerBytes[HEADER_MAJOR_VERSION_INDEX]);
		header.setRevisionVersion(headerBytes[HEADER_REVISION_INDEX]);
	
		byte flags = headerBytes[HEADER_FLAGS_INDEX];
		int unsync = flags << 1;
		int extended = flags << 2;
		int experimental = flags << 3;
		
		byte[] tagSizeBytes = Arrays.copyOfRange(headerBytes, 6, 10);
		
		header.setUsingUnsynchronization(unsync != 0);
		header.setIsFollowedByExtendedHeader(extended != 0);
		header.setExperimental(experimental != 0);
		header.setTagSize(Utils.parseSize(tagSizeBytes));
		
		return header;		
	}
	
	
	public boolean isUsingUnsynchronization() {
		return usingUnsynchronization;
	}

	public void setUsingUnsynchronization(boolean usingUnsynchronization) {
		this.usingUnsynchronization = usingUnsynchronization;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getRevisionVersion() {
		return revisionVersion;
	}

	public void setRevisionVersion(int revisionVersion) {
		this.revisionVersion = revisionVersion;
	}

	public boolean isFollowedByExtendedHeader() {
		return isFollowedByExtendedHeader;
	}

	public void setIsFollowedByExtendedHeader(boolean isFollowedByExtendedHeader) {
		this.isFollowedByExtendedHeader = isFollowedByExtendedHeader;
	}

	public boolean isExperimental() {
		return isExperimental;
	}

	public void setExperimental(boolean isExperimental) {
		this.isExperimental = isExperimental;
	}

	public void setTagSize(int tagSize) {
		this.tagSize = tagSize;
	}
	
	public int getTagSize() {
		return tagSize;
	}
	
	@Override
	public String toString() {
		return String.format("ID3v2.%s.%s Extended: %s, Experimental: %s ", majorVersion, revisionVersion, 
				isFollowedByExtendedHeader, isExperimental);
	}

}
