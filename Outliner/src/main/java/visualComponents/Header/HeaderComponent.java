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
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
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
    final int HEADERCONTAINER_FOLDED_HEIGHT = 40;
    final int HEADERCONTAINER_UNFOLDED_HEIGHT = 200;
    final Color FOCUS_COLOR = new Color(214, 220, 229);

    boolean isOpen;
    Header connectedHeader;
    static LinkedList<HeaderComponent> allHeaderComponents = new LinkedList<>();
    JPanel parentContainer;

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

        //add header to same level
        addingHeaderActions(-1, "Add Header on same level", connectedHeader.getParentElement(),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK), "addSubheaderToParent");

        //add subheader adding function
        addingHeaderActions(-1, "Add subheader", connectedHeader,
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), "addSubHeader");
        //add header to level above
        //addingHeaderActions(-1, "Add Header to level above", connectedHeader.getParentElement().getParentElement(),
        //KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "addHeaderToParentParent");
        // adjust open or not open size
        if (isOpen) {
            openHeader();
        } else {
            closeHeader();
        }
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
        // displayedNumber.setFont(displayedNumber.getFont());
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
     * Adds an add Header Action from the Focused Header.
     */
    private void addingHeaderActions(int index, String actionText, Header parentElement, KeyStroke keystroke,
            String actionMapKey ) {

        //if (!parentElement.isRoot()) {
            // Action definition
            Action action = new AbstractAction(actionText) {
                @Override
                public void actionPerformed(ActionEvent e) {

                    Header h = new Header("Add Title Here", index, parentElement, false);
                    HeaderComponent hc = new HeaderComponent(backgroundColor, false, h, parentContainer);
                    parentContainer.add(hc, h.getIndex(Header.root)-1);
                    parentContainer.revalidate();
                }
            };

            // Contextmenue Item configuration
            JMenuItem menuItem;
            menuItem = new JMenuItem(actionText);
            menuItem.setAction(action);
            menuItem.setAccelerator(keystroke);
            this.popupMenu.add(menuItem);

            // makes keystroke possible without opening the contextmenue.
            this.getInputMap(this.WHEN_FOCUSED).put(keystroke, actionMapKey);
            this.getActionMap().put(actionMapKey, action);

        //}

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
}
