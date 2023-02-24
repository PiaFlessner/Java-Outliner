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
import main.java.visualComponents.Actions.ToolBoxExportMDAction;
import main.java.visualComponents.Actions.ToolBoxOpenFileAction;
import main.java.visualComponents.Actions.ToolBoxSaveFileAction;
import main.java.visualComponents.Header.HeaderComponent;
import main.java.visualComponents.ToolBox.ToolBoxComponent;

public class MainFrame {

    Header headerRoot = new Header("Root", 1, null, true);
    
    JFrame fenster;
    JPanel headerElementContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu = new JPopupMenu();
    ToolBoxComponent toolboxComponent;
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    KeyStroke keyStrokeForToolbarVisibility = KeyStroke.getKeyStroke(KeyEvent.VK_0,KeyEvent.VK_ALT);
    KeyStroke keyStrokeForAddNewHeader = KeyStroke.getKeyStroke(KeyEvent.VK_ADD,KeyEvent.VK_ALT);
    KeyStroke keyStrokeForMDExport = KeyStroke.getKeyStroke(KeyEvent.VK_F8, KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForSave = KeyStroke.getKeyStroke(KeyEvent.VK_F7, KeyEvent.VK_SHIFT);
    KeyStroke keyStrokeForOpen = KeyStroke.getKeyStroke(KeyEvent.VK_F6, KeyEvent.VK_SHIFT);
    JPanel headerContainer;
    JPanel columnContainer;
    JLabel topicLabel;
    JMenuItem addNewHeaderItem;
    final Color WINDOW_BACKGROUND_COLOR = new Color(255,255,255);
    final String ICONPATH = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"appIcon.png";
    ToolBoxExportMDAction exportAction;
    ToolBoxSaveFileAction saveFileAction;
    ToolBoxOpenFileAction openFileAction;

    public MainFrame(){
        Header.ROOT = headerRoot;
        initComponents();
    }

    public void setRoot(Header root){
        this.headerRoot = root;
        Header.ROOT = root;

        //TODO: reload the components.
    }

    public JFrame getWindow(){
        return this.fenster;
    }

    private void initComponents(){
 
            setUpWindow();
            setUpMasterContainer();
            setUpHeaderContainer();

            setUpToolBoxActions();
            toolboxComponent = new ToolBoxComponent(exportAction,saveFileAction, openFileAction);
            masterContainer.add(toolboxComponent, BorderLayout.NORTH);
            setUpShowHideAction();           

            Header h1 = new Header("1", 1,headerRoot,false);
            HeaderComponent hc = new HeaderComponent(WINDOW_BACKGROUND_COLOR, false, h1, headerElementContainer );
            headerElementContainer.add(hc); 


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
        
        //Configure Scroller Element
        headerElementScroller = new JScrollPane();
        headerElementScroller.setViewportView(headerElementContainer);
        headerElementScroller.setBackground(WINDOW_BACKGROUND_COLOR);
        
        headerContainer.add(headerElementScroller, BorderLayout.CENTER);
    }
    /**
     * Set up the Master Container an adds it to the Window
     */
    private void setUpMasterContainer(){
        masterContainer = new JPanel();
        masterContainer.setLayout(new BorderLayout());
        fenster.add(masterContainer);
    }

    /**
     * Setup the Window
     */
    private void setUpWindow(){
        fenster = new JFrame("Outliner");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(800,400);
        fenster.setVisible(true);
        fenster.setBackground(WINDOW_BACKGROUND_COLOR);

        //Set Image
        ImageIcon appIcon = new ImageIcon(ICONPATH);
        fenster.setIconImage(appIcon.getImage());


    }

    private void setUpToolBoxActions(){

        //Save FileAction
        String actionMapKeySaveFile = "saveFile";
        saveFileAction = new ToolBoxSaveFileAction(headerRoot, fenster, "Save", keyStrokeForSave);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForMDExport, actionMapKeySaveFile);
        masterContainer.getActionMap().put(actionMapKeySaveFile, exportAction);

        //Export MD Action
        String actionMapKeyExportMD = "exportMD";
        exportAction = new ToolBoxExportMDAction(headerRoot, fenster, "Export to MD", keyStrokeForMDExport);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForMDExport, actionMapKeyExportMD);
        masterContainer.getActionMap().put(actionMapKeyExportMD, exportAction);

        //Export OpenFile Action
        String actionMapKeyOpen = "openFile";
        openFileAction = new ToolBoxOpenFileAction(this, "Open File", keyStrokeForOpen);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForMDExport, actionMapKeyOpen);
        masterContainer.getActionMap().put(actionMapKeyOpen, openFileAction);
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
 
         masterContainer.setComponentPopupMenu(contextMenu);
 
         headerContainer.setInheritsPopupMenu(true);
         columnContainer.setInheritsPopupMenu(true);
         headerElementScroller.setInheritsPopupMenu(true);
         headerElementContainer.setInheritsPopupMenu(true);


        //Bind an Action globally for the whole window, and give action menue item.
        ////Show Hide Toolbar
        ShowHideToolBarAction showHideAction = new ShowHideToolBarAction(toolboxComponent, showToolbarMenuItem, "Show Toolbox", keyStrokeForToolbarVisibility);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForToolbarVisibility, "show or Hide Toolbox");
        masterContainer.getActionMap().put("show or Hide Toolbox", showHideAction);
        showToolbarMenuItem.setAction(showHideAction);
    }
    
}
