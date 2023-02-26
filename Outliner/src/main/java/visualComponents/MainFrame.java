package main.java.visualComponents;

import java.io.File;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import main.java.backendData.Header;
import main.java.visualComponents.Actions.ShowHideToolBarAction;
import main.java.visualComponents.Actions.ToolBoxAddHeaderToRootAction;
import main.java.visualComponents.Actions.ToolBoxExportMDAction;
import main.java.visualComponents.Actions.ToolBoxNewFileAction;
import main.java.visualComponents.Actions.ToolBoxOpenFileAction;
import main.java.visualComponents.Actions.ToolBoxSaveFileAction;
import main.java.visualComponents.Header.HeaderComponent;
import main.java.visualComponents.ToolBox.ToolBoxComponent;

public class MainFrame {

    Header headerRoot = new Header("Root", 0, null, true);
    
    JFrame window;
    JPanel headerElementContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu = new JPopupMenu();
    ToolBoxComponent toolboxComponent;
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    KeyStroke keyStrokeForNewFile = KeyStroke.getKeyStroke(KeyEvent.VK_F5,KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForToolbarVisibility = KeyStroke.getKeyStroke(KeyEvent.VK_F6,KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForMDExport = KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForSave = KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForOpen = KeyStroke.getKeyStroke(KeyEvent.VK_F2, KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForAddHeaderRoot = KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.VK_SHIFT);
    JPanel headerContainer;
    JPanel columnContainer;
    JLabel topicLabel;
    JMenuItem addNewHeaderItem;
    public static final Color WINDOW_BACKGROUND_COLOR = new Color(255,255,255);
    final String ICONPATH = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"appIcon.png";
    
    ToolBoxNewFileAction newFileAction;
    ToolBoxOpenFileAction openFileAction;
    ToolBoxSaveFileAction saveFileAction;
    ToolBoxAddHeaderToRootAction addHeaderAction;
    ToolBoxExportMDAction exportAction;

    public MainFrame(){
        Header.ROOT = headerRoot;
        initComponents();
    }

    public void setRoot(Header root){
        Header.ROOT = root;
        this.headerRoot = root;

        this.reloadComponents();

    }

    public Header getRoot(){
        return this.headerRoot;
    }
    
    public JFrame getWindow(){
        return this.window;
    }

    private void initComponents(){
 
            setUpWindow();
            setUpMasterContainer();
            setUpHeaderContainer();

            setUpToolBoxActions();
            toolboxComponent = new ToolBoxComponent(exportAction,saveFileAction, openFileAction,addHeaderAction, newFileAction);
            masterContainer.add(toolboxComponent, BorderLayout.NORTH);
            setUpShowHideAction();      

            try {
                // Set System L&F
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
        catch (ClassNotFoundException e) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
        catch (InstantiationException e) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
        catch (IllegalAccessException e) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }    
    }

    private void setUpHeaderContainer(){
        headerContainer = new JPanel();
        headerContainer.setBackground(WINDOW_BACKGROUND_COLOR);
        headerContainer.setLayout(new BorderLayout());
        //Inherit contextmenue
        headerContainer.setInheritsPopupMenu(true);

        setUpColumnContainer();
        setUpHeaderElementContainer();

        masterContainer.add(headerContainer,BorderLayout.CENTER);
    }

    private void setUpColumnContainer(){
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

        //inherit context menue
        columnContainer.setInheritsPopupMenu(true);
        headerContainer.add(columnContainer, BorderLayout.NORTH);
    }

    /**
     * Set up the HeaderElement Container with a Scroller Element and adds it to the headerContainer
     */
    private void setUpHeaderElementContainer(){

        //Configure Header Container
        headerElementContainer = new JPanel();
        headerElementContainer.setLayout(new BoxLayout(headerElementContainer, BoxLayout.Y_AXIS));
        headerElementContainer.setMaximumSize(new Dimension(32767, 200));
        headerElementContainer.setMinimumSize(new java.awt.Dimension(1024, 768));
        headerElementContainer.setPreferredSize(new Dimension(1024, 768));
        headerElementContainer.setBackground(WINDOW_BACKGROUND_COLOR);
        //inherit contextmenue
        headerElementContainer.setInheritsPopupMenu(true);
        
        //Configure Scroller Element
        headerElementScroller = new JScrollPane();
        headerElementScroller.setViewportView(headerElementContainer);
        headerElementScroller.setBackground(WINDOW_BACKGROUND_COLOR);

        //Inherit contextmenue
        headerElementScroller.setInheritsPopupMenu(true);
        
        headerContainer.add(headerElementScroller, BorderLayout.CENTER);
    }
    /**
     * Set up the Master Container an adds it to the Window
     */
    private void setUpMasterContainer(){
        masterContainer = new JPanel();
        masterContainer.setLayout(new BorderLayout());
        window.add(masterContainer);
        masterContainer.setComponentPopupMenu(contextMenu);
    }

    /**
     * Setup the Window
     */
    private void setUpWindow(){
        window = new JFrame("Outliner");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800,400);
        window.setVisible(true);
        window.setBackground(WINDOW_BACKGROUND_COLOR);

        //Set Image
        ImageIcon appIcon = new ImageIcon(ICONPATH);
        window.setIconImage(appIcon.getImage());

    }

    private void setUpToolBoxActions(){

        //Export OpenFile Action
        String actionMapNewFile = "newFile";
        newFileAction = new ToolBoxNewFileAction(this, "New File", keyStrokeForNewFile);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForNewFile, actionMapNewFile);
        masterContainer.getActionMap().put(actionMapNewFile, newFileAction);
        ////Add Contextmenu option
        JMenuItem newFileItem = new JMenuItem();
        newFileItem.setAction(newFileAction);
        contextMenu.add(newFileItem);

        //Export OpenFile Action
        String actionMapKeyOpen = "openFile";
        openFileAction = new ToolBoxOpenFileAction(this, "Open File", keyStrokeForOpen);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForOpen, actionMapKeyOpen);
        masterContainer.getActionMap().put(actionMapKeyOpen, openFileAction);
        ////Add Contextmenu option
        JMenuItem openFileItem = new JMenuItem();
        openFileItem.setAction(openFileAction);
        contextMenu.add(openFileItem);

        //Save FileAction
        String actionMapKeySaveFile = "saveFile";
        saveFileAction = new ToolBoxSaveFileAction(this, "Save", keyStrokeForSave);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForSave, actionMapKeySaveFile);
        masterContainer.getActionMap().put(actionMapKeySaveFile, saveFileAction);
        ////Add Contextmenu option
        JMenuItem saveFileItem = new JMenuItem();
        saveFileItem.setAction(saveFileAction);
        contextMenu.add(saveFileItem);

        //Add Header to Root Action
        String actionMapKeyAddHeaderRoot = "addHeaderToRoot";
        addHeaderAction = new ToolBoxAddHeaderToRootAction(this,headerElementContainer, "Add to first level", keyStrokeForAddHeaderRoot);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForAddHeaderRoot, actionMapKeyAddHeaderRoot);
        masterContainer.getActionMap().put(actionMapKeyAddHeaderRoot, addHeaderAction);
        ////Add Contextmenu option
        JMenuItem addHeaderRootItem = new JMenuItem();
        addHeaderRootItem.setAction(addHeaderAction);
        contextMenu.addSeparator();
        contextMenu.add(addHeaderRootItem);
        contextMenu.addSeparator();

        //Export MD Action
        String actionMapKeyExportMD = "exportMD";
        exportAction = new ToolBoxExportMDAction(this, "Export to MD", keyStrokeForMDExport);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForMDExport, actionMapKeyExportMD);
        masterContainer.getActionMap().put(actionMapKeyExportMD, exportAction);
        ////Add Contextmenu option
        JMenuItem exportFileItem = new JMenuItem();
        exportFileItem.setAction(exportAction);
        contextMenu.add(exportFileItem);
        contextMenu.addSeparator();
    }

    /**
     * Setup globally accessible keystrokes
     */
    public void setUpShowHideAction(){

         //Show Hide Toolbar
         showToolbarMenuItem = new JRadioButtonMenuItem();
         showToolbarMenuItem.setActionCommand("hide");
         showToolbarMenuItem.setSelected(true);
         contextMenu.add(showToolbarMenuItem);

        //Bind an Action globally for the whole window, and give action menue item.
        ////Show Hide Toolbar
        ShowHideToolBarAction showHideAction = new ShowHideToolBarAction(toolboxComponent, showToolbarMenuItem, "Show Toolbox", keyStrokeForToolbarVisibility);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForToolbarVisibility, "show or Hide Toolbox");
        masterContainer.getActionMap().put("show or Hide Toolbox", showHideAction);
        showToolbarMenuItem.setAction(showHideAction);
    }
    
    private void reloadComponents(){
        
        headerElementContainer.removeAll();
        HeaderComponent.deleteAllInstances();
        this.addWholeHeaderTree(headerRoot);
        headerElementContainer.revalidate();
        headerElementContainer.repaint();
    }

    /**
     * Rekursive methods which adds a whole Header inclusive subheaders to the GUI
     * @param root starting point of adding. Mostly the root.
     */
    private void addWholeHeaderTree(Header root){
        for(Header header : root.getSubheaders()){
            HeaderComponent hc = new HeaderComponent(WINDOW_BACKGROUND_COLOR, false, header, headerElementContainer );
            headerElementContainer.add(hc);
            addWholeHeaderTree(header);
        }
    }
}
