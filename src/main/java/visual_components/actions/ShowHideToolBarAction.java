package main.java.visual_components.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import main.java.visual_components.toolbox.ToolBoxComponent;

public class ShowHideToolBarAction extends AbstractAction{

    ToolBoxComponent component;
    JRadioButtonMenuItem bindedRadioButton;


    public ShowHideToolBarAction(ToolBoxComponent component, JRadioButtonMenuItem bindedRadioButton, String text, KeyStroke keystroke){
        super(text);
        this.component = component;
        this.bindedRadioButton = bindedRadioButton;
        putValue(ACCELERATOR_KEY, keystroke);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(bindedRadioButton.getActionCommand().equals("show")){
            component.setVisible(true);
            bindedRadioButton.setActionCommand("hide");
            bindedRadioButton.setSelected(true);
            component.revalidate();
        }
        else{
            component.setVisible(false);
            bindedRadioButton.setActionCommand("show");
            bindedRadioButton.setSelected(false);
            component.revalidate();
        }
    }    
    
}
