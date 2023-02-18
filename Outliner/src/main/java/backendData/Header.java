package main.java.backendData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.omg.PortableServer.ServantLocatorOperations;

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
    public static Header root;

    public Header(String title, int ownNr, Header parentElement, boolean isRoot) {
        this.title = title;
        this.ownNr = ownNr;
        subheaders = new LinkedList<>();
        this.setParentElement(parentElement, this.ownNr - 1);
        this.isRoot = isRoot;
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

    public void setOwnNr(int ownNr) {
        this.ownNr = ownNr;
        this.refreshSubHeaderNumbers();
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
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException
                | IllegalArgumentException e) {
            log.warning("Unexpected Error in insertNewSubheaderInBetween");

        } catch (IndexOutOfBoundsException e) {
            this.insertNewSubheaderEnd(head);
        }
    }

    /**
     * Refreshes all assigned Numbers
     */
    private void refreshSubHeaderNumbers() {
        // artificial index to spare runtime;
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
     * @param startParent starting point
     * @return treeindex
     */
    public int getIndex(Header startParent){
        return getTreeIndex(startParent, new Tuple()).index;
    }

    /**
     * Actual implementation of getting the tree index. It is working recursive.
     * Its going throuh all subheaders and count them until it finds the desired header.
     * From this moment on, it is skipping all counting operations.
     * @param startParent starting point of counting, mostly the root.
     * @param indexAndFound a combined class which inherits the index and the bool is found.
     * @return the modified tuple with index and isFound.
     */
    private Tuple getTreeIndex(Header startParent, Tuple indexAndFound) {
        for (Header header : startParent.subheaders) {
            if (header == this) {
                indexAndFound.index += 1;
                indexAndFound.isFound = true;
                return  indexAndFound;
            } else {
                indexAndFound = this.getTreeIndex(header, indexAndFound);
                indexAndFound.index += 1;
                if(indexAndFound.isFound){
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
    }

    // public int getTreeIndex(Header startParent) {
    //     int index = 0;
    //     for (Header header : startParent.subheaders) {
    //         if (header == this) {
    //             return  1+ index;
    //         } else {
    //             index = index + 1 + this.getTreeIndex(header);
    //         }
    //     }
    //     return index;
    // }

    //
    // /**
    // * Gets the total subtree count of a tree, for example for the tree:
    // * Tree 1, 1.1, 1.2, 1.2.1, 1.2.2
    // * the Tree 1 has a total subtreeCount of 4 (1.1 -> 1, 1.2 -> 2, 1.2.1 -> 3,
    // * 1.2.2 -> 4).
    // *
    // * @return total sub tree count
    // */
    // private int getTotalSubTreeCount() {
    //
    // int elementCounter = 0;
    // if (this.subheaders.size() == 0)
    // return elementCounter;
    // else {
    // elementCounter = elementCounter + this.subheaders.size();
    // for (Header header : this.subheaders) {
    // elementCounter = elementCounter + header.getTotalSubTreeCount();
    // }
    // }
    // return elementCounter;
    // }
    //
    // /**
    // * Finds out, on which index the current element on the branch is.
    // * For example
    // * Tree 1, 1.1, 1.2, 1.2.1, 1.2.2, the Header 1.2 has the index count of the
    // * tree 3 and 1.2.2 has 5
    // *
    // * @return index, which described on which branch index the element is.
    // */
    // private int getOwnBranchIndex(Header startHeader) {
    // int treeIndex = 1;
    // if (!this.parentElement.isRoot) {
    // treeIndex = this.ownNr;
    // if(this == startHeader){
    // treeIndex = treeIndex + getBeforeNeighboursSubtreeCount(false);
    // }
    // treeIndex = treeIndex + this.parentElement.getOwnBranchIndex(startHeader);
    // }
    // return treeIndex;
    // }
    //
    // /**
    // * Finds out, which overall branch the header is in.
    // *
    // * @return
    // */
    // private Header climbToRootAndSaveBranch() {
    // Header desiredKnot = this;
    // if (!this.parentElement.isRoot) {
    // desiredKnot = this.parentElement.climbToRootAndSaveBranch();
    // }
    // return desiredKnot;
    // }
    //
    // private int getBeforeNeighboursSubtreeCount(boolean countKnot){
    // int index = 0;
    // int knotCount = 0;
    // if(countKnot) knotCount = 1;
    //
    // for (Header header : this.parentElement.subheaders) {
    // if (header == this)
    // break;
    // else
    // index = index + knotCount + header.getTotalSubTreeCount();
    // }
    // return index;
    // }
    //
    // /*
    // * Gets the overall Tree Index of a Header in the whole tree construct.
    // * For example:
    // * For example the overall construct inherits 1, 1.1, 1.2, 2, 2.1, 2.2, 2.2.1
    // * so 2.2.1 would have the overall index of 7.
    // *
    // * @return overall index
    // */
    // public int getOwnTreeIndex() {
    // int index = 0;
    // if (!this.isRoot) {
    // // Tree 1, 1.1, 1.2, 2, 2.1, 2.2 , search Element 2.2
    //
    // // 2.2 has the own Branch index of 3
    // // index = 3
    // index = this.getOwnBranchIndex(this);
    // Header ownBranch = this.climbToRootAndSaveBranch();
    //
    // // 1 has the subheaderCount of 2, and each knot has to be counted also
    // (therefore countKnot = true), so add 1
    // // index = 3 + 1 + 2
    // index = index + ownBranch.getBeforeNeighboursSubtreeCount(true);
    // }
    // // index = 6
    // return index;
    // }
}
