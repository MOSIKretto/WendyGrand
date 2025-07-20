package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;

import main.Resources.UI.UIUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.*;
import java.awt.*;


public class HistoryPanel extends JPanel
{

    private JTextArea historyArea;
    private final Path HIST_FILE = Paths.get("Configs", "History.conf");

    public HistoryPanel()
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));

        initUI();
        loadHistory();
        startFileWatcher();
    }

    private void initUI()
    {
        historyArea = new JTextArea();
        historyArea.setBackground(new Color(60, 60, 60));
        historyArea.setForeground(Color.WHITE);
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(historyArea);

        UIUtils.styleScrollPane(scroll);

        add(scroll, BorderLayout.CENTER);
    }

    private void loadHistory()
    {
        try (BufferedReader reader = Files.newBufferedReader(HIST_FILE))
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) 
                content.append(line).append("\n");

            historyArea.setText(content.toString());
        }
        catch (IOException e) { historyArea.setText("Ошибка загрузки: " + e.getMessage());}
    }

    public void startFileWatcher() {
        Thread watcherThread = new Thread(() -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                Path dir = HIST_FILE.getParent();
                dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(HIST_FILE.getFileName().toString())) {
                            loadHistory();
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                System.err.println("Ошибка мониторинга файла: " + e.getMessage());
            }
        });
        watcherThread.setDaemon(true);
        watcherThread.start();
    }
}
