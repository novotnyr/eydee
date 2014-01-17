package eydee;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TagParserTest {
	@BeforeClass
	public static void setUpClass() throws IOException {
		System.setProperty("java.util.logging.config.file", "src/eydee/logging.properties");
	}

	@Test
	public void testRegularMp3() throws FileNotFoundException {
		TagParser parser = new TagParser();
		Tag tag = parser.parse(new File("d:/MP3/Kapela ze wsi Warszawa/2009 Infinity/(01) Pieœñ m¹drego dziecka [Wise Kid Song].mp3"));
		System.out.println(tag);
		
		assertNotNull(tag);
	}

	@Test
	public void testMp3_with_ID3v2_3_0() throws Exception {
		TagParser parser = new TagParser();
		Tag tag = parser.parse(new File("c:/Users/rn/Downloads/bear.mp3"));
		System.out.println(tag);
		
		assertNotNull(tag);
	}

}
