package dev.levi.utils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import static java.nio.file.StandardWatchEventKinds.*;


public class FileWatcher extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private Path filePath;
    private WatchService watchService;
    private WatchKey watchKey;
    private Timer timer;

    public FileWatcher(Path filePath) {
        this.filePath = filePath;

        try {
            watchService = FileSystems.getDefault().newWatchService();
            watchKey = filePath.getParent().register(watchService, ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initComponents();
        startTimer();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("File Watcher");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);
    }

    private void startTimer() {
        timer = new Timer(5000, this); // check for changes every 5 seconds
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (watchKey.isValid()) {
            watchKey.pollEvents().stream()
                    .filter(event -> event.context().toString().equals(filePath.getFileName()
                            .toString()))
                    .forEach(event -> {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                String contents = new String(java.nio.file.Files.readAllBytes
                                        (filePath));
                                textArea.setText(contents);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });
                    });

            watchKey.reset();
        }
    }

//    public static void main(String[] args) {
//        Path filePath = Paths.get("/home/login/TextEditor/index.html");
//        new FileWatcher(filePath);
//    }
}




