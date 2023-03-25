package dev.levi.presetation;

import dev.levi.presetation.components.DarkThemeFileChooser;
import org.apache.commons.io.IOUtils;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.java.source.ui.DialogBinding;
import org.netbeans.api.lexer.Language;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.CloneableEditorSupport;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
   public static File droid = new File("./Fonts/DroidSans.ttf");
  public  static File jetbrains = new File("./Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
   public static File fira = new File("./Fonts/Fira Code Regular Nerd Font Complete Mono.ttf");
  public static   File hack = new File("./Fonts/Hack Bold Nerd Font Complete.ttf");
    private static Font uifont;

    public static void main(String[] args) throws IOException {


        setUpTheme();
        EditorFrame frame = new EditorFrame();
    }

    public static void doSyntaxColoring(String fileName) {
        JFrame f = new JFrame("JAVA Syntax Coloring");

        StyledDocument doc = new DefaultStyledDocument();
        doc.putProperty(Language.class, JavaTokenId.language());


        JEditorPane pane = new JEditorPane();
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/x-java");
        pane.setEditorKit(kit);

//        File file = new File("./App.java");
//        try {
//            pane.setPage(file.toURI().toURL());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    try (FileReader fileReader = new FileReader(fileName)) {

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
        pane.setDocument(doc);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        JScrollPane comp = new JScrollPane(pane);
        comp.setBounds(300, 45, 250, 233);
        f.getContentPane().add(comp);
        f.setSize(800, 800);
        f.setVisible(true);
    }


    public static void completion() throws IOException {
        JFrame frame = new JFrame();
        JEditorPane editorPane = new JEditorPane();

        // This will find the Java editor kit and associate it with
        // our editor pane. But that does not give us code completion
        // just yet because we have no Java context (i.e. no class path, etc.).
        // However, this does give us syntax coloring.
        EditorKit kit = CloneableEditorSupport.getEditorKit("text/x-java");
        editorPane.setEditorKit(kit);

        // You can specify any ".java" file.
        // If the file does not exist, it will be created.
        // The contents of the file does not matter.
        // The extension must be ".java", however.
        String newSourcePath = "./App.java";

        File tmpFile = new File(newSourcePath);
        FileObject fob = FileUtil.createData(tmpFile);

        DataObject dob = DataObject.find(fob);
        editorPane.getDocument().putProperty(
                Document.StreamDescriptionProperty,
                dob);

        // This sets up a default class path for us so that
        // we can find all the JDK classes via code completion.
        DialogBinding.bindComponentToFile(fob, 0, 0, editorPane);

        // Last but not least, we need to fill the editor pane with
        // some initial dummy code - as it seems somehow required to
        // kick-start code completion.
        // A simple dummy package declaration will do.
        editorPane.setText("package dummy;");
        frame.add(editorPane);
        frame.setVisible(true);

    }

    public static void setUpTheme() {

//        try {
//            uifont = Font.createFont(Font.TRUETYPE_FONT, droid);
//            uifont = uifont.deriveFont(15f);

//
//        } catch (FontFormatException | IOException e) {
//            throw new RuntimeException(e);
//        }
        uifont = generateFonts(droid,15f);
        UIManager.put("EditorPane.font",generateFonts(jetbrains,12f));
        UIManager.put("MenuItem.font", uifont);
            UIManager.put("Button.border", null);
        DarkThemeFileChooser.setColors();
    }

    public static Font generateFonts(File fontFile,Float size) {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT,fontFile);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        f= f.deriveFont(size);
        return f;

    }

}