package src.main.UI.main.components;

import src.main.UI.main.utils.UIUtils;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public abstract class UniversalDictionaryPanel extends JPanel 
{
    protected JTextArea dictionaryArea;
    private final String configPath;
    private final String dictionaryName;

    public UniversalDictionaryPanel(String configPath, String dictionaryName) 
    {
        this.configPath = configPath;
        this.dictionaryName = dictionaryName;
        
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
        loadDictionary();
        dictionaryArea.setCaretPosition(0);
    }

    private void initUI() 
    {
        dictionaryArea = new JTextArea();
        UIUtils.styleTextArea(dictionaryArea, Color.WHITE, 500, 5, false, null);
        dictionaryArea.setBackground(new Color(60, 60, 60));
        dictionaryArea.setForeground(Color.WHITE);
        dictionaryArea.setFont(new Font("Courier", Font.BOLD, 15));
        dictionaryArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(dictionaryArea);
        CustomButton saveBtn = UIUtils.createSaveButton("Сохранить словарь", getWidth(), getHeight());

        saveBtn.addActionListener(this::saveDictionary);

        UIUtils.styleScrollPane(scroll);
        
        add(scroll, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configPath))) 
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");

            dictionaryArea.setText(content.toString());
        } catch (IOException e) {
            dictionaryArea.setText("Ошибка загрузки " + dictionaryName + ": " + e.getMessage());
        }
    }

    private void saveDictionary(ActionEvent e) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configPath))) 
        {
            writer.write(dictionaryArea.getText());
            JOptionPane.showMessageDialog(this, dictionaryName + " сохранен");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка сохранения " + dictionaryName + ": " + ex.getMessage());
        }
    }
}