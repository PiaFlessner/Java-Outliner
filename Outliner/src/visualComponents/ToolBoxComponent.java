package visualComponents;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;


public class ToolBoxComponent extends JPanel{
                 
     private JTextField FileNameTextField;
     private JButton detachToolTipButton;
     private JButton exportMDButton;
     private JPanel leftTooltipContainer;
     private JPanel middleTooltipContainer;
     private JButton newFileButton;
     private JPanel rightTooltipContainer;
     private JButton saveFileButton;        
     private JButton scrollToolbarOutButton;
     private final Color BACKGROUND_COLOR = new Color(165, 165, 165);
     private final Color FOREGROUND_COLOR = new Color(255 , 255, 255);

     private Dimension MAXIMUM_SIZE = (new Dimension(32767, 50));
     private Dimension MINIMUM_SIZE = (new Dimension(100, 10));
     private Dimension PREFERRED_SIZE= (new Dimension(636, 50));
     

     //START ACTION PERFORMED SECTION
     private void FileNameTextFieldActionPerformed(ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void newFileButtonActionPerformed(ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void saveFileButtonActionPerformed(ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void scrollToolbarOutButtonActionPerformed(ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void detachToolTipButtonActionPerformed(ActionEvent evt) {                                                    
        leftTooltipContainer.setVisible(false);
        middleTooltipContainer.setVisible(false);
        rightTooltipContainer.setVisible(false);

        this.setPreferredSize(new java.awt.Dimension(this.getWidth(), 15));
        revalidate();
        
    }    

    private void exportMDButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    } 

    //Constructor
    public ToolBoxComponent(){
        this.setBackground(BACKGROUND_COLOR);
        this.setMaximumSize(MAXIMUM_SIZE);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setPreferredSize(PREFERRED_SIZE);

        this.initComponents();
    }
    
    @SuppressWarnings("unchecked")                       
    private void initComponents() {

        leftTooltipContainer = new ToolBoxOuterContainer(BACKGROUND_COLOR);
        newFileButton = new ToolBoxButton("New File", BACKGROUND_COLOR,FOREGROUND_COLOR,leftTooltipContainer);
        saveFileButton = new ToolBoxButton("Save",BACKGROUND_COLOR,FOREGROUND_COLOR,leftTooltipContainer);
        middleTooltipContainer = new JPanel();
        FileNameTextField = new JTextField();
        rightTooltipContainer = new ToolBoxOuterContainer(BACKGROUND_COLOR);
        exportMDButton = new ToolBoxButton("Export MD", BACKGROUND_COLOR, FOREGROUND_COLOR, rightTooltipContainer);
        detachToolTipButton = new ToolBoxButton("Detach", BACKGROUND_COLOR, FOREGROUND_COLOR, rightTooltipContainer);

        middleTooltipContainer.setBackground(BACKGROUND_COLOR);
        middleTooltipContainer.setMinimumSize(new Dimension(10, 40));
        middleTooltipContainer.setLayout(new BorderLayout());

        FileNameTextField.setText("Project Name");
        FileNameTextField.setPreferredSize(new Dimension(150, 22));
        FileNameTextField.setBackground(BACKGROUND_COLOR);
        FileNameTextField.setForeground(FOREGROUND_COLOR);

        middleTooltipContainer.add(FileNameTextField, BorderLayout.CENTER);

        newFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileButtonActionPerformed(evt);
            }
        });
        saveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileButtonActionPerformed(evt);
            }
        });

        FileNameTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                FileNameTextFieldActionPerformed(evt);
            }
        });

        exportMDButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMDButtonActionPerformed(evt);
            }
        });
        detachToolTipButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detachToolTipButtonActionPerformed(evt);
            }
        });

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
