import javax.swing.*;
import main.java.visual_components.MainFrame;
public class App {
    
    public static void main(String[] args) throws Exception {   

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Java-Outliner");
        System.setProperty( "apple.awt.application.appearance", "system" );
        
        Runnable guiCreator = new Runnable(){
            public void run(){
                new MainFrame();
            }
        };
        SwingUtilities.invokeLater(guiCreator);
    }
}
