package main.java.visualComponents.Header;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.io.File;
public class Icon extends JLabel{
    
    final String ARROW_ICON = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrow.png";
    final String ARROW_ICON_DOWN = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrowDown.png";
    final ImageIcon arrowOpen = new ImageIcon(ARROW_ICON_DOWN);
    final ImageIcon arrow = new ImageIcon(ARROW_ICON);


    public Icon(){
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setIcon(new ImageIcon(ARROW_ICON));
        this.setToolTipText("");
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(20, 20));
    }

    public void setArrowOpen(){
        this.setIcon(this.arrowOpen);
    }

    public void setArrowClose(){
        this.setIcon(this.arrow);
    }
}