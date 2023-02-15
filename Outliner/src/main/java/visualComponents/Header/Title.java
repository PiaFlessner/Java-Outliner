package main.java.visualComponents.Header;
import javax.swing.JTextField;

import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;

import javafx.scene.input.KeyEvent;
import javafx.util.converter.CurrencyStringConverter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractAction;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Cursor;

public class Title extends JTextField{
    boolean isEditable;
    HeaderComponent parent;
    Cursor NORMAL_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    Cursor EDIT_CURSOR = new Cursor(Cursor.TEXT_CURSOR);

    public Title(HeaderComponent parent){
        this.parent = parent;

        this.setHorizontalAlignment(JTextField.LEFT);
        this.setText("Enter Title Here");
        this.setBorder(null);
        this.setMaximumSize(new Dimension(500, 16));
        this.setMinimumSize(new Dimension(500, 16));
        this.setPreferredSize(new Dimension(500, 16));
        this.setFocusable(true);
        this.setEditable(false);
        this.setCursor(this.NORMAL_CURSOR);
        this.setBackground(parent.backgroundColor);
        addFocusingFunction();
        setUpEditableFunction();


    }

    private void addFocusingFunction(){
        this.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            } 

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(null);
            }
        });
    }

    private void setUpEditableFunction(){
        //KeyStroke Function
        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "EditTextField");
        this.getActionMap().put("EditTextField", new AbstractAction(){
          @Override
          public void actionPerformed(java.awt.event.ActionEvent e) {
              editAbleChange();
          }
        });

  
        //Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
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
            //Change to not editable
            this.setEditable(false);
            this.isEditable = false;
            this.setBackground(parent.backgroundColor);
            this.setCursor(this.NORMAL_CURSOR);
              
        }else {
            //change to editable
            this.setEditable(true);
            this.isEditable = true;
            this.setBackground(parent.EDIT_COLOR);
            this.setCursor(this.EDIT_CURSOR);
          }
  
      }
    
}
