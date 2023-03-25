package dev.levi.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileCopier {

    public static void copyFileToClipboard(File file) {
        if (file != null) {
            //StringSelection selection = new StringSelection(file.getAbsolutePath());
            List<File> files = List.of(file);

            Clipboard clipboard =Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable transferable = new Transferable() {
                @Override
                public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                    if (flavor.equals(DataFlavor.javaFileListFlavor)) {
                        return java.util.Collections.singletonList(file);
                    } else if (flavor.equals(DataFlavor.stringFlavor)) {
                        return file.getAbsolutePath();
                    } else {
                        throw new UnsupportedFlavorException(flavor);
                    }
                }

                @Override
                public DataFlavor[] getTransferDataFlavors() {
                    return new DataFlavor[]{DataFlavor.javaFileListFlavor, DataFlavor.stringFlavor};
                }

                @Override
                public boolean isDataFlavorSupported(DataFlavor flavor) {
                    return flavor.equals(DataFlavor.javaFileListFlavor) || flavor.equals(DataFlavor.stringFlavor);
                }
            };
            clipboard.setContents(transferable, null);
        }
    }
}
