package eydee;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class TagParser {
	private static final int FRAME_HEADER_BYTES = 10;

	public Tag parse(File mp3File) throws FileNotFoundException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(mp3File));
		
		ID3Header header = parseHeader(in);
		return parseFrames(in, header);
	}

	private Tag parseFrames(BufferedInputStream in, ID3Header header) {
		try {
			byte[] wholeTagBuffer = new byte[header.getTagSize()];
			int read = in.read(wholeTagBuffer);
			if(read < header.getTagSize()) {
				throw new ID3ParseException("Actual tag size is not equal to declared size");
			}

			if(header.isFollowedByExtendedHeader()) {
				// Where the 'Extended header size', currently 6 or 10 bytes, excludes itself.
				int extendedHeaderSize = Utils.parseSize(Arrays.copyOf(wholeTagBuffer, 4));
				wholeTagBuffer = Arrays.copyOfRange(wholeTagBuffer, 0, wholeTagBuffer.length - extendedHeaderSize);
			}
			int pointer = 0;
			Tag tag = new Tag();
			do {
				byte[] frameHeaderBytes = Arrays.copyOfRange(wholeTagBuffer, pointer, pointer + FRAME_HEADER_BYTES);
				FrameHeader frameHeader = new FrameHeader(frameHeaderBytes);
				byte[] frameBytes = Arrays.copyOfRange(wholeTagBuffer, pointer + FRAME_HEADER_BYTES, pointer + FRAME_HEADER_BYTES + frameHeader.getFrameSize());
				
				handleFrame(frameHeader, frameBytes, tag);
				
				pointer = pointer + FRAME_HEADER_BYTES + frameHeader.getFrameSize();
			} while(pointer < wholeTagBuffer.length);
			return tag;
		} catch (IOException e) {
			throw new ID3ParseException("Cannot parse header due to I/O problems.", e);
		}
		
	}

	protected Tag handleFrame(FrameHeader frameHeader, byte[] frameBytes, Tag tag) {
		try {
			if(frameHeader.getId().equals("TIT2")) {
				TextFrame textFrame = new TextFrame(frameBytes);
				tag.setTitle(textFrame.getContents());
			}
			if(frameHeader.getId().equals("TALB")) {
				TextFrame textFrame = new TextFrame(frameBytes);
				tag.setAlbum(textFrame.getContents());
			}
			if(frameHeader.getId().equals("TPE1")) {
				TextFrame textFrame = new TextFrame(frameBytes);
				tag.setArtist(textFrame.getContents());
			}

			if(frameHeader.getId().equals("TYER")) {
				NumericStringFrame textFrame = new NumericStringFrame(frameBytes);
				tag.setYear(textFrame.getValue());
			}


			return tag;
		} catch (ID3ParseException e) {
			throw new ID3ParseException("Cannot handle frame while parsing " + frameHeader.getId(), e);
		}
	}

	private ID3Header parseHeader(BufferedInputStream in) throws ID3ParseException {
		try {
			byte[] headerBuf = new byte[ID3Header.HEADER_SIZE];
			int read = in.read(headerBuf);
			if(read < ID3Header.HEADER_SIZE) {
				throw new ID3ParseException("Header is too short: 10 bytes must be read!");				
			}
			return ID3Header.parse(headerBuf);
		} catch (IOException e) {
			throw new ID3ParseException("Cannot parse header due to I/O problems.", e);
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		TagParser parser = new TagParser();
		Tag tag = parser.parse(new File("d:/MP3/Kapela ze wsi Warszawa/2009 Infinity/(01) Pieœñ m¹drego dziecka [Wise Kid Song].mp3"));
		System.out.println(tag);
	}
}
