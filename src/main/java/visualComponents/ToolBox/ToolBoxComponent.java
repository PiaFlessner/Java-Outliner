package main.java.visualComponents.ToolBox;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.visualComponents.Actions.ToolBoxAddHeaderToRootAction;
import main.java.visualComponents.Actions.ToolBoxExportMDAction;
import main.java.visualComponents.Actions.ToolBoxNewFileAction;
import main.java.visualComponents.Actions.ToolBoxOpenFileAction;
import main.java.visualComponents.Actions.ToolBoxSaveFileAction;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ToolBoxComponent extends JPanel{

     private static final Color BACKGROUND_COLOR = new Color(165, 165, 165);
     private static final Color FOREGROUND_COLOR = new Color(255 , 255, 255);
     private static final Dimension MAXIMUM_SIZE = (new Dimension(32767, 60));
     private static final Dimension MINIMUM_SIZE = (new Dimension(100, 10));
     private static final Dimension PREFERRED_SIZE= (new Dimension(636, 60));
     private ToolBoxNewFileAction newFileAction;
     private ToolBoxExportMDAction exportAction;
     private ToolBoxSaveFileAction saveFileAction;
     private ToolBoxOpenFileAction openFileAction;
     private ToolBoxAddHeaderToRootAction addHeaderAction;

     //Constructor
    public ToolBoxComponent(ToolBoxExportMDAction exportAction, ToolBoxSaveFileAction saveFileAction, ToolBoxOpenFileAction openFileAction, ToolBoxAddHeaderToRootAction addHeaderAction, ToolBoxNewFileAction newFileAction){
        this.newFileAction = newFileAction;
        this.exportAction = exportAction;
        this.saveFileAction = saveFileAction;
        this.openFileAction = openFileAction;
        this.addHeaderAction = addHeaderAction;
        this.setBackground(BACKGROUND_COLOR);
        this.setMaximumSize(MAXIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setPreferredSize(PREFERRED_SIZE);
        this.initComponents();

    }

    //Fill Element with other Elements                    
    private void initComponents() {

        //Create all neccessary Elements of Toolbox
        JPanel leftTooltipContainer = new OuterContainer(BACKGROUND_COLOR);
        JButton newFileButton = new Button(leftTooltipContainer);
        JButton openFileButton = new Button( leftTooltipContainer);
        JButton saveFileButton = new Button(leftTooltipContainer);
        JPanel middleTooltipContainer = new InnerContainer(BACKGROUND_COLOR, FOREGROUND_COLOR);
        JPanel rightTooltipContainer = new OuterContainer(BACKGROUND_COLOR);
        JButton addHeaderButton = new Button(middleTooltipContainer);
        JButton exportMDButton = new Button( rightTooltipContainer);

        //Add Action Listeners
        newFileButton.setAction(newFileAction);
        openFileButton.setAction(openFileAction);
        saveFileButton.setAction(saveFileAction);
        addHeaderButton.setAction(addHeaderAction);
        exportMDButton.setAction(exportAction);
        
        //Add header button configuration
        addHeaderButton.setText("+");
        addHeaderButton.setFont(new Font(getFont().getName(), Font.PLAIN, 35));
        addHeaderButton.setVerticalAlignment(SwingConstants.CENTER);
        
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
