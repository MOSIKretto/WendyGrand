package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import main.Resources.UI.UIUtils;
import main.Resources.enums.ConstPaths;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;


public class DictionaryPanel extends JPanel 
{

    private JTextArea dictionaryArea;
    private static final String CONFIG = ConstPaths.DICTIONARY.getConfPath();

    public DictionaryPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
        loadDictionary();
    }

    private void initUI() 
    {
        dictionaryArea = new JTextArea();
        dictionaryArea.setCaretPosition(0);
        dictionaryArea.setBackground(new Color(60, 60, 60));
        dictionaryArea.setForeground(Color.WHITE);
        dictionaryArea.setFont(new Font("Courier", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(dictionaryArea);
        JButton saveBtn = new JButton("Сохранить словарь");

        saveBtn.addActionListener(this::saveDictionary);
        
        UIUtils.styleTextArea(dictionaryArea, Color.WHITE, 500, 5, false, null);
        UIUtils.styleScrollPane(scroll);
        UIUtils.styleButton(saveBtn);
        
        add(scroll, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void loadDictionary() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG))) 
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");

            dictionaryArea.setText(content.toString());
        } 
        catch (IOException e) { dictionaryArea.setText("Ошибка загрузки: " + e.getMessage()); }
    }

    private void saveDictionary(ActionEvent e) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG))) 
        {
            writer.write(dictionaryArea.getText());
            JOptionPane.showMessageDialog(this, "Словарь сохранен");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}