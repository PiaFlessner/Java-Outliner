package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import main.java.backendData.Header;
import main.java.backendData.HeaderConverter;

public class ToolBoxExportMDAction extends AbstractAction {

    JFrame parentFrame;
    Header header;

    public ToolBoxExportMDAction(Header header, JFrame parentFrame, String text, KeyStroke keystroke){
        super(text);
        this.header = header;
        this.parentFrame = parentFrame;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            HeaderConverter converter = new HeaderConverter();
            converter.saveMD(this.header, 0, fileToSave.getAbsolutePath());
        }
    }    
}
