package main.java.visualComponents.ToolBox;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Button extends JButton{

    public Button(Color backgroundColor, Color foregroundColor, JPanel parent){
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        parent.add(this);
    }    
}
