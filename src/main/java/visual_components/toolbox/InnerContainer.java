package main.java.visual_components.toolbox;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class InnerContainer extends JPanel{
    private final Dimension MINIMUM_SIZE = new Dimension(10, 40);    

    public InnerContainer(Color backgroundColor){
        this.setBackground(backgroundColor);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setLayout(new BorderLayout());
    }  
}
