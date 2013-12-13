package eydee;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TextFrame {
	private String contents;
	
	public static final int ISO_8859_1_ENCODING = 0;
	
	public static final int UNICODE_ENCODING = 1;
	
	public TextFrame(byte[] content) throws ID3ParseException {
		int encoding = content[0];
		if(encoding != ISO_8859_1_ENCODING && encoding != UNICODE_ENCODING) {
			throw new ID3ParseException("Unknown encoding code " + encoding);
		}
		List<Byte> byteList = new ArrayList<Byte>();
		// skip encoding byte
		for(int i = 1; i < content.length; i++) {
			byteList.add(content[i]);
		}
		byte[] bytes = new byte[byteList.size()];
		for (int j = 0; j < bytes.length; j++) {
			bytes[j] = byteList.get(j);			
		}
		try {
			if(encoding == ISO_8859_1_ENCODING) {
				contents = new String(bytes, "ISO-8859-1");
			}
			if(encoding == UNICODE_ENCODING) {
				// Unicode strings must begin with the Unicode BOM ($FF FE or $FE FF) to identify the byte order. 
				contents = new String(bytes, "UTF-16");
			}
		} catch (UnsupportedEncodingException e) {
			throw new ID3ParseException("Unsupported encoding in the JVM.", e);
		}		
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
}
