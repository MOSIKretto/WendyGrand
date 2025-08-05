package src.main.ui.java.panels.tabPanel.settings;

import src.main.ui.java.components.CustomButton;
import src.main.helpers.java.enums.ConstPaths;

import javax.swing.border.EmptyBorder;
import src.main.ui.java.UIUtils;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Font;

public class AppsSettingsPanel extends JPanel 
{
    private static final String CONFIG = ConstPaths.APPS.getConfPath();
    private final JTextField[] fields = new JTextField[PROGRAMS.length];

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
        settingsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        Dimension fieldSize = new Dimension(300, 30);
        Map<String, String> config = loadConfig();

        for (int i = 0; i < PROGRAMS.length; i++) 
        {
            JPanel row = new JPanel(new GridLayout(1,2));
            row.setBorder(new EmptyBorder(8, 8, 8, 8));
            row.setBackground(new Color(50, 50, 50));
            
            JLabel label = new JLabel(LABELS[i]);
            label.setForeground(new Color(225, 215, 198));
            label.setPreferredSize(new Dimension(150, 30));
            label.setFont(new Font("Courier", Font.BOLD, 15));

            fields[i] = new JTextField(config.getOrDefault(PROGRAMS[i], ""));
            fields[i].setBackground(new Color(70, 70, 70));
            fields[i].setFont(new Font("Courier", Font.BOLD, 16));
            fields[i].setForeground(Color.WHITE);
            fields[i].setPreferredSize(fieldSize);
            fields[i].setBorder(new EmptyBorder(0, 0, 0, 0));
            fields[i].setMaximumSize(fieldSize);
            
            row.add(label);
            row.add(fields[i]);
            settingsPanel.add(row);
        }
        
        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initSaveButton() 
    {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        CustomButton saveBtn = UIUtils.createSaveButton("Сохранить", getWidth(), getHeight());
        saveBtn.addActionListener(e -> saveConfig());

        buttonPanel.add(saveBtn, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private Map<String, String> loadConfig() 
    {
        Map<String, String> config = new HashMap<>();
        try 
        {
            List<String> lines = Files.readAllLines(Paths.get(CONFIG));
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
            List<String> lines = Files.readAllLines(Paths.get(CONFIG));
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
            
            Files.write(Paths.get(CONFIG), newLines);
            JOptionPane.showMessageDialog(this, "Настройки сохранены!");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}