package main.java.visualComponents.Header;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.io.File;
public class Icon extends JLabel{
    
    final String ARROW_ICON = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrow.png";
    final String ARROW_ICON_DOWN = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"arrowDown.png";
    final int iconSize = 25;
    ImageIcon arrowOpen;
    ImageIcon arrow;


    public Icon(){
        ImageIcon icon = new ImageIcon(ARROW_ICON);
        Image img = icon.getImage();
        img = img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        arrow = new ImageIcon(img);

        icon = new ImageIcon(ARROW_ICON_DOWN);
        Image img2 = icon.getImage();
        img2 = img2.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        arrowOpen = new ImageIcon(img2);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setIcon(arrow);
        this.setToolTipText("");
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(20, 20));
    }

    /**
     * Changes the arrow to the open symbol.
     */
    public void setArrowOpen(){
        this.setIcon(this.arrowOpen);
    }

    /**
     * Changes the arrow to the closed symbol.
     */
    public void setArrowClose(){
        this.setIcon(this.arrow);
    }
}