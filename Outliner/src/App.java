import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        Runnable guiCreator = new Runnable(){
            public void run(){
                JFrame fenster = new JFrame("hallo Welt mit Swing");
                fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                fenster.setSize(300,200);
                fenster.setVisible(true);
            }
        };

        SwingUtilities.invokeLater(guiCreator);
    }

}
