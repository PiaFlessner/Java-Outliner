package main.java.visualComponents.Header;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.SwingConstants;
public class Icon extends JLabel{

    final int iconSize = 16;


    public Icon(){
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setToolTipText("");
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setPreferredSize(new Dimension(iconSize+5, iconSize+5));
        this.setFont(new Font(getFont().getName(), Font.PLAIN, iconSize));
    }

    /**
     * Changes the arrow to the open symbol.
     */
    public void setArrowOpen(){
        this.setText("▼");
    }

    /**
     * Changes the arrow to the closed symbol.
     */
    public void setArrowClose(){
        this.setText("►");
    }
}