package dev.levi.presetation;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class App extends JFrame {

    public static App getInstance() throws IOException {
        if (INSTANCE == null) {
            return new App();
        } else {
            return INSTANCE;
        }
    }

    private static App INSTANCE = null;
    private JMenuBar bar = new JMenuBar();
    private JMenu fileMenu = new JMenu();
    private JMenu view = new JMenu();
    private JMenu settings = new JMenu();
    private JMenu edit = new JMenu();
    private JMenu help = new JMenu();
    private JMenu viewMenu = new JMenu();
    private JPanel secondPanel = new JPanel();
    private JPanel panel = new JPanel();
    private String fileName = "";

    private JEditorPane jEditorPane = new JEditorPane();


    private JScrollPane scrollPane;

    private App() throws HeadlessException, IOException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(null);
        try {
            configureEditor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int height = getSize().height;
        int width = getSize().width;
        panel.setSize((int) (width * .2), height);
        HTMLDocument document = new HTMLDocument();
        //scrollPane.setSize(new Dimension((int) (width*.8),height));
        scrollPane = new JScrollPane(jEditorPane);
        scrollPane.setBounds((int) (width * .2), 0, (int) (width * .8), height);
        panel.setBounds(0, 0, (int) (width * .2), height);
        resizeEvent();


        scrollPane.add(jEditorPane);
        panel.setBackground(Color.BLUE);
        getContentPane().add(scrollPane);
        //     add(panel);

//        getContentPane().setBackground(Color.BLACK);
//        getContentPane().add(scrollPane);


        configureMenu();

        setJMenuBar(bar);
        setVisible(true);
        new EditorFrame().initializeView("");
    }

    private void resizeEvent() {
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                int height = e.getComponent().getSize().height;
                int width = e.getComponent().getSize().width;
                panel.setSize((int) (width * .2), height);
                scrollPane = new JScrollPane(jEditorPane);
                scrollPane.setBounds((int) (width * .2), 0, (int) (width * .8), height);
                panel.setBounds(0, 0, (int) (width * .2), height);
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
        settings.setText("Settings");
        help.setText("Help");
        JMenu[] menus = {fileMenu, view, edit, settings, help};
        Arrays.stream(menus).forEach(item ->
                bar.add(item)
        );
//        fileMenu.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
        JMenuItem newFile = new JMenuItem("New file");
        JMenuItem openExistingFile = new JMenuItem("Open file");
        JMenuItem openRecentFile = new JMenuItem("Recent Files");
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("here");
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    String filepath = f.getPath();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(filepath));
                        String s1 = "", s2 = "";
                        while ((s1 = br.readLine()) != null) {
                            s2 += s1 + "\n";
                        }
                        jEditorPane.setText(s2);
                        fileName = filepath;
                        br.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        fileMenu.add(newFile);
        fileMenu.add(openExistingFile);
        fileMenu.add(openRecentFile);


    }

    private void configureEditor() throws IOException {

    }

    private void configureSidePanel() {

    }

}
