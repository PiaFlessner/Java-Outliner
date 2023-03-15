package main.java.visual_components.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import main.java.backend_data.Header;
import main.java.visual_components.MainFrame;

public class ToolBoxNewFileAction extends AbstractAction{
    MainFrame mainFrame;

    public ToolBoxNewFileAction(MainFrame mainFrame, String text, KeyStroke keystroke){
        super(text);
        this.mainFrame = mainFrame;
        putValue(ACCELERATOR_KEY, keystroke);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Show a yes or no option pane
        int dialogresult = JOptionPane.showConfirmDialog(mainFrame.getWindow(), "Do you want to discard all changes and create a new File?", "New file", JOptionPane.YES_NO_OPTION);
        
        //Yes
        if(dialogresult == 0){
            Header headerRoot = new Header("Root", 0, null, true);
            mainFrame.setRoot(headerRoot);
        }
    }
    
}
