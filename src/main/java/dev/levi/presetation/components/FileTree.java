package dev.levi.presetation.components;

import com.formdev.flatlaf.ui.FlatTreeUI;
import dev.levi.presetation.Main;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;

public class FileTree extends JTree
{
    public FileTree(String path)
    {
        super(scan(new File(path)));

      //  setCellRenderer(new DarkTreeCellRenderer());

    }

    private static MutableTreeNode scan(File node)
    {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(node.getName());
        if (node.isDirectory())
            for (File child: node.listFiles())
                ret.add(scan(child));
        return ret;
    }
    public class DarkTreeCellRenderer extends DefaultTreeCellRenderer {

        private static final Color selectionColor = new Color(51, 153, 255);
        private static final Color backgroundNonSelectionColor = new Color(0, 0, 0);
        private static final Color backgroundSelectionColor = new Color(51, 153, 255);
        private static final Color textNonSelectionColor = new Color(255, 255, 255);
        private static final Color textSelectionColor = Color.WHITE;

        public DarkTreeCellRenderer() {
            setOpaque(true);
        }



        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
setFont(Main.generateFonts(Main.droid,12f));

            if (selected) {
                setBackground(backgroundSelectionColor);
                setForeground(textSelectionColor);
            } else {
                setBackground(backgroundNonSelectionColor);
                setForeground(textNonSelectionColor);
            }
//            if (value instanceof File file) {
//                if (file.isDirectory()) {
//                    setIcon(folderIcon);
//                    System.out.println("file");
//                } else {
//                    setIcon(fileIcon);
//                }
//            }




            return this;
        }
    }
}


