package dev.levi.presetation;

import com.formdev.flatlaf.icons.FlatTreeCollapsedIcon;
import com.formdev.flatlaf.icons.FlatTreeExpandedIcon;
import dev.levi.presetation.components.DarkThemeFileChooser;
import dev.levi.presetation.components.FileTree;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.rsta.ac.perl.PerlLanguageSupport;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.autocomplete.*;
import org.fife.rsta.ac.LanguageSupportFactory;



import org.fife.ui.rtextarea.RTextScrollPane;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.java.platform.util.FileNameUtil;
import org.openide.text.CloneableEditorSupport;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
  //  TextEditorPane textArea = new TextEditorPane();


//    private JEditorPane textArea = new JEditorPane();

    private RTextScrollPane main = null;
    private JScrollPane sidebar = null;


   // private RTextScrollPane scrollPane;
    private boolean previewHtml = false;
    private JTree fileTree;

   RSyntaxTextArea textArea = new RSyntaxTextArea();
    //RSyntaxDocument document = (RSyntaxDocument)textArea.getDocument();


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
        //textArea.setEditorKit(kit);

        File file = new File(fileName == "" || fileName == null ? "./error.html" : fileName);
        if (fileName == "000000") {
            file = new File("./landing.html");
            fileName =  "./landing.html";
        }
        System.out.println(fileName);
        setUpPanel();
        textArea.setForeground(Color.white);

        try {
            textArea.setText(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
            fileName = file.toURI().toURL().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





        setSize(600, 600);
        textArea.setText(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
        int height = getSize().height;
        int width = getSize().width;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

        RSyntaxDocument document = (RSyntaxDocument) textArea.getDocument();
        // document.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        Color backgroundColor = UIManager.getColor("TextArea.background");
        Color foregroundColor = UIManager.getColor("TextArea.foreground");
        Color selectionColor = UIManager.getColor("TextArea.selectionBackground");
        Color caretColor = UIManager.getColor("TextArea.caretForeground");
        textArea.setBackground(backgroundColor);
        textArea.setForeground(foregroundColor);
        textArea.setSelectionColor(selectionColor);
        textArea.setCurrentLineHighlightColor(new Color(204, 204, 204));
        textArea.setCaretColor(caretColor);
        textArea.setFont(Main.generateFonts(Main.fira,20F));
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) { // Never happens
            ioe.printStackTrace();
        }

        sidebar = new JScrollPane(fileTree);
        main = new RTextScrollPane(textArea);
        main.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        main.setFoldIndicatorEnabled(true);

      //  sidebar.setSize((int) (width * .2), height);
        sidebar.setBounds(0, 0, (int) (width * .2), height);
        main.setBounds((int) (width * .2), 0, (int) (width * .8), height);
        getContentPane().add(main);
        getContentPane().add(sidebar);
//        add(new JScrollPane(panel));
        LanguageSupportFactory lsf = LanguageSupportFactory.get();
        JavaLanguageSupport support = (JavaLanguageSupport) lsf.
                getSupportFor(SyntaxConstants.SYNTAX_STYLE_JAVA);


        CompletionProvider provider = createCompletionProvider();

        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(textArea);
        support.setAutoActivationEnabled(true);
        support.install(textArea);
        configureMenu();
        setJMenuBar(bar);
        setVisible(true);
    }
    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

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
        settings.setText("Settings ");
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
                    fr.write(textArea.getText());
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

                    }
                }


        );

        openExistingFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("here");
                DarkThemeFileChooser fc = new DarkThemeFileChooser();

                    File f = new File(fc.chooseAnyFile(true));
                    try {
                        textArea.setText(IOUtils.toString(new FileReader(f)));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String filepath = f.getPath();
                    fileName = filepath;
                    setUpPanel();

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
                            fr.write(textArea.getText());
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


    private void presentationMode(String javaFile) {

        File file = new File(javaFile);

        try {
            textArea.setText(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setSize(10000, 10000);


    }
public void setUpPanel() {
    File file = new File(fileName);
    fileTree = new FileTree(file.getAbsoluteFile().getParent());
  //  JTree tree = new JTree(rootNode);

    // Customize the tree cell renderer to use FlatLaf icons
    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) fileTree.getCellRenderer();

    ImageIcon closed = new ImageIcon("./Images/folder.png");
    Image image = closed.getImage();
    Image newImg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    closed = new ImageIcon(newImg);

    ImageIcon open = new ImageIcon("./Images/open.png");
    Image image2 = open.getImage();
    Image newImg2 = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    open = new ImageIcon(newImg2);

    ImageIcon fileIcon = new ImageIcon("./Images/file.png");
    Image iconImage = fileIcon.getImage();
    Image image1 = iconImage.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
    fileIcon = new ImageIcon(image1);

    renderer.setOpenIcon(open);
    renderer.setClosedIcon(closed);
    renderer.setLeafIcon(fileIcon);
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
                textArea.setText(IOUtils.toString(fr));
                fr.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
          String ext = FilenameUtils.getExtension(fullPath);
            System.out.println(ext);
           // File file = new File(e.getPath().toString());
            fileName =fullPath;


            TreePath selectedPath = e.getPath();
            Object selectedNode = selectedPath.getLastPathComponent();
            // ...
        }
    });
    panel.add(fileTree);
}

public static void getCompletions(){

}

}
