package dev.levi.utils;

import dev.levi.Main;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileCopier {
    static final File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

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

    public static List<String> copyFromJar(String path) throws IOException, URISyntaxException {
            List<String> list = new ArrayList<String>();


        if(jarFile.isFile()) {  // Run with JAR file
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while(entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(path + "/")) { //filter according to the path
                 //   URL inputUrl = Main.class.getResource(name);

//                    File dest = new File("./file");
//                    FileUtils.copyURLToFile(inputUrl, dest);
//                    FileUtils.copyFile(new File(inputUrl.toURI()), new File("./file.png"));
                    list.add(name);

                }
            }
            jar.close();
            return list;

        } else { // Run with IDE

//            final URL url = Main.class.getClassLoader().getResource( path);
//            System.out.println("with ide"+ url);
//            if (url != null) {
//                try {
//                    final File apps = new File(url.toURI());
//                    for (File app : apps.listFiles()) {
//                        list.add(app.getName());
//                        System.out.println(app);
//                    }
//                } catch (URISyntaxException ex) {
//                    // never happens
//                }
//            }
//            ClassLoader classLoader = Main.class.getClassLoader();
//            URL resourceUrl = classLoader.getResource(path);
            File resourceFolder = new File(path);
            File[] files = resourceFolder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    // System.out.println(file.getPath());
                    list.add("./"+file.getPath());
                }
            }

        }
        return list;
    }

}
