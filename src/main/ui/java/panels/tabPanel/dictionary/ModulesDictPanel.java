package src.main.ui.java.panels.tabPanel.dictionary;

import src.main.ui.java.components.CustomButton;
import src.main.helpers.java.enums.ConstPaths;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import src.main.ui.java.UIUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public class ModulesDictPanel extends JPanel
{
    private static final String CONFIG = ConstPaths.DICTIONARY_MODULES.getConfPath();
    private JTextArea modulesDictionaryArea;
    
    public ModulesDictPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
        loadModulesDict();
        modulesDictionaryArea.setCaretPosition(0);
    }

    private void initUI() 
    {
        modulesDictionaryArea = new JTextArea();
        UIUtils.styleTextArea(modulesDictionaryArea, Color.WHITE, 500, 5, false, null);
        modulesDictionaryArea.setBackground(new Color(60, 60, 60));
        modulesDictionaryArea.setForeground(Color.WHITE);
        modulesDictionaryArea.setFont(new Font("Courier", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(modulesDictionaryArea);
        CustomButton saveBtn = UIUtils.createSaveButton("Сохранить словарь", getWidth(), getHeight());

        saveBtn.addActionListener(this::saveModulesDict);

        UIUtils.styleScrollPane(scroll);
        
        add(scroll, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void loadModulesDict() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG))) 
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG))) 
        {
            writer.write(modulesDictionaryArea.getText());
            JOptionPane.showMessageDialog(this, "Словарь сохранен");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}
