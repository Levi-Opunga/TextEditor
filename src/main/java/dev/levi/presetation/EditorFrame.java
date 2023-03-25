package dev.levi.presetation;

import dev.levi.presetation.components.FileTree;
import org.apache.commons.io.IOUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.Language;
import org.openide.text.CloneableEditorSupport;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;

import static dev.levi.presetation.Main.generateFonts;

public class EditorFrame extends JFrame {

    private JPanel panel = new JPanel();
    private JMenuBar bar = new JMenuBar();
    private JMenu fileMenu = new JMenu();
    private JMenu view = new JMenu();
    private JMenu settings = new JMenu();
    private JMenu edit = new JMenu();
    private JMenu help = new JMenu();
    private JMenu viewMenu = new JMenu();
    private String fileName = "000000";

    private JEditorPane pane = new JEditorPane();

    private JScrollPane main = null;
    private JScrollPane sidebar = null;


    private JScrollPane scrollPane;
    private boolean previewHtml = false;
    private FileTree fileTree;

    RSyntaxTextArea textArea = new RSyntaxTextArea();
    RSyntaxDocument document = (RSyntaxDocument)textArea.getDocument();


    public EditorFrame getInstance() throws IOException {
        if (instance == null) {
            return new EditorFrame();
        } else {
            return instance;
        }

    }
    {

    }

    public EditorFrame(String fileName) throws HeadlessException, IOException {
        this.fileName = fileName;
        System.out.println("df6 " + fileName);
        initializeView(fileName);
        resizeEvent();
    }

    private EditorFrame instance;

    public EditorFrame() throws IOException {
        initializeView("000000");
        resizeEvent();
    }

    public void initializeView(String fileName) throws IOException {



        StyledDocument doc = new DefaultStyledDocument();
        doc.putProperty(Language.class, JavaTokenId.language());

        panel.setBackground(Color.black);

        //EditorKit kit = CloneableEditorSupport.getEditorKit("text/x-java");
        //pane.setEditorKit(kit);

        File file = new File(fileName == "" || fileName == null ? "./error.html" : fileName);
        if (fileName == "000000") {
            file = new File("./landing.html");
            fileName =  "./landing.html";
        }
        System.out.println(fileName);
        setUpPanel();
        pane.setForeground(Color.white);
        try {
            pane.setPage(file.toURI().toURL());
            fileName = file.toURI().toURL().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try {
//            SwingUtilities.invokeAndWait(new Runnable() {
//
//                public void run() {
//                    try (FileReader fileReader = new FileReader(file)) {
//
//                        String text = IOUtils.toString(fileReader);
//                        // Add the text to the document
//                        doc.insertString(0, text, null);
//
//                    } catch (BadLocationException | IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            System.out.println("Exception when constructing document: " + e);
//            System.exit(1);
//        }
        setSize(600, 600);
        pane.setPage(file.toURI().toURL());
        int height = getSize().height;
        int width = getSize().width;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

        RSyntaxDocument document = (RSyntaxDocument) textArea.getDocument();
        document.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setFont(Main.generateFonts(Main.jetbrains,15F));


        JScrollPane scrollPane = new JScrollPane(textArea);
        sidebar = new JScrollPane(fileTree);
        main = new JScrollPane(textArea);
      //  sidebar.setSize((int) (width * .2), height);
        sidebar.setBounds(0, 0, (int) (width * .2), height);
        main.setBounds((int) (width * .2), 0, (int) (width * .8), height);
        getContentPane().add(main);
        getContentPane().add(sidebar);
//        add(new JScrollPane(panel));
        configureMenu();
        setJMenuBar(bar);
        setVisible(true);
    }

    private void setClickLISTener() {

    }


    private void resizeEvent() {
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (main != null) {
                    int height = e.getComponent().getSize().height;
                    int width = e.getComponent().getSize().width;
                    setLayout(null);
                   // sidebar.setSize((int) (width * .2), height);
                    sidebar.setBounds(0, 0, (int) (width * .2), height);
                    main.setBounds((int) (width * .2), 0, (int) (width * .8), height);
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }


    private void configureMenu() {
        fileMenu.setText("File");
        view.setText("View");
        edit.setText("Edit");
        settings.setText("Settings");
        help.setText("Help");
        JMenu[] menus = {fileMenu, view, edit, settings, help};
        Arrays.stream(menus).forEach(item ->
                {
                    bar.add(item);
                    item.setFont(generateFonts(Main.droid,15f));
                }
        );

        JMenuItem newFile = new JMenuItem("New file");
        JMenuItem openExistingFile = new JMenuItem("Open file");
        JMenuItem openRecentFile = new JMenuItem("Recent Files");
        JMenuItem saveFile = new JMenuItem("Save");
        Action saveAction = new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(fileName);
                    FileWriter fr = new FileWriter(file);
                    fr.write(pane.getText());
                    fr.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveFile.setAction(saveAction);


        String key = "Referesh";


        saveFile.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), key);

        saveFile.getActionMap().put(key, saveAction);

        newFile.addActionListener(e -> new FileCreator().chooseDirectory()
        );

        JMenuItem htmlPreview = new JMenuItem("Preview Html");
        htmlPreview.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            htmlPreview();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }


        );

        openExistingFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("here");
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    try {
                        pane.setText(IOUtils.toString(new FileReader(f)));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String filepath = f.getPath();

                    fileName = filepath;
//                    try {
//                        BufferedReader br = new BufferedReader(new FileReader(filepath));
//                        String s1 = "", s2 = "";
//                        while ((s1 = br.readLine()) != null) {
//                            s2 += s1 + "\n";
//                        }
//                       // pane.setText(s2);
//                        fileName = filepath;
//                        br.close();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
                }
            }
        });
        saveFile.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        System.out.println(fileName);
                        try {
                            File file = new File(fileName);
                            FileWriter fr = new FileWriter(file);
                            fr.write(pane.getText());
                            fr.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
        );

        fileMenu.add(newFile);
        fileMenu.add(openExistingFile);
        fileMenu.add(openRecentFile);
        fileMenu.add(saveFile);

        view.add(htmlPreview);


    }

    private void htmlPreview() throws IOException {
        if (previewHtml == false) {
            if (fileName.contains("html") || fileName.contains("htm")) {
                pane.setContentType("text/html");
                HTMLEditorKit editorKit = new HTMLEditorKit();
                pane.setEditorKit(editorKit);

                File file = new File(fileName);
                pane.setPage(file.toURI().toURL());
                pane.setEditable(false);
                previewHtml=!previewHtml;
            }
        } else {
            pane.setContentType("text/plain");
            previewHtml=!previewHtml;
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            pane.setText(IOUtils.toString(reader));
            pane.setEditable(true);
        }
    }

    private void javaPresent(String javaFile) {
        StyledDocument doc = new DefaultStyledDocument();
        doc.putProperty(Language.class, JavaTokenId.language());

        EditorKit kit = CloneableEditorSupport.getEditorKit("text/x-java");
        pane.setEditorKit(kit);

        File file = new File(javaFile);
        try {
            pane.setPage(file.toURI().toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    try (FileReader fileReader = new FileReader(file)) {

                        String text = IOUtils.toString(fileReader);
                        // Add the text to the document
                        doc.insertString(0, text, null);

                    } catch (BadLocationException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Exception when constructing document: " + e);
            System.exit(1);
        }
        setSize(600, 600);
        pane.setDocument(doc);


    }
public void setUpPanel() {
    File file = new File("./file.html");
    fileTree = new FileTree(file.getAbsoluteFile().getParent());
    fileTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
            // Handle tree selection change event here
            System.out.println("tree "+e.getPath());
            String fullPath = file.getAbsoluteFile().getParent();
            for (int i = 1; i < e.getPath().getPath().length; i++){
                fullPath=fullPath+"/"+e.getPath().getPath()[i].toString();
            }
            try {
                File file = new File(fullPath);
                if (!   file.isDirectory()) {
                FileReader fr = new FileReader(file);
                pane.setText(IOUtils.toString(fr));
                fr.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println(fullPath);
            File file = new File(e.getPath().toString());
            fileName =fullPath;

            TreePath selectedPath = e.getPath();
            Object selectedNode = selectedPath.getLastPathComponent();
            // ...
        }
    });
    panel.add(fileTree);
}
}
