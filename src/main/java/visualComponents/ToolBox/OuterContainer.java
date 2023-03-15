package main.java.visualComponents.ToolBox;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;

public class OuterContainer extends JPanel {
    private final Dimension CONTAINER_DIMENSION = new Dimension(62, 38);
    private final GridLayout CONTAINER_GRIDLAYOUT = new GridLayout(1, 0);

    public OuterContainer(Color backgroundColor){
        this.setBackground(backgroundColor);
        this.setMaximumSize(CONTAINER_DIMENSION);
        this.setLayout(CONTAINER_GRIDLAYOUT);
    }



}
