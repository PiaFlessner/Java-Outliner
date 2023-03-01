package main.java.visualComponents.Header;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.java.backendData.Header;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPopupMenu;

public class HeaderComponent extends JPanel {

    JPanel headerTitle;
    Icon icon;

    JLabel displayedNumber;
    JTextField displayedNumberEdit;

    Title displayedHeaderTitle;
    JTextField displayedHeaderTitleEdit;

    JPanel headerContent;
    TextArea textArea;

    JPopupMenu popupMenu;

    Color backgroundColor;
    final Color EDIT_COLOR = new Color(242, 242, 242);
    static final int HEADERCONTAINER_FOLDED_HEIGHT = 40;
    static final int HEADERCONTAINER_UNFOLDED_HEIGHT = 200;
    final Color FOCUS_COLOR = new Color(214, 220, 229);

    boolean isOpen;
    Header connectedHeader;
    static LinkedList<HeaderComponent> allHeaderComponents = new LinkedList<>();
    JPanel parentContainer;

    /**
     * Constructor for the HeaderComponent. Displays the whole Container with
     * Numbers, Icon, Title and inherited Text.
     * 
     * @param backgroundColor backgroundcolor which should be used.
     * @param isOpen          Determines, if the Container should be instantiated
     *                        opened or closed.
     * @param connectedHeader The Backend Header Element which belongs to the
     *                        ComponentHeader.
     * @param parentContainer The ParentContainer, which inherits all
     *                        HeaderComponents.
     */
    public HeaderComponent(Color backgroundColor, boolean isOpen, Header connectedHeader, JPanel parentContainer) {
        this.isOpen = isOpen;
        this.parentContainer = parentContainer;
        this.connectedHeader = connectedHeader;
        this.backgroundColor = backgroundColor;
        this.setLayout(new BorderLayout());
        this.popupMenu = new JPopupMenu();
        this.setComponentPopupMenu(popupMenu);
        this.setFocusable(true);

        setUpHeaderTitle();
        setUpContent();
        addFocusingFunction();
        setUpOpenHeaderFunction();
        setUpHoverColorChangeFunction();
        setUpEditTextfieldFunction();
        
        //// Action addition, generates action as well as the contextmenue
        // add header to same level before current
        addingHeaderActions(0, "Add Header on same level before", connectedHeader.getParentElement(),
        KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK), "addSubheaderToParentBefore", true, false);
        
        // add subheader at start adding function
        //addingHeaderActions(0, "Add subheader start", connectedHeader,
        //KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "addSubHeaderStart", false, false);

        // add subheader before adding function
        addingHeaderBeforeActions("Add before",
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "addSubHeaderBefore", false, false);

        // add subheader at ending adding function
        addingHeaderActions(-1, "Add Subheader", connectedHeader,
        KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), "addSubHeaderEnd", false, false);

        // add header to same level after current
        addingHeaderActions(1, "Add Header on same level after", connectedHeader.getParentElement(),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK), "addSubheaderToParentAfter", false, true);


        shiftHeaderAction(1, "Shift header up",
                KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.ALT_DOWN_MASK),
                "shiftOneUp", false,false,false);

        shiftHeaderAction(1, "Shift header down",
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.ALT_DOWN_MASK),
                "shiftOneDown", true, false, false);

        shiftTreeLevelUpDownAction("Shift header level up",
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.ALT_DOWN_MASK), "shiftLevelUp", false, false,false);

        shiftTreeLevelUpDownAction("Shift header level down",
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.ALT_DOWN_MASK), "shiftLevelDown", true, false, true);

        deleteHeaderAction("Delete Header", KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, KeyEvent.CTRL_DOWN_MASK),
                "deleteHeader");

        // adjust open or not open size
        if (isOpen) {
            openHeader();
        } else {
            closeHeader();
        }

        HeaderComponent.addInstance(this);
    }

    /**
     * adds an HeaderComponent to the allInstances List.
     * 
     * @param hc
     */
    public static void addInstance(HeaderComponent hc) {
        allHeaderComponents.add(hc);
    }

    /**
     * Deletes an HeaderComponent
     * 
     * @param hc
     */
    public static void deleteInstance(HeaderComponent hc) {
        allHeaderComponents.remove(hc);
    }

    /**
     * refreshes all displayed Numbers based on the backend information
     */
    public static void refreshNumbers() {
        for (HeaderComponent hc : HeaderComponent.allHeaderComponents) {
            hc.displayedNumber.setText(hc.connectedHeader.getLabelNr());
        }
    }

    /**
     * Deletes all instances from the allHeaderComponents.
     */
    public static void deleteAllInstances(){
        HeaderComponent.allHeaderComponents.clear();
    }

    /**
     * sets up the header title with an icon and the actual title.
     */
    private void setUpHeaderTitle() {
        headerTitle = new JPanel();
        headerTitle.setBackground(backgroundColor);
        headerTitle.setMaximumSize(new Dimension(32767, 40));
        headerTitle.setMinimumSize(new Dimension(664, 40));
        FlowLayout titleContainerFlowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        titleContainerFlowLayout.setAlignOnBaseline(true);
        headerTitle.setLayout(titleContainerFlowLayout);

        // Icon adding
        icon = new Icon();
        headerTitle.add(icon);

        setUpDisplayedNumber();

        // Title adding
        displayedHeaderTitle = new Title(this);
        headerTitle.add(displayedHeaderTitle);
        this.add(headerTitle, BorderLayout.NORTH);
        
    }

    /**
     * sets up the Displayed number
     */
    private void setUpDisplayedNumber() {
        displayedNumber = new JLabel();
        displayedNumber.setFont(displayedNumber.getFont());
        displayedNumber.setText(this.connectedHeader.getLabelNr());
        headerTitle.add(displayedNumber);
    }

    /**
     * Sets up the Content Panel, which inherits a textfield.
     */
    private void setUpContent() {
        headerContent = new JPanel();
        // TextContent of the Header
        headerContent.setBackground(backgroundColor);
        headerContent.setMaximumSize(new Dimension(32767, 160));
        headerContent.setMinimumSize(new Dimension(100, 160));
        headerContent.setPreferredSize(new Dimension(712, 160));
        headerContent.setLayout(new BorderLayout());

        textArea = new TextArea(this);
        headerContent.add(textArea, BorderLayout.CENTER);

        this.add(headerContent, BorderLayout.CENTER);

    }
    /**
     * Invokes the deleteHeader for the current header.
     * @param actionText text for the contextmenue
     * @param keystroke keystroke for invoking the action
     * @param actionMapKey unique identifier for invoking action.
     */
    private void deleteHeaderAction(String actionText, KeyStroke keystroke,
            String actionMapKey) {

        // Action definition
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteHeader();
            }
        };
        contextMenuAdding(actionText, action, keystroke, actionMapKey, false, false);
    }

    /**
     * deletes a header and the belonging subheader from the frontend and the backend.
     */
    private void deleteHeader() {
        int displayIndex = parentContainer.getComponentZOrder(this);
        // Affected are all, which are subheader to the focused header
        ArrayList<Component> affectedHeaderComponents = this.getConnectedSubHeaderToComponent(displayIndex);
        this.connectedHeader.getParentElement().deleteSubheader(this.connectedHeader);

        for (Component hc : affectedHeaderComponents) {
            parentContainer.remove(hc);
            HeaderComponent.deleteInstance((HeaderComponent) hc);
        }
        HeaderComponent.refreshNumbers();
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    private void addingHeaderBeforeActions(String actionText, KeyStroke keystroke, String actionMapKey, boolean sepBefore, boolean sepAfter){
        HeaderComponent self = this;
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int insertIndex = self.connectedHeader.getIndex(Header.ROOT) -1;
                Header beforeHeader = self.connectedHeader.getHeaderViaIndex(Header.ROOT,insertIndex+1);
                Header h = new Header("Add Title Here", beforeHeader.getOwnNr(), beforeHeader.getParentElement(), false);
                HeaderComponent hc = new HeaderComponent(backgroundColor, false, h, parentContainer);
                parentContainer.add(hc, h.getIndex(Header.ROOT)-1);
                HeaderComponent.refreshNumbers();
                parentContainer.revalidate();
                parentContainer.repaint();
            }            
        };
        contextMenuAdding(actionText, action, keystroke, actionMapKey, sepBefore, sepAfter);
    }

    /**
     * Adds an add Header Action from the Focused Header.
     */
    private void addingHeaderActions(int addPlace, String actionText, Header parentElement, KeyStroke keystroke,
            String actionMapKey, boolean sepBefore, boolean sepAfter) {
                HeaderComponent self = this;

        // Action definition
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {           
                int indexInParent = self.connectedHeader.getOwnNr() + addPlace;
                Header h = new Header("Add Title Here", indexInParent, parentElement, false);
                HeaderComponent hc = new HeaderComponent(backgroundColor, false, h, parentContainer);
                parentContainer.add(hc, h.getIndex(Header.ROOT)-1);
                HeaderComponent.refreshNumbers();
                parentContainer.revalidate();
                parentContainer.repaint();
            }
        };
        contextMenuAdding(actionText, action, keystroke, actionMapKey, sepBefore, sepAfter);
    }

    /**
     * Gets all the Components, which belongs to one Header.
     * 
     * @param displayIndex The startIndex in the GUI Container of the Header which
     *                     is affected
     * @param hc           The affected HeaderComponent
     * @return ArrayList of HeaderComponents, which should be also shifted.
     */
    private ArrayList<Component> getConnectedSubHeaderToComponent(int displayIndex) {
        int rangeOfShiftElements = this.connectedHeader.getTotalSubTreeCount() + 1;
        ArrayList<Component> affectedHeaderComponents = new ArrayList<>();
        // Get the affected Components (affected are all, which belongs to the header);
        for (int i = displayIndex, j = 0; j < rangeOfShiftElements; i++, j++) {
            affectedHeaderComponents.add(parentContainer.getComponent(i));
        }
        return affectedHeaderComponents;
    }

    /**
     * Makes the shifting via ContextMenu and Keystroke possible.
     * 
     * @param shiftIndex   index, to what the current Element should be shifted.
     * @param actionText   Text description which will be displayed in the
     *                     contextmenu.
     * @param keystroke    Keystroke, which will be used to execute the command.
     * @param actionMapKey actionKeymap for backend orientation.
     * @param down         true= down shift, false= up shift.
     * @param sepBefore    true = a separator before item will be added in contextmenu
     * @param sepAfter     true = a separator after item will be added in contextmenu
     */
    private void shiftHeaderAction(int shiftIndex, String actionText, KeyStroke keystroke, String actionMapKey,
            boolean down, boolean sepBefore, boolean sepAfter) {

        HeaderComponent self = this;
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //self.shift(shiftIndex);
                self.shiftUpOrDown(shiftIndex, down);
            }
        };

        contextMenuAdding(actionText, action, keystroke, actionMapKey, sepBefore, sepAfter);
    }

    /**
     * Actual implementaion of HeaderShifting in Gui and Backend.
     * 
     * @param shiftIndex Index to be shifted (always positive).
     * @param down       true = shifting down, false = shifting up.
     */
    private void shiftUpOrDown(int shiftIndex, boolean down) {

        int displayIndex = parentContainer.getComponentZOrder(this);
        int newIndex;

        // Affected are all, which are subheader to the focused header
        ArrayList<Component> affectedHeaderComponents = this.getConnectedSubHeaderToComponent(displayIndex);

        // if the direction is up or down, the operations are slightly different
        if (down) {
            // only shift down, if the header is not the last element.
            if (this.connectedHeader.getNextNeigbourHeader() != null) {
                newIndex = displayIndex + shiftIndex;
                // The new index is dependend on the subheadercount of all neighbour header
                for (int i = 0; i < shiftIndex; i++) {
                    newIndex = newIndex + this.connectedHeader.getNextNeigbourHeader().getTotalSubTreeCount();
                }

                // refresh backend also.
                this.connectedHeader.getParentElement()
                        .rearrangeSubHeader(this.connectedHeader.getOwnNr() - 1 + shiftIndex, connectedHeader);
                // shift all affected elements
                for (int i = affectedHeaderComponents.size() - 1; i >= 0; i--) {
                    parentContainer.setComponentZOrder(affectedHeaderComponents.get(i), newIndex + i);
                }
            }

        } else {
            // only shift up, if the header ist not the first element.
            if (this.connectedHeader.getBeforeNeigbourHeader() != null) {
                newIndex = displayIndex - shiftIndex;
                // The new index is dependend on the subheadercount of all neighbour header
                for (int i = 0; i < shiftIndex; i++) {
                    newIndex = newIndex - this.connectedHeader.getBeforeNeigbourHeader().getTotalSubTreeCount();
                }

                // refresh backend also.
                this.connectedHeader.getParentElement()
                        .rearrangeSubHeader(this.connectedHeader.getOwnNr() - 1 - shiftIndex, connectedHeader);

                // shift all affected elements
                for (int i = affectedHeaderComponents.size() - 1; i >= 0; i--) {
                    parentContainer.setComponentZOrder(affectedHeaderComponents.get(i), newIndex);
                }
            }
        }
        HeaderComponent.refreshNumbers();
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    private void shift(int shiftIndex){
        int displayIndex = parentContainer.getComponentZOrder(this);
        int newIndex;
        int newInnerIndex = 0;
        //If the shift index is negative, therefore it will be shifted up, then the ownNr needs an incrementation
        if(shiftIndex <0) newInnerIndex = 1;

        // Affected are all, which are subheader to the focused header
        ArrayList<Component> affectedHeaderComponents = this.getConnectedSubHeaderToComponent(displayIndex);

        //calculate the new index
        newIndex = this.connectedHeader.getIndex(Header.ROOT) + shiftIndex;
        Header aimReplaceHeader = this.connectedHeader.getHeaderViaIndex(Header.ROOT, newIndex);
        //replace the targeted header
        aimReplaceHeader.getParentElement().insertNewSubheaderInBetween(aimReplaceHeader.getOwnNr()+newInnerIndex, this.connectedHeader);

        //let the frontend execute the shifting
        //down Addition is needed, since otherwise the elements would be in a wrong order when shifting down.
        int downAddition = 0;
        for (int i = affectedHeaderComponents.size() - 1; i >= 0; i--) {
            if (shiftIndex >= 0) downAddition = i;
            parentContainer.setComponentZOrder(affectedHeaderComponents.get(i), newIndex + downAddition);
        }
        HeaderComponent.refreshNumbers();
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Visibly opens the header.
     */
    private void openHeader() {
        icon.setArrowOpen();
        this.isOpen = true;
        this.setMaximumSize(new Dimension(2147483647, this.HEADERCONTAINER_UNFOLDED_HEIGHT));
        this.textArea.setVisible(true);
        this.headerContent.setVisible(true);
        this.textArea.textArea.setFocusable(true);
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Visibly closes the header.
     */
    private void closeHeader() {
        icon.setArrowClose();
        this.isOpen = false;
        this.setMaximumSize(new Dimension(2147483647, this.HEADERCONTAINER_FOLDED_HEIGHT));
        this.headerContent.setVisible(false);
        this.textArea.setVisible(false);
        this.textArea.textArea.setFocusable(false);
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Inherits the logic to open and close a subeader
     */
    private void openClose() {
        if (this.isOpen) {
            closeHeader();
        } else {
            openHeader();
        }
    }

    /**
     * Sets up the open Header function via keystrokes and via mouse click.
     */
    private void setUpOpenHeaderFunction() {
        // KeyStroke Function
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "OpenCloseHeader");
        this.getActionMap().put("OpenCloseHeader", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClose();
            }
        });

        // Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openClose();
            }
        });
    }

    private void setUpEditTextfieldFunction(){
        String actionMapKey = "EditTextfield";
        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), actionMapKey);
        this.getActionMap().put(actionMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayedHeaderTitle.editAbleChange();
            }
        });

    }

    /**
     * Sets up the Color change, when the user hovers over the element by setting
     * the focus.
     * Meaning: hover == focus.
     */
    private void setUpHoverColorChangeFunction() {
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                grabFocus();
            }
        });
    }

    /**
     * Adds the Focusing function to the component.
     */
    private void addFocusingFunction() {
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                headerTitle.setBackground(FOCUS_COLOR);
            }

            @Override
            public void focusLost(FocusEvent e) {
                headerTitle.setBackground(backgroundColor);
            }
        });
    }
    /**
     * Adds a contextMenu to the HeaderComponent fast.
     * 
     * @param actionText   Text which will be displayed in contextmenu.
     * @param action       Executed Action, when clicking on contextmenu.
     * @param keystroke    Keystroke to use the action without contextmenu.
     * @param actionMapKey Text to let the Backend identify action, needs to be
     *                     unique in class.
     * @param sepBefore    true = a separator before item will be added in contextmenu
     * @param sepAfter     true = a separator after item will be added in contextmenu
     */
    private void contextMenuAdding(String actionText, Action action, KeyStroke keystroke, String actionMapKey, boolean sepBefore, boolean sepAfter) {
        // Contextmenue Item configuration
        JMenuItem menuItem;
        menuItem = new JMenuItem(actionText);
        menuItem.setAction(action);
        menuItem.setAccelerator(keystroke);
        if(sepBefore) this.popupMenu.addSeparator();
        this.popupMenu.add(menuItem);
        if(sepAfter) this.popupMenu.addSeparator();

        // makes keystroke possible without opening the contextmenue.
        this.getInputMap(this.WHEN_FOCUSED).put(keystroke, actionMapKey);
        this.getActionMap().put(actionMapKey, action);
    }

    /**
     * Instantiation of the shift header up or down one level.
     * @param actionText Text which will be displayed in the contextmenue
     * @param keystroke Used Keystroke for controlling with keyboard
     * @param actionMapKey backend actionMapKey, which should be unique
     * @param down true= level down, false = level up.
     * @param sepBefore true = a separator before item will be added in contextmenu
     * @param sepAfter true = a separator after item will be added in contextmenu
     */
    private void shiftTreeLevelUpDownAction(String actionText, KeyStroke keystroke, String actionMapKey,
            boolean down, boolean sepBefore, boolean sepAfter) {

        HeaderComponent self = this;
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.shiftTreeLevelUpOrDown(down);
            }
        };
        contextMenuAdding(actionText, action, keystroke, actionMapKey, sepBefore, sepAfter);
    }

    /**
     * shifting the element actually up or down.
     * @param down true= level down, false = level up
     */
    private void shiftTreeLevelUpOrDown(boolean down) {
        int displayIndex = parentContainer.getComponentZOrder(this);
        int newIndex;
        // Affected are all, which are subheader to the focused header
        ArrayList<Component> affectedHeaderComponents = this.getConnectedSubHeaderToComponent(displayIndex);

        if (down) {

            Header neighbour = this.connectedHeader.getNextNeigbourHeader();
            Header beforeNeighbour = this.connectedHeader.getBeforeNeigbourHeader();
            Header usedParent;
            // Level down is only possible, if header has siblings.
            if ((neighbour != null && this.connectedHeader.getOwnNr() == 1) ||
                    (beforeNeighbour != null && this.connectedHeader.getOwnNr() > 1)) {

                // #1 if self ownr = 1, then use nextSibling as new Parent (because there is no alternate)
                if (this.connectedHeader.getOwnNr() == 1) {
                    usedParent = neighbour;
                } else {
                    usedParent = beforeNeighbour;
                }

                // #2 delete self from current parent
                this.connectedHeader.getParentElement().deleteSubheader(this.connectedHeader);

                // #3 add self to new Parent
                usedParent.insertNewSubheaderInBetween(this.connectedHeader.getOwnNr(), this.connectedHeader);

                // #3 validate the new Index
                newIndex = this.connectedHeader.getIndex(Header.ROOT) - 1;

                // #4 shift all affected elements
                for (int i = affectedHeaderComponents.size() - 1; i >= 0; i--) {
                    parentContainer.setComponentZOrder(affectedHeaderComponents.get(i), newIndex + i);
                }

            }
        } else {
            Header parentHeader = this.connectedHeader.getParentElement();
            // Level Up only possible, if parent is not root.
            if (!parentHeader.isRoot()) {

                // #1 remove this from parent.
                parentHeader.deleteSubheader(this.connectedHeader);

                // #2 add self to parentparent in ownNrParent
                parentHeader.getParentElement().insertNewSubheaderInBetween(parentHeader.getOwnNr(),
                        this.connectedHeader);

                // #3 validate the new Index
                newIndex = this.connectedHeader.getIndex(Header.ROOT) - 1;

                // #4 shift all affected elements
                for (int i = affectedHeaderComponents.size() - 1; i >= 0; i--) {
                    parentContainer.setComponentZOrder(affectedHeaderComponents.get(i), newIndex + i);
                }
            }
        }
        HeaderComponent.refreshNumbers();
        parentContainer.revalidate();
        parentContainer.repaint();
    }
}