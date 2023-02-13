import java.awt.BorderLayout;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.event.*;
import javafx.scene.control.Tooltip;
import visualComponents.ToolBoxComponent;
import visualComponents.Actions.ShowHideToolBarAction;
import javax.swing.JScrollPane;
import java.awt.Dimension;
public class App {
    JFrame fenster;
    JPanel headerContainer;
    JPanel masterContainer;
    JPopupMenu contextMenu;
    ToolBoxComponent toolboxComponent = new ToolBoxComponent();;
    JRadioButtonMenuItem showToolbarMenuItem;
    JScrollPane headerElementScroller;
    KeyStroke keyStrokeForToolbarVisibility = KeyStroke.getKeyStroke(KeyEvent.VK_0,KeyEvent.VK_ALT);

    public App(){
        initComponents();
    }

    private void initComponents(){

            setUpWindow();
            setUpMasterContainer();
            setUpHeaderContainer();
            masterContainer.add(toolboxComponent, BorderLayout.NORTH);
            setUpContextMenue();            
            setUpGlobalKeystrokes();            
            
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
    /**
     * Set up the Header Container with a Scroller Element and adds it to the master Container.
     */
    private void setUpHeaderContainer(){

        //Configure Header Container
        headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.setMaximumSize(new java.awt.Dimension(32767, 200));
        headerContainer.setPreferredSize(new java.awt.Dimension(1024, 768));

        //Configure Scroller Element
        headerElementScroller = new JScrollPane();
        headerElementScroller.setViewportView(headerContainer);

        masterContainer.add(headerElementScroller, BorderLayout.CENTER);             
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
    }

    /**
     * Setup globally accessible keystrokes
     */
    public void setUpGlobalKeystrokes(){
        //Bind an Action globally for the whole window, and give action menue item.
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
        showToolbarMenuItem = new JRadioButtonMenuItem("Show Toolbox");
        showToolbarMenuItem.setActionCommand("hide");
        showToolbarMenuItem.setSelected(true);
        contextMenu.add(showToolbarMenuItem);
        masterContainer.setComponentPopupMenu(contextMenu);
        headerElementScroller.setInheritsPopupMenu(true);
        headerContainer.setInheritsPopupMenu(true);
        
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
