package dev.levi.presentation.components;

import dev.levi.presentation.Main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class SwingHTMLBrowser extends JFrame implements ActionListener, HyperlinkListener {
    private JTextField addressBar;

   Pane pane;
   the other day i was walking dpwn the road the i realized how much i missed being at home 
   so i decided to head back home suddenly it started raining i didnt know what to do so decided not to do anything
   i hate water spilling on me so yes it was no the most pkeasant feeling in the world

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
avec moi
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