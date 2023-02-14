package main.java.backendData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Header {
    
    private String title;
    private String text;
    private int ownNr;
    private HashMap<String, String> columns;
    private List<Header> subheaders;
    private Header parentElement;
    private final boolean isRoot;
    private static final String DISPLAYDIVIDER = ".";
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOwnNr(){
        return this.ownNr;
    }

    public void setOwnNr(int ownNr){
        this.ownNr = ownNr;
        this.refreshSubHeaderNumbers();
    }

    public Header getParentElement() {
        return this.parentElement;
    }

    public int getSubheaderSize(){
        return this.subheaders.size();
    }

    /**
     * Remove self from old Parent element and then insert self into new Parent Element.
     * @param parent
     */
    public void setParentElement(Header parent, int index) {
        if(this.parentElement != null) this.parentElement.deleteSubheader(this);
        this.parentElement = parent;
        if(parent != null) this.parentElement.insertNewSubheaderInBetween(index, this);
    }

    /**
     * Get the Label Nr for displaying right sequence, for example 1.1.2
     * @return Stringlabel representation of Number in this outliner.
     */
    public String getLabelNr(){
        if(this.parentElement != null && !this.isRoot){
            return this.parentElement.getLabelNr() + DISPLAYDIVIDER + Integer.toString(this.ownNr);
        }       
        else return Integer.toString(this.getOwnNr());
    }


    public Header(String title, int ownNr, Header parentElement, boolean isRoot){
        this.title = title;
        this.ownNr = ownNr;
        subheaders = new LinkedList<>();
        this.setParentElement(parentElement, this.ownNr-1);
        this.isRoot = isRoot;
    }  
    /**
     * Rearranges the subheader into the new index
     * @param newIndex new Index for the subheader
     * @param head Subheader, which should be rearranged.
     */
    public void rearrangeSubHeader(int newIndex, Header head){
        if(this.subheaders.contains(head) && newIndex != this.subheaders.indexOf(head)){
            this.subheaders.remove(head);
            this.insertNewSubheaderInBetween(newIndex, head);
        }
    }
    /**
     * Deletes a Subheader
     * @param head Subheader which should be deleted
     */
    public void deleteSubheader(Header head){
        this.subheaders.remove(head);
        head.parentElement = null;
        this.refreshSubHeaderNumbers();
    }
    /**
     * Insert a new Subheader on the start of the list
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderStart(Header head){
        if(!this.subheaders.contains(head)){
            this.subheaders.add(0, head);
            head.parentElement = this;
            this.refreshSubHeaderNumbers();
        }
    }
    /**
     * Insert a new Subheader at the end of the list
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderEnd(Header head){
        if(!this.subheaders.contains(head)){
            head.ownNr = this.subheaders.size()+1;
            this.subheaders.add(head);
            head.parentElement = this;
        }
    }

    /**
     * Inserts a new Header on a specific index. If the index is not existing, it will be inserted an the end
     * @param index index, where it should be inserted
     * @param head Header which should be inserted.
     */
    public void insertNewSubheaderInBetween(int index, Header head){
        try{
            if(!this.subheaders.contains(head)){
                this.subheaders.add(index, head);
                head.parentElement = this;
                this.refreshSubHeaderNumbers();
            }
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e){
            log.warning("Unexpected Error in insertNewSubheaderInBetween");

        } catch (IndexOutOfBoundsException e){
            this.insertNewSubheaderEnd(head);
        }
    }
    /**
     * Refreshes all assigned Numbers
     */
    private void refreshSubHeaderNumbers(){
        //artificial index to spare runtime;
        int listIndex = 0;
        for (Header subheader : this.subheaders){
            subheader.ownNr = listIndex+1;
            listIndex++;
        }
    }

    /**
     * Empty the Subheader
     */
    public void emptySubHeaders(){
        for (Header subheader : this.subheaders){
            subheader.parentElement = null;
        }
        this.subheaders.clear();
    }
}
