package visualComponents;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backendData.Header;

public class HeaderComponent extends JPanel{

    JPanel HeaderTitle;

    JLabel arrowIcon;
    JLabel arrowIconDown;

    JLabel displayedNumber;
    JTextField displayedNumberEdit;
    
    JLabel displayedHeaderTitle;
    JTextField displayedHeaderTitleEdit;


    JPanel HeaderContent;
    JScrollPane headerContentScrollPane;
    JTextArea headerContentTextArea;

    
    public HeaderComponent(){
   
    }
}
