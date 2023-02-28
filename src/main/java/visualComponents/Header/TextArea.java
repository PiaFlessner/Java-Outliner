package main.java.visualComponents.Header;

import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        if(parent.connectedHeader.getText() != null) 
        this.textArea.setText(parent.connectedHeader.getText());    
        else this.textArea.setText("Please put your Text here.");
        this.textArea.setMaximumSize(new Dimension(2147483647, 150));
        this.textArea.setFocusable(false);
        this.setViewportView(textArea);
        addFocusingFunction();
        addChangedFunction();
    }

/**
 * Adds a focusing behavior
 */
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

    /**
     * Invokes the refreshing of the backend data when user writes something down.
     */
    private void addChangedFunction(){
        this.textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                parent.connectedHeader.setText(textArea.getText());               
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                parent.connectedHeader.setText(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                parent.connectedHeader.setText(textArea.getText());  
            }
            
        });
    }
}
