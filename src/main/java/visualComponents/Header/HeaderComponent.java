package main.java.visualComponents.Header;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.backendData.AddingDirection;
import main.java.backendData.Header;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TooManyListenersException;
import javax.swing.JPopupMenu;

public class HeaderComponent extends JPanel implements DragGestureListener {

    JPanel headerTitle;
    JButton openListButton;
    JPanel distanceField;
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
    final Color DND_TARGET_HOVERCOLOR = new Color(143, 170, 220);
    final Color DND_TARGET_COLOR = new Color(180, 199, 231);

    Header connectedHeader;
    static LinkedList<HeaderComponent> allHeaderComponents = new LinkedList<>();
    JPanel parentContainer;

    JPanel dropPanel;

    JLabel dropUp;
    JLabel dropDown;
    JLabel dropSub;
    JPanel dropUpPanel;
    JPanel dropDownPanel;
    JPanel dropSubPanel;

    /**
     * Constructor for the HeaderComponent. Displays the whole Container with
     * Numbers, Icon, Title and inherited Text.
     * 
     * @param backgroundColor backgroundcolor which should be used.
     * @param isOpenChildren  Determines, if the Container should be instantiated
     *                        opened or closed.
     * @param connectedHeader The Backend Header Element which belongs to the
     *                        ComponentHeader.
     * @param parentContainer The ParentContainer, which inherits all
     *                        HeaderComponents.
     */
    public HeaderComponent(Color backgroundColor, Header connectedHeader, JPanel parentContainer,
            boolean isChildrenOpen, boolean isContentOpen) {
        this.parentContainer = parentContainer;
        this.connectedHeader = connectedHeader;
        this.backgroundColor = backgroundColor;
        this.setLayout(new BorderLayout());
        this.popupMenu = new JPopupMenu();
        this.setComponentPopupMenu(popupMenu);
        this.setFocusable(true);

        setUpHeaderTitle();
        setUpDropElements();
        setUpContent();
        addFocusingFunction();
        setUpOpenChildrenFunction();
        setUpHoverColorChangeFunction();
        setUpArrowHeaderNavigation();
        setUpEditTextfieldFunction();
        // Makes Element Draggable
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);

        //// Action addition, generates action as well as the contextmenue
        // add header to same level before current
        addingHeaderActions(0, "Add Header on same level before", connectedHeader.getParentElement(),
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.ALT_DOWN_MASK), "addSubheaderToParentBefore", true,
                false);

        // add subheader before adding function
        addingHeaderBeforeActions("Add before",
                KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.ALT_DOWN_MASK), "addSubHeaderBefore", false, false);

        // add subheader at ending adding function
        addingHeaderActions(-1, "Add Subheader", connectedHeader,
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.ALT_DOWN_MASK), "addSubHeaderEnd", false, false);

        // add header to same level after current
        addingHeaderActions(1, "Add Header on same level after", connectedHeader.getParentElement(),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.ALT_DOWN_MASK), "addSubheaderToParentAfter", false,
                true);

        shiftHeaderAction(1, "Shift header up",
                KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.SHIFT_DOWN_MASK),
                "shiftOneUp", false, false, false);

        shiftHeaderAction(1, "Shift header down",
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_DOWN_MASK),
                "shiftOneDown", true, false, false);

        shiftTreeLevelUpDownAction("Shift header level up",
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_DOWN_MASK), "shiftLevelUp", false, false,
                false);

        shiftTreeLevelUpDownAction("Shift header level down",
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_DOWN_MASK), "shiftLevelDown", true, false,
                true);

        deleteHeaderAction("Delete Header", KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, KeyEvent.CTRL_DOWN_MASK),
                "deleteHeader");

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
    public static void deleteAllInstances() {
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

        setUpOpenContextButton();
        setUpDistanceField();

        // Icon adding
        icon = new Icon();
        headerTitle.add(icon);

        // Decide the Icon Picture and Load the right children displaying
        if (this.connectedHeader.empty()) {
            icon.setArrowDefault();
        } else if (connectedHeader.isShowSubHeader()) {
            icon.setArrowOpen();
        } else {
            icon.setArrowClose();
        }

        setUpDisplayedNumber();

        // Title adding
        displayedHeaderTitle = new Title(this);
        headerTitle.add(displayedHeaderTitle);
        this.add(headerTitle, BorderLayout.NORTH);

        // Makes the Headertitle a dropTarget, so that the Real droptargets can appeal,
        // after dragOver is registrated.
        DropTarget dt = new DropTarget();
        try {
            dt.addDropTargetListener(new DropTargetAdapter() {
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    setSize(getWidth(), dropPanel.getHeight());
                    remove(headerTitle);
                    add(dropPanel, BorderLayout.CENTER);
                    repaint();
                    revalidate();
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {
                    setSize(getWidth(), dropPanel.getHeight());
                    remove(headerTitle);
                    add(dropPanel, BorderLayout.CENTER);
                    repaint();
                    revalidate();
                }

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    dtde.rejectDrop();
                }
            });
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        headerTitle.setDropTarget(dt);
    }

    private void setUpOpenContextButton() {
        openListButton = new JButton("▤");
        openListButton.setFocusable(false);
        headerTitle.add(openListButton);
        openListButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openCloseContent();
            }
        });

        // Keyboard Control
        // Open via Space
        String actionMapKey = "openHeader";
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), actionMapKey);
        this.getActionMap().put(actionMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCloseContent();
            }
        });
    }

    private void setUpDistanceField() {
        distanceField = new JPanel();
        int distanceMultiplicator = this.connectedHeader.getKnotLevel(0) - 1;
        Dimension size = new Dimension(25 * distanceMultiplicator, distanceField.getHeight());
        distanceField.setPreferredSize(size);
        headerTitle.add(distanceField);
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

        if (connectedHeader.isShowText())
            openContent();
        else
            closeContent();

    }

    /**
     * Invokes the deleteHeader for the current header.
     * 
     * @param actionText   text for the contextmenue
     * @param keystroke    keystroke for invoking the action
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
     * deletes a header and the belonging subheader from the frontend and the
     * backend.
     */
    private void deleteHeader() {
        this.connectedHeader.getParentElement().deleteSubheader(this.connectedHeader);
        reloadComponents();
    }

    /**
     * Adds a Header before the current element
     * 
     * @param actionText   Action text which is represented in Contextmenu
     * @param keystroke    Keystroke which is binded
     * @param actionMapKey ActionMapKey for binding purporses. Needs to be uniqe
     * @param sepBefore    Tells, if a separator in the contextmenu beforehand is
     *                     needed
     * @param sepAfter     Tells, if a separator in the contextmenue afterwards is
     *                     needed
     */
    private void addingHeaderBeforeActions(String actionText, KeyStroke keystroke, String actionMapKey,
            boolean sepBefore, boolean sepAfter) {
        HeaderComponent self = this;
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int insertIndex = self.connectedHeader.getIndex(Header.ROOT) - 1;
                Header beforeHeader = self.connectedHeader.getHeaderViaIndex(Header.ROOT, insertIndex + 1);
                Header h = new Header("Add Title Here", beforeHeader.getOwnNr(), beforeHeader.getParentElement(),
                        false);
                HeaderComponent hc = new HeaderComponent(backgroundColor, h, parentContainer, h.isShowSubHeader(),
                        h.isShowText());
                parentContainer.add(hc, h.getIndex(Header.ROOT) - 1);
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
                new Header("Add Title Here", indexInParent, parentElement, false);
                reloadComponents();
                parentContainer.getComponent(connectedHeader.getIndex(Header.ROOT) - 1).requestFocusInWindow();
            }
        };
        contextMenuAdding(actionText, action, keystroke, actionMapKey, sepBefore, sepAfter);
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
     * @param sepBefore    true = a separator before item will be added in
     *                     contextmenu
     * @param sepAfter     true = a separator after item will be added in
     *                     contextmenu
     */
    private void shiftHeaderAction(int shiftIndex, String actionText, KeyStroke keystroke, String actionMapKey,
            boolean down, boolean sepBefore, boolean sepAfter) {

        HeaderComponent self = this;
        Action action = new AbstractAction(actionText) {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        int getFocusIndex;
        // if the direction is up or down, the operations are slightly different
        if (down) {
            // only shift down, if the header is not the last element.
            if (this.connectedHeader.getNextNeigbourHeader() != null) {
                this.connectedHeader.getParentElement()
                        .rearrangeSubHeader(this.connectedHeader.getOwnNr() - 1 + shiftIndex, connectedHeader);
                getFocusIndex = connectedHeader.getIndex(Header.ROOT);
                reloadComponents();
                parentContainer.getComponent(getFocusIndex - 1).requestFocusInWindow();
            }

        } else {
            // only shift up, if the header ist not the first element.
            if (this.connectedHeader.getBeforeNeigbourHeader() != null) {
                this.connectedHeader.getParentElement()
                        .rearrangeSubHeader(this.connectedHeader.getOwnNr() - 1 - shiftIndex, connectedHeader);
                getFocusIndex = connectedHeader.getIndex(Header.ROOT);
                reloadComponents();
                parentContainer.getComponent(getFocusIndex - 1).requestFocusInWindow();
            }
        }
    }

    /**
     * Visibly opens the header content.
     */
    private void openContent() {
        connectedHeader.setShowText(true);
        this.setMaximumSize(new Dimension(2147483647, HeaderComponent.HEADERCONTAINER_UNFOLDED_HEIGHT));
        this.textArea.setVisible(connectedHeader.isShowText());
        this.headerContent.setVisible(connectedHeader.isShowText());
        this.textArea.textArea.setFocusable(connectedHeader.isShowText());
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Visibly closes the header content.
     */
    private void closeContent() {
        connectedHeader.setShowText(false);
        this.setMaximumSize(new Dimension(2147483647, HeaderComponent.HEADERCONTAINER_FOLDED_HEIGHT));
        this.headerContent.setVisible(connectedHeader.isShowText());
        this.textArea.setVisible(connectedHeader.isShowText());
        this.textArea.textArea.setFocusable(connectedHeader.isShowText());
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Inherits the logic to open and close a subeader
     */
    private void openCloseContent() {
        if (connectedHeader.isShowText()) {
            closeContent();
        } else {
            openContent();
        }
    }

    /**
     * Makes the Children of the Header visible
     */
    private void openChildren() {
        if (!connectedHeader.empty()) {
            connectedHeader.setShowSubHeader(true);
            icon.setArrowOpen();

            //Iterate throuh header and change visibility State
            for (Header header : connectedHeader.getSubheaders()) {
                int zOrderIndex = header.getIndex(Header.ROOT) - 1;
                HeaderComponent hc = (HeaderComponent) parentContainer.getComponent(zOrderIndex);

                hc.setVisible(connectedHeader.isShowSubHeader());
                hc.setFocusable(connectedHeader.isShowSubHeader());
                
                //Only show the Subsub header, if the subheader is marked as open
                if (header.isShowSubHeader()) {
                    hc.openChildren();
                }
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Makes the Children of a Header invisible
     */
    private void closeChildren() {
        if (!connectedHeader.empty()) {
            connectedHeader.setShowSubHeader(false);
            icon.setArrowClose();
            changeHeaderComponentsChildrenDisplaying(connectedHeader.isShowSubHeader());
            revalidate();
            repaint();
        }
    }

    /**
     * Changes the visibility of Header, without changing the status of it.
     * Needed, when Closing a Parent HeaderComponent, but the Subheader are
     * open. Therfore,  Header.isShowChildren is not allowed to be changed.
     * @param display true = displaying components | false = hide components
     */
    private void changeHeaderComponentsChildrenDisplaying(boolean display) {
        for (Header header : connectedHeader.getSubheaders()) {
            int zOrderIndex = header.getIndex(Header.ROOT) - 1;
            HeaderComponent hc = (HeaderComponent) parentContainer.getComponent(zOrderIndex);
            hc.setVisible(display);
            hc.setFocusable(display);
            hc.changeHeaderComponentsChildrenDisplaying(display);
        }

    }

    /**
     * Inherits the logic to open and close the children to a Header
     */
    private void openCloseChildren() {
        if (connectedHeader.isShowSubHeader()) {
            closeChildren();
        } else {
            openChildren();
        }
    }

    /**
     * Binds the Focus shifting of Header via arrow keys to the actual keystrokes
     */
    private void setUpArrowHeaderNavigation() {
        // KeyStroke Function open
        HeaderComponent self = this;
        String nextHeader = "nextHeader";
        this.getInputMap().put((KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0)), nextHeader);
        this.getActionMap().put(nextHeader, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.shiftFocusOfHeader(1);

            }
        });

        // KeyStroke Function open
        String beforeHeader = "beforeHeader";
        this.getInputMap().put((KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0)), beforeHeader);
        this.getActionMap().put(beforeHeader, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.shiftFocusOfHeader(-1);
            }
        });

    }

    /**
     * Implements the actual focus shifting logic for back, forth and looping
     * behavior.
     * If shifting forth at the end, the focus will be shifted at the start element.
     * If shifting back at the start, the focus will be shiftet at the end element.
     * 
     * @param shiftAmount amount of shifting.
     */
    private void shiftFocusOfHeader(int shiftAmount) {
        int newIndex = parentContainer.getComponentZOrder(this) + shiftAmount;

        // Shift to end, if index is smaller than 0
        if (newIndex < 0) {
            parentContainer.getComponent(parentContainer.getComponentCount() - 1).requestFocusInWindow();
        }
        // Shift to start, if index is gerater than actual component count
        else if (newIndex > parentContainer.getComponentCount() - 1) {
            parentContainer.getComponent(0).requestFocusInWindow();
        }
        // else perform as expected
        else
            parentContainer.getComponent(newIndex).requestFocusInWindow();

    }

    /**
     * Sets up the open Header function via keystrokes and via mouse click.
     */
    private void setUpOpenChildrenFunction() {
        // KeyStroke Function open
        String openMapKey = "openSubHeader";
        this.getInputMap().put((KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0)), openMapKey);
        this.getActionMap().put(openMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openChildren();
            }
        });
        // KeyStroke Function close
        String closeMapKey = "closeSubHeader";
        this.getInputMap().put((KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0)), closeMapKey);
        this.getActionMap().put(closeMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeChildren();
            }
        });

        // Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCloseChildren();
            }
        });
    }

    private void setUpEditTextfieldFunction() {
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
     * @param sepBefore    true = a separator before item will be added in
     *                     contextmenu
     * @param sepAfter     true = a separator after item will be added in
     *                     contextmenu
     */
    private void contextMenuAdding(String actionText, Action action, KeyStroke keystroke, String actionMapKey,
            boolean sepBefore, boolean sepAfter) {
        // Contextmenue Item configuration
        JMenuItem menuItem;
        menuItem = new JMenuItem(actionText);
        menuItem.setAction(action);
        menuItem.setAccelerator(keystroke);
        if (sepBefore)
            this.popupMenu.addSeparator();
        this.popupMenu.add(menuItem);
        if (sepAfter)
            this.popupMenu.addSeparator();

        // makes keystroke possible without opening the contextmenue.
        this.getInputMap(HeaderComponent.WHEN_FOCUSED).put(keystroke, actionMapKey);
        this.getActionMap().put(actionMapKey, action);
    }

    /**
     * Instantiation of the shift header up or down one level.
     * 
     * @param actionText   Text which will be displayed in the contextmenue
     * @param keystroke    Used Keystroke for controlling with keyboard
     * @param actionMapKey backend actionMapKey, which should be unique
     * @param down         true= level down, false = level up.
     * @param sepBefore    true = a separator before item will be added in
     *                     contextmenu
     * @param sepAfter     true = a separator after item will be added in
     *                     contextmenu
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
     * 
     * @param down true= level down, false = level up
     */
    private void shiftTreeLevelUpOrDown(boolean down) {
        int getFocusIndex;
        if (down) {
            Header neighbour = this.connectedHeader.getNextNeigbourHeader();
            Header beforeNeighbour = this.connectedHeader.getBeforeNeigbourHeader();
            Header usedParent;
            // Level down is only possible, if header has siblings.
            if ((neighbour != null && this.connectedHeader.getOwnNr() == 1) ||
                    (beforeNeighbour != null && this.connectedHeader.getOwnNr() > 1)) {

                // #1 if self ownr = 1, then use nextSibling as new Parent (because there is no
                // alternate)
                if (this.connectedHeader.getOwnNr() == 1) {
                    usedParent = neighbour;
                } else {
                    usedParent = beforeNeighbour;
                }

                // #2 delete self from current parent
                this.connectedHeader.getParentElement().deleteSubheader(this.connectedHeader);

                // #3 add self to new Parent
                usedParent.insertNewSubheaderInBetween(this.connectedHeader.getOwnNr(), this.connectedHeader);
                getFocusIndex = this.connectedHeader.getIndex(Header.ROOT);
                reloadComponents();
                parentContainer.getComponent(getFocusIndex - 1).requestFocusInWindow();
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
                getFocusIndex = this.connectedHeader.getIndex(Header.ROOT);
                reloadComponents();
                parentContainer.getComponent(getFocusIndex - 1).requestFocusInWindow();

            }
        }
    }

    /**
     * Reload the components
     */
    private void reloadComponents() {
        // small hack to prevent strange focus flickering
        parentContainer.requestFocus();
        parentContainer.removeAll();
        HeaderComponent.deleteAllInstances();
        this.addWholeHeaderTree(Header.ROOT);
        // Since the Subheader Components are not even existing, when generating a
        // Header Component,
        // the open Close Property
        // of Subheader Components needs to be setted afterward.
        for (HeaderComponent headerComponent : HeaderComponent.allHeaderComponents) {
            if (headerComponent.connectedHeader.isShowSubHeader())
                headerComponent.openChildren();
            else
                headerComponent.closeChildren();

        }
        parentContainer.revalidate();
        parentContainer.repaint();
    }

    /**
     * Recursive methods which adds a whole Header inclusive subheaders to the GUI
     * 
     * @param root starting point of adding. Mostly the root.
     */
    private void addWholeHeaderTree(Header root) {
        for (Header header : root.getSubheaders()) {
            HeaderComponent hc = new HeaderComponent(backgroundColor, header, parentContainer, header.isShowSubHeader(),
                    header.isShowText());
            parentContainer.add(hc);
            addWholeHeaderTree(header);
        }
    }

    /**
     * Sets Up the Drag and Drop Target Elements for DnD Actions.
     * 
     */
    private void setUpDropElements() {
        dropPanel = new JPanel();
        dropUpPanel = new JPanel();
        dropDownPanel = new JPanel();
        dropSubPanel = new JPanel();
        dropUp = new JLabel();
        dropDown = new JLabel();
        dropSub = new JLabel();
        dropPanel.setLayout(new BoxLayout(dropPanel, BoxLayout.X_AXIS));

        dropUpPanel.setBackground(DND_TARGET_COLOR);
        dropUp.setForeground(Color.WHITE);
        dropUp.setText("︽");
        dropUp.setFont(new Font(dropUp.getName(), Font.BOLD, 25));
        dropUpPanel.add(dropUp);
        dropPanel.add(dropUpPanel);

        dropDownPanel.setBackground(DND_TARGET_COLOR);
        dropDown.setForeground(Color.WHITE);
        dropDown.setText("︾");
        dropDown.setFont(new Font(dropDown.getName(), Font.BOLD, 25));
        dropDownPanel.add(dropDown);
        dropPanel.add(dropDownPanel);

        dropSubPanel.setBackground(DND_TARGET_COLOR);
        dropSub.setForeground(Color.WHITE);
        dropSub.setText("➥");
        dropSub.setFont(new Font(dropSub.getName(), Font.BOLD, 25));
        dropSubPanel.add(dropSub);
        dropPanel.add(dropSubPanel);

        dropPanel.setVisible(true);

        // Sets the panels as drop Targets
        new DropTargetListenerHeaderComponents(dropUpPanel, AddingDirection.UP);
        new DropTargetListenerHeaderComponents(dropDownPanel, AddingDirection.DOWN);
        new DropTargetListenerHeaderComponents(dropSubPanel, AddingDirection.SUB);
    }

    /**
     * Starts Drag Gesture Recognizing
     */
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {

        Cursor cursor = null;
        HeaderComponent component = (HeaderComponent) dge.getComponent();
        if (dge.getDragAction() == DnDConstants.ACTION_MOVE) {
            cursor = DragSource.DefaultMoveDrop;
        }
        dge.startDrag(cursor, new TransferableHeaderComponent(component));
    }

    private class DropTargetListenerHeaderComponents extends DropTargetAdapter {
        private JPanel target;
        private AddingDirection direction;

        public DropTargetListenerHeaderComponents(JPanel target, AddingDirection direction) {
            this.direction = direction;
            this.target = target;
            new DropTarget(target, DnDConstants.ACTION_MOVE, this, true, null);
        }

        /**
         * Removes the Drag and Drop Target Element, for example when the user is not in
         * the Header anymore
         */
        private void removeDnDTargetElements() {
            remove(dropPanel);
            add(headerTitle, BorderLayout.CENTER);
            repaint();
            revalidate();
        }

        /**
         * Replaces the Header to the before neighbour from the target.
         * "This" ist the target.
         * This method is intended to be only used for drag and drop operations.
         * 
         * @param sourceHeaderComponent
         */
        private void replaceHeaderUp(HeaderComponent sourceHeaderComponent) {
            Header targetParent = connectedHeader.getParentElement();
            Header sourceParent = sourceHeaderComponent.connectedHeader.getParentElement();
            // Backend
            // remove from source
            sourceParent.deleteSubheader(sourceHeaderComponent.connectedHeader);
            int newNr = connectedHeader.getOwnNr();

            if (isShiftingAllowed(sourceHeaderComponent.connectedHeader)) {
                // Insert Start
                if (newNr == 1) {
                    targetParent.insertNewSubheaderStart(sourceHeaderComponent.connectedHeader);
                    assert sourceHeaderComponent.connectedHeader.getOwnNr() == 1;
                }
                // Insert Inbetween
                else {
                    targetParent.insertNewSubheaderInBetween(newNr - 1, sourceHeaderComponent.connectedHeader);
                    assert sourceHeaderComponent.connectedHeader.getOwnNr() == newNr;
                }
                assert connectedHeader.getParentElement().equals(targetParent);
                reloadComponents();
            }

        }

        /**
         * Replaces the Header to the next neighbour from the target.
         * "This" ist the target.
         * This method is intended to be only used for drag and drop operations.
         * 
         * @param sourceHeaderComponent The dragged source HeaderComponent
         */
        private void replaceHeaderDown(HeaderComponent sourceHeaderComponent) {
            Header targetParent = connectedHeader.getParentElement();
            Header sourceParent = sourceHeaderComponent.connectedHeader.getParentElement();
            // remove from source
            sourceParent.deleteSubheader(sourceHeaderComponent.connectedHeader);
            int newNr = connectedHeader.getOwnNr();

            if (isShiftingAllowed(sourceHeaderComponent.connectedHeader)) {
                // Insert at End
                if (newNr > targetParent.getSubheaderSize()) {
                    targetParent.insertNewSubheaderEnd(sourceHeaderComponent.connectedHeader);
                    assert sourceHeaderComponent.connectedHeader.getOwnNr() == targetParent.getSubheaderSize();
                }
                // Insert inBetween
                else {
                    targetParent.insertNewSubheaderInBetween(newNr, sourceHeaderComponent.connectedHeader);
                    assert sourceHeaderComponent.connectedHeader.getOwnNr() == newNr + 1;
                }
                assert connectedHeader.getParentElement().equals(targetParent);
                reloadComponents();
            }
        }

        /**
         * Replaces the Header to the targetHeader children.
         * "This" ist the target.
         * This method is intended to be only used for drag and drop operations.
         * 
         * @param sourceHeaderComponent The dragged source HeaderComponent
         */
        private void replaceHeaderSub(HeaderComponent sourceHeaderComponent) {
            Header sourceParent = sourceHeaderComponent.connectedHeader.getParentElement();

            if (isShiftingAllowed(sourceHeaderComponent.connectedHeader)) {
                sourceParent.deleteSubheader(sourceHeaderComponent.connectedHeader);
                connectedHeader.insertNewSubheaderStart(sourceHeaderComponent.connectedHeader);
                assert connectedHeader.getParentElement().equals(connectedHeader);
                reloadComponents();
            }
        }

        /**
         * checks, whether the target- and sourceheader combination is valid. Its not
         * valid, if the target is a children of the source
         * or if the target is equal to the source.
         * The "This" element is the target.
         * 
         * @param sourceHeader The header, which is the source.
         * @return true= shifting allowed | false = shifting is not allowed.
         */
        private boolean isShiftingAllowed(Header sourceHeader) {
            if (connectedHeader == sourceHeader || sourceHeader.isHeaderInParentHeader(connectedHeader))
                return false;
            else
                return true;
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            target.setBackground(DND_TARGET_HOVERCOLOR);
            target.repaint();
        }

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            target.setBackground(DND_TARGET_HOVERCOLOR);
            target.repaint();
        }

        @Override
        public void dragExit(DropTargetEvent dtde) {
            target.setBackground(DND_TARGET_COLOR);
            remove(dropPanel);
            add(headerTitle, BorderLayout.CENTER);
            repaint();
            revalidate();
        }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                Transferable tr = dtde.getTransferable();
                HeaderComponent headerComponent = (HeaderComponent) tr
                        .getTransferData(TransferableHeaderComponent.headerComponentFlavor);
                if (dtde.isDataFlavorSupported(TransferableHeaderComponent.headerComponentFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);

                    // Action which should be followed after drag and drop
                    assert HeaderComponent.allHeaderComponents.contains(headerComponent);

                    switch (direction) {
                        case UP:
                            replaceHeaderUp(headerComponent);
                            break;
                        case DOWN:
                            replaceHeaderDown(headerComponent);
                            break;
                        case SUB:
                            replaceHeaderSub(headerComponent);
                            break;
                    }
                    removeDnDTargetElements();
                    dtde.dropComplete(true);
                    return;
                }
                dtde.rejectDrop();
                removeDnDTargetElements();
            } catch (Exception e) {
                e.printStackTrace();
                removeDnDTargetElements();
                dtde.rejectDrop();
            }
        }
    }
}

/**
 * Class for preparing the Transferable with a Drag an Drop. Essential needed,
 * if someone want to perform a drag and drop Action.
 * Translates the HeaderComponent to a transferable object.
 */
class TransferableHeaderComponent implements Transferable {

    // Registration of the DataFlavor Header Component.
    // javaJVMLocal... -> Let the VM regocnize the element as the already existing
    // element.
    // Therefore does not make a copy and does not claim storage
    protected static DataFlavor headerComponentFlavor = new DataFlavor(
            DataFlavor.javaJVMLocalObjectMimeType + "; class=\"" + HeaderComponent.class.getCanonicalName() + "\"",
            "A HeaderComponent Object");
    protected static DataFlavor[] supportedFlavors = { headerComponentFlavor };

    HeaderComponent headerComponent;

    public TransferableHeaderComponent(HeaderComponent headerComponent) {
        this.headerComponent = headerComponent;
    }

    /**
     * Returns all allowed flavors (properties)
     */
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    /**
     * Checks, if a flavor is allowed.
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(headerComponentFlavor) || flavor.equals(DataFlavor.stringFlavor))
            return true;
        return false;
    }

    /**
     * returns a flavor and the connected Data element to it.
     */
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(headerComponentFlavor))
            return headerComponent;
        else if (flavor.equals(DataFlavor.stringFlavor))
            return headerComponent.toString();
        else
            throw new UnsupportedFlavorException(flavor);
    }
}
