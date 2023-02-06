package backendData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Header {
    
    private String title;
    private String description;
    private String text;
    private int parentNr;
    private int ownNr;
    private HashMap columns;
    private List<Header> subheaders;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public int getParentNr(){
        return this.parentNr;
    }

    public void setParentNr(int parentNr){
        this.parentNr = parentNr;
        this.refreshSubheaderParentNumbers();
    }


    public Header(String title, int parentNr, int ownNr){
        this.title = title;
        this.parentNr = parentNr;
        this.ownNr = ownNr;
    }  
    /**
     * Rearranges the subheader into the new index
     * @param newIndex new Index for the subheader
     * @param head Subheader, which should be rearranged.
     */
    public void rearrangeSubHeader(int newIndex, Header head){
        if(newIndex != this.subheaders.indexOf(head)){
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
    }
    /**
     * Insert a new Subheader on the start of the list
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderStart(Header head){
        this.subheaders.add(0, head);
    }
    /**
     * Insert a new Subheader at the end of the list
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderEnd(Header head){
        head.ownNr = this.subheaders.size();
        this.subheaders.add(head);
    }

    /**
     * Inserts a new Header on a specific index. If the index is not existing, it will be inserted an the end
     * @param index index, where it should be inserted
     * @param head Header which should be inserted.
     */
    public void insertNewSubheaderInBetween(int index, Header head){
        try{
            this.subheaders.add(index, head);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e){
        } catch (IndexOutOfBoundsException e){
            this.insertNewSubheaderEnd(head);
        }
    }

    /** 
    * Refreshes all ParentNumbers of subheaders
    */
    private void refreshSubheaderParentNumbers(){
        this.subheaders.forEach((subheader) -> subheader.setParentNr(ownNr));
    }

    /**
     * Refreshes all assigned Numbers
     */
    private void refreshSubHeaderNumbers(){
        //artificial index to spare runtime;
        int listIndex = 0;
        this.subheaders.forEach((subheader) -> { 
            if(subheader.ownNr != listIndex+1){
                subheader.setOwnNr(listIndex+1);
            } 
        });
    }
}
