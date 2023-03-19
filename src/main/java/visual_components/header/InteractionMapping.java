package main.java.visual_components.header;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public enum InteractionMapping {
    DELETE("Delete header", KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK), "deleteHeader"),
    SHIFT_UP("Shift header up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK), "shiftOneUp"),
    SHIFT_DOWN("Shift header down", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK), "shiftOneDown"),
    SHIFT_LEVEL_UP("Shift header level up", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK), "shiftLevelUp"),
    SHIFT_LEVEL_DOWN("shift header level down", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK), "shiftLevelDown"),
    ADD_UP("Add header before", KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.ALT_DOWN_MASK), "addBefore"),
    ADD_DOWN("Add header after", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.ALT_DOWN_MASK), "addAfter"),
    ADD_SUB("Add subheader", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK), "addSub"),
    TOOLBOX_SHOW_HIDE("Show Toolbox", KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "showHideToolbar"),
    OPEN_FILE("Open File", KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "openFile"),
    SAVE_FILE("Save File", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "saveFile"),
    ADD_HEADER_ROOT("Add header to root", KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), "addHeaderToRoot"),
    EXPORT_MD("Export to Markdown", KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "exportToMarkdown"),
    NEW_FILE("New File", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "newFile");
 

    private final String actionName;
    private final KeyStroke keystroke;
    private final String actionMapKey;

    InteractionMapping(String actionName, KeyStroke keystroke, String actionMapKey){
        this.actionName = actionName;
        this.keystroke = keystroke;
        this.actionMapKey = actionMapKey;
    }

    public String getActionName() {
        return actionName;
    }

    public KeyStroke getKeystroke() {
        return keystroke;
    }

    public String getActionMapKey() {
        return actionMapKey;
    }

    
}
