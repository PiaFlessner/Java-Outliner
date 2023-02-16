package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import main.java.backendData.Header;
import main.java.visualComponents.Header.HeaderComponent;
import java.awt.Color;

public class AddNewHeaderAction extends AbstractAction{
    Header parentHeader;
    int indexToAdd;
    String displayedText;
    Color backgrouColor;
    JPanel panelToBeAdded;

    /**
     * Creates a new Header Action, which helps to add a new Header on an Action.
     * @param displayedText The Text which should be displayed as a title.
     * @param keystroke The keystroke which should be binded.
     * @param parentHeader The parentHeader of the created Header
     * @param indexToAdd The index, where the Header should be placed. Equals to Header.ownNr.
     */
    public AddNewHeaderAction(String text, String displayedText, KeyStroke keystroke, Header parentHeader, int indexToAdd, Color backgroundColor, JPanel panelToBeAdded){
        super(text);
        this.displayedText = displayedText;
        this.parentHeader = parentHeader;
        this.indexToAdd = indexToAdd;
        this.panelToBeAdded = panelToBeAdded;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Header header = new Header(this.displayedText, this.indexToAdd, this.parentHeader, false);        
        HeaderComponent headerComponent = new HeaderComponent(this.backgrouColor,false, header );
        this.panelToBeAdded.add(headerComponent);
        panelToBeAdded.revalidate();
    }
    
}
