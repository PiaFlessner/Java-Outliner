package main.java.visualComponents.Header;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.java.backendData.Header;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.event.MouseInputAdapter;

public class HeaderComponent extends JPanel{

    JPanel headerTitle;
    Icon icon;

    JLabel displayedNumber;
    JTextField displayedNumberEdit;
    
    Title displayedHeaderTitle;
    JTextField displayedHeaderTitleEdit;

    JPanel headerContent;
    TextArea textArea;

    Color backgroundColor;
    final Color EDIT_COLOR = new Color(242,242,242);
    final int HEADERCONTAINER_FOLDED_HEIGHT = 40;
    final int HEADERCONTAINER_UNFOLDED_HEIGHT = 200;
    final Color FOCUS_COLOR = new Color(214,220,229);

    boolean isOpen;
    Header connectedHeader;
    
    public HeaderComponent(Color backgroundColor, boolean isOpen, Header connectedHeader){
        this.setMaximumSize(new Dimension(2147483647, HEADERCONTAINER_FOLDED_HEIGHT));
        this.setMinimumSize(new Dimension(275, HEADERCONTAINER_FOLDED_HEIGHT));
        this.setLayout(new BorderLayout());
        this.backgroundColor = backgroundColor;
        this.isOpen = isOpen;
        this.connectedHeader = connectedHeader;
        
        setUpHeaderTitle();
        setUpContent();
        this.setFocusable(true);
        addFocusingFunction();
        setUpOpenHeaderFunction();
   
    }

    private void setUpHeaderTitle(){
        headerTitle = new JPanel();
        headerTitle.setBackground(backgroundColor);
        headerTitle.setMaximumSize(new Dimension(32767, 40));
        headerTitle.setMinimumSize(new Dimension(664, 40));
        FlowLayout titleContainerFlowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        titleContainerFlowLayout.setAlignOnBaseline(true);
        headerTitle.setLayout(titleContainerFlowLayout);

        //Icon adding
        icon = new Icon();
        headerTitle.add(icon);

        setUpDisplayedNumber();

        //Title adding
        displayedHeaderTitle = new Title(this);
        headerTitle.add(displayedHeaderTitle);
        this.add(headerTitle, BorderLayout.NORTH);
    }

    private void setUpDisplayedNumber(){
        displayedNumber = new JLabel();
        displayedNumber.setFont(displayedNumber.getFont());
        displayedNumber.setText(this.connectedHeader.getLabelNr());
        headerTitle.add(displayedNumber);
    }

    private void setUpContent(){
        headerContent = new JPanel();
        //TextContent of the Header
        headerContent.setBackground(backgroundColor);
        headerContent.setMaximumSize(new Dimension(32767, 160));
        headerContent.setMinimumSize(new Dimension(100, 160));
        headerContent.setPreferredSize(new Dimension(712, 160));
        headerContent.setLayout(new BorderLayout());
       
        textArea = new TextArea(this);
        headerContent.add(textArea, BorderLayout.CENTER);

        this.add(headerContent,BorderLayout.CENTER);

    }

    private void openClose(){
        if(this.isOpen){
            //Close Header
            icon.setArrowClose();
            this.isOpen = false;
            this.setMaximumSize(new Dimension(2147483647,this.HEADERCONTAINER_FOLDED_HEIGHT));
            this.textArea.setVisible(false);
            this.textArea.textArea.setFocusable(false);
        }
        else{
            //Open Header
            icon.setArrowOpen();
            this.isOpen = true;
            this.setMaximumSize(new Dimension(2147483647, this.HEADERCONTAINER_UNFOLDED_HEIGHT));
            this.textArea.setVisible(true);
            this.textArea.textArea.setFocusable(true);
        }
    }

    private void setUpOpenHeaderFunction(){
        //KeyStroke Function
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "OpenCloseHeader");
        this.getActionMap().put("OpenCloseHeader", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openClose();
                System.out.println("HallO");
            }
        });

        //Button Click Function
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                openClose();
                System.out.println("HallO");
            }           
        });
    }

    private void addFocusingFunction(){
        this.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                headerTitle.setBackground(FOCUS_COLOR);
            } 

            @Override
            public void focusLost(FocusEvent e) {
                headerTitle.setBackground(backgroundColor);
            }
        });
    }
}
