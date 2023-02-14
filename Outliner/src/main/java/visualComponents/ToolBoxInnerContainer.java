package main.java.visualComponents;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ToolBoxInnerContainer extends JPanel{
    private JTextField FileNameTextField;
    private final Dimension MINIMUM_SIZE = new Dimension(10, 40);
    private final Dimension BUTTON_PREFFERED_SIZE = new Dimension(150, 22);
    

    public ToolBoxInnerContainer(Color backgroundColor, Color foregroundColor){

        this.setBackground(backgroundColor);
        this.setMinimumSize(MINIMUM_SIZE);
        this.setLayout(new BorderLayout());

        initComponents(backgroundColor, foregroundColor);
    }

    private void initComponents(Color backgroundColor, Color foregroundColor){
        FileNameTextField = new JTextField();

        FileNameTextField.setText("Project Name");
        FileNameTextField.setPreferredSize(BUTTON_PREFFERED_SIZE);
        FileNameTextField.setBackground(backgroundColor);
        FileNameTextField.setForeground(foregroundColor);

        this.add(FileNameTextField, BorderLayout.CENTER);

        FileNameTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                FileNameTextFieldActionPerformed(evt);
            }
        });

    }

    private void FileNameTextFieldActionPerformed(ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }      

       

    
}
