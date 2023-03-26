package dev.levi.presentation.components;

import dev.levi.Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class SwingHTMLBrowser extends JFrame implements ActionListener, HyperlinkListener {
    private JTextField addressBar;
    private JEditorPane pane;

    public SwingHTMLBrowser(String initial){
        this();
        try {
            if(initial.startsWith("/")||initial.startsWith(".")){
                File file = new File(initial);
                pane.setPage(file.toURI().toURL());
            }
            pane.setPage(initial);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    SwingHTMLBrowser() {
        super("Swing HTML Browser");
        Main.setUpTheme(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addressBar = new JTextField();
        addressBar.addActionListener(this);
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);
        add(addressBar, BorderLayout.NORTH);
        add(new JScrollPane(pane));
        setSize(new Dimension(400, 400));
        setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        String url = addressBar.getText();
        try {
            if(url.startsWith("/")||url.startsWith(".")){
                File file = new File(url);
                pane.setPage(file.toURI().toURL());
            }
            pane.setPage(url);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }
        JEditorPane srcPane = (JEditorPane)evt.getSource();
        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument)pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)evt);
        } else {
            String url = evt.getURL().toString();
            addressBar.setText(url);
            try {
                pane.setPage(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}