package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        JFileChooser fileChooser = new JFileChooser(){
            @Override
            public void approveSelection() {

                File selectedFile = getSelectedFile();
                if (selectedFile.exists() && getDialogType() == JFileChooser.SAVE_DIALOG)
                {
                  int result = JOptionPane.showConfirmDialog(this,
                    "Do you want to overwrite?",
                    "File already exists",
                    JOptionPane.YES_NO_OPTION);
                  if (result != JOptionPane.YES_OPTION)
                  {
                    return;
                  }
                }
               
                
                super.approveSelection();
            }
        };
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MD Files", "md", "MD");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();            
            HeaderConverter converter = new HeaderConverter();
            converter.saveMD(this.header, 0, fileToSave.getAbsolutePath());
        }
    }    
}
