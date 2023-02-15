package main.java.visualComponents.Header;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
public class HeaderComponent extends JPanel{

    JPanel headerTitle;
    JLabel arrowIcon;

    JLabel displayedNumber;
    JTextField displayedNumberEdit;
    
    JLabel displayedHeaderTitle;
    JTextField displayedHeaderTitleEdit;

    JPanel headerContent;
    TextArea textArea;

    Color backgroundColor;
    final Color EDIT_COLOR = new Color(242,242,242);
    final int HEADERCONTAINER_FOLDED_HEIGHT = 40;
    final int HEADERCONTAINER_UNFOLDED_HEIGHT = 200;

    boolean isOpen;
    
    public HeaderComponent(Color backgroundColor, boolean isOpen){
        this.setMaximumSize(new Dimension(2147483647, HEADERCONTAINER_FOLDED_HEIGHT));
        this.setMinimumSize(new Dimension(275, HEADERCONTAINER_FOLDED_HEIGHT));
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

        //Icon adding
        Icon icon = new Icon(this);
        headerTitle.add(icon);

        setUpDisplayedNumber();
        
        //Title adding
        Title displayedHeaderTitle = new Title(this);
        headerTitle.add(displayedHeaderTitle);

        this.add(headerTitle, BorderLayout.NORTH);

    }

    private void setUpDisplayedNumber(){
        displayedNumber = new JLabel();
        displayedNumber.setFont(displayedNumber.getFont());
        displayedNumber.setText("Displayed Number");
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
}
