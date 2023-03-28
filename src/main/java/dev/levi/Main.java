package dev.levi;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dev.levi.data.FileDaoImpl;
import dev.levi.domain.Files;
import dev.levi.presentation.App;
import dev.levi.presentation.EditorFrame;
import dev.levi.utils.FileEdit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class Main {
    public static File droid = new File("./Fonts/DroidSans.ttf");
    public static File jetbrains = new File("./Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
    public static File fira = new File("./Fonts/Fira Code Regular Nerd Font Complete Mono.ttf");
    public static File hack = new File("./Fonts/Hack Bold Nerd Font Complete.ttf");
    private static Font uifont;


    public static List<String> list ;
    public static   Map<String, ImageIcon> images =new HashMap<>();


    static {
        try {
            list = FileEdit.copyFromJar("images");
            for (int i=1; i<list.size(); i++) {
                String icon = list.get(i);
                if(icon==null){

                }else {
                    {

                        try {
                            //

                            if (icon.endsWith("png")){
                                System.out.println( icon);
                                Image image = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(icon));
                               // Image image = ImageIO.read(new File(icon));

                                if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                                    icon = icon.replace("images/", "");
                                    icon = icon.replace(".png", "");
                                    icon = icon.replace("./", "");
                                } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                                    icon = icon.replace("images\\", "");
                                    icon = icon.replace(".png", "");
                                    icon = icon.replace(".", "");
                                    icon = icon.replace("/", "");
                                }
                                System.out.println(icon);
                                images.put(icon,generateImageIcon(image));
                                System.out.println(images);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }}
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }



    public static void main(String[] args)  {
        FileDaoImpl dao = new FileDaoImpl();
        List<Files> last = (List<Files>) dao.findAllFiles();
        if (last.size() == 0) {
            dao.createFile(new Files(0, "./", "./", System.currentTimeMillis()));
        }
        setUpTheme(true);
        last = (List<Files>) dao.findAllFiles();

        try {

            String name = last.get(0).getName();
            String path = last.get(0).getPath();
            if(new File(name).exists()&&new File(path).isDirectory()){
                setUpTheme(true);
                EditorFrame frame = new EditorFrame(name, path);

            }else {
                EditorFrame frame = new EditorFrame("./", "./");
            }
         //   EditorFrame frame = new EditorFrame(System.getenv().get("HOME"), System.getenv().get("HOME"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static ImageIcon generateImageIcon(Image icon) {
        Image image = icon;
        image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }


    public static void setUpTheme(boolean darkTheme) {
        if (darkTheme) {
            try {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
        }

        uifont = generateFonts(fira, 15f);
        UIManager.put("ScrollPane.horizontalScrollBarPolicy", ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        UIManager.put("REditorPane.font", generateFonts(jetbrains, 17f));
        UIManager.put("MenuItem.font", generateFonts(fira, 12f));
        UIManager.put("Menu.font", generateFonts(droid, 13f));
        UIManager.put("Button.border", null);
//        DarkThemeFileChooser.setColors();
    }

    public static Font generateFonts(File fontFile, Float size) {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        f = f.deriveFont(size);
        return f;

    }

}