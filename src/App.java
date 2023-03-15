
import javax.swing.*;

import main.java.visual_components.MainFrame;


public class App {
    

    public static void main(String[] args) throws Exception {   
        Runnable guiCreator = new Runnable(){
            public void run(){
                new MainFrame();
            }
        };
        SwingUtilities.invokeLater(guiCreator);
    }
}
