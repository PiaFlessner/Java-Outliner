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
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.*;
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
import main.java.visual_components.toolbox.ToolBoxComponent;

import java.awt.event.InputEvent;

public class MainFrame {

    Header headerRoot = new Header("Root", 0, null, true);
    JFrame window;
    JPanel headerElementContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu;
    ToolBoxComponent toolboxComponent;
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    KeyStroke keyStrokeForNewFile = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
    KeyStroke keyStrokeForToolbarVisibility = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK);
    KeyStroke keyStrokeForMDExport = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
    KeyStroke keyStrokeForSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
    KeyStroke keyStrokeForOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
    KeyStroke keyStrokeForAddHeaderRoot = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);
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
        String actionMapNewFile = "newFile";
        newFileAction = new ToolBoxNewFileAction(this, "New File", keyStrokeForNewFile);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForNewFile, actionMapNewFile);
        masterContainer.getActionMap().put(actionMapNewFile, newFileAction);
        //// Add Contextmenu option
        JMenuItem newFileItem = new JMenuItem();
        newFileItem.setAction(newFileAction);
        contextMenu.add(newFileItem);

        // Export OpenFile Action
        String actionMapKeyOpen = "openFile";
        openFileAction = new ToolBoxOpenFileAction(this, "Open File", keyStrokeForOpen);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForOpen, actionMapKeyOpen);
        masterContainer.getActionMap().put(actionMapKeyOpen, openFileAction);
        //// Add Contextmenu option
        JMenuItem openFileItem = new JMenuItem();
        openFileItem.setAction(openFileAction);
        contextMenu.add(openFileItem);

        // Save FileAction
        String actionMapKeySaveFile = "saveFile";
        saveFileAction = new ToolBoxSaveFileAction(this, "Save", keyStrokeForSave);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForSave, actionMapKeySaveFile);
        masterContainer.getActionMap().put(actionMapKeySaveFile, saveFileAction);
        //// Add Contextmenu option
        JMenuItem saveFileItem = new JMenuItem();
        saveFileItem.setAction(saveFileAction);
        contextMenu.add(saveFileItem);

        // Add Header to Root Action
        String actionMapKeyAddHeaderRoot = "addHeaderToRoot";
        addHeaderAction = new ToolBoxAddHeaderToRootAction(this, headerElementContainer, "Add to first level",
                keyStrokeForAddHeaderRoot);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForAddHeaderRoot,
                actionMapKeyAddHeaderRoot);
        masterContainer.getActionMap().put(actionMapKeyAddHeaderRoot, addHeaderAction);
        //// Add Contextmenu option
        JMenuItem addHeaderRootItem = new JMenuItem();
        addHeaderRootItem.setAction(addHeaderAction);
        contextMenu.addSeparator();
        contextMenu.add(addHeaderRootItem);
        contextMenu.addSeparator();

        // Export MD Action
        String actionMapKeyExportMD = "exportToMarkdown";
        exportAction = new ToolBoxExportMDAction(this, "Export to Markdown", keyStrokeForMDExport);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForMDExport,
                actionMapKeyExportMD);
        masterContainer.getActionMap().put(actionMapKeyExportMD, exportAction);
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
                "Show Toolbox", keyStrokeForToolbarVisibility);
        masterContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForToolbarVisibility,
                "show or Hide Toolbox");
        masterContainer.getActionMap().put("show or Hide Toolbox", showHideAction);
        showToolbarMenuItem.setAction(showHideAction);
    }
}
