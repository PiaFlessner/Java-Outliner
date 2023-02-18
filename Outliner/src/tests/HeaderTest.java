package tests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import main.java.backendData.Header;

public class HeaderTest {

    Header h0 = new Header("Root",0,null, true);
    Header h1 = new Header("second",1,h0, false);
    Header h2 = new Header("third",2,h0,false);
    Header h11 = new Header("test",1,h1,false);

    @BeforeEach
    public void setup(){
        this.h0.insertNewSubheaderEnd(h1);
        this.h0.insertNewSubheaderEnd(h2);
    }

    @AfterEach
    public void tearDown(){
        h0.emptySubHeaders();
        h1.emptySubHeaders();
        h2.emptySubHeaders();
        h11.emptySubHeaders();
        h11.setParentElement(h1, 1);
    }

    @Test
    public void testDeleteSubheader() {
        this.h1.deleteSubheader(this.h11);
        assertEquals(0, h1.getSubheaderSize());
        assertEquals(null, h11.getParentElement());
    }

    @Test
    public void testGetLabelNr() {
        assertEquals("1.1" , h11.getLabelNr());
    }

    @Test
    public void testGetOwnNrAfterChange() {
        Header newH11 = new Header("test",1,h1,false);
        assertEquals("1.2", h11.getLabelNr());
        assertEquals("1.1", newH11.getLabelNr());
    }

    @Test
    public void testGetParentElement() {
        assertEquals(this.h1, this.h11.getParentElement());
    }

    @Test
    public void testInsertNewSubheaderEnd() {

        Header h12 = new Header("test",1,null,false);
        Header h21 = new Header("test",1,null,false);
        Header h22 = new Header("test",1,null,false);

        h1.insertNewSubheaderEnd(h12);
        assertEquals("1.2", h12.getLabelNr());
        assertEquals(h1, h12.getParentElement());
        h2.insertNewSubheaderEnd(h21);
        assertEquals("2.1", h21.getLabelNr());
        assertEquals(h2, h21.getParentElement());
        h2.insertNewSubheaderEnd(h22);
        assertEquals("2.2", h22.getLabelNr());
        assertEquals(h2, h22.getParentElement());
    }

    @Test
    public void testInsertNewSubHeaderDouble(){
        Header h12 = new Header("test",1,null,false);
        h1.insertNewSubheaderEnd(h12);
        h1.insertNewSubheaderEnd(h12);
        assertEquals(2, h1.getSubheaderSize());
    }

    @Test
    public void testInsertNewSubheaderInBetween() {
        Header h13 = new Header("test3",-1,h1,false);
        Header h12 = new Header("test2",1,null,false);

        h1.insertNewSubheaderInBetween(1, h12);
        assertEquals("1.2", h12.getLabelNr());
        assertEquals(h1, h12.getParentElement());
        assertEquals("1.3", h13.getLabelNr());
    }

    @Test
    public void testInsertNewSubheaderStart() {
        Header newH11 = new Header("test3",1,null,false);
        Header nenewH11 = new Header("test2",1,null,false);

        h1.insertNewSubheaderStart(newH11);
        assertEquals("1.1", newH11.getLabelNr());
        assertEquals(h1, newH11.getParentElement());

        h1.insertNewSubheaderStart(nenewH11);
        assertEquals("1.1", nenewH11.getLabelNr());
        assertEquals(h1, nenewH11.getParentElement());;
    }

    @Test
    public void testRearrangeSubHeader() {
        Header h12 = new Header("test2",-1,h1,false);
        Header h13 = new Header("test3",-1,h1,false);

        h1.rearrangeSubHeader(2, h11);

        assertEquals("1.3", h11.getLabelNr());
        assertEquals("1.1", h12.getLabelNr());
        assertEquals("1.2", h13.getLabelNr());

    }

    @Test
    public void testSetParentElement() {
        Header h12 = new Header("test2",-1,null,false);
        h12.setParentElement(h1, -1);
        assertEquals(h1, h12.getParentElement());
    }

    @Test
    public void testGetOverallIndex() {
        assertEquals(1, h1.getIndex(h0));
        assertEquals(2, h11.getIndex(h0));
        assertEquals(3, h2.getIndex(h0));
    }

    @Test
    public void testGetOverallIndexComplicatedStructure(){
        Header h111 = new Header("1.1.1", 1,h11,false);
        Header h112 = new Header("1.1.2", 2,h11,false);
        Header h2 = new Header("2", 2,h0,false);
        Header h21 = new Header("2.1", 1,h2,false);
        Header h22 = new Header("2.2", 2,h2,false);
        Header h3 = new Header("3", 3,h0,false);
        Header h4 = new Header("4", 4,h0,false);
        Header h41 = new Header("4.1", 1,h4,false);

        assertEquals(1, h1.getIndex(h0));
        assertEquals(2, h11.getIndex(h0));
        assertEquals(3, h111.getIndex(h0));
        assertEquals(4, h112.getIndex(h0));
        assertEquals(5, h2.getIndex(h0));
        assertEquals(6, h21.getIndex(h0));
        assertEquals(7, h22.getIndex(h0));
        assertEquals(8, h3.getIndex(h0));
        assertEquals(9, h4.getIndex(h0));
        assertEquals(10, h41.getIndex(h0));
    }

}
