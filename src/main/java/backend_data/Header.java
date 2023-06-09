package main.java.backend_data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Header implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;
    private String text;
    private int ownNr;
    private HashMap<String, String> columns;
    private List<Header> subheaders;
    private Header parentElement;
    private final boolean isRoot;
    private static final String DISPLAYDIVIDER = ".";
    private static Header root;
    //Saving for reload components
    private boolean showSubHeader;
    private boolean showText;


    public Header(String title, int ownNr, Header parentElement, boolean isRoot) {
        this.title = title;
        this.ownNr = ownNr;
        subheaders = new LinkedList<>();
        this.setParentElement(parentElement, this.ownNr - 1);
        this.isRoot = isRoot;
        this.showText = false;
        this.showSubHeader = true;
    }


    public static void setRoot(Header root){
        Header.root = root;
    }

    public static Header getRoot(){
        return Header.root;
    }

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

    public int getOwnNr() {
        return this.ownNr;
    }

    public Header getParentElement() {
        return this.parentElement;
    }

    public int getSubheaderSize() {
        return this.subheaders.size();
    }

    public boolean isRoot() {
        return isRoot;
    }

    public List<Header> getSubheaders(){
        return this.subheaders;
    }

    public boolean isShowSubHeader(){
        return this.showSubHeader;
    }

    public boolean isShowText(){
        return this.showText;
    }

    public void setShowText(boolean showText){
        this.showText = showText;
    }

    public void setShowSubHeader(boolean showSubHeader){
        this.showSubHeader = showSubHeader;
    }

    public boolean isEmpty(){
        return this.subheaders.isEmpty();
    }

    /**
     * Remove self from old Parent element and then insert self into new Parent
     * Element.
     * 
     * @param parent
     */
    public void setParentElement(Header parent, int index) {
        if (this.parentElement != null)
            this.parentElement.deleteSubheader(this);
        this.parentElement = parent;
        if (parent != null)
            this.parentElement.insertNewSubheaderInBetween(index, this);
    }

    /**
     * Get the Label Nr for displaying right sequence, for example 1.1.2
     * 
     * @return Stringlabel representation of Number in this outliner.
     */
    public String getLabelNr() {
        if (this.parentElement.isRoot)
            return Integer.toString(ownNr);
        else
            return this.parentElement.getLabelNr() + DISPLAYDIVIDER + Integer.toString(this.ownNr);
    }

    /**
     * Rearranges the subheader into the new index
     * 
     * @param newIndex new Index for the subheader
     * @param head     Subheader, which should be rearranged.
     */
    public void rearrangeSubHeader(int newIndex, Header head) {
        if (this.subheaders.contains(head) && newIndex != this.subheaders.indexOf(head)) {
            this.subheaders.remove(head);
            this.insertNewSubheaderInBetween(newIndex, head);
        }
    }

    /**
     * Gets next the neighbour of a header.
     * @return next neighbour (neighbour of 1 would be 2).
     */
    public Header getNextNeigbourHeader(){
        if(this.ownNr == this.parentElement.subheaders.size()) return null;
        return this.parentElement.subheaders.get(this.ownNr);
    }
    /**
     * Geths the neighbour before a header.
     * @return before neighbour (neighbour of 2 would be 1).
     */
    public Header getBeforeNeigbourHeader(){
        if(this.ownNr == 1) return null;
        return this.parentElement.subheaders.get(this.ownNr-2);
    }

    /**
     * Deletes a Subheader
     * 
     * @param head Subheader which should be deleted
     */
    public void deleteSubheader(Header head) {
        this.subheaders.remove(head);
        head.parentElement = null;
        this.refreshSubHeaderNumbers();
    }

    /**
     * Insert a new Subheader on the start of the list
     * 
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderStart(Header head) {
        if (!this.subheaders.contains(head)) {
            this.subheaders.add(0, head);
            head.parentElement = this;
            this.refreshSubHeaderNumbers();
        }
    }

    /**
     * Insert a new Subheader at the end of the list
     * 
     * @param head Header which should be inserted
     */
    public void insertNewSubheaderEnd(Header head) {
        if (!this.subheaders.contains(head)) {
            head.ownNr = this.subheaders.size() + 1;
            this.subheaders.add(head);
            head.parentElement = this;
        }
    }

    /**
     * Inserts a new Header on a specific index. If the index is not existing, it
     * will be inserted an the end
     * 
     * @param index index, where it should be inserted
     * @param head  Header which should be inserted.
     */
    public void insertNewSubheaderInBetween(int index, Header head) {
        try {
            if (!this.subheaders.contains(head)) {
                this.subheaders.add(index, head);
                head.parentElement = this;
                this.refreshSubHeaderNumbers();
            }
        } catch (IndexOutOfBoundsException e) {
            this.insertNewSubheaderEnd(head);
        }
    }

    /**
     * Refreshes all assigned Numbers
     */
    private void refreshSubHeaderNumbers() {
        //artificial index to spare runtime;
        int listIndex = 0;
        for (Header subheader : this.subheaders) {
            subheader.ownNr = listIndex + 1;
            listIndex++;
        }
    }

    /**
     * Empty the Subheader
     */
    public void emptySubHeaders() {
        for (Header subheader : this.subheaders) {
            subheader.parentElement = null;
        }
        this.subheaders.clear();
    }

    /**
     * Gets the overall Tree Index of a Header in the whole tree construct.
     * For example:
     * For example the overall construct inherits 1, 1.1, 1.2, 2, 2.1, 2.2, 2.2.1
     * so 2.2.1 would have the overall index of 7.
     * 
     * @param startParent starting point
     * @return treeindex
     */
    public int getIndex(Header startParent) {
        return getTreeIndex(startParent, new Tuple()).index;
    }

    /**
     * Actual implementation of getting the tree index. It is working recursive.
     * Its going throuh all subheaders and count them until it finds the desired
     * header.
     * From this moment on, it is skipping all counting operations.
     * 
     * @param startParent   starting point of counting, mostly the root.
     * @param indexAndFound a combined class which inherits the index and the bool
     *                      is found.
     * @return the modified tuple with index and isFound.
     */
    private Tuple getTreeIndex(Header startParent, Tuple indexAndFound) {
        for (Header header : startParent.subheaders) {
            if (header == this) {
                indexAndFound.index += 1;
                indexAndFound.isFound = true;
                return indexAndFound;
            } else {
                indexAndFound = this.getTreeIndex(header, indexAndFound);
                indexAndFound.index += 1;
                if (indexAndFound.isFound) {
                    break;
                }
            }
        }
        return indexAndFound;
    }

    /**
     * Searches an Header with an Tree index.
     * If you want the overall index, always give the root as startParent.
     * @param startParent Header where the counting gets started.
     * @param index Index, with will be searched.
     * @return Header on the index index.
     */
    public Header getHeaderViaIndex(Header startParent, int index) {
        Tuple tuple = new Tuple(index);
        return getHeaderViaTreeIndex(startParent, tuple).header;
    }

    /**
     * Rekursive search implementation of searchign header via index.
     * Goes through all subheader and decrements index until it finds the acutal index.
     * @param startParent starting point of searching.
     * @param indexAndFound tuple, with inherits header, isFound and the indexCounter.
     * @return Tuple with the actual found header.
     */
    private Tuple getHeaderViaTreeIndex(Header startParent, Tuple indexAndFound) {
        for (Header header : startParent.subheaders) {
            indexAndFound.index -= 1;
            if (indexAndFound.index == 0) {
                indexAndFound.header = header;
                indexAndFound.isFound = true;
                return indexAndFound;
            } else {
                indexAndFound = this.getHeaderViaTreeIndex(header, indexAndFound);
                if (indexAndFound.isFound) {
                    break;
                }
            }
        }
        return indexAndFound;
    }

    /**
     * Helping class for returning two values, since java 8 has no tuples.
     */
    private class Tuple {
        int index;
        boolean isFound = false;
        Header header;

        public Tuple(){}

        public  Tuple(int index){
            this.index = index;
        }
    }

    /**
     * Gets the total subtree count of a tree, for example for the tree:
     * Tree 1, 1.1, 1.2, 1.2.1, 1.2.2
     * the Tree 1 has a total subtreeCount of 4 (1.1 -> 1, 1.2 -> 2, 1.2.1 -> 3,
     * 1.2.2 -> 4).
     *
     * @return total sub tree count
     */
    public int getTotalSubTreeCount() {
        int elementCounter = 0;
        if (this.isEmpty())
            return elementCounter;
        else {
            elementCounter = elementCounter + this.subheaders.size();
            for (Header header : this.subheaders) {
                elementCounter = elementCounter + header.getTotalSubTreeCount();
            }
        }
        return elementCounter;
    }

    /**
     * Checks, whether a Header is in any form a parent of a target header
     * @param targetHeader the occused header, which may is a child of the this element.
     * @return true means, the this Element is a parent, false means its not a parent.
     */
    public boolean isHeaderInParentHeader(Header targetHeader){
        if(targetHeader.parentElement == null) return false;
        if(targetHeader.parentElement.isRoot) return false;
        if(targetHeader.parentElement == this) return true;
        return this.isHeaderInParentHeader(targetHeader.parentElement);
    }

    /**
     * Returns the knot level of the Header via recursive climbing. 
     * In 1 , 1.1, 1.1.1, the knot level of 1.1.1 would be 3.
     * @param startCounter Starting counter, to start the counting, give 0.
     * @return The actual knot level.
     */
    public int getKnotLevel(int startCounter){
        if(this.isRoot) return startCounter;
        return this.parentElement.getKnotLevel(startCounter+1);
    }
}
