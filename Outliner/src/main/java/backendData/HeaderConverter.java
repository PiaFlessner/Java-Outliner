package main.java.backendData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;

public class HeaderConverter {

    public static String HEADERSYMBOL = "#";
    public static String SPACESYMBOL = " ";

    /**
     * converts the content of a header into a MD String representation recursevly.
     * 
     * @param header     Header which should be written
     * @param startDepth start depht
     * @return MD String.
     */
    public String convertHeaderToMD(Header header, int startDepth) {
        String answer = "";
        if (header.getSubheaderSize() == 0)
            answer += convertSingleObject(header, startDepth);
        else {
            if (!header.isRoot())
                answer += convertSingleObject(header, startDepth);
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
     * @param depht  the current depht of the header,
     *               this number say how often the TitleSymbol should be repeated.
     * @return a String translation of the header
     */
    private String convertSingleObject(Header header, int depht) {
        String answer = "";
        String textInput;

        if (header.getText() == null)
            textInput = HeaderConverter.SPACESYMBOL;
        else
            textInput = header.getText();

        // repeat Headersymbol depth times
        answer += String.join("", Collections.nCopies(depht, HeaderConverter.HEADERSYMBOL))
                + HeaderConverter.SPACESYMBOL
                + header.getLabelNr()
                + HeaderConverter.SPACESYMBOL
                + header.getTitle()
                + System.lineSeparator()
                + System.lineSeparator()
                + textInput
                + System.lineSeparator()
                + System.lineSeparator();

        return answer;
    }

    /**
     * Creates a File with the target path,
     * then writes the header content in file.
     * 
     * @param header header which should be written down in file
     * @param depht  starting depht
     * @param target target path
     * @return true = everything was right, false = something went wrong.
     */
    public boolean saveMD(Header header, int depht, String target) {
        String targetName = target + ".md";
        boolean isCreated = this.createFile(targetName);
        boolean isSaved = this.saveHeaderInFile(header, targetName, depht);
        return isCreated && isSaved;
    }

    /**
     * Create file in target.
     * 
     * @param target File which should be created.
     * @return boolean, if everyting was ok.
     */
    private boolean createFile(String target) {
        File newFile = new File(target);
        newFile.setExecutable(false);
        try {
            if (newFile.exists())
                return true;
            if (newFile.createNewFile())
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * writes the content into the file
     * 
     * @param header header to be written.
     * @param target target path
     * @param depht  start depht
     * @return is everyting was ok.
     */
    private boolean saveHeaderInFile(Header header, String target, int depht) {
        try {
            FileWriter writer = new FileWriter(target);
            writer.write(this.convertHeaderToMD(header, depht));
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
