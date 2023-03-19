package main.java.visual_components;

import java.io.File;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import main.java.backend_data.Header;
import main.java.visual_components.actions.ShowHideToolBarAction;
import main.java.visual_components.actions.ToolBoxAddHeaderToRootAction;
import main.java.visual_components.actions.ToolBoxExportMDAction;
import main.java.visual_components.actions.ToolBoxNewFileAction;
import main.java.visual_components.actions.ToolBoxOpenFileAction;
import main.java.visual_components.actions.ToolBoxSaveFileAction;
import main.java.visual_components.header.HeaderComponent;
import main.java.visual_components.header.InteractionMapping;
import main.java.visual_components.toolbox.ToolBoxComponent;

public class MainFrame {

    Header headerRoot = new Header("Root", 0, null, true);
    JFrame window;
    JPanel headerElementContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu;
    ToolBoxComponent toolboxComponent;
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    JPanel headerContainer;
    JPanel columnContainer;
    JLabel topicLabel;
    JMenuItem addNewHeaderItem;
    public static final Color WINDOW_BACKGROUND_COLOR = new Color(255, 255, 255);
    public static final Color BUTTON_BACKGROUND_COLOR = new Color(165, 165, 165);
    static final String ICONPATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator
            + "appIcon.png";

    ToolBoxNewFileAction newFileAction;
    ToolBoxOpenFileAction openFileAction;
    ToolBoxSaveFileAction saveFileAction;
    ToolBoxAddHeaderToRootAction addHeaderAction;
    ToolBoxExportMDAction exportAction;

    public MainFrame() {
        Header.setRoot(headerRoot);
        initComponents();
    }

    public void setRoot(Header root) {
        Header.setRoot(root);
        this.headerRoot = root;

        HeaderComponent.reloadComponents();
    }

    public Header getRoot() {
        return this.headerRoot;
    }

    public JFrame getWindow() {
        return this.window;
    }

    private void initComponents() {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | 
            ClassNotFoundException | 
            InstantiationException | 
            IllegalAccessException e) {
                java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
        
        contextMenu = new JPopupMenu();

        setUpWindow();
        setUpMasterContainer();
        setUpHeaderContainer();

        setUpToolBoxActions();
        toolboxComponent = new ToolBoxComponent(exportAction, saveFileAction, openFileAction, addHeaderAction,
                newFileAction);
        masterContainer.add(toolboxComponent, BorderLayout.NORTH);

        
        setUpShowHideAction();
        setUpDummyHeaderComponentActions();

        
    }

    private void setUpHeaderContainer() {
        headerContainer = new JPanel();
        headerContainer.setBackground(WINDOW_BACKGROUND_COLOR);
        headerContainer.setLayout(new BorderLayout());
        // Inherit contextmenue
        headerContainer.setInheritsPopupMenu(true);

        setUpColumnContainer();
        setUpHeaderElementContainer();

        masterContainer.add(headerContainer, BorderLayout.CENTER);
    }

    private void setUpColumnContainer() {
        columnContainer = new JPanel();
        columnContainer.setBackground(new Color(230, 230, 230));
        columnContainer.setMaximumSize(new Dimension(32767, 40));
        columnContainer.setMinimumSize(new Dimension(10, 40));
        columnContainer.setPreferredSize(new Dimension(100, 40));
        FlowLayout columnContainerFlowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        columnContainerFlowLayout.setAlignOnBaseline(true);
        columnContainer.setLayout(columnContainerFlowLayout);

        topicLabel = new JLabel();
        topicLabel.setText("Topic");
        columnContainer.add(topicLabel);

        // inherit context menue
        columnContainer.setInheritsPopupMenu(true);
        headerContainer.add(columnContainer, BorderLayout.NORTH);
    }

    /**
     * Set up the HeaderElement Container with a Scroller Element and adds it to the
     * headerContainer
     */
    private void setUpHeaderElementContainer() {

        // Configure Header Container
        headerElementContainer = new JPanel();
        headerElementContainer.setLayout(new BoxLayout(headerElementContainer, BoxLayout.Y_AXIS));
        headerElementContainer.setBackground(WINDOW_BACKGROUND_COLOR);
        // inherit contextmenue
        headerElementContainer.setInheritsPopupMenu(true);
        HeaderComponent.setParentCointainer(headerElementContainer);

        // Configure Scroller Element
        headerElementScroller = new JScrollPane();
        headerElementScroller.setViewportView(headerElementContainer);
        headerElementScroller.setBackground(WINDOW_BACKGROUND_COLOR);

        // Inherit contextmenue
        headerElementScroller.setInheritsPopupMenu(true);

        headerContainer.add(headerElementScroller, BorderLayout.CENTER);
    }

    /**
     * Set up the Master Container an adds it to the Window
     */
    private void setUpMasterContainer() {
        masterContainer = new JPanel();
        masterContainer.setLayout(new BorderLayout());
        window.add(masterContainer);
        masterContainer.setComponentPopupMenu(contextMenu);
    }

    /**
     * Setup the Window
     */
    private void setUpWindow() {
        window = new JFrame("Outliner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 400);
        window.setVisible(true);
        window.setBackground(WINDOW_BACKGROUND_COLOR);

        // Set Image
        ImageIcon appIcon = new ImageIcon(ICONPATH);
        window.setIconImage(appIcon.getImage());

    }

    private void setUpToolBoxActions() {

        // Export OpenFile Action
        newFileAction = new ToolBoxNewFileAction(this, InteractionMapping.NEW_FILE.getActionName(), InteractionMapping.NEW_FILE.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.NEW_FILE.getKeystroke(), InteractionMapping.NEW_FILE.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.NEW_FILE.getActionMapKey(), newFileAction);
        //// Add Contextmenu option
        JMenuItem newFileItem = new JMenuItem();
        newFileItem.setAction(newFileAction);
        contextMenu.add(newFileItem);

        // Export OpenFile Action
        openFileAction = new ToolBoxOpenFileAction(this, InteractionMapping.OPEN_FILE.getActionName(), InteractionMapping.OPEN_FILE.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.OPEN_FILE.getKeystroke(), InteractionMapping.OPEN_FILE.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.OPEN_FILE.getActionMapKey(), openFileAction);
        //// Add Contextmenu option
        JMenuItem openFileItem = new JMenuItem();
        openFileItem.setAction(openFileAction);
        contextMenu.add(openFileItem);

        // Save FileAction
        saveFileAction = new ToolBoxSaveFileAction(this, InteractionMapping.SAVE_FILE.getActionName(), InteractionMapping.SAVE_FILE.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.SAVE_FILE.getKeystroke(), InteractionMapping.SAVE_FILE.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.SAVE_FILE.getActionMapKey(), saveFileAction);
        //// Add Contextmenu option
        JMenuItem saveFileItem = new JMenuItem();
        saveFileItem.setAction(saveFileAction);
        contextMenu.add(saveFileItem);

        // Add Header to Root Action
        addHeaderAction = new ToolBoxAddHeaderToRootAction(this, headerElementContainer, InteractionMapping.ADD_HEADER_ROOT.getActionName(),
                InteractionMapping.ADD_HEADER_ROOT.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.ADD_HEADER_ROOT.getKeystroke(),
                InteractionMapping.ADD_HEADER_ROOT.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.ADD_HEADER_ROOT.getActionMapKey(), addHeaderAction);
        //// Add Contextmenu option
        JMenuItem addHeaderRootItem = new JMenuItem();
        addHeaderRootItem.setAction(addHeaderAction);
        contextMenu.addSeparator();
        contextMenu.add(addHeaderRootItem);
        contextMenu.addSeparator();

        // Export MD Action
        exportAction = new ToolBoxExportMDAction(this, InteractionMapping.EXPORT_MD.getActionName(), InteractionMapping.EXPORT_MD.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.EXPORT_MD.getKeystroke(),
                InteractionMapping.EXPORT_MD.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.EXPORT_MD.getActionMapKey(), exportAction);
        //// Add Contextmenu option
        JMenuItem exportFileItem = new JMenuItem();
        exportFileItem.setAction(exportAction);
        contextMenu.add(exportFileItem);
        contextMenu.addSeparator();
    }

    /**
     * Setup globally accessible keystrokes
     */
    public void setUpShowHideAction() {

        // Show Hide Toolbar
        showToolbarMenuItem = new JRadioButtonMenuItem();
        showToolbarMenuItem.setActionCommand("hide");
        showToolbarMenuItem.setSelected(true);
        contextMenu.add(showToolbarMenuItem);

        // Bind an Action globally for the whole window, and give action menue item.
        //// Show Hide Toolbar
        ShowHideToolBarAction showHideAction = new ShowHideToolBarAction(toolboxComponent, showToolbarMenuItem,
                InteractionMapping.TOOLBOX_SHOW_HIDE.getActionName(), InteractionMapping.TOOLBOX_SHOW_HIDE.getKeystroke());
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(InteractionMapping.TOOLBOX_SHOW_HIDE.getKeystroke(),
                InteractionMapping.TOOLBOX_SHOW_HIDE.getActionMapKey());
        masterContainer.getActionMap().put(InteractionMapping.TOOLBOX_SHOW_HIDE.getActionMapKey(), showHideAction);
        showToolbarMenuItem.setAction(showHideAction);
    }

    /**
     * Generates the dummy menu buttons of HeaderComponent for the MainContextmenu.
     * For fulfilling Apple contextmenu best practices.
     */
    public void setUpDummyHeaderComponentActions(){

        contextMenu.addSeparator();

        JMenuItem addBeforeItem = new JMenuItem();
        addBeforeItem.setText(InteractionMapping.ADD_UP.getActionName());
        addBeforeItem.setAccelerator(InteractionMapping.ADD_UP.getKeystroke());
        addBeforeItem.setEnabled(false);
        contextMenu.add(addBeforeItem);

        JMenuItem addAfterItem = new JMenuItem();
        addAfterItem.setText(InteractionMapping.ADD_DOWN.getActionName());
        addAfterItem.setAccelerator(InteractionMapping.ADD_DOWN.getKeystroke());
        addAfterItem.setEnabled(false);
        contextMenu.add(addAfterItem);

        JMenuItem addSubItem = new JMenuItem();
        addSubItem.setText(InteractionMapping.ADD_SUB.getActionName());
        addSubItem.setAccelerator(InteractionMapping.ADD_SUB.getKeystroke());
        addSubItem.setEnabled(false);
        contextMenu.add(addSubItem);

        contextMenu.addSeparator();

        JMenuItem shiftUpItem = new JMenuItem();
        shiftUpItem.setText(InteractionMapping.SHIFT_UP.getActionName());
        shiftUpItem.setAccelerator(InteractionMapping.SHIFT_UP.getKeystroke());
        shiftUpItem.setEnabled(false);
        contextMenu.add(shiftUpItem);

        JMenuItem shiftDownItem = new JMenuItem();
        shiftDownItem.setText(InteractionMapping.SHIFT_DOWN.getActionName());
        shiftDownItem.setAccelerator(InteractionMapping.SHIFT_DOWN.getKeystroke());
        shiftDownItem.setEnabled(false);
        contextMenu.add(shiftDownItem);

        JMenuItem shiftLevelUpItem = new JMenuItem();
        shiftLevelUpItem.setText(InteractionMapping.SHIFT_LEVEL_UP.getActionName());
        shiftLevelUpItem.setAccelerator(InteractionMapping.SHIFT_LEVEL_UP.getKeystroke());
        shiftLevelUpItem.setEnabled(false);
        contextMenu.add(shiftLevelUpItem);

        JMenuItem shiftLevelDownItem = new JMenuItem();
        shiftLevelDownItem.setText(InteractionMapping.SHIFT_LEVEL_DOWN.getActionName());
        shiftLevelDownItem.setAccelerator(InteractionMapping.SHIFT_LEVEL_DOWN.getKeystroke());
        shiftLevelDownItem.setEnabled(false);
        contextMenu.add(shiftLevelDownItem);

        contextMenu.addSeparator();

        JMenuItem deleteItem = new JMenuItem();
        deleteItem.setText(InteractionMapping.DELETE.getActionName());
        deleteItem.setAccelerator(InteractionMapping.DELETE.getKeystroke());
        deleteItem.setEnabled(false);
        contextMenu.add(deleteItem);
        


    }
}
