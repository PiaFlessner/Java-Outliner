import java.awt.BorderLayout;
import javax.swing.*;

import main.java.backendData.Header;
import main.java.visualComponents.Actions.ShowHideToolBarAction;
import main.java.visualComponents.Header.HeaderComponent;
import main.java.visualComponents.ToolBox.ToolBoxComponent;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
public class App {
    Header headerRoot = new Header("Root", 1, null, true);
    
    JFrame fenster;
    JPanel headerElementContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu;
    ToolBoxComponent toolboxComponent = new ToolBoxComponent();
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    KeyStroke keyStrokeForToolbarVisibility = KeyStroke.getKeyStroke(KeyEvent.VK_0,KeyEvent.VK_ALT);
    KeyStroke keyStrokeForAddNewHeader = KeyStroke.getKeyStroke(KeyEvent.VK_ADD,KeyEvent.VK_ALT);
    JPanel headerContainer;
    JPanel columnContainer;
    JLabel topicLabel;
    JMenuItem addNewHeaderItem;
    final Color WINDOW_BACKGROUND_COLOR = new Color(255,255,255);

    public App(){
        Header.root = headerRoot;
        initComponents();
    }

    private void initComponents(){

            setUpWindow();
            setUpMasterContainer();
            setUpHeaderContainer();
            masterContainer.add(toolboxComponent, BorderLayout.NORTH);
            setUpContextMenue();            
            setUpGlobalKeystrokes();  

            Header h1 = new Header("1", 1,headerRoot,false);
            //Header h11 = new Header("1.1", 1,h1,false);
            //Header h111 = new Header("1.1.1", 1,h11,false);
            //Header h112 = new Header("1.1.2", 2,h11,false);
            //Header h2 = new Header("2", 2,headerRoot,false);
            //Header h21 = new Header("2.1", 1,h2,false);
            //Header h22 = new Header("2.2", 2,h2,false);
            //Header h3 = new Header("3", 3,headerRoot,false);
            //Header h4 = new Header("4", 4,headerRoot,false);
            //Header h41 = new Header("4.1", 1,h4,false);
            

            //Header h12 = new Header("1.2", 2,h1,false);
            //Header h13 = new Header("1.3", 3,h1,false);
            //Header h14 = new Header("1.4",4,h1,false);
            //Header h15 = new Header("1.5",5,h1,false);
            //Header h151 = new Header("1.5.1",5,h15,false);
            //Header h152 = new Header("1.5.2",5,h15,false);
            //Header h153 = new Header("1.5.3",5,h15,false);
            //Header h16 = new Header("1.6",6,h1,false);

           /* Header h2 = new Header("2", 2,headerRoot,false);
            Header h21 = new Header("2.1", 1,h2,false);
            Header h22 = new Header("2.2", 2,h2,false);
            Header h221 = new Header("2.2.1", 1,h22,false);
            */
            //Header testHeader = h112;
            //System.out.println(testHeader.getIndex(headerRoot) + " own Nr :" + testHeader.getOwnNr() + " labelNr :" + testHeader.getLabelNr());

            HeaderComponent hc = new HeaderComponent(WINDOW_BACKGROUND_COLOR, false, h1, headerElementContainer );
            headerElementContainer.add(hc); 
            

            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        fenster = new JFrame("Java Outliner");
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(300,200);
        fenster.setVisible(true);
        fenster.setBackground(WINDOW_BACKGROUND_COLOR);
    }

    /**
     * Setup globally accessible keystrokes
     */
    public void setUpGlobalKeystrokes(){
        //Bind an Action globally for the whole window, and give action menue item.
        ////Show Hide Toolbar
        ShowHideToolBarAction showHideAction = new ShowHideToolBarAction(toolboxComponent, showToolbarMenuItem, "Show Toolbox", keyStrokeForToolbarVisibility);
        masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeForToolbarVisibility, "show or Hide Toolbox");
        masterContainer.getActionMap().put("show or Hide Toolbox", showHideAction);
        showToolbarMenuItem.setAction(showHideAction);
    }

    /**
     * Creates a globally available contextmenu and adds it to the masterContainer
     */
    public void setUpContextMenue(){
        contextMenu = new JPopupMenu();

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
        
    }


    public static void main(String[] args) throws Exception {   
        Runnable guiCreator = new Runnable(){
            public void run(){
                new App();
            }
        };
        SwingUtilities.invokeLater(guiCreator);
    }
}
