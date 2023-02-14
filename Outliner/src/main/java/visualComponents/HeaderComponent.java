package main.java.visualComponents;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.backendData.Header;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import java.awt.Cursor;
import javax.swing.ImageIcon;

import java.io.File;

public class HeaderComponent extends JPanel{

    JLabel icon;

    JPanel headerTitle;

    JLabel arrowIcon;
    JLabel arrowIconDown;

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


    
    public HeaderComponent(Color backgroundColor){
        this.setMaximumSize(new Dimension(2147483647, 200));
        this.setMinimumSize(new Dimension(275, 40));
        this.setLayout(new BorderLayout());
        this.backgroundColor = backgroundColor;
        
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
        icon = new JLabel();
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setIcon(new ImageIcon("src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrow.png"));
        icon.setToolTipText("");
        icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        icon.setHorizontalTextPosition(SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(20, 20));
        headerTitle.add(icon);
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
        headerTitle.add(displayedHeaderTitle);

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
}
