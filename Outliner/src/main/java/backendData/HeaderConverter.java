package main.java.backendData;

import java.util.Collections;

public class HeaderConverter {

    public static String HEADERSYMBOL = "#";
    public static String SPACESYMBOL = " ";

    public String convertHeaderToMD(Header header, int startDepth) {
        String answer = "";
        if (header.getSubheaderSize() == 0)
            answer += convertSingleObject(header, startDepth);
        else {
            if(!header.isRoot()) answer += convertSingleObject(header, startDepth);
            for (Header headerElement : header.getSubheaders()) {
                answer += this.convertHeaderToMD(headerElement, startDepth + 1);
            }
        }
        return answer;
    }

    /**
     * Convert a single header object into a String .md Structure Representation.
     * The output would look like:
     * #(dephTimes) [Number] [Title]
     * 
     * [Text]
     * 
     * @param header the header which should be translated.
     * @param depht the current depht of the header, 
     * this number say how often the TitleSymbol should be repeated.
     * @return a String translation of the header
     */
    private String convertSingleObject(Header header, int depht) {
        String answer = "";
        // repeat Headersymbol depth times
        answer += String.join("", Collections.nCopies(depht, HeaderConverter.HEADERSYMBOL))
                + HeaderConverter.SPACESYMBOL
                + header.getLabelNr()
                + HeaderConverter.SPACESYMBOL
                + header.getTitle()
                + System.lineSeparator()
                + System.lineSeparator()
                + header.getText()
                + System.lineSeparator()
                + System.lineSeparator();

        return answer;
    }

}
