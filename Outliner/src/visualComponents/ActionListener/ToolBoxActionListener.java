package visualComponents.ActionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import visualComponents.ToolBoxComponent;

public class ToolBoxActionListener implements ActionListener {

    ToolBoxComponent component;

    public ToolBoxActionListener(ToolBoxComponent component){
        this.component = component;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.setVisible(false);
        component.revalidate(); 
    }
}


