package dev.levi.presetation;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
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
        setUpTheme(true);
        EditorFrame frame = new EditorFrame();

    }


    public static void setUpTheme(boolean darkTheme) {
        if (darkTheme){
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }}else {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
        }

        uifont = generateFonts(fira,15f);
        UIManager.put("ScrollPane.horizontalScrollBarPolicy",ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        UIManager.put("REditorPane.font",generateFonts(jetbrains,17f));
        UIManager.put("MenuItem.font", generateFonts(fira,12f));
        UIManager.put("Menu.font",generateFonts(droid,13f));
            UIManager.put("Button.border", null);
//        DarkThemeFileChooser.setColors();
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