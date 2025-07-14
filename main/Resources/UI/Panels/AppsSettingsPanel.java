package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import main.Resources.UI.UIUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import java.awt.*;


public class AppsSettingsPanel extends JPanel 
{

    private static final String APPS_CONFIG = "../WendyGrand/Configs/Apps.conf";

    private static final String[] PROGRAMS = {
        "browser", "conductor", "terminal", "store", 
        "office", "messenger", "socialnetwork", "notes", 
        "codeeditor", "websearch"
    };
    
    private static final String[] LABELS = {
        "Браузер", "Проводник", "Терминал", "Менеджер пакетов", 
        "Офис", "Мессенджер", "Соц. сети", "Заметки", 
        "Редактор кода", "Поисковая система"
    };
    
    private final JTextField[] fields = new JTextField[PROGRAMS.length];

    public AppsSettingsPanel() 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));
        
        initSettingsPanel();
        initSaveButton();
    }

    private void initSettingsPanel() 
    {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(new Color(50, 50, 50));
        
        Dimension fieldSize = new Dimension(300, 30);
        Map<String, String> config = loadConfig();

        for (int i = 0; i < PROGRAMS.length; i++) 
        {
            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(new EmptyBorder(5, 0, 5, 0));
            row.setBackground(new Color(50, 50, 50));
            
            JLabel label = new JLabel(LABELS[i]);
            label.setForeground(new Color(225, 215, 198));
            label.setPreferredSize(new Dimension(150, 30));
            
            fields[i] = new JTextField(config.getOrDefault(PROGRAMS[i], ""));
            fields[i].setBackground(new Color(70, 70, 70));
            fields[i].setForeground(Color.WHITE);
            fields[i].setPreferredSize(fieldSize);
            fields[i].setMaximumSize(fieldSize);
            
            row.add(label, BorderLayout.WEST);
            row.add(fields[i], BorderLayout.CENTER);
            settingsPanel.add(row);
        }
        
        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initSaveButton() 
    {
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(e -> saveConfig());
        UIUtils.styleButton(saveBtn);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.add(saveBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Map<String, String> loadConfig() 
    {
        Map<String, String> config = new HashMap<>();
        try 
        {
            List<String> lines = Files.readAllLines(Paths.get(APPS_CONFIG));
            for (String line : lines) 
            {
                String trimmed = line.trim();
                if (!trimmed.isEmpty() && !trimmed.startsWith("#") && trimmed.contains("=")) 
                {
                    String[] parts = trimmed.split("=", 2);
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка загрузки конфига: " + ex.getMessage()); }
        return config;
    }

    private void saveConfig() 
    {
        try 
        {
            List<String> lines = Files.readAllLines(Paths.get(APPS_CONFIG));
            List<String> newLines = new ArrayList<>();
            Set<String> processed = new HashSet<>();
            
            for (String line : lines) 
            {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) 
                {
                    newLines.add(line);
                    continue;
                }
                
                String[] parts = line.split("=", 2);
                if (parts.length == 2) 
                {
                    String key = parts[0].trim();
                    if (Arrays.asList(PROGRAMS).contains(key)) 
                    {
                        int index = Arrays.asList(PROGRAMS).indexOf(key);
                        newLines.add(key + "=" + fields[index].getText().trim());
                        processed.add(key);
                        continue;
                    }
                }
                newLines.add(line);
            }
            
            for (int i = 0; i < PROGRAMS.length; i++) 
            {
                if (!processed.contains(PROGRAMS[i]))
                    newLines.add(PROGRAMS[i] + "=" + fields[i].getText().trim());
            }
            
            Files.write(Paths.get(APPS_CONFIG), newLines);
            JOptionPane.showMessageDialog(this, "Настройки сохранены!");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}