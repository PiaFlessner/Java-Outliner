package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import main.java.backendData.Header;
import main.java.backendData.HeaderConverter;

public class HeaderConverterTest {

    String title1 = "Ein";
    String title2 = "Zwei";
    String title11 = "Unterpunkt";
    String text1 = "Ich bin der Untertext mit allen möglichen Sachen.";
    String text2 = "Ebenso ein Text.";
    String text11 = "Untertext";

    Header h0 = new Header("Root", 0, null, true);
    Header h1 = new Header(title1, 1, h0, false);
    Header h2 = new Header(title2, 2, h0, false);
    Header h11 = new Header(title11, 1, h1, false);

    HeaderConverter converter = new HeaderConverter();

    @BeforeEach
    public void setup() {
        this.h0.insertNewSubheaderEnd(h1);
        this.h0.insertNewSubheaderEnd(h2);
    }

    @AfterEach
    public void tearDown() {
        h0.emptySubHeaders();
        h1.emptySubHeaders();
        h2.emptySubHeaders();
        h11.emptySubHeaders();
        h11.setParentElement(h1, 1);
    }

    @Test
    public void testMDStringCreation() {

        h2.setText(text2);
        h1.setText(text1);
        h11.setText(text11);
        String expectedString = 
        HeaderConverter.HEADERSYMBOL + HeaderConverter.SPACESYMBOL + "1" 
        + HeaderConverter.SPACESYMBOL 
        + title1
        + System.lineSeparator() + System.lineSeparator() 
        + text1 
        + System.lineSeparator() + System.lineSeparator()
        + HeaderConverter.HEADERSYMBOL + HeaderConverter.HEADERSYMBOL + HeaderConverter.SPACESYMBOL + "1.1" + HeaderConverter.SPACESYMBOL + title11
        + System.lineSeparator() + System.lineSeparator() 
        + text11 
        + System.lineSeparator() + System.lineSeparator()
        + HeaderConverter.HEADERSYMBOL + HeaderConverter.SPACESYMBOL + "2" + HeaderConverter.SPACESYMBOL + title2
        + System.lineSeparator() + System.lineSeparator() 
        + text2 
        + System.lineSeparator() + System.lineSeparator();

  
        String mdTranslated = converter.convertHeaderToMD(h0, 0);
        assertEquals(expectedString, mdTranslated);
    }

    @Test
    public void testSaveFile(){
        h2.setText(text2);
        h1.setText(text1);
        h11.setText(text11);
        String target = "target.md";
        converter.saveMD(h0, 0, target);
        File file = new File(target);
        assertEquals(true, file.exists());
        file.delete();
    }
}
