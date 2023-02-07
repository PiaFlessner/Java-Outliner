package tests;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import backendData.Header;

public class HeaderTest {
    
    @Before
    void setup(){
        Header h0 = new Header("Root",0,null, true);
        Header h1 = new Header("second",1,null, false);
        Header h2 = new Header("third",2,null,false);
        h0.insertNewSubheaderEnd(h1);
        h0.insertNewSubheaderEnd(h2);
        Header h11 = new Header("test",1,h1,false);
        h1.insertNewSubheaderEnd(h11);
    }

    @Test
    public void testDeleteSubheader() {
        
    }

    @Test
    public void testGetLabelNr() {
        
    }

    @Test
    public void testGetOwnNr() {
        
    }

    @Test
    public void testGetParentElement() {
        
    }

    @Test
    public void testInsertNewSubheaderEnd() {
        
    }

    @Test
    public void testInsertNewSubheaderInBetween() {
        
    }

    @Test
    public void testInsertNewSubheaderStart() {
        
    }

    @Test
    public void testRearrangeSubHeader() {
        
    }

    @Test
    public void testSetParentElement() {
        
    }

}
