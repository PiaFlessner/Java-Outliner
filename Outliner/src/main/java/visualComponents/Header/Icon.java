package main.java.visualComponents.Header;

import javax.swing.JLabel;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import javafx.scene.input.MouseEvent;

import java.awt.Cursor;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;


import java.io.File;
public class Icon extends JLabel{
    

    HeaderComponent parent;
    final String ARROW_ICON = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrow.png";
    final String ARROW_ICON_DOWN = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrowDown.png";


    public Icon(HeaderComponent parent){
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setIcon(new ImageIcon(ARROW_ICON));
        this.setToolTipText("");
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(20, 20));
        this.setFocusable(true);

        this.parent = parent;
        addFocusingFunction();
        setUpOpenHeaderFunction();
       
        parent.headerTitle.add(this);
    }

    private void openClose(){
        if(parent.isOpen){
            //Close Header
            this.setIcon(new ImageIcon(ARROW_ICON));
            parent.isOpen = false;
            parent.setMaximumSize(new Dimension(2147483647,parent.HEADERCONTAINER_FOLDED_HEIGHT));
        }
        else{
            //Open Header
            this.setIcon(new ImageIcon(ARROW_ICON_DOWN));
            parent.isOpen = true;
            parent.setMaximumSize(new Dimension(2147483647, parent.HEADERCONTAINER_UNFOLDED_HEIGHT));
        }
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

    private void setUpOpenHeaderFunction(){
        //KeyStroke Function
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "OpenCloseHeader");
        this.getActionMap().put("OpenCloseHeader", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openClose();
            }
        });

        //Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openClose();
            }           
        });
    }
}