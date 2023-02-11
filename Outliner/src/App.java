import java.awt.BorderLayout;
import javax.swing.*;

import javafx.scene.control.Tooltip;
import visualComponents.ToolBoxComponent;
public class App {
    public static void main(String[] args) throws Exception {
        Runnable guiCreator = new Runnable(){
            public void run(){
                JFrame fenster = new JFrame("Java Outliner");
                JPanel headerContainer = new JPanel();
                JPanel masterContainer = new JPanel();
                JPopupMenu popupMenue;

                fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenster.setSize(300,200);
                fenster.setVisible(true);
                fenster.add(masterContainer);
                //fenster.setLayout(new BorderLayout());

                masterContainer.setLayout(new BorderLayout());
                masterContainer.add(new ToolBoxComponent(), BorderLayout.NORTH);
                masterContainer.add(headerContainer, BorderLayout.CENTER);
                
                popupMenue = new JPopupMenu();
                popupMenue.add(new JMenuItem("Klick mich!"));
                masterContainer.setComponentPopupMenu(popupMenue);
                headerContainer.setInheritsPopupMenu(true);
                
                
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
        };

        SwingUtilities.invokeLater(guiCreator);
    }
}
