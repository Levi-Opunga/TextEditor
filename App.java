package dev.levi;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class App extends JFrame {
 private JMenuBar bar = new JMenuBar();
 private JMenu fileMenu = new JMenu();

 private JMenu viewMenu = new JMenu();
private JPanel panel = new JPanel();

 private JEditorPane jEditorPane = new JEditorPane();





    public App() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setLayout(null);

        int height = getSize().height;
        int width = getSize().width;
        panel.setSize((int)(width*.2),height);
        HTMLDocument document = new HTMLDocument();
        jEditorPane.setSize((int) (width*.8),height);
        jEditorPane.setBounds((int)(width*.2),0, (int) (width*.8),height);
        panel.setBounds(0,0, (int) (width*.2),height);
        resizeEvent();
        try {
            configureEditor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        panel.setBackground(Color.BLUE);
        add(panel);

        add(jEditorPane);



        add(bar);
        setVisible(true);
    }

    private void resizeEvent(){
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                int height = e.getComponent().getSize().height;
                int width = e.getComponent().getSize().width;
                panel.setSize((int)(width*.2),height);
                HTMLDocument document = new HTMLDocument();
                jEditorPane.setSize((int) (width*.8),height);
                jEditorPane.setBounds((int)(width*.2),0, (int) (width*.8),height);
                panel.setBounds(0,0, (int) (width*.2),height);
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

    private void configureMenu(){
        fileMenu.setText("File");
    }
    private void configureEditor() throws IOException {
//      jEditorPane.setContentType("text/html");
        HTMLEditorKit editorKit = new HTMLEditorKit();
        jEditorPane.setEditorKit(editorKit);
        File file = new File("./index.html");
        jEditorPane.setPage(file.toURI().toURL());
    }
    private void configureSidePanel(){

    }
}
