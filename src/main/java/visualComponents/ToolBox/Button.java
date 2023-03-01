package main.java.visualComponents.ToolBox;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Button extends JButton{
    public Button(Color backgroundColor, Color foregroundColor, JPanel parent){
        this.setFocusPainted(false);
        this.setForeground(foregroundColor);
        this.setBackground(backgroundColor);
        parent.add(this);
    }    
}
