package backendData;

public class main {
    
    public static void main (String argv[]){

        Header h0 = new Header("Root",0,null, true);
        Header h1 = new Header("second",1,null, false);
        Header h2 = new Header("third",2,null,false);
        h0.insertNewSubheaderEnd(h1);
        h0.insertNewSubheaderEnd(h2);
        Header h11 = new Header("test",1,h1,false);
        h1.insertNewSubheaderEnd(h11);

        System.out.println(h1.getLabelNr());
        System.out.println(h2.getLabelNr());
        System.out.println(h11.getLabelNr());

        Header h1new1 = new Header("test",1,h1,false);
        h1.insertNewSubheaderStart(h1new1);
        System.out.println(h1new1.getLabelNr());
        System.out.println(h11.getLabelNr());



    }
}
