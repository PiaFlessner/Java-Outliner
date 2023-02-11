package visualComponents.ActionListener;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import visualComponents.ToolBoxComponent;

import java.awt.event.ActionEvent;

public class MenuItemToolboxShowActionListener implements ActionListener{
    ToolBoxComponent component;
    JRadioButtonMenuItem source;

    public MenuItemToolboxShowActionListener(ToolBoxComponent component, JRadioButtonMenuItem source){
        this.component = component;
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getActionCommand().equals("show")){
            component.setVisible(true);
            source.setActionCommand("hide");
            component.revalidate();
        }
        else{
            component.setVisible(false);
            source.setActionCommand("show");
            component.revalidate();
        }
        
    }
    
}
