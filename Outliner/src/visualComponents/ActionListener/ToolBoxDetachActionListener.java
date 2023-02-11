package visualComponents.ActionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import visualComponents.ToolBoxComponent;

public class ToolBoxDetachActionListener implements ActionListener {

    ToolBoxComponent component;

    public ToolBoxDetachActionListener(ToolBoxComponent component){
        this.component = component;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        component.setVisible(false);
        component.revalidate(); 
    }
}


