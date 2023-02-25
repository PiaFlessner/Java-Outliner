package main.java.visualComponents.ToolBox;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Button extends JButton{

    public Button(String text, Color backgroundColor, Color foregroundColor, JPanel parent){
        this.setText(text);
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        parent.add(this);
    }    

    public Button(Color backgroundColor, Color foregroundColor, JPanel parent, String pathToIcon){
        this.setIcon(new ImageIcon(pathToIcon));
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        parent.add(this);
    }
}
