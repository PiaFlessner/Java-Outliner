import java.awt.BorderLayout;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.event.*;
import javafx.scene.control.Tooltip;
import visualComponents.ToolBoxComponent;
import visualComponents.ActionListener.MenuItemToolboxShowActionListener;
import visualComponents.Actions.ShowHideToolBarAction;

public class App {
    JFrame fenster;
    JPanel headerContainer;
    JPanel masterContainer;
    JPopupMenu popupMenue;
    ToolBoxComponent toolboxComponent;
    JRadioButtonMenuItem subMenue;

    public App(){
        initComponents();
    }

    private void initComponents(){

            fenster = new JFrame("Java Outliner");
            headerContainer = new JPanel();
                masterContainer = new JPanel();

                fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenster.setSize(300,200);
                fenster.setVisible(true);
                fenster.add(masterContainer);

                toolboxComponent = new ToolBoxComponent();

                masterContainer.setLayout(new BorderLayout());
                masterContainer.add(toolboxComponent, BorderLayout.NORTH);
                masterContainer.add(headerContainer, BorderLayout.CENTER);

                
                popupMenue = new JPopupMenu();
                subMenue = new JRadioButtonMenuItem("Show Toolbox");
                subMenue.setActionCommand("hide");
                subMenue.setSelected(true);
                popupMenue.add(subMenue);
                
                //Bind an Action globally for the whole window, and give action menue item.
                KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_0,KeyEvent.VK_ALT);
                ShowHideToolBarAction showHideAction = new ShowHideToolBarAction(toolboxComponent, subMenue, "Show Toolbox", keyStroke);
                
                masterContainer.getInputMap(masterContainer.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "show or Hide Toolbox");
                masterContainer.getActionMap().put("show or Hide Toolbox", showHideAction);
                subMenue.setAction(showHideAction);

                //Make Popupmenue visible
                masterContainer.setComponentPopupMenu(popupMenue);
                
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

    private void subMenueMouseClicked(java.awt.event.MouseEvent evt) {                                         
        toolboxComponent.setVisible(true);
        toolboxComponent.revalidate();
        fenster.revalidate();
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
