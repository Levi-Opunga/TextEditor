package dev.levi;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import dev.levi.data.FileDaoImpl;
import dev.levi.domain.Files;
import dev.levi.presentation.EditorFrame;
import dev.levi.utils.FileEdit;
import org.h2.expression.UnaryOperation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Main {
    public static Font droid;

    public static Font fira;
  //  public static InputStream jetbrainsIS = Main.class.getClassLoader().getResourceAsStream("Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
  public static   Font jetbrains;
    //  public static File jetbrains = new File("./Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
    //public static Font hack = generateFontClassLoader("Fonts/Hack Bold Nerd Font Complete.ttf");

    static {
//        droid =  generateFonts(new File("./Fonts/DroidSans.ttf"),12f);//generateFontClassLoader("./Fonts/DroidSans.ttf");
//        fira = generateFonts(new File("./Fonts/Fira Code Regular Nerd Font Complete Mono.ttf"),12f);//generateFontClassLoader("./Fonts/Fira Code Regular Nerd Font Complete Mono.ttf");
//        jetbrains = generateFonts(new File("./Fonts/JetBrains Mono Bold Nerd Font Complete.ttf"),12f);//generateFontClassLoader("./Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
        droid = generateFontClassLoader("Fonts/DroidSans.ttf");
        fira = generateFontClassLoader("Fonts/Fira Code Regular Nerd Font Complete Mono.ttf");
        jetbrains = generateFontClassLoader("Fonts/JetBrains Mono Bold Nerd Font Complete.ttf");
    }


    public static List<String> list ;
    public static   Map<String, ImageIcon> images =new HashMap<>();


    static {
        try {
            List<String> list = new ArrayList<String>();
            String decodedPath = URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            System.out.println(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println("decoded path "+decodedPath);
            File jarFile = new File(decodedPath);

            list = FileEdit.copyFromJar("images");
            for (int i=1; i<list.size(); i++) {
                String icon = list.get(i);
                if(icon==null){

                }else {
                    {

                        try {
                            //

                            if (icon.endsWith("png")){
                                System.out.println(list);
                                icon = icon.replace("src/main/resources/","");
                                icon = icon.replace("src\\main\\resources\\","");
                                System.out.println( icon);

                               Image image = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(icon));
                        //      Image image = ImageIO.read(new File(icon));

                                if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                                    icon = icon.replace("images/", "");
                                    icon = icon.replace(".png", "");
                                    icon = icon.replace("./", "");
                                } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                                    icon = icon.replace("images\\", "");
                                    icon = icon.replace("images/", "");
                                    icon = icon.replace(".png", "");
                                    icon = icon.replace(".", "");
                                    icon = icon.replace("/", "");
                                    icon = icon.replace("\\", "");
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
            File file  = new File("./");
            dao.createFile(new Files(0, file.getAbsolutePath(), file.getAbsolutePath(), System.currentTimeMillis()));
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
        UIManager.put("Label.font", Main.droid);
        UIManager.put("TextField.font", Main.droid);
        UIManager.put("RSyntaxTextArea.font", Main.jetbrains);
        UIManager.put("ScrollPane.horizontalScrollBarPolicy", ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        UIManager.put("REditorPane.font", jetbrains);
        UIManager.put("MenuItem.font", droid);
        UIManager.put("Menu.font",droid);
        UIManager.put("Button.border", null);
//        DarkThemeFileChooser.setColors();
    }
    public static Font generateFontClassLoader(String url) {
        System.out.println(url);
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(url);
        Font font ;
        try {

            font = Font.createFont(Font.TRUETYPE_FONT,inputStream);
            font = font.deriveFont(12f);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return font;
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