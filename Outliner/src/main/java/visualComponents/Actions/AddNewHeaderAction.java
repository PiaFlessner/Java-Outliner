package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class AddNewHeaderAction extends AbstractAction{

    public AddNewHeaderAction(String text, KeyStroke keystroke){
        super(text);
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
