package dev.levi.presetation;

import dev.levi.data.FileDao;
import dev.levi.data.FileDaoImpl;
import dev.levi.domain.Files;
import dev.levi.presetation.components.DarkThemeFileChooser;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileCreator extends JPanel
        implements ActionListener {
    private final FileDao dao = new FileDaoImpl();
    JButton go;

    JLabel directoryLabel =new JLabel("Select folder to create");
    JLabel fileLabel =new JLabel("File Name");

    TextField filename =new TextField(20);
    String directory="";

    JFileChooser chooser;
    String choosertitle;
    JButton saveButton =new JButton("save");
    private Font uifont;
    JFrame frame = new JFrame("Create File");

      {
      File font_file = new File("./Fonts/DroidSans.ttf");
      try {
          uifont = Font.createFont(Font.TRUETYPE_FONT, font_file);
          uifont = uifont.deriveFont(15f);
          UIManager.put("Label.font",uifont);
          UIManager.put("TextField.font",uifont);
      } catch (FontFormatException | IOException e) {
          throw new RuntimeException(e);
      }
    }





    public FileCreator() {
        setLayout(null);
        //setBackground(new Color(96,96,96));

        go = new JButton("...");
       // go.setBackground(Color.white);
        go.setFocusable(false);
        saveButton.setFocusable(false);
        saveButton.setBorderPainted(false);
       // saveButton.setBackground(Color.white);


        go.addActionListener(this);
        directoryLabel.setBounds(20,20,300,40);
        go.setBounds(330,20,50,40);
        fileLabel.setBounds(60,80,100,40);
        filename.setBounds(200,80,160,40);
        saveButton.setBounds(160,140,80,40);
        filename.setPreferredSize(new Dimension(200, 30)); // Set preferred size

        saveButton.addActionListener(e->{
if(directory!=""&&filename.getText().length()>1){
    directory = directory+"/"+filename.getText();
    File file = new File(directory);
    try {
        file.createNewFile();
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
    if(directory.contains("html")){
        try {
            FileWriter writer = new FileWriter(directory);
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
    }

    try {
        dao.createFile(new Files(0,directory,directory,System.currentTimeMillis()));
        new EditorFrame(directory);
        frame.dispose();
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

        });


        add(directoryLabel);
        add(go);
        add(fileLabel);
       add(filename);
       add(saveButton);

    }

    public void actionPerformed(ActionEvent e) {

        directory= DarkThemeFileChooser.chooseAnyFile(false);

    }

    public Dimension getPreferredSize(){
        return new Dimension(400, 200);
    }

    public void chooseDirectory() {
        frame.setUndecorated(true);
        directoryLabel.setForeground(Color.WHITE);
        fileLabel.setForeground(Color.white);
       // frame.setOpacity(.9F);
        FileCreator panel = new FileCreator();
        Shape shape = new RoundRectangle2D.Double(0, 0, panel.getPreferredSize().width, panel.getPreferredSize().getHeight(), 20, 20);
        frame.setShape(shape);
        frame.setSize(panel.getPreferredSize());


        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel,"Center");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}