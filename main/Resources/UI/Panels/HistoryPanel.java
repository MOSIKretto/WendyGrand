package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;

import main.Resources.UI.UIUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;


public class HistoryPanel extends JPanel
{

    private JTextArea historyArea;
    private static final String HIST_FILE = "../WendyGrand/Configs/History.conf";

    public HistoryPanel()
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));

        initUI();
        loadHistory();
    }

    private void initUI()
    {
        historyArea = new JTextArea();
        historyArea.setBackground(new Color(60, 60, 60));
        historyArea.setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(historyArea);

        UIUtils.styleScrollPane(scroll);

        add(scroll, BorderLayout.CENTER);
    }

    private void loadHistory()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIST_FILE)))
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) 
                content.append(line).append("\n");

            historyArea.setText(content.toString());
        }
        catch (IOException e) { historyArea.setText("Ошибка загрузки: " + e.getMessage());}
    }
}
