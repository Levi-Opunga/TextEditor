package dev.levi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class StyleChanger {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> show());
    }

    private static final String HIGHLIGHT =
            StyleChanger.class.getName() + ".highlight";

    static void show() {
        JTextPane textPane = new JTextPane();

        Style normal = textPane.getStyle(StyleContext.DEFAULT_STYLE);
        Style highlight = textPane.addStyle(HIGHLIGHT, normal);
        StyleConstants.setBackground(highlight, Color.YELLOW);

        class Fragment {
            final AttributeSet style;
            final String text;

            Fragment(AttributeSet style,
                     String text) {
                this.style = style;
                this.text = text;
            }
        }

        Fragment[] fragments = {
                new Fragment(highlight, "Space"),
                new Fragment(normal, ": the final frontier.  These are the "),
                new Fragment(highlight, "voyages"),
                new Fragment(normal, " of the starship "),
                new Fragment(highlight, "Enterprise"),
                new Fragment(normal, ".  Its five-year "),
                new Fragment(highlight, "mission"),
                new Fragment(normal, ": to explore strange "),
                new Fragment(highlight, "new worlds"),
                new Fragment(normal, "; to seek out "),
                new Fragment(highlight, "new life"),
                new Fragment(normal, " and new civilizations; to "),
                new Fragment(highlight, "boldly go"),
                new Fragment(normal, " where no man has gone before!"),
        };

        for (Fragment fragment : fragments) {
            textPane.setCharacterAttributes(fragment.style, true);
            textPane.replaceSelection(fragment.text);
        }

        Action change = new AbstractAction("Change Highlight\u2026") {
            private static final long serialVersionUID = 1;

            @Override
            public void actionPerformed(ActionEvent event) {
                Color color = JColorChooser.showDialog(
                        textPane.getTopLevelAncestor(),
                        "Highlight Color",
                        textPane.getBackground());

                if (color != null) {
                    StyleConstants.setBackground(highlight, color);
                    updateHighlight(textPane.getStyledDocument(), highlight);
                }
            }
        };

        JButton changeButton = new JButton(change);

        JPanel buttonPane = new JPanel();
        buttonPane.add(changeButton);

        JFrame frame = new JFrame("Style Changer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new JScrollPane(textPane));
        frame.getContentPane().add(buttonPane, BorderLayout.PAGE_END);

        frame.setSize(450, 300);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static void updateHighlight(StyledDocument doc,
                                        AttributeSet newStyle) {

        Object styleName = newStyle.getAttribute(AttributeSet.NameAttribute);

        ElementIterator i = new ElementIterator(doc);
        for (Element e = i.first(); e != null; e = i.next()) {
            AttributeSet attr = e.getAttributes();
            Object name = attr.getAttribute(AttributeSet.NameAttribute);
            if (styleName.equals(name)) {
                int start = e.getStartOffset();
                int end = e.getEndOffset();
                doc.setCharacterAttributes(start, end - start,
                        newStyle, false);
            }
        }
    }
}
