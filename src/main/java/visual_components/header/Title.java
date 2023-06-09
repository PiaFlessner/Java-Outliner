package main.java.visual_components.header;

import javax.swing.JTextField;

import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractAction;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Cursor;

public class Title extends JTextField {
    boolean isEditable;
    HeaderComponent parentComponent;
    Cursor NORMAL_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    Cursor EDIT_CURSOR = new Cursor(Cursor.TEXT_CURSOR);

    public Title(HeaderComponent parentComponent) {
        this.parentComponent = parentComponent;

        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setText(parentComponent.connectedHeader.getTitle());
        this.setBorder(null);
        this.setMaximumSize(new Dimension(500, 16));
        this.setMinimumSize(new Dimension(500, 16));
        this.setPreferredSize(new Dimension(500, 16));
        this.setFocusable(false);
        this.setEditable(false);
        this.setCursor(this.NORMAL_CURSOR);
        this.setBackground(parentComponent.backgroundColor);
        addFocusingFunction();
        setUpEditableFunction();
        setUpChangedFunction();

    }

    /**
     * Sets the focusing function.
     */
    private void addFocusingFunction() {
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(null);
                isEditable = true;
                editAbleChange();
            }
        });
    }

    /**
     * Makes the textfield editable when user presses enter.
     */
    private void setUpEditableFunction() {
        // KeyStroke Function
        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "EditTextField");
        this.getActionMap().put("EditTextField", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                editAbleChange();
            }
        });
        // Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editAbleChange();

                }
            }
        });
    }

    /**
     * Refreshes the beackend data when user changes something.
     */
    private void setUpChangedFunction() {
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                parentComponent.connectedHeader.setTitle(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                parentComponent.connectedHeader.setTitle(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                parentComponent.connectedHeader.setTitle(getText());
            }

        });
    }
    /**
     * Changes the editablity
     */
    public void editAbleChange() {

        if (this.isEditable) {
            // Change to not editable
            this.setEditable(false);
            this.isEditable = false;
            this.setFocusable(false);
            this.setBackground(parentComponent.backgroundColor);
            this.setCursor(this.NORMAL_CURSOR);
            this.transferFocusBackward();

        } else {
            // change to editable
            this.setEditable(true);
            this.isEditable = true;
            this.setBackground(HeaderComponent.EDIT_COLOR);
            this.setCursor(this.EDIT_CURSOR);
            this.setFocusable(true);
            this.requestFocus();
            this.selectAll();
        }

    }

}
