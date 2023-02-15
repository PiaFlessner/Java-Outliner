package main.java.visualComponents;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.backendData.Header;
import main.java.visualComponents.Actions.OpenHeaderAction;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;

import java.awt.Cursor;

import javax.management.openmbean.OpenDataException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.io.File;
import java.security.Key;

public class HeaderComponent extends JPanel{


    JPanel headerTitle;

    JLabel arrowIcon;

    JLabel displayedNumber;
    JTextField displayedNumberEdit;
    
    JLabel displayedHeaderTitle;
    JTextField displayedHeaderTitleEdit;


    JPanel headerContent;
    JScrollPane headerContentScrollPane;
    JTextArea headerContentTextArea;

    Color backgroundColor;
    final int HEADERCONTAINER_FOLDED_HEIGHT = 40;
    final int HEADERCONTAINER_UNFOLDED_HEIGHT = 200;

    final String ARROW_ICON = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrow.png";
    final String ARROW_ICON_DOWN = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrowDown.png";
    boolean isOpen;
    

    
    
    
    public HeaderComponent(Color backgroundColor, boolean isOpen){
        this.setMaximumSize(new Dimension(2147483647, 200));
        this.setMinimumSize(new Dimension(275, 40));
        this.setLayout(new BorderLayout());
        this.backgroundColor = backgroundColor;
        this.isOpen = isOpen;
        
        setUpHeaderTitle();
        setUpContent();
   
    }

    private void setUpHeaderTitle(){
        headerTitle = new JPanel();
        headerTitle.setBackground(backgroundColor);
        headerTitle.setMaximumSize(new Dimension(32767, 40));
        headerTitle.setMinimumSize(new Dimension(664, 40));
        FlowLayout titleContainerFlowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        titleContainerFlowLayout.setAlignOnBaseline(true);
        headerTitle.setLayout(titleContainerFlowLayout);

        setUpIcon();
        setUpDisplayedNumber();
        setUpDisplayedTitle();

        this.add(headerTitle, BorderLayout.NORTH);

    }

    private void setUpIcon(){
        arrowIcon = new JLabel();
        arrowIcon.setHorizontalAlignment(SwingConstants.CENTER);
        arrowIcon.setIcon(new ImageIcon(ARROW_ICON));
        arrowIcon.setToolTipText("");
        arrowIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        arrowIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        arrowIcon.setPreferredSize(new Dimension(20, 20));
        arrowIcon.setFocusable(true);
        
        //Adding, so Element can be visible focused.
        arrowIcon.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                arrowIcon.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            } 
            
            @Override
            public void focusLost(FocusEvent e) {
                arrowIcon.setBorder(null);
            }
        });
        arrowIcon.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "OpenCloseHeader");
        arrowIcon.getActionMap().put("OpenCloseHeader", new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isOpen){
                    System.out.print("Closing the Header now");
                    isOpen = false;
                }
                else{
                    System.out.print("Opening the Header now");
                    isOpen = true;
                
                }
            }
            
        });

        //TODO : keystrokes mit enter für focus
        //TODO: auslagern, da es doch sehr viel wird
        //TODO: rausfinden, ob Listener und Actions immer ne eigene Klasse brauchen sollten.
        //https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html

        

        headerTitle.add(arrowIcon);
    }

    private void setUpDisplayedNumber(){
        displayedNumber = new JLabel();
        displayedNumber.setFont(displayedNumber.getFont());
        displayedNumber.setText("Displayed Number");
        headerTitle.add(displayedNumber);
    }

    private void setUpDisplayedTitle(){
        displayedHeaderTitle = new JLabel();
        displayedHeaderTitle.setFont(displayedHeaderTitle.getFont());
        displayedHeaderTitle.setText("DisplayedHeaderTitle");
        displayedHeaderTitle.setMaximumSize(new Dimension(500, 16));
        displayedHeaderTitle.setMinimumSize(new Dimension(500, 16));
        displayedHeaderTitle.setPreferredSize(new Dimension(500, 16));

        displayedHeaderTitle.setFocusable(true);
        displayedHeaderTitle.addFocusListener(new FocusAdapter(){
            @Override
                public void focusGained(FocusEvent e) {
                    displayedHeaderTitle.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                } 

                @Override
                public void focusLost(FocusEvent e) {
                    displayedHeaderTitle.setBorder(null);
                }
        });

        headerTitle.add(displayedHeaderTitle);



        JMenuItem editHeaderTitle = new JMenuItem("Edit Menue Item");

    }

    private void setUpContent(){
        headerContent = new JPanel();
        headerContentTextArea = new JTextArea(); 
        headerContentScrollPane = new JScrollPane();

        //TextContent of the Header
        headerContent.setBackground(backgroundColor);
        headerContent.setMaximumSize(new Dimension(32767, 160));
        headerContent.setMinimumSize(new Dimension(100, 160));
        headerContent.setPreferredSize(new Dimension(712, 160));
        headerContent.setLayout(new BorderLayout());

        headerContentTextArea.setEditable(false);
        headerContentTextArea.setBackground(backgroundColor);
        headerContentTextArea.setColumns(20);
        headerContentTextArea.setLineWrap(true);
        headerContentTextArea.setRows(5);
        headerContentTextArea.setText("Text of the Header");
        headerContentTextArea.setMaximumSize(new Dimension(2147483647, 150));

        headerContentScrollPane.setViewportView(headerContentTextArea);

        headerContent.add(headerContentScrollPane, BorderLayout.CENTER);
        this.add(headerContent,BorderLayout.CENTER);

    }

    public void foldContainer(){
        this.setMaximumSize(new Dimension(2147483647,HEADERCONTAINER_FOLDED_HEIGHT));
    }

    public void unfoldContainer(){
        this.setMaximumSize(new Dimension(2147483647, HEADERCONTAINER_UNFOLDED_HEIGHT));
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean b) {
        this.isOpen = isOpen;
    }
}
