package main.java.visualComponents.Actions;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
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

        int userSelection = fileChooser.showSaveDialog(this.mainFrame.getWindow());

        if(userSelection ==JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();            
            HeaderConverter converter = new HeaderConverter();
            converter.saveMD(this.mainFrame.getRoot(), 0, fileToSave.getAbsolutePath());
        }
    }    
}
