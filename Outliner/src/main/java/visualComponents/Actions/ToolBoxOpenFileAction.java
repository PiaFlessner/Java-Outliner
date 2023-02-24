package main.java.visualComponents.Actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import main.java.backendData.Header;
import main.java.visualComponents.MainFrame;

public class ToolBoxOpenFileAction extends AbstractAction {

    MainFrame parent;

    public ToolBoxOpenFileAction(MainFrame parent, String text, KeyStroke keystroke) {
        super(text);
        this.parent = parent;
        putValue(ACCELERATOR_KEY, keystroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {

                File selectedFile = getSelectedFile();
                if (selectedFile.exists() && getDialogType() == JFileChooser.SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "Do you want to dismiss the current file?",
                            "Current changes will be dissmissed",
                            JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                super.approveSelection();
            }
        };
        fileChooser.setDialogTitle("Specify a file to load");

        int userSelection = fileChooser.showOpenDialog(parent.getWindow());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();

            FileInputStream fileIn;
            try {
                fileIn = new FileInputStream(fileToLoad);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Object obj = objectIn.readObject();
                objectIn.close();
                Header newRoot = (Header) obj;
                parent.setRoot(newRoot);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(parent.getWindow(),
                        "File is not a valid datafile.",
                        "File could not be loaded",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
