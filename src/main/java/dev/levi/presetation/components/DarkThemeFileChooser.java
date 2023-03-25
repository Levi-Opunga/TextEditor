package dev.levi.presetation.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.net.URL;


public class DarkThemeFileChooser {
    public static String chooseAnyFile(Boolean allfiles) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFileChooser chooser = new JFileChooser();
        if (allfiles) {
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        } else {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        }
        chooser.setMultiSelectionEnabled(false);
        chooser.setApproveButtonText("Select");
        chooser.setDialogTitle("Choose File");
        // chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));

       // setColors();

        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {

            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    public static void setColors() {


     //   FlatLightLaf.setup();
//        try {
//            UIManager.setLookAndFeel( new FlatLightLaf() );
//        } catch (UnsupportedLookAndFeelException e) {
//            throw new RuntimeException(e);
//        }

//        UIManager.put("Tree.closedIcon", folderIcon);
//        UIManager.put("Tree.openIcon", folderIcon);
//        UIManager.put("Tree.leafIcon", fileIcon);

//        UIManager.put("Tree.background",Color.BLACK);
//        UIManager.put("Frame.ContentPane.background", new Color(96, 96, 96));
//        UIManager.put("EditorPane.background", new Color(96, 96, 96));
//        UIManager.put("EditorPane.foreground", Color.white);
//        UIManager.put("FileChooser.background", new Color(32, 32, 32));
//        UIManager.put("Panel.background", new Color(32, 32, 32));
//        UIManager.put("Button.background", new Color(48, 48, 48));
//        UIManager.put("Button.foreground", new Color(255, 255, 255));
//        UIManager.put("Label.foreground", new Color(255, 255, 255));
//        UIManager.put("List.foreground", new Color(255, 255, 255));
//        UIManager.put("List.background", new Color(48, 48, 48));
//        UIManager.put("TextField.foreground", new Color(255, 255, 255));
//        UIManager.put("TextField.background", new Color(48, 48, 48));
//        UIManager.put("ComboBox.foreground", new Color(255, 255, 255));
//        UIManager.put("ComboBox.background", new Color(48, 48, 48));
//        UIManager.put("ComboBox.selectionBackground", new Color(96, 96, 96));
//        UIManager.put("ComboBox.selectionForeground", new Color(255, 255, 255));
//        UIManager.put("TextField.selectionBackground", new Color(96, 96, 96));
//        UIManager.put("TextField.selectionForeground", new Color(255, 255, 255));
    }
}
