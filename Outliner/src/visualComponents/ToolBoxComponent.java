package visualComponents;

import javax.swing.JPanel;

public class ToolBoxComponent extends JPanel{
                 
     private javax.swing.JTextField FileNameTextField;
     private javax.swing.JButton detachToolTipButton;
     private javax.swing.JButton exportMDButton;
     private javax.swing.JPanel leftTooltipContainer;
     private javax.swing.JPanel middleTooltipContainer;
     private javax.swing.JButton newFileButton;
     private javax.swing.JPanel rightTooltipContainer;
     private javax.swing.JButton saveFileButton;        
     
     private void FileNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void newFileButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void saveFileButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void exportMDButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void detachToolTipButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
    }    

    public ToolBoxComponent(){
        this.initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        leftTooltipContainer = new javax.swing.JPanel();
        newFileButton = new javax.swing.JButton();
        saveFileButton = new javax.swing.JButton();
        middleTooltipContainer = new javax.swing.JPanel();
        FileNameTextField = new javax.swing.JTextField();
        rightTooltipContainer = new javax.swing.JPanel();
        exportMDButton = new javax.swing.JButton();
        detachToolTipButton = new javax.swing.JButton();

        this.setBackground(new java.awt.Color(165, 165, 165));
        this.setMaximumSize(new java.awt.Dimension(32767, 50));
        this.setMinimumSize(new java.awt.Dimension(100, 50));
        this.setPreferredSize(new java.awt.Dimension(636, 50));

        leftTooltipContainer.setBackground(new java.awt.Color(165, 165, 165));
        leftTooltipContainer.setMaximumSize(new java.awt.Dimension(62, 38));
        leftTooltipContainer.setLayout(new java.awt.GridLayout(1, 0));

        newFileButton.setText("New File");
        newFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileButtonActionPerformed(evt);
            }
        });
        leftTooltipContainer.add(newFileButton);

        saveFileButton.setText("Save");
        saveFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileButtonActionPerformed(evt);
            }
        });
        leftTooltipContainer.add(saveFileButton);

        middleTooltipContainer.setBackground(new java.awt.Color(165, 165, 165));
        middleTooltipContainer.setMinimumSize(new java.awt.Dimension(10, 40));
        middleTooltipContainer.setLayout(new java.awt.BorderLayout());

        FileNameTextField.setText("Project Name");
        FileNameTextField.setPreferredSize(new java.awt.Dimension(150, 22));
        FileNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileNameTextFieldActionPerformed(evt);
            }
        });
        middleTooltipContainer.add(FileNameTextField, java.awt.BorderLayout.CENTER);

        rightTooltipContainer.setBackground(new java.awt.Color(165, 165, 165));
        rightTooltipContainer.setMaximumSize(new java.awt.Dimension(196, 40));
        rightTooltipContainer.setPreferredSize(new java.awt.Dimension(196, 40));
        rightTooltipContainer.setLayout(new java.awt.GridLayout(1, 0));

        exportMDButton.setText("Export MD");
        exportMDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMDButtonActionPerformed(evt);
            }
        });
        rightTooltipContainer.add(exportMDButton);

        detachToolTipButton.setText("Detach");
        detachToolTipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detachToolTipButtonActionPerformed(evt);
            }
        });
        rightTooltipContainer.add(detachToolTipButton);

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
