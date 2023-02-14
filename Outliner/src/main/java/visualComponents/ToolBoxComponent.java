package main.java.visualComponents;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.visualComponents.ActionListener.ToolBoxExportMDActionListener;
import main.java.visualComponents.ActionListener.ToolBoxNewFileActionListener;
import main.java.visualComponents.ActionListener.ToolBoxSaveFileActionListener;

import java.awt.Color;
import java.awt.Dimension;



public class ToolBoxComponent extends JPanel{

     private static final Color BACKGROUND_COLOR = new Color(165, 165, 165);
     private static final Color FOREGROUND_COLOR = new Color(255 , 255, 255);
     private static final Dimension MAXIMUM_SIZE = (new Dimension(32767, 50));
     private static final Dimension MINIMUM_SIZE = (new Dimension(100, 10));
     private static final Dimension PREFERRED_SIZE= (new Dimension(636, 50));

     //Constructor
    public ToolBoxComponent(){
        this.setBackground(BACKGROUND_COLOR);
        this.setMaximumSize(MAXIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setPreferredSize(PREFERRED_SIZE);
        this.initComponents();

    }

    //Fill Element with other Elements
    @SuppressWarnings("unchecked")                       
    private void initComponents() {

        //Create all neccessary Elements of Toolbox
        JPanel leftTooltipContainer = new ToolBoxOuterContainer(BACKGROUND_COLOR);
        JButton newFileButton = new ToolBoxButton("New File", BACKGROUND_COLOR,FOREGROUND_COLOR,leftTooltipContainer);
        JButton saveFileButton = new ToolBoxButton("Save",BACKGROUND_COLOR,FOREGROUND_COLOR,leftTooltipContainer);
        JPanel middleTooltipContainer = new ToolBoxInnerContainer(BACKGROUND_COLOR, FOREGROUND_COLOR);
        JPanel rightTooltipContainer = new ToolBoxOuterContainer(BACKGROUND_COLOR);
        JButton exportMDButton = new ToolBoxButton("Export MD", BACKGROUND_COLOR, FOREGROUND_COLOR, rightTooltipContainer);
        //JButton detachToolTipButton = new ToolBoxButton("Detach", BACKGROUND_COLOR, FOREGROUND_COLOR, rightTooltipContainer);

        //Add Action Listeners
        newFileButton.addActionListener(new ToolBoxNewFileActionListener());
        saveFileButton.addActionListener(new ToolBoxSaveFileActionListener());
        exportMDButton.addActionListener(new ToolBoxExportMDActionListener());
        //detachToolTipButton.addActionListener(new ToolBoxDetachActionListener(this));

        //Group Layouting - NetBeans Generated Code
        javax.swing.GroupLayout tooltipContainerLayout = new javax.swing.GroupLayout(this);
        this.setLayout(tooltipContainerLayout);
        tooltipContainerLayout.setHorizontalGroup(
            tooltipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tooltipContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(middleTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rightTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        tooltipContainerLayout.setVerticalGroup(
            tooltipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tooltipContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tooltipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rightTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(middleTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leftTooltipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );        
    }
}