package main.java.visualComponents.Header;

import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class TextArea extends JScrollPane {
    JTextArea textArea;
    HeaderComponent parent;
    boolean isEditable;

    public TextArea(HeaderComponent parent){
        this.parent = parent;
        this.isEditable = false;

        this.textArea = new JTextArea(); 
        this.textArea.setEditable(true);
        this.textArea.setBackground(parent.backgroundColor);
        this.textArea.setColumns(20);
        this.textArea.setLineWrap(true);
        this.textArea.setRows(5);
        this.textArea.setText("Please put your Text here.");
        this.textArea.setMaximumSize(new Dimension(2147483647, 150));
        this.textArea.setFocusable(false);
        this.setViewportView(textArea);
        //setUpEditableFunction();
        addFocusingFunction();
    }


    private void addFocusingFunction(){
        textArea.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                textArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            } 

            @Override
            public void focusLost(FocusEvent e) {
                textArea.setBorder(null);
            }
        });
    }


    private void setUpEditableFunction(){
      //KeyStroke Function
      textArea.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "EditTextField");
      textArea.getActionMap().put("EditTextField", new AbstractAction(){
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            editAbleChange();
        }
      });

      //Button Click Function
      textArea.addMouseListener(new MouseInputAdapter() {
          @Override
          public void mouseClicked(java.awt.event.MouseEvent e) {
            if(e.getClickCount() == 2){
                editAbleChange();         

            }
          }           
      });
    }

    private void editAbleChange(){

        if(this.isEditable){
            //Cange to not editable
            textArea.setEditable(false);
        }
        else{
            //change to editable
            textArea.setEditable(true);

        }

    }
}
