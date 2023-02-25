package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import main.java.visualComponents.MainFrame;

public class ToolBoxSaveFileAction extends AbstractAction {

    MainFrame mainFrame;
    File file;

    public ToolBoxSaveFileAction(MainFrame mainframe, String text, KeyStroke keystroke){
        super(text);
        this.mainFrame = mainframe;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(){

            //Ask to overwrite action
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

        int userSelection = fileChooser.showSaveDialog(mainFrame.getWindow());

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();            
            
            //Save File here
            try {
                FileOutputStream fileOut = new FileOutputStream(fileToSave,false);
                ObjectOutputStream objectOut;
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(mainFrame.getRoot());
                objectOut.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } 
        }
    }    
}
