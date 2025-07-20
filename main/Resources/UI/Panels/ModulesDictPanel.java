package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import main.Resources.UI.UIUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public class ModulesDictPanel extends JPanel
{


    private JTextArea modulesDictionaryArea;
    private static final String MODULE_DICT_CONFIG = "../WendyGrand/Configs/DictionaryModules.conf";

    public ModulesDictPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
        loadModulesDict();
    }

    private void initUI() 
    {
        modulesDictionaryArea = new JTextArea();
        modulesDictionaryArea.setCaretPosition(0);
        UIUtils.styleTextArea(modulesDictionaryArea, Color.WHITE, 500, 5, false, null);
        modulesDictionaryArea.setBackground(new Color(60, 60, 60));
        modulesDictionaryArea.setForeground(Color.WHITE);
        modulesDictionaryArea.setFont(new Font("Courier", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(modulesDictionaryArea);
        JButton saveBtn = new JButton("Сохранить словарь");

        saveBtn.addActionListener(this::saveModulesDict);

        UIUtils.styleScrollPane(scroll);
        UIUtils.styleButton(saveBtn);
        
        add(scroll, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void loadModulesDict() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(MODULE_DICT_CONFIG))) 
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");

            modulesDictionaryArea.setText(content.toString());
        } 
        catch (IOException e) { modulesDictionaryArea.setText("Ошибка загрузки: " + e.getMessage()); }
    }

    private void saveModulesDict(ActionEvent e) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MODULE_DICT_CONFIG))) 
        {
            writer.write(modulesDictionaryArea.getText());
            JOptionPane.showMessageDialog(this, "Словарь сохранен");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}
