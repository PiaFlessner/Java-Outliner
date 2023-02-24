package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import main.java.backendData.Header;

public class ToolBoxSaveFileAction extends AbstractAction {

    JFrame parentFrame;
    Header header;
    File file;

    public ToolBoxSaveFileAction(Header header, JFrame parentFrame, String text, KeyStroke keystroke){
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
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();            
            
            //Save File here
            try {
                FileOutputStream fileOut = new FileOutputStream(fileToSave,false);
                ObjectOutputStream objectOut;
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(header);
                objectOut.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } 
        }
    }    
}
