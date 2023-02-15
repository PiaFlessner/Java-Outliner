package main.java.visualComponents.Actions;

import java.awt.event.ActionEvent;
import java.time.chrono.IsoChronology;

import javax.swing.AbstractAction;
import main.java.visualComponents.HeaderComponent;

public class OpenHeaderAction extends AbstractAction{
    HeaderComponent component;


    public OpenHeaderAction(HeaderComponent component){
        this.component = component;
    }

    /**
     * Opens or closes the Header, depending on isOpen;
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(component.getIsOpen()){
            System.out.print("Closing the Header now");
            component.setIsOpen(false);
        }
        else{
            System.out.print("Opening the Header now");
            component.setIsOpen(true);
        
        }
    }   
}
