package main.java.visualComponents;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBoxButton extends JButton{

    public ToolBoxButton(String text, Color backgroundColor, Color foregroundColor, JPanel parent){
        this.setText(text);
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        parent.add(this);
    }    
}
