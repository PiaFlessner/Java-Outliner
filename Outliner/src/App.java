import java.awt.BorderLayout;
import javax.swing.*;

import javafx.scene.control.Tooltip;
import visualComponents.ToolBoxComponent;
public class App {
    JFrame fenster;
    JPanel headerContainer;
    JPanel masterContainer;
    JPopupMenu popupMenue;
    JPanel toolboxComponent;
    JMenuItem subMenue;

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
                subMenue = new JMenuItem("Klick mich!");
                popupMenue.add(subMenue);


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
