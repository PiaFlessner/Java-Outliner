package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import main.java.backendData.Header;

public class AddNewHeaderAction extends AbstractAction{
    Header parentHeader;

    public AddNewHeaderAction(String text, KeyStroke keystroke, Header parentHeader){
        super(text);
        this.parentHeader = parentHeader;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Header("test", -1, parentHeader, false);
        // TODO Auto-generated method stub
        
    }
    
}
