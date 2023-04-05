# 1 Java-Outliner

The application enables the quick creation of outlines. This includes adding, editing, moving and deleting headings, and subheadings. In addition, the application provides saving and loading of created files as well as exporting to Markdown.
All functionalities are provided by drag-and-drop, context menu and/or keyboard operation.
Java 8 was used as the programming language and the Swing library was used. The development was predominantly object-oriented.

By the way, this README was created with this program =)

# 2 Minimum Requirements



## 2.1 Execution

1. MacOS (v. 10) or Linux (tested on Ubuntu 22.04 and Fedora v. 37) or Windows (tested on Win 10).
2. Java 8 RE

## 2.2 Build

1. Java SE Development Kit 8u202 
2. Favorite Java Build Tool (if install script is not used)

# 3 Build / Install



## 3.1 MacOS/Linux

Execute the following commands from the Java-Outliner directory:
```
chmod +x install_MacOS_Linux.sh
bash install_MacOS_Linux.sh
```
INFO: Java needs to be set in the PATH environment variable.

## 3.2 Windows

Execute the following commands from the Java-Outliner directory:
```
install_Windows.sh
```
INFO: Java needs to be set in the PATH environment variable.

# 4 Execution

Execute the generated jar file with the following command:
```
java -jar Java-Outliner.jar
```

Or double click on the jar icon.

# 5 Get to Work

 

## 5.1 Create first Header

To create the first header, click on the big plus button, or press CTRL-A.

## 5.2 ToolBox

The toolbox is located at the top of the window and can be made visible or invisible by pressing CTRL+V.

### 5.2.1 New File

To reset the current file, you can click the "New File" button or CTRL+N.

This action will delete all the headers already created at once.

### 5.2.2 Open File

To open an already saved file, you can click the "Open File" button or use the CTRL-O keyboard shortcut.

### 5.2.3 Save

To save a created file, the "Save" button can be clicked or CTRL-S can be pressed.

### 5.2.4 +

Inserts a new header at the first level.

### 5.2.5 Export to Markdown

Exports a created outline to a Markdown file. Each header forms a separate heading with subheadings and the content.

## 5.3 Header

 

### 5.3.1 Navigation

The down and up arrow buttons can be used to quickly navigate through the created headers by focus. Alternatively, the usual tab navigation is also available.

Of course, you can also navigate over the headers using the mouse.

### 5.3.2 Title

To edit the title, you can press Enter while focusing, or double-click on the text box.
The way out of the edit mode is the same.

### 5.3.3 Content

To access the content of a header, you can click the button to the left of the header or alternatively press the space bar while focusing. The content can be closed again in the same way.
The content can be used like a common text field.

### 5.3.4 Child-Header

The child headers can be opened by arrow-right and closed by arrow-left during focus. With the mouse this can be realized with a simple click on the header.

### 5.3.5 Operations

 

#### 5.3.5.1 Adding

To insert a header between the existing headers, you can right-click on a header and perform the Add operations there.
These include:
1. "Add header before"- inserts a header before the current header. (keyboard shortcut: CTRL+UP)
2. "add header after" - adds a header after the current header. (keyboard shortcut: CTRL+DOWN).
3. "add header sub" - adds a header as a child header to the header. (Keyboard shortcut: CTRL+RIGHT)

#### 5.3.5.2 Shifting

To move a header, this can be accomplished via context menu, drag-and-drop, and keyboard shortcuts.

The drag-and-drop functions appear when you drag one header onto another. The first button from the left adds the dragged header above the target header, the second below and the third as a child header.
By context menu the following options exist:
1. "Shift header up"-shifts a header up by one index, as far as possible. (Keyboard shortcut: SHIFT+UP).
2. "Shift header down" - shifts a header one index down, if possible. (keyboard shortcut: SHIFT+DOWN).
3. "Shift header level up" - shifts a header one level up, if possible. (Keyboard shortcut: SHIFT+LEFT)
4. "Shift header level down" - shifts a header one level down, if possible. (Keyboard shortcut: SHIFT+RIGHT)

#### 5.3.5.3 Deleting

To delete a header, you can do this using the context menu or the keyboard shortcut CTRL+BACK.

## 5.4 Uninstall

To uninstall the program, you can simply delete Java-Outliner.jar.

