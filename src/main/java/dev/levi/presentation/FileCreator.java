package dev.levi.presentation;

import dev.levi.Main;
import dev.levi.data.FileDao;
import dev.levi.data.FileDaoImpl;
import dev.levi.domain.Files;
import dev.levi.presentation.components.DarkThemeFileChooser;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class FileCreator extends JPanel
        implements ActionListener {
    private final FileDao dao = new FileDaoImpl();
    JButton anotherDir;
    JButton cancelButton = new JButton("Cancel") ;

    static JLabel directoryLabel = new JLabel("Select folder to create");
    static JLabel fileLabel = new JLabel("File Name");
    private int width;
    private int height;

    TextField filename = new TextField(20);
    String directory = "";

    JFileChooser chooser;
    String choosertitle;
    JButton saveButton = new JButton("Save");
    private String rootFolder;
    private Font uifont;
    private static JFrame frame;

    {
        File font_file = new File("Fonts/DroidSans.ttf");

        UIManager.put("Label.font", Main.droid);
        UIManager.put("TextField.font", Main.droid);
    }

    private FileCreator(String startFolder,String rootFolder, int width, int height) {
        this();
        Main.setUpTheme(true);
        this.width = width;
        this.height = height;
        this.rootFolder = rootFolder;
        if (new File(startFolder).isDirectory()) {
            directory = startFolder;
        } else {
            directory = new File(startFolder).getParent();


        }
    }


    public FileCreator() {
        setLayout(null);
        //setBackground(new Color(96,96,96));

        anotherDir = new JButton("...");
        // go.setBackground(Color.white);
        anotherDir.setFocusable(false);
        saveButton.setFocusable(false);
        saveButton.setBorderPainted(false);
        // saveButton.setBackground(Color.white);


        anotherDir.addActionListener(e -> {

            String pathname = DarkThemeFileChooser.chooseAnyFile(false, directory);
            if (pathname == null){
                return;
            }
            directory = pathname;
        });
        directoryLabel.setBounds(20, 20, 300, 40);
        anotherDir.setBounds(330, 20, 50, 40);
        fileLabel.setBounds(60, 80, 100, 40);
        filename.setBounds(200, 80, 160, 40);
        filename.setBackground(frame.getBackground());
        filename.setForeground(Color.white);
        saveButton.setBounds(200, 140, 80, 40);
        saveButton.setBackground(Color.green);
        cancelButton.setBounds(100, 140, 80, 40);

        saveButton.setForeground(Color.white);
        saveButton.setBackground(new Color(0x57ACF4));

        filename.setPreferredSize(new Dimension(200, 30)); // Set preferred size
cancelButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent event){
        frame.dispose();
    }
});
        saveButton.addActionListener(e -> {
            if (directory != "" && filename.getText().length() > 1) {
                String name = directory + "/" + filename.getText();
                File file = new File(name);
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (name.endsWith("html")) {
                    try {
                        FileWriter writer = new FileWriter(name);
                        writer.write("<!DOCTYPE html>\n" +
                                "<html lang=\"en\">\n" +
                                "<head>\n" +
                                "    <meta charset=\"UTF-8\">\n" +
                                "    <title>Title</title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<h1 style=\"color:#0046b2;align-content: center\">Hello World</h1>\n" +
                                "</body>\n" +
                                "</html>");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (name.endsWith(".java")) {
                    try {
                        FileWriter writer = new FileWriter(name);
                        writer.write("public class " + filename.getText().replace(".java", "") + " {\n" +
                                "    \n" +
                                "}\n");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        FileWriter writer = new FileWriter(name);
                        writer.write("");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }

                new Thread(() -> {
                    dao.createFile(new Files(0, directory, directory, System.currentTimeMillis()));
                    try {
                        EditorFrame editorFrame = new EditorFrame(name, rootFolder);
                        editorFrame.setSize(width, height);
                        Arrays.stream(EditorFrame.getFrames()).filter(frame1 -> frame1.getTitle().equals(EditorFrame.previousWindow)).collect(Collectors.toList()).get(0).dispose();
                        frame.dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
            frame.setVisible(false);
            //  frame.dispose();

//                try {
//                    new EditorFrame(name, directory);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
        });


        add(directoryLabel);
        add(anotherDir);
        add(fileLabel);
        add(filename);
        add(saveButton);
        add(cancelButton);

    }

    public void actionPerformed(ActionEvent e) {


    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 200);
    }

    public static void chooseDirectory(String folderName,String rootFolder, int width, int height) {
        frame = new JFrame("Create File");
        frame.setUndecorated(true);
        directoryLabel.setForeground(Color.WHITE);
        fileLabel.setForeground(Color.white);
        // frame.setOpacity(.9F);
        FileCreator panel = new FileCreator(folderName, rootFolder,width, height);
        Shape shape = new RoundRectangle2D.Double(0, 0, panel.getPreferredSize().width, panel.getPreferredSize().getHeight(), 20, 20);
        frame.setShape(shape);
        frame.setSize(panel.getPreferredSize());


        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel, "Center");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}