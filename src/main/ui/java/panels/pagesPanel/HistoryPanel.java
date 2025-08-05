package src.main.ui.java.panels.pagesPanel;

import java.nio.file.StandardWatchEventKinds;
import javax.swing.border.EmptyBorder;
import java.nio.file.WatchService;
import java.nio.file.FileSystems;
import src.main.ui.java.UIUtils;
import java.nio.file.WatchEvent;
import javax.swing.JScrollPane;
import java.io.BufferedReader;
import java.nio.file.WatchKey;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class HistoryPanel extends JPanel
{
    private final Path HIST_FILE = Paths.get("configs", "History.conf");
    private JTextArea historyArea;
    
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
        UIUtils.styleTextArea(historyArea, Color.WHITE, 500, 5, false, null);
        historyArea.setBackground(new Color(60, 60, 60));
        historyArea.setForeground(Color.WHITE);
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setFont(new Font("Courier", Font.BOLD, 15));

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
        historyArea.setCaretPosition(0);
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
