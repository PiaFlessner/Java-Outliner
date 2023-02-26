package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import main.java.backendData.Header;
import main.java.visualComponents.MainFrame;
import main.java.visualComponents.Header.HeaderComponent;

public class ToolBoxAddHeaderToRootAction extends AbstractAction{
    MainFrame mainFrame;
    JPanel parentContainer;

    public ToolBoxAddHeaderToRootAction(MainFrame mainFrame, JPanel parentContainer, String text, KeyStroke keystroke){
        super(text);
        this.mainFrame = mainFrame;
        this.parentContainer = parentContainer;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Header h = new Header("Add Title Here", -1,mainFrame.getRoot(),false);
        HeaderComponent hc = new HeaderComponent(MainFrame.WINDOW_BACKGROUND_COLOR,  false, h, this.parentContainer);
        parentContainer.add(hc); 
        parentContainer.revalidate();
        parentContainer.repaint();
    }
}
