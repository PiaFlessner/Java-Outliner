package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.java.backendData.HeaderConverter;
import main.java.visualComponents.MainFrame;

public class ToolBoxExportMDAction extends AbstractAction {

  MainFrame mainFrame;

    public ToolBoxExportMDAction(MainFrame mainFrame, String text, KeyStroke keystroke){
        super(text);
        this.mainFrame = mainFrame;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(){
            @Override
            public void approveSelection() {

                File selectedFile = getSelectedFile();
                File withEnding = new File(HeaderConverter.rightName(selectedFile.getAbsolutePath()));
                if (withEnding.exists() && getDialogType() == JFileChooser.SAVE_DIALOG)
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

        int userSelection = fileChooser.showSaveDialog(mainFrame.getWindow());

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();            
            HeaderConverter converter = new HeaderConverter();
            converter.saveMD(mainFrame.getRoot(), 0, fileToSave.getAbsolutePath());
        }
    }    
}
