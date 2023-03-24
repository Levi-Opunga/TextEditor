package dev.levi.presetation;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileCreator extends JPanel
        implements ActionListener {
    JButton go;
    JLabel directoryLabel =new JLabel("Select folder to create");
    JLabel fileLabel =new JLabel("File Name");

    JTextField filename =new JTextField(20);
    String directory="";

    JFileChooser chooser;
    String choosertitle;
    JButton saveButton =new JButton("save");


    public FileCreator() {
        setLayout(null);
        go = new JButton("...");
        go.setBackground(Color.white);
        go.setFocusable(false);
        saveButton.setFocusable(false);
        saveButton.setBackground(Color.white);
        go.addActionListener(this);
        directoryLabel.setBounds(20,20,300,40);
        go.setBounds(330,20,50,40);
        fileLabel.setBounds(60,80,100,40);
        filename.setBounds(200,80,160,40);
        saveButton.setBounds(160,140,80,40);
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
        new EditorFrame(directory);
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
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
            directory=chooser.getSelectedFile().getAbsolutePath();
            System.out.println(directory);
        }
        else {
            System.out.println("No Selection ");
        }

    }

    public Dimension getPreferredSize(){
        return new Dimension(400, 200);
    }

    public void chooseDirectory() {
        JFrame frame = new JFrame("");
        FileCreator panel = new FileCreator();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}