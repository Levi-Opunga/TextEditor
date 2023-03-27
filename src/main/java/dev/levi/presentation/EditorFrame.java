package dev.levi.presentation;

import dev.levi.Main;
import dev.levi.data.FileDaoImpl;
import dev.levi.domain.Files;
import dev.levi.presentation.components.DarkThemeFileChooser;
import dev.levi.presentation.components.FileTree;
import dev.levi.presentation.components.SwingHTMLBrowser;
import dev.levi.utils.FileEdit;
import dev.levi.utils.FileExtensionUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.fife.rsta.ac.c.CLanguageSupport;
import org.fife.rsta.ac.css.CssLanguageSupport;
import org.fife.rsta.ac.groovy.GroovyLanguageSupport;
import org.fife.rsta.ac.html.HtmlLanguageSupport;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.jsp.JspLanguageSupport;
import org.fife.rsta.ac.perl.PerlLanguageSupport;
import org.fife.rsta.ac.php.PhpLanguageSupport;
import org.fife.rsta.ac.sh.ShellLanguageSupport;
import org.fife.rsta.ac.xml.XmlLanguageSupport;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.autocomplete.*;
import org.fife.rsta.ac.LanguageSupportFactory;


import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;


import static dev.levi.Main.generateFonts;

public class EditorFrame extends JFrame implements ActionListener, WindowListener {

    private JPanel panel = new JPanel();
    private JMenuBar bar = new JMenuBar();
    private JMenu fileMenu = new JMenu();
    private JMenu view = new JMenu();
    private JMenu settings = new JMenu();
    private JMenu features = new JMenu();
    private JMenu help = new JMenu();
    private JMenu viewMenu = new JMenu();
    private JLabel tab = new JLabel();
    public static int openWindowCount = 0;

    public static String fileName = "./";
    public static String folderName = "./";
    //  TextEditorPane textArea = new TextEditorPane();
    public static ImageIcon openFolderIcon = Main.images.get("open");
    //generateImageIcon(new ImageIcon("/images/open.png"));

    public static ImageIcon fileIcon = Main.images.get("file");
    public static ImageIcon recentIcon = Main.images.get("recent");
    public static ImageIcon copyIcon = Main.images.get("copy");
    public static ImageIcon onIcon = Main.images.get("off-button");
    public static ImageIcon offIcon = Main.images.get("on-button");
    public static ImageIcon closedFolderIcon = Main.images.get("folder");
    public static ImageIcon settingsIcon = Main.images.get("settings");
    public static ImageIcon deleteIcon = Main.images.get("delete");
    public static ImageIcon saveIcon = Main.images.get("save");
    public static ImageIcon helpIcon = Main.images.get("help");
    public static ImageIcon previewIcon = Main.images.get("preview");
    public static ImageIcon webIcon = Main.images.get("browser");
    private static int windowCount = 0;
    private FileDaoImpl dao = dao = new FileDaoImpl();
    List<Files> recentlyOpenedFiles = (List<Files>) dao.findAllFiles();

    public static List<String> editorWindows = new ArrayList<>();


    public EditorFrame(Action action) {

    }

    {
        java.util.Timer timer = new java.util.Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    File file = new File(fileName);
                    if (file.exists() && !file.isDirectory()) {
                        FileWriter fr = new FileWriter(file);
                        fr.write(textArea.getText());
                        fr.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    JMenuItem sidebarMenuItem;
    private RTextScrollPane main = null;
    private JScrollPane sidebar = null;


    // private RTextScrollPane scrollPane;
    private boolean previewHtml = false;
    private JTree fileTree;
    public static String previousWindow = "";


    RSyntaxTextArea textArea = new RSyntaxTextArea();
    //RSyntaxDocument document = (RSyntaxDocument)textArea.getDocument();


    public EditorFrame getInstance(String name, String folderName, int width, int height) throws IOException {
        if (instance == null) {
            EditorFrame editorFrame = new EditorFrame(name, folderName);

            openWindowCount++;

            editorFrame.setSize(width, height);
            return editorFrame;

        } else {
            instance.setSize(width, height);
            return instance;
        }

    }


    public EditorFrame(String fileName, String folderName) throws HeadlessException, IOException {
        this.fileName = fileName;
        this.folderName = folderName;
        this.setTitle("Text Editor " + (openWindowCount + 1));
        editorWindows.add(getTitle());

        System.out.println("constructor with file " + fileName);
        initializeView(fileName);
        resizeEvent();
        openWindowCount++;

    }

    private EditorFrame instance;

    public EditorFrame() throws IOException {
        initializeView("App.java");
        resizeEvent();
        openWindowCount++;

    }

    public void initializeView(String fileName) throws IOException {
        // Add window listener to decrease count when the frame is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                openWindowCount--;
                if (openWindowCount == 0) {
                    System.exit(0); // Exit only when there are no more open windows
                }
            }
        });

        panel.setBackground(Color.black);

        //TODO() recent / new
        File file = new File(fileName);
//        if (fileName == "000000") {
//            file = new File("./landing.html");
//            fileName = "./landing.html";
//        }
        // System.out.println(fileName);
        setUpPanel();


        setSize(600, 600);
        if (
                !file.isDirectory()
        ) {
            textArea.setText(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            System.out.println(ext);
            getSyntaxCompletions(ext);
        }

        int height = getSize().height;
        int width = getSize().width;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        System.out.println("hidden " + windowCount);


        setLayout(null);

        textArea.setCodeFoldingEnabled(true);


        textArea.setFont(Main.generateFonts(Main.droid, 20F));
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) { // Never happens
            ioe.printStackTrace();
        }

        sidebar = new JScrollPane(fileTree);
        main = new RTextScrollPane(textArea, true, Color.ORANGE);
        main.setHorizontalScrollBarPolicy(RTextScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        main.setFoldIndicatorEnabled(true);
        main.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //  sidebar.setSize((int) (width * .2), height);
        tab.setBounds((int) (width * .2), 0, (int) (width * .8), 30);
        tab.setFont(Main.generateFonts(Main.jetbrains, 12f));
        tab.setText(fileName);
        tab.setIcon(fileIcon);
        sidebar.setBounds(0, 0, (int) (width * .2), height);
        main.setBounds((int) (width * .2), 30, (int) (width * .8), height - 30);
        getContentPane().add(main);
        getContentPane().add(sidebar);
        getContentPane().add(tab);
//        add(new JScrollPane(panel));

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


    private void resizeEvent() {
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (main != null) {
                    int height = e.getComponent().getSize().height;
                    int width = e.getComponent().getSize().width;
                    resizeHelper(height, width);
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

    private void resizeHelper(int height, int width) {
        if (!sidebar.isVisible()) {

            tab.setBounds(0, 0, width, 30);
            sidebarMenuItem.setIcon(onIcon);
            //sidebar.setBounds(0, 0, (int) (width * .2), height);
            main.setBounds(0, 30, width, height - 30);

        } else {

            // sidebar.setSize((int) (width * .2), height);
            tab.setBounds((int) (width * .2), 0, (int) (width * .8), 30);

            sidebar.setBounds(0, 0, (int) (width * .2), height);
            main.setBounds((int) (width * .2), 30, (int) (width * .8), height - 30);
            sidebarMenuItem.setIcon(offIcon);
        }
    }


    private void configureMenu() {

        fileMenu.setText("File");
        view.setText("View");
        features.setText("Features");

        help.setText("Help");
        JMenu[] menus = {fileMenu, view, features, help};
        Arrays.stream(menus).forEach(item ->
                {
                    bar.add(item);
                    item.setFont(generateFonts(Main.droid, 15f));
                }
        );

        JMenu settings = new JMenu("Settings");
        JMenu appTheme = new JMenu("App Theme");
        settings.add(appTheme);
        JMenuItem light = new JMenuItem("Light");
        JMenuItem dark = new JMenuItem("Dark");
        appTheme.add(light);
        appTheme.add(dark);
        light.addActionListener((actionEvent -> {
            Timer timer = new Timer(1000, e -> {
                Main.setUpTheme(false);

                dispose();
                try {
                    getInstance(fileName, folderName, getWidth(), getHeight());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        ));

        dark.addActionListener((actionEvent -> {
            Timer timer = new Timer(1000, e -> {
                Main.setUpTheme(true);

                dispose();
                try {
                    getInstance(fileName, folderName, getWidth(), getHeight());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }));


        JMenuItem delete = new JMenuItem("Delete File");
        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file = new File(fileName);
                if (!file.exists()) {
                    return;
                }
                int sure = JOptionPane.showConfirmDialog(Frame.getFrames()[Frame.getFrames().length - 1], "Are sure you want to delete " + file.getName());
                if (sure == JOptionPane.YES_OPTION) {
                    file.delete();
                }

                //new File(fileName);

                fileName = folderName;

                try {
                    EditorFrame editorFrame = new EditorFrame(fileName, folderName);
                    editorFrame.setSize(getWidth(), getHeight());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();

            }
        });
        JMenuItem web = new JMenuItem("Browser");
        web.setIcon(webIcon);
        web.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingHTMLBrowser swingHTMLBrowser = new SwingHTMLBrowser("https://google.com");

            }
        });
        delete.setIcon(deleteIcon);
        web.setIcon(webIcon);
        features.add(web);
        settings.setIcon(settingsIcon);
        JMenu editorStyle = new JMenu("Editor Colors");
        settings.add(editorStyle);
        JMenuItem darktheme = new JMenuItem("Dark");
        JMenuItem defaulttheme = new JMenuItem("Default");
        JMenuItem defaultalt = new JMenuItem("Default 2");
        JMenuItem druid = new JMenuItem("Druid");
        JMenuItem eclipse = new JMenuItem("Eclipse");
        JMenuItem idea = new JMenuItem("Idea");
        JMenuItem monokai = new JMenuItem("Monokai");
        JMenuItem vscode = new JMenuItem("Vscode");
        darktheme.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/dark.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        defaulttheme.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/default.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        defaultalt.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/default-alt.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        druid.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/druid.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        idea.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        monokai.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        vscode.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/vs.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });
        eclipse.addActionListener(e -> {
            try {
                Theme theme = Theme.load(getClass().getResourceAsStream(
                        "/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml"));
                theme.apply(textArea);
            } catch (IOException ioe) { // Never happens
                ioe.printStackTrace();
            }

        });

        sidebarMenuItem = new JMenuItem("Toggle Sidebar");
        sidebarMenuItem.addActionListener((e) -> {
            sidebar.setVisible(!sidebar.isVisible());
            int width = getWidth();
            int height = getHeight();
            resizeHelper(height, width);
        });
        view.add(sidebarMenuItem);
        JMenuItem newFile = new JMenuItem("New file");
        newFile.setIcon(fileIcon);
        JMenuItem openExistingFile = new JMenuItem("Open file");
        openExistingFile.setIcon(fileIcon);
        JMenuItem openFolder = new JMenuItem("Open folder");
        openFolder.setIcon(closedFolderIcon);
        JMenu openRecentFile = new JMenu("Recent Files");
        openRecentFile.setIcon(recentIcon);
        JMenuItem reademe = new JMenuItem("Guide");
        reademe.setIcon(helpIcon);
        reademe.addActionListener(e ->
        {
            URI uri = null;
            try {
                uri = new URI("https://github.com/Levi-Opunga/TextEditor");
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }

            // Use the Desktop class to open the URI in the user's default browser
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        help.add(reademe);


        List<JMenuItem> items = recentlyOpenedFiles.stream().map(e -> new JMenuItem(e.getPath())).toList();
        items.forEach(openRecentFile::add);

        for (int i = 0; i < items.size(); i++) {
            int finalI = i;

            items.get(i).addActionListener(e -> {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            dao.updateFile(recentlyOpenedFiles.get(finalI));
                            new EditorFrame(recentlyOpenedFiles.get(finalI).getName(), recentlyOpenedFiles.get(finalI).getPath());

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                thread.start();

            });

        }


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
        editorStyle.add(defaulttheme);
        editorStyle.add(defaultalt);
        editorStyle.add(darktheme);
        editorStyle.add(druid);
        editorStyle.add(eclipse);
        editorStyle.add(idea);
        editorStyle.add(monokai);
        editorStyle.add(vscode);

        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveFile.setAction(saveAction);


        String key = "Referesh";


        saveFile.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), key);

        saveFile.getActionMap().put(key, saveAction);

        newFile.addActionListener(e -> {
                    //  int previousframeCount = windowCount;
                    FileCreator.chooseDirectory(folderName, getWidth(), getHeight());
                    previousWindow = getTitle();

                }
        );

        JMenuItem htmlPreview = new JMenuItem("Preview Html");
        htmlPreview.setIcon(previewIcon);
        htmlPreview.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Thread thread = new Thread(() -> new SwingHTMLBrowser(fileName));
                        thread.start();
                    }
                }


        );

        openExistingFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // DarkThemeFileChooser fc = new DarkThemeFileChooser();


                String pathname = DarkThemeFileChooser.chooseAnyFile(true, folderName);
                if (pathname == null){
                    return;
                }
                File f = new File(pathname);

                fileName = f.getPath();
                folderName = f.getParent();
                Thread thread = new Thread(() -> {
                    try {
                        dao.createFile(new Files(0, fileName, fileName, System.currentTimeMillis()));

                        EditorFrame frame = new EditorFrame(fileName, folderName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    System.out.println("Thread Running");
                });
                thread.start();
//                executor.submit(thread);
//                System.out.println("here  " + fileName);
                setUpPanel();

            }

        });
        openFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // DarkThemeFileChooser fc = new DarkThemeFileChooser();


                String pathname = DarkThemeFileChooser.chooseAnyFile(true, folderName);
                if (pathname == null){
                    return;
                }
                File f = new File(pathname);
                if (f.getPath() == fileName) {
                    return;
                }
                fileName = f.getPath();
                folderName = f.getPath();

                Thread thread = new Thread(() -> {
                    try {
                        dao.createFile(new Files(0, fileName, fileName, System.currentTimeMillis()));

                        EditorFrame frame = new EditorFrame(fileName, folderName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    System.out.println("Thread Running");
                });
                thread.start();
//                executor.submit(thread);
//                System.out.println("here  " + fileName);
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
        JMenuItem exit = new JMenuItem("Close");
        exit.addActionListener(e -> System.exit(0));
        saveFile.setIcon(saveIcon);
        fileMenu.add(newFile);
        fileMenu.add(openExistingFile);
        fileMenu.add(openFolder);
        fileMenu.add(settings);
        fileMenu.add(openRecentFile);
        fileMenu.add(saveFile);
        fileMenu.add(delete);
        fileMenu.add(exit);


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
        File file = new File(folderName);

//        if (file.isDirectory()) {
        fileTree = new FileTree(file.toURI().getPath());

//        } else {
//            fileTree = new FileTree(file.getAbsoluteFile().getParent());
//        }
        //  JTree tree = new JTree(rootNode);
        // System.out.println();
        System.out.println("setiing up files tree " + file.getAbsoluteFile().getParent());
        // Customize the tree cell renderer to use FlatLaf icons
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) fileTree.getCellRenderer();


        renderer.setOpenIcon(openFolderIcon);
        renderer.setClosedIcon(closedFolderIcon);
        renderer.setLeafIcon(fileIcon);

        if (!file.isDirectory()) {
            fileTree.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem copy = new JMenuItem("Copy Path");
                        copy.setIcon(copyIcon);

                        popupMenu.add(copy);
                        JMenuItem delete = new JMenuItem("Delete File");
                        //  JMenuItem web = new JMenuItem("Browser");
                        delete.setIcon(deleteIcon);
                        popupMenu.add(delete);
                        String full = file.getAbsoluteFile().getParent();
                        int row = fileTree.getClosestRowForLocation(e.getX(), e.getY());
                        fileTree.setSelectionRow(row);

                        // TreePath path = fileTree.getPathForRow(row);
                        TreePath path = fileTree.getPathForLocation(e.getX(), e.getY());

                        if (path != null) {
                            Object[] nodes = path.getPath();
                            StringBuilder fullPath = new StringBuilder();
                            fullPath.append("/");
                            for (int i = 1; i < nodes.length; i++) {
                                fullPath.append(nodes[i].toString());
                                fullPath.append("/");
                            }
                            // Remove trailing slash


                            fullPath.deleteCharAt(fullPath.length() - 1);
                            full = full + fullPath.toString();
                            // System.out.println("Full path: " + full);
                        }
                        String finalFull = full;
                        copy.addActionListener(
                                event -> FileEdit.copyFileToClipboard(new File(finalFull))
                        );
                        delete.addActionListener(event -> {
                            if (safeDelete(finalFull)) return;


                            try {
                                if (finalFull == fileName) {
                                    fileName = folderName;
                                }
                                dao.createFile(new Files(folderName, folderName));
                                EditorFrame editorFrame = new EditorFrame(folderName, folderName);
                                editorFrame.setSize(getWidth(), getHeight());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            dispose();

                        });
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            fileTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {


                    // Handle tree selection change event here
                    System.out.println("tree " + e.getPath());
                    String fullPath = file.getAbsoluteFile().getParent();
                    for (int i = 1; i < e.getPath().getPath().length; i++) {
                        fullPath = fullPath + "/" + e.getPath().getPath()[i].toString();
                    }
                    try {
                        File file = new File(fullPath);
                        if (!file.isDirectory()) {
                            FileReader fr = new FileReader(file);
                            textArea.setText(IOUtils.toString(fr));
                            fr.close();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String ext = FilenameUtils.getExtension(fullPath);
                    System.out.println(ext);
                    getSyntaxCompletions(ext);
                    // File file = new File(e.getPath().toString());
                    fileName = fullPath;

                    tab.setText(fullPath);
                    TreePath selectedPath = e.getPath();
                    Object selectedNode = selectedPath.getLastPathComponent();
                    // ...
                }
            });
        } else {

            fileTree.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem copy = new JMenuItem("Copy Path");
                        copy.setIcon(copyIcon);
                        JMenuItem newFile = new JMenuItem("New file");
                        newFile.setIcon(fileIcon);
                        newFile.addActionListener(actionEvent -> FileCreator.chooseDirectory(fileName, getWidth(), getHeight()));
                        popupMenu.add(newFile);
                        popupMenu.add(copy);
                        JMenuItem delete = new JMenuItem("Delete File");
                        //  JMenuItem web = new JMenuItem("Browser");
                        delete.setIcon(deleteIcon);
                        popupMenu.add(delete);
                        String full = file.getAbsoluteFile().getParent();
                        int row = fileTree.getClosestRowForLocation(e.getX(), e.getY());
                        fileTree.setSelectionRow(row);

                        // TreePath path = fileTree.getPathForRow(row);
                        TreePath path = fileTree.getPathForLocation(e.getX(), e.getY());

                        if (path != null) {
                            Object[] nodes = path.getPath();
                            StringBuilder fullPath = new StringBuilder();
                            fullPath.append("/");
                            for (int i = 0; i < nodes.length; i++) {
                                fullPath.append(nodes[i].toString());
                                fullPath.append("/");
                            }
                            // Remove trailing slash


                            fullPath.deleteCharAt(fullPath.length() - 1);
                            full = full + fullPath.toString();
                            // System.out.println("Full path: " + full);
                        }
                        String finalFull = full;
                        copy.addActionListener(
                                event -> FileEdit.copyFileToClipboard(new File(finalFull))
                        );
                        delete.addActionListener(event -> {
                            if (safeDelete(finalFull)) return;
                            if (fileName == finalFull) {
                                fileName = folderName;
                            }

                            try {
                                dao.createFile(new Files(folderName, folderName));
                                EditorFrame editorFrame = new EditorFrame(folderName, folderName);
                                editorFrame.setSize(getWidth(), getHeight());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            dispose();
                        });
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            fileTree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {


                    // Handle tree selection change event here
                    System.out.println("tree " + e.getPath());
                    String fullPath = file.getAbsoluteFile().getParent();

                    for (int i = 0; i < e.getPath().getPath().length; i++) {
                        fullPath = fullPath + "/" + e.getPath().getPath()[i].toString();
                    }
                    try {
                        File file = new File(fullPath);
                        if (!file.isDirectory()) {
                            FileReader fr = new FileReader(file);
                            textArea.setText(IOUtils.toString(fr));
                            fr.close();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String ext = FilenameUtils.getExtension(fullPath);
                    System.out.println(ext);
                    getSyntaxCompletions(ext);
                    // File file = new File(e.getPath().toString());

                    dao.createFile(new Files(fullPath, folderName));
                    dao.findAllFiles().forEach(System.out::println);
                    fileName = fullPath;
                    tab.setText(fullPath);
                    TreePath selectedPath = e.getPath();
                    Object selectedNode = selectedPath.getLastPathComponent();
                    // ...
                }
            });


        }
        panel.add(fileTree);
    }

    private boolean safeDelete(String finalFull) {
        File deletefile = new File(finalFull);
        if (!deletefile.exists()) {
            return true;
        }
        int sure = JOptionPane.showConfirmDialog(Frame.getFrames()[Frame.getFrames().length - 1], "Are sure you want to delete " + deletefile.getName());
        if (sure == JOptionPane.YES_OPTION) {
            if (deletefile.isDirectory()) {
                FileEdit.deleteFolder(deletefile);
            } else {
                deletefile.delete();
            }
        } else {
            return true;
        }
        return false;
    }

    public void getSyntaxCompletions(String extension) {

        String mime = FileExtensionUtil.getMime(extension);
        System.out.println("mime" + mime);
        if (mime != null) {
            textArea.setSyntaxEditingStyle(mime);
            //   RSyntaxDocument document = (RSyntaxDocument) textArea.getDocument();

            if (extension.toLowerCase().contains("c") ||
                    extension.toLowerCase().contains("css") ||
                    extension.toLowerCase().contains("groovy") ||
                    extension.toLowerCase().contains("html") ||
                    extension.toLowerCase().contains("java") ||
                    extension.toLowerCase().contains("js") ||
                    extension.toLowerCase().contains("jsp") ||
                    extension.toLowerCase().contains("pl") ||
                    extension.toLowerCase().contains("php") ||
                    extension.toLowerCase().contains("sh") ||
                    extension.toLowerCase().contains("xml")) {

                if (extension.equals("java")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    JavaLanguageSupport support = (JavaLanguageSupport) lsf.
                            getSupportFor(mime);
//    CompletionProvider provider = createCompletionProvider();
//                AutoCompletion ac = new AutoCompletion(provider);
//                ac.install(textArea);
                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("c")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    CLanguageSupport support = (CLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("css")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    CssLanguageSupport support = (CssLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("groovy")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    GroovyLanguageSupport support = (GroovyLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("html")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    HtmlLanguageSupport support = (HtmlLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("js")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    JavaScriptLanguageSupport support = (JavaScriptLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("jsp")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    JspLanguageSupport support = (JspLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("pl")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    PerlLanguageSupport support = (PerlLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("php")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    PhpLanguageSupport support = (PhpLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("sh")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    ShellLanguageSupport support = (ShellLanguageSupport) lsf.
                            getSupportFor(mime);

                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                } else if (extension.equals("xml")) {
                    LanguageSupportFactory lsf = LanguageSupportFactory.get();
                    XmlLanguageSupport support = (XmlLanguageSupport) lsf.
                            getSupportFor(mime);
//
                    support.setAutoActivationEnabled(true);
                    support.install(textArea);
                }

            } else {

            }
        }
    }

    public void setTextEditorTheme(String xmlfile) {

    }

    public static ImageIcon generateImageIcon(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("exit")) {
            int number = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
            if (number == 0) {
                System.exit(0);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        windowCount = windowCount - 1;
        System.out.println("closing " + windowCount);
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

}


// document.setSyntaxStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
//        Color backgroundColor = UIManager.getColor("TextArea.background");
//        Color foregroundColor = UIManager.getColor("TextArea.foreground");
//        Color selectionColor = UIManager.getColor("TextArea.selectionBackground");
//        Color caretColor = UIManager.getColor("TextArea.caretForeground");
//        textArea.setBackground(backgroundColor);
//        textArea.setForeground(foregroundColor);
//        textArea.setSelectionColor(selectionColor);
//        textArea.setCurrentLineHighlightColor(new Color(204, 204, 204));
//        textArea.setCaretColor(caretColor);